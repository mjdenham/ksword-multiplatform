package org.crosswire.ksword.book.sword

import org.crosswire.ksword.versification.Testament

/**
 * A verse's entry in a zVerse index. osis2mod points every verse of a multi-verse
 * comment at one stored block, so linked verses have an identical entry.
 */
internal data class VerseIndexEntry(
    val testament: Testament,
    val blockNum: Int,
    val verseStart: Int,
    val verseSize: Int,
) {
    /** A verse with no content; never treated as linked to its neighbours. */
    val isEmpty: Boolean get() = verseSize == 0
}
