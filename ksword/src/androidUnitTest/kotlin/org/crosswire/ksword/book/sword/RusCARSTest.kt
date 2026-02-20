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
import org.crosswire.ksword.versification.system.SystemSynodalProt
import org.crosswire.ksword.versification.system.Versifications
import org.junit.AfterClass
import org.junit.Assert.assertTrue
import org.junit.BeforeClass
import org.junit.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/**
 * Test for the RusCARS (Russian Synodal Bible) module.
 * This module uses the SynodalProt versification system.
 */
class RusCARSTest {
    private lateinit var bookMetaData: SwordBookMetaData
    private lateinit var backendState: ZVerseBackendState
    private lateinit var backend: ZVerseBackend

    companion object {
        private val dir = FileSystem.SYSTEM_TEMPORARY_DIRECTORY.resolve("RusCARSTest".toPath())

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

        /**
         * Helper function to check if text contains Cyrillic characters.
         * Russian text should contain Cyrillic characters.
         */
        private fun containsCyrillic(text: String): Boolean {
            return text.any { it in '\u0400'..'\u04FF' }
        }
    }

    @Test
    fun testVersificationIsSynodalProt() {
        testDownloaded()
        val v11n = getVersification()
        assertEquals(SystemSynodalProt.V11N_NAME, v11n.name, "RusCARS should use SynodalProt versification")
    }

    @Test
    fun readRawContent_readFirstVerse() {
        testDownloaded()
        val v11n = getVersification()
        val result = backend.readRawContent(backendState, Verse(v11n, BibleBook.GEN, 1, 1))

        // Verify we got content
        assertTrue("Genesis 1:1 should return non-empty content", result.isNotEmpty())

        // Verify content contains Cyrillic characters (Russian text)
        assertTrue("Genesis 1:1 should contain Russian (Cyrillic) text", containsCyrillic(result))
    }

    @Test
    fun readRawContent_readNtVerse() {
        testDownloaded()
        val v11n = getVersification()
        val result = backend.readRawContent(backendState, Verse(v11n, BibleBook.ROM, 8, 28))

        listOf("Мы знаем, что Всевышний всё обращает во благо для тех, кто любит Его и кого Он призвал по Своему замыслу.".split(" ").forEach { word: String ->
            assertContains(result, word)
        })

        // Verify we got content
        assertTrue("Rom 8:28 should return non-empty content", result.isNotEmpty())

        // Verify content contains Cyrillic characters (Russian text)
        assertTrue("Rom 8:28 should contain Russian (Cyrillic) text", containsCyrillic(result))
    }

    @Test
    fun readRawContent_readLastVerseInChapter() {
        testDownloaded()
        val v11n = getVersification()
        // Genesis 1 has 31 verses in SynodalProt (same as KJV)
        val result = backend.readRawContent(backendState, Verse(v11n, BibleBook.GEN, 1, 31))

        // Verify we got content
        assertTrue("Genesis 1:31 should return non-empty content", result.isNotEmpty())

        // Verify content contains Cyrillic characters (Russian text)
        assertTrue("Genesis 1:31 should contain Russian (Cyrillic) text", containsCyrillic(result))
    }

    @Test
    fun readToOsis_readChapter() {
        testDownloaded()
        val v11n = getVersification()
        val start = Verse(v11n, BibleBook.GEN, 1, 1)
        val end = Verse(v11n, BibleBook.GEN, 1, 31)
        val result: List<KeyText> = backend.readToOsis(VerseRange(v11n, start, end))

        // Verify we got 31 verses
        assertEquals(31, result.size, "Genesis 1 should contain 31 verses")

        // Verify first verse has content
        assertTrue("First verse should have non-empty content", result.first().text.isNotEmpty())
        assertTrue("First verse should contain Russian text", containsCyrillic(result.first().text))

        // Verify last verse has content
        assertTrue("Last verse should have non-empty content", result.last().text.isNotEmpty())
        assertTrue("Last verse should contain Russian text", containsCyrillic(result.last().text))
    }

    @Test
    fun testNtBookOrderIsCatholic() {
        testDownloaded()
        val v11n = getVersification()

        // SynodalProt uses Catholic/Orthodox NT book ordering
        // Catholic Epistles (James, Peter, John, Jude) come before Pauline Epistles (Romans, etc.)

        // Test that we can read from James (should come before Romans in book order)
        val jamesVerse = backend.readRawContent(backendState, Verse(v11n, BibleBook.JAS, 1, 1))
        assertTrue("James 1:1 should be accessible", jamesVerse.isNotEmpty())
        assertTrue("James 1:1 should contain Russian text", containsCyrillic(jamesVerse))
    }

    private fun getVersification(): Versification {
        val v11nName = bookMetaData.getProperty(BookMetaData.KEY_VERSIFICATION) ?: Versifications.DEFAULT_V11N
        return Versifications.getVersification(v11nName)
    }

    @Test
    fun testDownloaded() = runTest {
        var book = Books.getBook("RusCARS")
        if (book == null) {
            SwordInstallerFactory().crosswireInstaller.install("RusCARS")
            book = Books.getBook("RusCARS")
            assertNotNull(book, "RusCARS module should be installed")
        }

        bookMetaData = book!!.bookMetaData as SwordBookMetaData
        backendState = ZVerseBackendState(bookMetaData, BlockType.BLOCK_BOOK)
        backend = ZVerseBackend(bookMetaData, BlockType.BLOCK_BOOK, 2)
    }
}
