package org.crosswire.ksword.book.sword

import kotlinx.coroutines.test.runTest
import okio.FileSystem
import okio.Path.Companion.toPath
import org.crosswire.ksword.book.Book
import org.crosswire.ksword.book.Books
import org.crosswire.ksword.book.TestBooks
import org.crosswire.ksword.book.install.sword.SwordInstallerFactory
import org.crosswire.ksword.passage.Verse
import org.crosswire.ksword.versification.BibleBook
import org.junit.AfterClass
import org.junit.BeforeClass
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

// Temporary: verifies LZSS against real CrossWire modules; delete once LZSS is proven.
class LzssIntegrationTest {

    companion object {
        private val dir = FileSystem.SYSTEM_TEMPORARY_DIRECTORY.resolve("LzssIntegrationTest".toPath())

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

    private suspend fun install(initials: String): Book {
        val installer = SwordInstallerFactory().crosswireInstaller
        val listed = installer.getBooks().find { it.initials.equals(initials, ignoreCase = true) }
        assertNotNull(listed, "$initials should be listed as supported")
        Books.getBook(listed.initials) ?: installer.install(listed.initials)
        val book = assertNotNull(Books.getBook(listed.initials))
        assertEquals("LZSS", book.bookMetaData.getProperty(SwordBookMetaData.KEY_COMPRESS_TYPE)?.uppercase())
        return book
    }

    private fun verse(book: Book, bibleBook: BibleBook, chapter: Int, verse: Int): String {
        val text = book.getRawText(Verse(book.bookMetaData.versification, bibleBook, chapter, verse))
        println("${book.initials} $bibleBook $chapter:$verse: $text")
        return text
    }

    @Test
    fun nestle1904() = runTest {
        val book = install("Nestle1904")
        assertContains(verse(book, BibleBook.JOHN, 1, 1), "ὁ Λόγος")
        assertContains(verse(book, BibleBook.REV, 22, 21), "Ἰησοῦ")
    }

    @Test
    fun sparvg() = runTest {
        val book = install("SpaRVG")
        assertContains(verse(book, BibleBook.GEN, 1, 1), "principio")
        assertContains(verse(book, BibleBook.JOHN, 3, 16), "mundo")
        assertContains(verse(book, BibleBook.MAL, 4, 6), "padres")
    }

    @Test
    fun spasciont() = runTest {
        val book = install("SpaScioNT")
        assertContains(verse(book, BibleBook.MATT, 1, 1), "Jesu-Christo")
        assertContains(verse(book, BibleBook.JOHN, 3, 16), "mundo")
    }

    @Test
    fun porlitcal2024() = runTest {
        val book = install("PorLitCal2024") as SwordDictionary
        val keys = book.getGlobalKeyList()
        println("PorLitCal2024 has ${keys.size} keys")
        assertTrue(keys.size > 300)
        var nonEmpty = 0
        for (key in keys) {
            val text = book.getRawText(key)
            if (text.isNotBlank()) nonEmpty++
            assertTrue(text.none { it == '�' }, "Entry ${key.getName()} has decode errors: $text")
        }
        println("first: ${keys.first().getName()} -> ${book.getRawText(keys.first())}")
        println("last: ${keys.last().getName()} -> ${book.getRawText(keys.last())}")
        assertEquals(keys.size, nonEmpty)
    }
}
