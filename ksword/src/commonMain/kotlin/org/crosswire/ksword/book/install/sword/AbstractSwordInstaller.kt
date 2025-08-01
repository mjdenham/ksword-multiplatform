package org.crosswire.ksword.book.install.sword

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.URLProtocol
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import okio.FileSystem
import okio.Path
import okio.SYSTEM
import org.crosswire.common.util.IoUtil
import org.crosswire.common.util.Log
import org.crosswire.common.util.WebResource
import org.crosswire.ksword.book.Book
import org.crosswire.ksword.book.BookMetaData
import org.crosswire.ksword.book.Books
import org.crosswire.ksword.book.install.Installer
import org.crosswire.ksword.book.sword.NullBackend
import org.crosswire.ksword.book.sword.SwordBook
import org.crosswire.ksword.book.sword.SwordBookMetaData
import org.crosswire.ksword.book.sword.SwordBookPath
import org.crosswire.ksword.book.sword.SwordConstants
import org.martin.ktar.TarGzExpander

abstract class AbstractSwordInstaller(val installerUrls: InstallerUrls) : Installer {

    companion object {
        /**
         * The sword index file
         */
        val FILE_LIST_GZ: String = "mods.d.tar.gz"

        private const val moduleNameKey = "{NAME}"
        private val tempDownloadPath = FileSystem.SYSTEM_TEMPORARY_DIRECTORY
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

    suspend fun install(book: Book) {
        install(book.initials)
    }

    suspend fun install(bookInitials: String) = withContext(Dispatchers.IO) {
        val url = getDownloadUrl(bookInitials)
        val zipFile = getZipFile(bookInitials)
        if (WebResource().download(url, zipFile)) {
            IoUtil().unpackZip(
                zipFile,
                SwordBookPath.swordBookPath,
                true,
                SwordConstants.DIR_CONF,
                SwordConstants.DIR_DATA
            )

            Books.refresh()
        }
    }

    private fun getDownloadUrl(bookInitials: String) = "https://${installerUrls.host}${installerUrls.packageDir}/$bookInitials.zip"
    private fun getZipFile(bookInitials: String) = tempDownloadPath.resolve("$bookInitials.zip")

    override suspend fun loadBookList(): Unit = withContext(Dispatchers.IO){
        if (!loaded) {
            val catalogFile = getCatalogDirectory().resolve(FILE_LIST_GZ)
            if (!FileSystem.SYSTEM.exists(catalogFile)) {
                refreshBookListFromServer()
            }

            val nullBackend = NullBackend()

            TarGzExpander().handleTarGzContent(catalogFile) { name, content ->
                val sbmd = SwordBookMetaData.createFromSource(content)

                if (sbmd.isSupported) {
                    val book: Book = SwordBook(sbmd, nullBackend)
                    entries[sbmd.initials + sbmd.name] = book
                } else {
                    Log.d("Skipping unsupported book: ${sbmd.initials} ${sbmd.getProperty(SwordBookMetaData.KEY_MOD_DRV)} ${sbmd.getProperty(BookMetaData.KEY_VERSIFICATION)} ${sbmd.getProperty(SwordBookMetaData.KEY_SOURCE_TYPE)}")
                }
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