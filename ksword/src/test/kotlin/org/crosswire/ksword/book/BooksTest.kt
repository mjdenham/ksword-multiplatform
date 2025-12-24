package org.crosswire.ksword.book

import kotlinx.coroutines.test.runTest
import okio.Path.Companion.toPath
import org.crosswire.ksword.book.sword.SwordBookPath
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class BooksTest {

    @Before
    fun setUp() {
        SwordBookPath.swordBookPath = "../testFiles".toPath()
        Books.refresh()
    }

    @Test
    fun getBooks() = runTest {
        val books = Books.getBooks()
        assertEquals(1, books.size)
        assertEquals("BSBTEST", books[0].initials)
    }

    @Test
    fun getBookByInitials() = runTest {
        val book = Books.getBook("BSBTEST")
        assertNotNull(book)
        assertEquals("BSBTEST", book!!.initials)
    }

    @Test
    fun getBookByName() = runTest {
        val book = Books.getBook("Berean Standard Bible")
        assertNotNull(book)
        assertEquals("BSBTEST", book!!.initials)
    }

    @Test
    fun refresh() = runTest {
        val books = Books.getBooks()
        assertEquals(1, books.size)
        Books.refresh()
        assertEquals(1, books.size)
    }
}