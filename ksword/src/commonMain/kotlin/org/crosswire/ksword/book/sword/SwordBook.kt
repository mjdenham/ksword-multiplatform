package org.crosswire.ksword.book.sword

import org.crosswire.ksword.book.Book
import org.crosswire.ksword.passage.Key
import org.crosswire.ksword.passage.KeyText

class SwordBook(val swordBookMetaData: SwordBookMetaData, val backend: Backend<*>): Book {

    override fun readToOsis(key: Key): List<KeyText> = backend.readToOsis(key)

    override fun getRawText(key: Key): String = backend.getRawText(key)
}