package org.crosswire.ksword.book.install.sword

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.URLProtocol
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import okio.FileSystem
import okio.Path
import okio.SYSTEM
import org.crosswire.common.util.Log
import org.crosswire.common.util.WebResource
import org.crosswire.ksword.book.Book
import org.crosswire.ksword.book.install.Installer
import org.crosswire.ksword.book.sword.NullBackend
import org.crosswire.ksword.book.sword.SwordBook
import org.crosswire.ksword.book.sword.SwordBookMetaData
import org.crosswire.ksword.book.sword.SwordBookPath
import org.martin.ktar.TarGzExpander

abstract class AbstractSwordInstaller(val installerUrls: InstallerUrls) : Installer {

    companion object {
        /**
         * The sword index file
         */
        val FILE_LIST_GZ: String = "mods.d.tar.gz"

        /**
         * The sword conf directory
         */
        val CONF_DIR: String = "mods.d"
    }

    /**
     * A map of the books in this download area
     */
    private var entries: MutableMap<String, Book> = mutableMapOf()

    /**
     * Do we need to reload the index file
     */
    private var loaded: Boolean = false


    override suspend fun getBooks(): List<Book> {
        try {
            if (!loaded) {
                loadBookList()
            }

            return entries.values.toList()
        } catch (ex: Exception) {
            Log.e("Failed to reload cached index file", ex)
            return listOf()
        }
    }

    override suspend fun loadBookList(): Unit = withContext(Dispatchers.IO){
        if (!loaded) {
            val catalogFile = getCatalogDirectory().resolve(FILE_LIST_GZ)
            if (!FileSystem.SYSTEM.exists(catalogFile)) {
                refreshBookListFromServer()
            }

            val nullBackend = NullBackend()

            TarGzExpander().handleTarGzContent(catalogFile) { name, content ->
                val sbmd = SwordBookMetaData.createFromSource(content)

                val book: Book = SwordBook(sbmd, nullBackend)
                entries[sbmd.initials + sbmd.name] = book
            }

            loaded = true
        }
    }

    override suspend fun refreshBookListFromServer(): Unit = withContext(Dispatchers.IO){
        val tarGzdownloadUrl = getRemoteCatalogFile() //"https://www.crosswire.org/ftpmirror/pub/sword/raw/mods.d.tar.gz"
        val catalogFile = getCatalogDirectory().resolve(FILE_LIST_GZ)
        WebResource().download(tarGzdownloadUrl, catalogFile)

        entries.clear()
        loaded = false
    }

    private fun getRemoteCatalogFile(): String = HttpRequestBuilder().let { builder ->
        builder.url {
            protocol = URLProtocol.HTTPS
            host = installerUrls.host
            encodedPathSegments = listOf(installerUrls.catalogDir, FILE_LIST_GZ)
        }
        builder.build().url.toString()
    }

    private fun getCatalogDirectory(): Path = SwordBookPath.catalogDir.resolve(installerUrls.name)
}