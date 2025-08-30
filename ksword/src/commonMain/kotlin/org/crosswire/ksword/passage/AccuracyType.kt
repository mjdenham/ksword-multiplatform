package org.crosswire.ksword.passage

import org.crosswire.ksword.JSMsg
import org.crosswire.ksword.versification.BibleBook
import org.crosswire.ksword.versification.Versification

//import org.crosswire.common.icu.NumberShaper

/**
 * Types of Accuracy for verse references. For example:
 *
 *  * Gen == BOOK_ONLY;
 *  * Gen 1 == BOOK_CHAPTER;
 *  * Gen 1:1 == BOOK_VERSE;
 *  * Jude 1 == BOOK_VERSE;
 *  * Jude 1:1 == BOOK_VERSE;
 *  * 1:1 == CHAPTER_VERSE;
 *  * 10 == BOOK_ONLY, CHAPTER_ONLY, or VERSE_ONLY
 *
 * With the last one, you will note that there is a choice. By itself there is
 * not enough information to determine which one it is. There has to be a
 * context in which it is used.
 *
 * It may be found in a verse range like: Gen 1:2 - 10. In this case the context
 * of 10 is Gen 1:2, which is BOOK_VERSE. So in this case, 10 is VERSE_ONLY.
 *
 * If it is at the beginning of a range like 10 - 22:3, it has to have more
 * context. If the context is a prior entry like Gen 2:5, 10 - 22:3, then its
 * context is Gen 2:5, which is BOOK_VERSE and 10 is VERSE_ONLY.
 *
 * However if it is Gen 2, 10 - 22:3 then the context is Gen 2, BOOK_CHAPTER so
 * 10 is understood as BOOK_CHAPTER.
 *
 * As a special case, if the preceding range is an entire chapter or book then
 * 10 would understood as CHAPTER_ONLY or BOOK_ONLY (respectively)
 *
 * If the number has no preceding context, then it is understood as being
 * BOOK_ONLY.
 *
 * In all of these examples, the start verse was being interpreted. In the case
 * of a verse that is the end of a range, it is interpreted in the context of
 * the range's start.
 *
 * @author Joe Walker
 * @author DM Smith
 * @author Martin Denham
 */
enum class AccuracyType {
    /**
     * The verse was specified as book, chapter and verse. For example, Gen 1:1,
     * Jude 3 (which only has one chapter)
     */
    BOOK_VERSE {
        override fun isVerse(): Boolean {
            return true
        }

        override fun createStartVerse(
            v11n: Versification,
            verseRangeBasis: VerseRange?,
            parts: Array<String>
        ): Verse {
            val book: BibleBook = v11n.getBook(parts[0]) ?: throw NoSuchVerseException(JSMsg.gettext("Book is missing"))
            var chapter = 1
            var verse = 1
            val subIdentifier = AccuracyType.Companion.getSubIdentifier(parts)
            val hasSub = subIdentifier != null

            //can be of form, BCV, BCV!sub, BV, BV!a
            //we only have a chapter and verse number if
            // a- BCV (3 parts) or b- BCV!sub (4 parts)
            // however, we have 3 parts if BV!a
            if (hasSub && parts.size == 4 || !hasSub && parts.size == 3) {
                chapter = AccuracyType.Companion.getChapter(v11n, book, parts[1])
                verse = AccuracyType.Companion.getVerse(v11n, book, chapter, parts[2])
            } else {
                // Some books only have 1 chapter
                verse = AccuracyType.Companion.getVerse(v11n, book, chapter, parts[1])
            }
            return Verse(v11n, book, chapter, verse, subIdentifier)
        }

        override fun createEndVerse(
            v11n: Versification,
            verseBasis: Verse,
            endParts: Array<String>
        ): Verse {
            // A fully specified verse is the same regardless of whether it is a
            // start or an end to a range.
            return createStartVerse(v11n, null, endParts)
        }
    },

    /**
     * The passage was specified to a book and chapter (no verse). For example,
     * Gen 1
     */
    BOOK_CHAPTER {
        override fun isChapter(): Boolean {
            return true
        }

        override fun createStartVerse(
            v11n: Versification,
            verseRangeBasis: VerseRange?,
            parts: Array<String>
        ): Verse {
            val book: BibleBook = v11n.getBook(parts[0]) ?: throw NoSuchVerseException(JSMsg.gettext("Book is missing"))
            val chapter = AccuracyType.Companion.getChapter(v11n, book, parts[1]!!)
            val verse = 0 // chapter > 0 ? 1 : 0; // 0 ?
            return Verse(v11n, book, chapter, verse)
        }

        override fun createEndVerse(
            v11n: Versification,
            verseBasis: Verse,
            endParts: Array<String>
        ): Verse {
            // Very similar to the start verse but we want the end of the chapter
            val book: BibleBook = v11n.getBook(endParts[0]) ?: throw NoSuchVerseException(JSMsg.gettext("Book is missing"))
            val chapter = AccuracyType.Companion.getChapter(v11n, book, endParts[1])
            val verse: Int = v11n.getLastVerse(book, chapter)
            return Verse(v11n, book, chapter, verse)
        }
    },

    /**
     * The passage was specified to a book only (no chapter or verse). For
     * example, Gen
     */
    BOOK_ONLY {
        override fun isBook(): Boolean {
            return true
        }

        override fun createStartVerse(
            v11n: Versification,
            verseRangeBasis: VerseRange?,
            parts: Array<String>
        ): Verse {
            val book: BibleBook = v11n.getBook(parts[0]) ?: throw NoSuchVerseException(JSMsg.gettext("Book is missing"))
            val chapter = 0 // v11n.getLastChapter(book) > 0 ? 1 : 0; // 0 ?
            val verse = 0 // v11n.getLastVerse(book, chapter) > 0 ? 1 : 0; // 0 ?
            return Verse(v11n, book, chapter, verse)
        }

        override fun createEndVerse(
            v11n: Versification,
            verseBasis: Verse,
            endParts: Array<String>
        ): Verse {
            val book: BibleBook = v11n.getBook(endParts[0]) ?: throw NoSuchVerseException(JSMsg.gettext("Book is missing"))
            val chapter: Int = v11n.getLastChapter(book)
            val verse: Int = v11n.getLastVerse(book, chapter)
            return Verse(v11n, book, chapter, verse)
        }
    },

    /**
     * The passage was specified to a chapter and verse (no book). For example,
     * 1:1
     */
    CHAPTER_VERSE {
        override fun isVerse(): Boolean {
            return true
        }

        override fun createStartVerse(
            v11n: Versification,
            verseRangeBasis: VerseRange?,
            parts: Array<String>
        ): Verse {
            if (verseRangeBasis == null) {
                // TRANSLATOR: The user supplied a verse reference but did not give the book of the Bible.
                throw NoSuchVerseException(JSMsg.gettext("Book is missing"))
            }
            val book: BibleBook = verseRangeBasis.end.book
            val chapter = AccuracyType.Companion.getChapter(v11n, book, parts[0])
            val verse = AccuracyType.Companion.getVerse(v11n, book, chapter, parts[1])

            return Verse(v11n, book, chapter, verse, AccuracyType.Companion.getSubIdentifier(parts))
        }

        override fun createEndVerse(
            v11n: Versification,
            verseBasis: Verse,
            endParts: Array<String>
        ): Verse {
            // Very similar to the start verse but use the verse as a basis
            val book: BibleBook = verseBasis.book
            val chapter = AccuracyType.Companion.getChapter(v11n, book, endParts[0])
            val verse = AccuracyType.Companion.getVerse(v11n, book, chapter, endParts[1])
            return Verse(
                v11n,
                book,
                chapter,
                verse,
                AccuracyType.Companion.getSubIdentifier(endParts)
            )
        }
    },

    /**
     * There was only a chapter number
     */
    CHAPTER_ONLY {
        override fun isChapter(): Boolean {
            return true
        }

        override fun createStartVerse(
            v11n: Versification,
            verseRangeBasis: VerseRange?,
            parts: Array<String>
        ): Verse {
            if (verseRangeBasis == null) {
                // TRANSLATOR: The user supplied a verse reference but did not give the book of the Bible.
                throw NoSuchVerseException(JSMsg.gettext("Book is missing"))
            }
            val book: BibleBook = verseRangeBasis.end.book
            val chapter = AccuracyType.Companion.getChapter(v11n, book, parts[0])
            val verse = 0 // chapter > 0 ? 1 : 0; // 0 ?
            return Verse(v11n, book, chapter, verse)
        }

        override fun createEndVerse(
            v11n: Versification,
            verseBasis: Verse,
            endParts: Array<String>
        ): Verse {
            // Very similar to the start verse but use the verse as a basis
            // and it gets the end of the chapter
            val book: BibleBook = verseBasis.book
            val chapter = AccuracyType.Companion.getChapter(v11n, book, endParts[0])
            return Verse(v11n, book, chapter, v11n.getLastVerse(book, chapter))
        }
    },

    /**
     * There was only a verse number
     */
    VERSE_ONLY {
        override fun isVerse(): Boolean {
            return true
        }

        override fun createStartVerse(
            v11n: Versification,
            verseRangeBasis: VerseRange?,
            parts: Array<String>
        ): Verse {
            if (verseRangeBasis == null) {
                // TRANSLATOR: The user supplied a verse reference but did not give the book or chapter of the Bible.
                throw NoSuchVerseException(JSMsg.gettext("Book and chapter are missing"))
            }
            val book: BibleBook = verseRangeBasis.end.book
            val chapter = verseRangeBasis.end.chapter
            val verse = AccuracyType.Companion.getVerse(v11n, book, chapter, parts[0])
            return Verse(v11n, book, chapter, verse, AccuracyType.Companion.getSubIdentifier(parts))
        }

        override fun createEndVerse(
            v11n: Versification,
            verseBasis: Verse,
            endParts: Array<String>
        ): Verse {
            // Very similar to the start verse but use the verse as a basis
            // and it gets the end of the chapter
            val book: BibleBook = verseBasis.book
            val chapter = verseBasis.chapter
            val verse = AccuracyType.Companion.getVerse(v11n, book, chapter, endParts[0])
            return Verse(
                v11n,
                book,
                chapter,
                verse,
                AccuracyType.Companion.getSubIdentifier(endParts)
            )
        }
    };

    /**
     * @param v11n
     * the versification to which this reference pertains
     * @param verseRangeBasis
     * the range that stood before the string reference
     * @param parts
     * a tokenized version of the original
     * @return a `Verse` for the original
     * @throws NoSuchVerseException
     */
    abstract fun createStartVerse(
        v11n: Versification,
        verseRangeBasis: VerseRange?,
        parts: Array<String>
    ): Verse?

    /**
     * @param v11n
     * the versification to which this reference pertains
     * @param verseBasis
     * the verse at the beginning of the range
     * @param endParts
     * a tokenized version of the original
     * @return a `Verse` for the original
     * @throws NoSuchVerseException
     */
    abstract fun createEndVerse(
        v11n: Versification,
        verseBasis: Verse,
        endParts: Array<String>
    ): Verse?

    /**
     * @return true if this AccuracyType specifies down to the book but not
     * chapter or verse.
     */
    open fun isBook(): Boolean = false

    /**
     * @return true if this AccuracyType specifies down to the chapter but not
     * the verse.
     */
    open fun isChapter(): Boolean = false

    /**
     * @return true if this AccuracyType specifies down to the verse.
     */
    open fun isVerse(): Boolean = false

    /**
     * Get an integer representation for this AccuracyType
     *
     * @return the ordinal representation
     */
    fun toInteger(): Int {
        return ordinal
    }

    companion object {
        /**
         * Interprets the chapter value, which is either a number or "ff" or "$"
         * (meaning "what follows")
         *
         * @param v11n
         * the versification to which this reference pertains
         * @param lbook
         * the book
         * @param chapter
         * a string representation of the chapter. May be "ff" or "$" for
         * "what follows".
         * @return the number of the chapter
         * @throws NoSuchVerseException
         */
        fun getChapter(v11n: Versification, lbook: BibleBook?, chapter: String): Int {
            if (isEndMarker(chapter)) {
                return v11n.getLastChapter(lbook)
            }
            return parseInt(chapter)
        }

        /**
         * Interprets the verse value, which is either a number or "ff" or "$"
         * (meaning "what follows")
         *
         * @param v11n
         * the versification to which this reference pertains
         * @param lbook
         * the integer representation of the book
         * @param lchapter
         * the integer representation of the chapter
         * @param verse
         * the string representation of the verse
         * @return the number of the verse
         * @throws NoSuchVerseException
         */
        fun getVerse(v11n: Versification, lbook: BibleBook?, lchapter: Int, verse: String): Int {
            if (isEndMarker(verse)) {
                return v11n.getLastVerse(lbook, lchapter)
            }
            return parseInt(verse)
        }

        /**
         * Determine how closely the string defines a verse.
         *
         * @param v11n
         * the versification to which this reference pertains
         * @param original
         * @param parts
         * is a reference split into parts
         * @return what is the kind of accuracy of the string reference.
         * @throws NoSuchVerseException
         */
        fun fromText(v11n: Versification, original: String?, parts: Array<String>): AccuracyType {
            return fromText(v11n, original, parts, null, null)
        }

        /**
         * @param v11n
         * the versification to which this reference pertains
         * @param original
         * @param parts
         * @param verseAccuracy
         * @return the accuracy of the parts
         * @throws NoSuchVerseException
         */
        fun fromText(
            v11n: Versification,
            original: String?,
            parts: Array<String>,
            verseAccuracy: AccuracyType?
        ): AccuracyType {
            return fromText(v11n, original, parts, verseAccuracy, null)
        }

        /**
         * @param v11n
         * the versification to which this reference pertains
         * @param original
         * @param parts
         * @param basis
         * @return the accuracy of the parts
         * @throws NoSuchVerseException
         */
        fun fromText(
            v11n: Versification,
            original: String?,
            parts: Array<String>,
            basis: VerseRange?
        ): AccuracyType {
            return fromText(v11n, original, parts, null, basis)
        }

        /**
         * Does this string exactly define a Verse. For example:
         *
         *  * fromText("Gen") == AccuracyType.BOOK_ONLY;
         *  * fromText("Gen 1:1") == AccuracyType.BOOK_VERSE;
         *  * fromText("Gen 1") == AccuracyType.BOOK_CHAPTER;
         *  * fromText("Jude 1") == AccuracyType.BOOK_VERSE;
         *  * fromText("Jude 1:1") == AccuracyType.BOOK_VERSE;
         *  * fromText("1:1") == AccuracyType.CHAPTER_VERSE;
         *  * fromText("1") == AccuracyType.VERSE_ONLY;
         *
         *
         * @param v11n
         * the versification to which this reference pertains
         * @param original
         * @param parts
         * @param verseAccuracy
         * @param basis
         * @return the accuracy of the parts
         * @throws NoSuchVerseException
         */
        fun fromText(
            v11n: Versification,
            original: String?,
            parts: Array<String>,
            verseAccuracy: AccuracyType?,
            basis: VerseRange?
        ): AccuracyType {
            var partsLength = parts.size
            val lastPart = parts.last()
            if (lastPart.isNotEmpty() && lastPart[0] == '!') {
                --partsLength
            }
            when (partsLength) {
                1 -> {
                    if (v11n.isBook(parts[0])) {
                        return BOOK_ONLY
                    }

                    // At this point we should have a number.
                    // But double check it
                    checkValidChapterOrVerse(parts[0])

                    // What it is depends upon what stands before it.
                    if (verseAccuracy != null) {
                        if (verseAccuracy.isVerse()) {
                            return VERSE_ONLY
                        }

                        if (verseAccuracy.isChapter()) {
                            return CHAPTER_ONLY
                        }
                    }

                    if (basis != null) {
                        if (basis.isWholeChapter) {
                            return CHAPTER_ONLY
                        }
                        return VERSE_ONLY
                    }

                    throw buildVersePartsException(original, parts)
                }

                2 -> {
                    // Does it start with a book?
                    val pbook: BibleBook? = v11n.getBook(parts[0])
                    if (pbook == null) {
                        checkValidChapterOrVerse(parts[0])
                        checkValidChapterOrVerse(parts[1])
                        return CHAPTER_VERSE
                    }

                    if (v11n.getLastChapter(pbook) == 1) {
                        return BOOK_VERSE
                    }

                    return BOOK_CHAPTER
                }

                3 -> {
                    if (v11n.getBook(parts[0]) != null) {
                        checkValidChapterOrVerse(parts[1])
                        checkValidChapterOrVerse(parts[2])
                        return BOOK_VERSE
                    }

                    throw buildVersePartsException(original, parts)
                }

                else -> throw buildVersePartsException(original, parts)
            }
        }

        private fun buildVersePartsException(
            original: String?,
            parts: Array<String>
        ): NoSuchVerseException {
            val buffer = StringBuilder(original!!)
            for (i in parts.indices) {
                buffer.append(", ").append(parts[i])
            }
            // TRANSLATOR: The user specified a verse with too many separators. {0} is a placeholder for the allowable separators.
            return NoSuchVerseException(
                JSMsg.gettext(
                    "Too many parts to the Verse. (Parts are separated by any of {0})",
                    buffer.toString()
                )
            )
        }

        /**
         * Is this text valid in a chapter/verse context
         *
         * @param text
         * The string to test for validity
         * @throws NoSuchVerseException
         * If the text is invalid
         */
        private fun checkValidChapterOrVerse(text: String) {
            if (!isEndMarker(text)) {
                parseInt(text)
            }
        }

        /**
         * This is simply a convenience function to wrap Integer.parseInt() and give
         * us a reasonable exception on failure. It is called by VerseRange hence
         * protected, however I would prefer private
         *
         * @param text
         * The string to be parsed
         * @return The correctly parsed chapter or verse
         * @exception NoSuchVerseException
         * If the reference is illegal
         */
        private fun parseInt(text: String?): Int {
            return text?.toIntOrNull()
                ?: throw NoSuchVerseException(JSMsg.gettext("Cannot understand $text as a chapter or verse."))
        }

        /**
         * Is this string a legal marker for 'to the end of the chapter'
         *
         * @param text
         * The string to be checked
         * @return true if this is a legal marker
         */
        private fun isEndMarker(text: String): Boolean {
            if (text.equals(VERSE_END_MARK1)) {
                return true
            }

            if (text.equals(VERSE_END_MARK2)) {
                return true
            }

            return false
        }

        private fun hasSubIdentifier(parts: Array<String>): Boolean {
            val subIdentifier = parts.lastOrNull() ?: return false
            return subIdentifier.isNotEmpty() && subIdentifier[0] == '!'
        }

        protected fun getSubIdentifier(parts: Array<String>): String? {
            var subIdentifier: String? = null
            if (hasSubIdentifier(parts)) {
                subIdentifier = parts.last().substring(1)
            }
            return subIdentifier
        }

        /**
         * Take a string representation of a verse and parse it into an Array of
         * Strings where each part is likely to be a verse part. The goal is to
         * allow the greatest possible variations in user input.
         *
         * Parts can be separated by pretty much anything. No distinction is made
         * between them. While chapter and verse need to be separated, a separator
         * is assumed between digits and non-digits. Adjacent words, (i.e. sequences
         * of non-digits) are understood to be a book reference. If a number runs up
         * against a book name, it is considered to be either part of the book name
         * (i.e. it is before it) or a chapter number (i.e. it stands after it.)
         *
         * Note: ff and $ are considered to be digits.
         *
         * Note: it is not necessary for this to be a BCV (book, chapter, verse), it
         * may just be BC, B, C, V or CV. No distinction is needed here for a number
         * that stands alone.
         *
         * @param input
         * The string to parse.
         * @return The string array
         * @throws NoSuchVerseException
         */
        fun tokenize(input: String): Array<String> {
            // The results are expected to be no more than 3 parts
            val args = arrayOf<String?>(
                null, null, null, null, null, null, null, null
            )

            // Normalize the input by replacing non-digits and non-letters with spaces.
            val length: Int = input.length
            // Create an output array big enough to add ' ' where needed
            val normalized = CharArray(length * 2)
            var lastChar = '0' // start with a digit so normalized won't start
            // with a space
            var curChar = ' ' // can be anything
            var tokenCount = 0
            var normalizedLength = 0
            val startIndex = 0
            var token: String? = null
            var foundBoundary = false
            var foundSubIdentifier = false
            for (i in 0..<length) {
                curChar = input[i]
                if (curChar == '!') {
                    foundSubIdentifier = true
                    token = normalized.concatToString(startIndex, normalizedLength)
                    args[tokenCount++] = token
                    normalizedLength = 0
                }
                if (foundSubIdentifier) {
                    normalized[normalizedLength++] = curChar
                    continue
                }
                val charIsDigit = curChar == '$' || curChar.isDigit()
                        || (curChar == 'f' && (if (i + 1 < length) input[i + 1] else ' ') == 'f' && !lastChar.isLetter())
                if (charIsDigit || curChar.isLetter()) {
                    foundBoundary = true
                    val charWasDigit =
                        lastChar == '$' || lastChar.isDigit() || (lastChar == 'f' && (if (i > 2) input[i - 2] else '0') == 'f')
                    if (charWasDigit || lastChar.isLetter()) {
                        foundBoundary = false
                        // Replace transitions between digits and letters with
                        // spaces.
                        if (normalizedLength > 0 && charWasDigit != charIsDigit) {
                            foundBoundary = true
                        }
                    }
                    if (foundBoundary) {
                        // On a boundary:
                        // Digits always start a new token
                        // Letters always continue a previous token
                        if (charIsDigit) {
                            if (tokenCount >= args.size) {
                                // TRANSLATOR: The user specified a verse with too many separators. {0} is a placeholder for the allowable separators.
                                throw NoSuchVerseException(
                                    JSMsg.gettext(
                                        "Too many parts to the Verse. (Parts are separated by any of {0})",
                                        input
                                    )
                                )
                            }

                            token = normalized.concatToString(startIndex, normalizedLength)
                            args[tokenCount++] = token
                            normalizedLength = 0
                        } else {
                            normalized[normalizedLength++] = ' '
                        }
                    }
                    normalized[normalizedLength++] = curChar
                }

                // Until the first character is copied, there is no last char
                if (normalizedLength > 0) {
                    lastChar = curChar
                }
            }

            if (tokenCount >= args.size) {
                // TRANSLATOR: The user specified a verse with too many separators. {0} is a placeholder for the allowable separators.
                throw NoSuchVerseException(
                    JSMsg.gettext(
                        "Too many parts to the Verse. (Parts are separated by any of {0})",
                        input
                    )
                )
            }

            token = normalized.concatToString(startIndex, startIndex + (normalizedLength - startIndex))
            args[tokenCount++] = token

            return Array(tokenCount) { index -> args[index]!! }
        }

        /**
         * What characters can we use to separate parts to a verse
         */
        val VERSE_ALLOWED_DELIMS: String = " :."

        /**
         * Characters that are used to indicate end of verse/chapter, part 1
         */
        const val VERSE_END_MARK1: String = "$"

        /**
         * Characters that are used to indicate end of verse/chapter, part 2
         */
        const val VERSE_END_MARK2: String = "ff"
    }
}