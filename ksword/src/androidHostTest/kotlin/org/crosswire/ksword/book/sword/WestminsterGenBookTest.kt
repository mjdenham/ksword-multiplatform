package org.crosswire.ksword.book.sword

import kotlinx.coroutines.test.runTest
import okio.FileSystem
import okio.Path.Companion.toPath
import org.crosswire.ksword.book.BookCategory
import org.crosswire.ksword.book.Books
import org.crosswire.ksword.book.TestBooks
import org.crosswire.ksword.book.install.sword.SwordInstallerFactory
import org.crosswire.ksword.passage.DefaultLeafKeyList
import org.crosswire.ksword.passage.NoSuchKeyException
import org.crosswire.ksword.passage.TreeKey
import org.junit.AfterClass
import org.junit.BeforeClass
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

private const val WESTMINSTER = "Westminster"

/**
 * Integration test for RawGenBook using the Westminster Standards: "WCF" is a heading node with no text above
 * its chapters, and the tree is two levels deep. Expected values were taken from SWORD's mod2imp output.
 */
class WestminsterGenBookTest {

    companion object {
        private val dir = FileSystem.SYSTEM_TEMPORARY_DIRECTORY.resolve("WestminsterGenBookTest".toPath())

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
            TestBooks.reset()
        }
    }

    private suspend fun loadBook(): SwordGenBook {
        var book = Books.getBook(WESTMINSTER)
        if (book == null) {
            SwordInstallerFactory().crosswireInstaller.install(WESTMINSTER)
            book = Books.getBook(WESTMINSTER)
            assertNotNull(book, "Westminster should install from the CrossWire repository")
        }
        assertTrue(book is SwordGenBook, "Westminster should be a SwordGenBook")
        return book
    }

    @Test
    fun keysAreInReadingOrderWithDepth() = runTest {
        val book = loadBook()
        assertEquals(BookCategory.GENERAL_BOOK, book.bookMetaData.bookCategory)
        val keys = book.getGlobalKeyList()
        assertEquals(40, keys.size)
        assertEquals(listOf("WCF", "WCF/Chapter I", "WCF/Chapter II"), keys.take(3).map { it.toString() })
        assertEquals("WLC/Q150-196", keys.last().toString())
        assertEquals(listOf(0, 1), keys.take(2).map { it.depth })
        assertEquals("Chapter I", keys[1].getName())
        assertEquals(keys[0], keys[1].parent)
    }

    @Test
    fun pathsRoundTrip() = runTest {
        val book = loadBook()
        for (key in book.getGlobalKeyList()) assertEquals(key, book.getKey(key.toString()))
        assertEquals(book.getKey("WCF/Chapter I"), book.getKey("/WCF/Chapter I"))
    }

    @Test
    fun headingHasNoTextAndIsSkipped() = runTest {
        val book = loadBook()
        val heading = book.getKey("WCF")
        assertFalse(book.contains(heading))
        assertEquals("", book.getRawText(heading))
        val first = book.getKey("WCF/Chapter I")
        assertEquals(first, book.getNextKey(heading))
        assertNull(book.getPreviousKey(first))
        assertNull(book.getNextKey(book.getKey("WLC/Q150-196")))
        assertEquals(book.getKey("WCF/Chapter II"), book.getNextKey(first))
    }

    @Test
    fun readsNodeText() = runTest {
        val book = loadBook()
        val key = book.getKey("WCF/Chapter I")
        assertTrue(book.getRawText(key).startsWith("<title>Of the Holy Scripture</title>"))
        assertEquals(listOf(key), book.readToOsis(key).map { it.key })
        assertTrue(book.getRawText(book.getKey("WLC/Q150-196")).contains("Q. 151. What are those aggravations"))
    }

    @Test
    fun missingPathIsNotFound() = runTest {
        val book = loadBook()
        assertFailsWith<NoSuchKeyException> { book.getKey("WCF/Chapter L") }
        assertFailsWith<NoSuchKeyException> { book.getRawText(DefaultLeafKeyList("WCF/Chapter L")) }
        assertTrue((book.getValidKey("WCF/Chapter L") !is TreeKey))
    }

    @Test
    fun imagePathIsInModuleDirectory() = runTest {
        val book = loadBook()
        val dataDir = SwordUtil.getExpandedDataPath(book.bookMetaData).parent!!
        assertEquals(dataDir.resolve("images/cover.jpg", normalize = true), book.resolveImagePath("./images/cover.jpg"))
        assertEquals(dataDir.resolve("images/cover.jpg", normalize = true), book.resolveImagePath("/images/cover.jpg"))
        assertNull(book.resolveImagePath("../../../../../etc/passwd"))
    }
}
