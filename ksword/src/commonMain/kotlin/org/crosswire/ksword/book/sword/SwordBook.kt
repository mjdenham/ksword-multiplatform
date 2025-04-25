package org.crosswire.ksword.book.sword

import org.crosswire.ksword.book.Book
import org.crosswire.ksword.book.BookMetaData
import org.crosswire.ksword.passage.Key
import org.crosswire.ksword.passage.KeyText

class SwordBook(override var bookMetaData: BookMetaData, val backend: Backend<*>): Book {

    override val name: String = bookMetaData.name

    override val initials: String = bookMetaData.initials

    override fun readToOsis(key: Key): List<KeyText> = backend.readToOsis(key)

    override fun getRawText(key: Key): String = backend.getRawText(key)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SwordBook) return false

        return this.bookMetaData == other.bookMetaData
    }

    override fun hashCode(): Int {
        return bookMetaData.hashCode()
    }
}