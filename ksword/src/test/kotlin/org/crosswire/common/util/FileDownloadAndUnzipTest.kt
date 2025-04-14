package org.crosswire.common.util

import kotlinx.coroutines.test.runTest
import okio.FileSystem
import okio.Path.Companion.toPath
import org.crosswire.ksword.book.sword.SwordConstants
import org.junit.After
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class FileDownloadAndUnzipTest {

    private val webResource = WebResource()
    private val ioUtil = IoUtil()

    @After
    @Before
    fun tidyUp() {
        zipFilePath.delete()
        folderToUnzipInto.delete()
        tarFilePath.delete()
    }

    @Test
    fun canDownloadTarGzipFile() = runTest {
        val success = webResource.download(tarGzdownloadUrl, tarFilePath)
        assertTrue(success)
        assertTrue(FileSystem.SYSTEM.exists(tarFilePath))
    }

    @Test
    fun canDownloadAndUnzipFile() = runTest {
        val success = webResource.download(downloadUrl, zipFilePath)
        assertTrue(success)
        assertTrue(FileSystem.SYSTEM.exists(zipFilePath))

        ioUtil.unpackZip(zipFilePath, folderToUnzipInto, true, SwordConstants.DIR_CONF, SwordConstants.DIR_DATA)
        assertTrue(FileSystem.SYSTEM.exists(folderToUnzipInto.resolve("mods.d/bsb.conf")))
    }

    companion object {
        const val downloadUrl = "https://www.crosswire.org/ftpmirror/pub/sword/packages/rawzip/BSB.zip"
        val zipFilePath = FileSystem.SYSTEM_TEMPORARY_DIRECTORY.resolve("downloaded.zip".toPath())
        val folderToUnzipInto = FileSystem.SYSTEM_TEMPORARY_DIRECTORY.resolve("unzipto".toPath())

        const val tarGzdownloadUrl = "https://www.crosswire.org/ftpmirror/pub/sword/raw/mods.d.tar.gz"
        val tarFilePath = FileSystem.SYSTEM_TEMPORARY_DIRECTORY.resolve("downloaded.tar".toPath())
    }
}