package org.crosswire.ksword.book.sword

import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import okio.FileSystem
import okio.Path.Companion.toPath
import org.crosswire.ksword.book.Books
import org.crosswire.ksword.book.install.sword.SwordInstallerFactory
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
import kotlin.test.assertNotNull
import kotlin.time.measureTime

class KingCommentsIntegrationTest {

    private lateinit var bookMetaData: SwordBookMetaData
    private lateinit var backendState: ZVerseBackendState
    private lateinit var backend: ZVerseBackend

    companion object {
        private const val MODULE_NAME = "KingComments"
        private val dir = FileSystem.SYSTEM_TEMPORARY_DIRECTORY.resolve("KingCommentsIntegrationTest".toPath())

        @JvmStatic
        @BeforeClass
        fun setUp() {
            SwordBookPath.swordBookPath = dir
            FileSystem.SYSTEM.createDirectories(SwordBookPath.swordBookPath)
            Books.refresh()
        }

        @JvmStatic
        @AfterClass
        fun tearDown() {
            FileSystem.SYSTEM.deleteRecursively(SwordBookPath.swordBookPath)
            SwordBookPath.swordBookPath = "../testFiles".toPath()
        }
    }

    @Test
    fun readRawContent_readFirstVerse() {
        testDownloaded()
        val v11nName = "KJV" //getBookMetaData().getProperty(BookMetaData.KEY_VERSIFICATION);
        val v11n = Versifications.getVersification(v11nName)
        val result = backend.readRawContent(backendState, Verse(v11n, BibleBook.GEN, 1, 1))
        println(result)
        assertContains(
            result,
            "Reading and studying God's Word gave me a deep desire to preach and share God’s Word both in teaching the believers to the edification of the church of God, with an especial care for young believers"
        )
    }


    @Test
    fun readRawContent_readLastVerseInChapter() {
        testDownloaded()
        val v11nName = "KJV" //getBookMetaData().getProperty(BookMetaData.KEY_VERSIFICATION);
        val v11n = Versifications.getVersification(v11nName)
        val result = backend.readRawContent(backendState, Verse(v11n, BibleBook.GEN, 1, 31))
        assertContains(
            result,
            "The sixth day is a unique day."
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
            "The purpose of this Gospel is to look at the Lord Jesus as God the Son."
        )
    }

    @Test
    fun readToOsis_readChapter() {
        testDownloaded()
        val v11nName = "KJV" //getBookMetaData().getProperty(BookMetaData.KEY_VERSIFICATION);
        val v11n = Versifications.getVersification(v11nName)
        val start = Verse(v11n, BibleBook.GEN, 1, 1)
        val end = Verse(v11n, BibleBook.GEN, 1, 31)
        val result: List<KeyText>
        val time = measureTime {
            result = backend.readToOsis(VerseRange(v11n, start, end))
        }
        println("Time taken: ${time.inWholeMilliseconds} ms")
        assertContains(
            result[1].text,
            "Then we see that God continues to work. His Spirit"
        )
        assertContains(
            result.last().text,
            "The sixth day is a unique day."
        )
    }

    fun testDownloaded() = runTest {
        var book = Books.getBook(MODULE_NAME)
        if (book == null) {
            SwordInstallerFactory().crosswireInstaller.install(MODULE_NAME)
            book = Books.getBook(MODULE_NAME)
            assertNotNull(book, "$MODULE_NAME module should be installed")
        }

        bookMetaData = book!!.bookMetaData as SwordBookMetaData
        backendState = ZVerseBackendState(bookMetaData, BlockType.BLOCK_BOOK)
        backend = ZVerseBackend(bookMetaData, BlockType.BLOCK_BOOK, 2)
    }
}