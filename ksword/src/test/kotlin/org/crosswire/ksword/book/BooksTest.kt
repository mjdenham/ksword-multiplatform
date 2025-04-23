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
    }

    @Test
    fun getBooks() = runTest {
        val books = Books.getBooks()
        assertEquals(1, books.size)
        assertEquals("BSB", books[0].initials)
    }

    @Test
    fun getBookByInitials() = runTest {
        val book = Books.getBook("BSB")
        assertNotNull(book)
        assertEquals("BSB", book!!.initials)
    }

    @Test
    fun getBookByName() = runTest {
        val book = Books.getBook("Berean Standard Bible")
        assertNotNull(book)
        assertEquals("BSB", book!!.initials)
    }
}