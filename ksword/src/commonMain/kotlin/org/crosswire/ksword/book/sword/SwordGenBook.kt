package org.crosswire.ksword.book.sword

import okio.Path
import org.crosswire.ksword.book.Book
import org.crosswire.ksword.book.BookMetaData
import org.crosswire.ksword.passage.DefaultLeafKeyList
import org.crosswire.ksword.passage.Key
import org.crosswire.ksword.passage.KeyText
import org.crosswire.ksword.passage.NoSuchKeyException
import org.crosswire.ksword.passage.TreeKey

/** A general book (RawGenBook): a tree of entries keyed by path, e.g. "Part 1/Chapter 3"; next/previous skip headings with no text. */
class SwordGenBook internal constructor(
    override var bookMetaData: BookMetaData,
    internal val backend: GenBookBackend
) : Book {

    override val name: String = bookMetaData.name
    override val initials: String = bookMetaData.initials

    override fun getNextKey(key: Key): Key? = backend.findNextKey(key)
    override fun getPreviousKey(key: Key): Key? = backend.findPreviousKey(key)

    /** True when the entry exists and has text; false for a heading node. */
    override fun contains(key: Key): Boolean = backend.contains(key)

    /** Throws NoSuchKeyException for a key not in this book and BookException when the text cannot be read. */
    override fun readToOsis(key: Key): List<KeyText> = backend.readToOsis(key)
    override fun getRawText(key: Key): String = backend.getRawText(key)

    /** The entry at [text], a path as given by [TreeKey.toString]; SWORD's leading "/" is accepted. */
    fun getKey(text: String): TreeKey = backend.find(text) ?: throw NoSuchKeyException("No entry for '$text' in $initials")

    fun getValidKey(name: String): Key =
        try {
            getKey(name)
        } catch (e: NoSuchKeyException) {
            DefaultLeafKeyList("")
        }

    /** Every entry in reading order, headings included; use [TreeKey.depth] to indent and [contains] to tell headings apart. */
    fun getGlobalKeyList(): List<TreeKey> = backend.keys()

    /** The file an image `src` in this book's text refers to, relative to the module's data directory as in SWORD; null if it points outside it. */
    fun resolveImagePath(src: String): Path? {
        val dataDir = SwordUtil.getExpandedDataPath(bookMetaData).parent?.normalized() ?: return null
        val path = dataDir.resolve(src.trimStart('/'), normalize = true)
        return path.takeIf { it.root == dataDir.root && it.segments.take(dataDir.segments.size) == dataDir.segments }
    }

    override fun equals(other: Any?): Boolean = other is SwordGenBook && bookMetaData == other.bookMetaData

    override fun hashCode(): Int = bookMetaData.hashCode()
}
