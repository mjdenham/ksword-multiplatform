package org.crosswire.common.util

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.request.prepareGet
import io.ktor.client.utils.DEFAULT_HTTP_BUFFER_SIZE
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.readRemaining
import kotlinx.io.readByteArray
import okio.FileSystem
import okio.Path
import okio.SYSTEM
import okio.buffer
import okio.use

class WebResource() {
    private val client = HttpClient {
        install(HttpTimeout) {
            connectTimeoutMillis = 30_000L  // 30s to establish connection
            socketTimeoutMillis = 60_000L   // 60s of inactivity before giving up
            // No requestTimeoutMillis — large books can take many minutes to download
        }
    }

    suspend fun download(url: String, filePath: Path): Boolean {
        println("Downloading $url to $filePath")
        var totalBytes = 0
        filePath.createParentDirectories()

        FileSystem.SYSTEM.sink(filePath).buffer().use { sink ->
            client.prepareGet(url).execute { httpResponse ->
                val channel: ByteReadChannel = httpResponse.body()
                while (!channel.isClosedForRead) {
                    val packet = channel.readRemaining(DEFAULT_HTTP_BUFFER_SIZE.toLong())
                    while (!packet.exhausted()) {
                        val bytes = packet.readByteArray()
                        sink.write(bytes)
                        totalBytes += bytes.size
                    }
                }
            }
        }
        println("Downloaded ${totalBytes / 1024} KB")
        return totalBytes > 0
    }
}