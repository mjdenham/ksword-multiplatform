package org.crosswire.ksword.versification

import org.crosswire.ksword.versification.system.Versifications
import kotlin.test.Test
import kotlin.test.assertEquals

class VersificationBooksTest {

    @Test
    fun booksMatchTheVersificationOrder() {
        Versifications.names.forEach { name ->
            val v = Versifications.getVersification(name)
            val books = v.books
            assertEquals(v.bookIterator.asSequence().toList(), books, name)
            assertEquals(v.bookCount, books.size, name)
            assertEquals(v.firstBook, books.first(), name)
            assertEquals(v.lastBook, books.last(), name)
            books.forEachIndexed { i, book -> assertEquals(book, v.getBook(i), name) }
        }
    }
}
