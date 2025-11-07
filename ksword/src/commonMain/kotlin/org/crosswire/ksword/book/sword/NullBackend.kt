package org.crosswire.ksword.book.sword

import org.crosswire.ksword.book.BookException
import org.crosswire.ksword.book.BookMetaData
import org.crosswire.ksword.book.sword.state.OpenFileState
import org.crosswire.ksword.passage.Key
import org.crosswire.ksword.passage.KeyText

/**
 * A NullBackend is not attached to resources.
 */
class NullBackend : Backend<OpenFileState> {
    override fun findNextKey(key: Key): Key? {
        throw BookException("NullBackend does not support findNextKey")
    }

    override fun findPreviousKey(key: Key): Key? {
        throw BookException("NullBackend does not support findPreviousKey")
    }

    override fun readToOsis(key: Key): List<KeyText> {
        throw BookException("NullBackend does not support readToOsis")
    }

    override fun getBookMetaData(): BookMetaData {
        throw BookException("NullBackend does not support getBookMetaData")
    }

    override fun getRawText(key: Key): String {
        throw BookException("NullBackend does not support getRawText")
    }

    override fun contains(key: Key): Boolean {
        throw BookException("NullBackend does not support contains")
    }
}
