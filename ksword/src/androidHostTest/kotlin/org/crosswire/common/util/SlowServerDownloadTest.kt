package org.crosswire.common.util

import io.ktor.client.plugins.HttpRequestTimeoutException
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import okio.FileSystem
import okio.Path.Companion.toPath
import java.io.IOException
import java.io.OutputStream
import java.net.InetAddress
import java.net.ServerSocket
import kotlin.concurrent.thread
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.seconds

class SlowServerDownloadTest {

    // Plain ServerSocket, as com.sun.net.httpserver is not in android.jar
    private val server = ServerSocket(0, 0, InetAddress.getLoopbackAddress())
    private val base = "http://127.0.0.1:${server.localPort}"
    private val file = FileSystem.SYSTEM_TEMPORARY_DIRECTORY.resolve("slow-server-test.tar.gz".toPath())

    init {
        thread(isDaemon = true) {
            while (!server.isClosed) {
                val socket = runCatching { server.accept() }.getOrNull() ?: break
                thread(isDaemon = true) {
                    socket.use {
                        val reader = it.getInputStream().bufferedReader()
                        val path = reader.readLine().split(" ")[1]
                        reader.lineSequence().takeWhile { line -> line.isNotEmpty() }.count() // skip headers
                        respond(path, it.getOutputStream())
                    }
                }
            }
        }
    }

    private fun respond(path: String, out: OutputStream) = runCatching {
        if (path == "/trickle") {
            out.write("HTTP/1.1 200 OK\r\nContent-Length: 1000000\r\n\r\n".toByteArray())
            while (true) {
                out.write('x'.code)
                out.flush()
                Thread.sleep(500)
            }
        } else {
            val page = "<html>Service Unavailable</html>"
            out.write("HTTP/1.1 503 Service Unavailable\r\nContent-Length: ${page.length}\r\n\r\n$page".toByteArray())
        }
    }

    @AfterTest
    fun tearDown() {
        server.close()
        FileSystem.SYSTEM.delete(file)
    }

    @Test
    fun trickleGivesUpAtTimeout() = runBlocking {
        val result = withTimeoutOrNull(10.seconds) {
            runCatching { WebResource().download("$base/trickle", file, timeoutMillis = 2_000) }
        }
        assertNotNull(result, "download was still running after 10s")
        assertTrue(result.exceptionOrNull() is HttpRequestTimeoutException, "got ${result.exceptionOrNull()}")
    }

    @Test
    fun errorPageFails() = runBlocking {
        val e = assertFailsWith<IOException> { WebResource().download("$base/down", file) }
        assertEquals("$base/down returned 503 Service Unavailable", e.message)
    }
}
