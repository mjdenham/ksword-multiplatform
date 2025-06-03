package org.crosswire.ksword.book.sword

import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import okio.FileSystem
import okio.Path.Companion.toPath
import org.crosswire.ksword.book.BookMetaData
import org.crosswire.ksword.book.Books
import org.crosswire.ksword.book.install.sword.SwordInstallerFactory
import org.crosswire.ksword.book.sword.state.ZVerseBackendState
import org.crosswire.ksword.passage.KeyText
import org.crosswire.ksword.passage.Verse
import org.crosswire.ksword.passage.VerseRange
import org.crosswire.ksword.versification.BibleBook
import org.crosswire.ksword.versification.Versification
import org.crosswire.ksword.versification.system.Versifications
import org.junit.AfterClass
import org.junit.Assert.assertTrue
import org.junit.BeforeClass
import org.junit.Test
import kotlin.test.assertContains

class KjvaTest {
    private lateinit var bookMetaData: SwordBookMetaData
    private lateinit var backendState: ZVerseBackendState
    private lateinit var backend: ZVerseBackend

    companion object {
        private val dir = FileSystem.SYSTEM_TEMPORARY_DIRECTORY.resolve("KjvaTest".toPath())

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
        val v11n = getVersification()
        val result = backend.readRawContent(backendState, Verse(v11n, BibleBook.GEN, 1, 1))
        listOf("In the beginning God created the heaven and the earth".split(" ").forEach { word: String ->
            assertContains(result, word)
        })
    }

    @Test
    fun readRawContent_readVerseFromKJVASpecificBook() {
        testDownloaded()
        val v11n = getVersification()
        val result = backend.readRawContent(backendState, Verse(v11n, BibleBook.TOB, 1, 1))
        listOf("The book of the words of Tobit son of".split(" ").forEach { word: String ->
            assertContains(result, word)
        })
    }

    @Test
    fun readRawContent_readNtVerse() {
        testDownloaded()
        val v11n = getVersification()
        val result = backend.readRawContent(backendState, Verse(v11n, BibleBook.JOHN, 1, 1))
        listOf("In the beginning was the Word and the Word was with God and the Word was God".split(" ").forEach { word: String ->
            assertContains(result, word)
        })
    }

    @Test
    fun readToOsis_readChapter() {
        testDownloaded()
        val v11n = getVersification()
        val start = Verse(v11n, BibleBook.GEN, 1, 1)
        val end = Verse(v11n, BibleBook.GEN, 1, 31)
        val result: List<KeyText> = backend.readToOsis(VerseRange(v11n, start, end))
        listOf("In the beginning God created the heaven and the earth".split(" ").forEach { word: String ->
            assertContains(result.first().text, word)
        })
        listOf("And God saw that he had made and it was very good".split(" ").forEach { word: String ->
            assertContains(result.last().text, word)
        })
    }

    private fun getVersification(): Versification {
        val v11nName = bookMetaData.getProperty(BookMetaData.KEY_VERSIFICATION) ?: Versifications.DEFAULT_V11N;
        return Versifications.instance().getVersification(v11nName)
    }

    @Test
    fun testDownloaded() = runTest {
        var book = Books.getBook("KJVA")
        if (book == null) {
            SwordInstallerFactory().crosswireInstaller.install("KJVA")
            delay(5000)
            book = Books.getBook("KJVA")
            assertTrue(book != null)
        }

        bookMetaData = book!!.bookMetaData as SwordBookMetaData
        backendState = ZVerseBackendState(bookMetaData, BlockType.BLOCK_BOOK)
        backend = ZVerseBackend(bookMetaData, BlockType.BLOCK_BOOK, 2)
    }
}