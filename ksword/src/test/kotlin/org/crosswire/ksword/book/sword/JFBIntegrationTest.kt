package org.crosswire.ksword.book.sword

import kotlinx.coroutines.test.runTest
import okio.FileSystem
import okio.Path.Companion.toPath
import org.crosswire.common.util.IoUtil
import org.crosswire.common.util.WebResource
import org.crosswire.common.util.delete
import org.crosswire.ksword.book.sword.state.ZVerseBackendState
import org.crosswire.ksword.passage.KeyText
import org.crosswire.ksword.passage.Verse
import org.crosswire.ksword.passage.VerseRange
import org.crosswire.ksword.versification.BibleBook
import org.crosswire.ksword.versification.system.Versifications
import org.junit.AfterClass
import org.junit.Assert.assertTrue
import org.junit.BeforeClass
import org.junit.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class JFBIntegrationTest {

    private val webResource = WebResource()
    private val ioUtil = IoUtil()

    private lateinit var bookMetaData: SwordBookMetaData
    private lateinit var backendState: ZVerseBackendState
    private lateinit var backend: ZVerseBackend

    companion object {
        @JvmStatic
        @AfterClass
        @BeforeClass
        fun removeDownloads() {
            zipFilePath.delete()
            folderToUnzipInto.delete()
        }

        const val downloadUrl = "https://www.crosswire.org/ftpmirror/pub/sword/packages/rawzip/JFB.zip"
        val zipFilePath = FileSystem.SYSTEM_TEMPORARY_DIRECTORY.resolve("downloaded.zip".toPath())
        val folderToUnzipInto = FileSystem.SYSTEM_TEMPORARY_DIRECTORY.resolve("unzipto".toPath())
    }

    @Test
    fun findNextKey() {
        testDownloaded()
        val v11nName = "KJV" //getBookMetaData().getProperty(BookMetaData.KEY_VERSIFICATION);
        val v11n = Versifications.getVersification(v11nName)
        val result = backend.findNextKey(Verse(v11n, BibleBook.EZEK, 20, 1))
        assertEquals(Verse(v11n, BibleBook.EZEK, 20, 3), result)
    }

    @Test
    fun findNextKeyAEndOfChapter() {
        testDownloaded()
        val v11nName = "KJV" //getBookMetaData().getProperty(BookMetaData.KEY_VERSIFICATION);
        val v11n = Versifications.getVersification(v11nName)
        val result = backend.findNextKey(Verse(v11n, BibleBook.JOHN, 3, 35))
        println(result)
        assertEquals(Verse(v11n, BibleBook.JOHN, 4, 1), result)
    }

    @Test
    fun findNextKeyWhenAtEnd() {
        testDownloaded()
        val v11nName = "KJV" //getBookMetaData().getProperty(BookMetaData.KEY_VERSIFICATION);
        val v11n = Versifications.getVersification(v11nName)
        val result = backend.findNextKey(Verse(v11n, BibleBook.REV, 22, 21))
        println(result)
        assertEquals(null, result)
    }

    @Test
    fun findPrevKey() {
        testDownloaded()
        val v11nName = "KJV" //getBookMetaData().getProperty(BookMetaData.KEY_VERSIFICATION);
        val v11n = Versifications.getVersification(v11nName)
        val result = backend.findPreviousKey(Verse(v11n, BibleBook.EZEK, 20, 3))
        assertEquals(Verse(v11n, BibleBook.EZEK, 20, 1), result)
    }

    @Test
    fun findPrevKeyWhenAtStart() {
        testDownloaded()
        val v11nName = "KJV" //getBookMetaData().getProperty(BookMetaData.KEY_VERSIFICATION);
        val v11n = Versifications.getVersification(v11nName)
        val result = backend.findPreviousKey(Verse(v11n, BibleBook.GEN, 0, 0))
        println(result)
        assertEquals(null, result)
    }

    @Test
    fun readRawContent_missing_verse_is_empty() {
        testDownloaded()
        val v11nName = "KJV" //getBookMetaData().getProperty(BookMetaData.KEY_VERSIFICATION);
        val v11n = Versifications.getVersification(v11nName)
        val result = backend.readRawContent(backendState, Verse(v11n, BibleBook.LUKE, 11, 19))

        assertEquals("", result)
    }

    @Test
    fun readRawContent_readFirstVerse() {
        testDownloaded()
        val v11nName = "KJV" //getBookMetaData().getProperty(BookMetaData.KEY_VERSIFICATION);
        val v11n = Versifications.getVersification(v11nName)
        val result = backend.readRawContent(backendState, Verse(v11n, BibleBook.GEN, 1, 1))
        assertContains(
            result,
            "the whole universe was produced by the creative power of God"
        )
    }


    @Test
    fun readRawContent_readLastVerseInChapter() {
        testDownloaded()
        val v11nName = "KJV" //getBookMetaData().getProperty(BookMetaData.KEY_VERSIFICATION);
        val v11n = Versifications.getVersification(v11nName)
        val result = backend.readRawContent(backendState, Verse(v11n, BibleBook.GEN, 1, 28))
        assertContains(
            result,
            "Great and manifold are thy works"
        )
    }

    @Test
    fun readRawContent_readNtVerse() {
        testDownloaded()
        val v11nName = "KJV" //getBookMetaData().getProperty(BookMetaData.KEY_VERSIFICATION);
        val v11n = Versifications.getVersification(v11nName)
        val result = backend.readRawContent(backendState, Verse(v11n, BibleBook.JOHN, 1, 1))
        println(result)
        assertContains(
            result,
            "The author of the Fourth Gospel was the younger of the two sons of Zebedee"
        )
    }

    @Test
    fun readToOsis_readChapter() {
        testDownloaded()
        val v11nName = "KJV" //getBookMetaData().getProperty(BookMetaData.KEY_VERSIFICATION);
        val v11n = Versifications.getVersification(v11nName)
        val start = Verse(v11n, BibleBook.GEN, 1, 1)
        val end = Verse(v11n, BibleBook.GEN, 1, 31)
        val result: List<KeyText> = backend.readToOsis(VerseRange(v11n, start, end))
        assertContains(
            result[19].text,
            "the waters and the air were suddenly made to swarm"
        )
    }

    @Test
    fun testDownloaded() = runTest {
        if (!FileSystem.SYSTEM.exists(folderToUnzipInto.resolve("mods.d/jfb.conf"))) {
            println("Temp dir: " + FileSystem.SYSTEM_TEMPORARY_DIRECTORY)
            zipFilePath.delete()

            val success = webResource.download(downloadUrl, zipFilePath)
            assertTrue(success)
            assertTrue(FileSystem.SYSTEM.exists(zipFilePath))

            ioUtil.unpackZip(
                zipFilePath,
                folderToUnzipInto,
                true,
                SwordConstants.DIR_CONF,
                SwordConstants.DIR_DATA
            )
            assertTrue(FileSystem.SYSTEM.exists(folderToUnzipInto.resolve("mods.d/jfb.conf")))
        }

        bookMetaData = SwordBookMetaData.createFromFile(folderToUnzipInto.resolve("mods.d/jfb.conf"), folderToUnzipInto)
        backendState = ZVerseBackendState(bookMetaData, BlockType.BLOCK_BOOK)
        backend = ZVerseBackend(bookMetaData, BlockType.BLOCK_BOOK, 4)
    }
}