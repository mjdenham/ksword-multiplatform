/**
 * Distribution License:
 * KSword is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License, version 2.1 or later
 * as published by the Free Software Foundation. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * The License is available on the internet at:
 * http://www.gnu.org/copyleft/lgpl.html
 * or by writing to:
 * Free Software Foundation, Inc.
 * 59 Temple Place - Suite 330
 * Boston, MA 02111-1307, USA
 *
 * © CrossWire Bible Society, 2005 - 2016
 *
 */
package org.crosswire.ksword.passage

import org.crosswire.ksword.versification.Versification

/**
 * A Passage implementation that stores a list of VerseRanges.
 * This implementation is optimized for storing biblical references
 * in a compact form by merging adjacent verse ranges.
 *
 * Based on JSword's RangedPassage but simplified for minimal functionality.
 *
 * @author Joe Walker (original JSword implementation)
 * @author DM Smith (original JSword implementation)
 * @author Martin Denham (KSword port)
 */
class RangedPassage(
    v11n: Versification,
    ref: String? = null,
    basis: Key? = null
) : AbstractPassage(v11n, ref) {

    /**
     * Internal storage of non-overlapping, sorted verse ranges
     */
    private val store: MutableList<VerseRange> = mutableListOf()

    init {
        // Parse the reference string if provided
        if (!ref.isNullOrBlank()) {
            addVerses(ref, basis)
            normalizeRanges()
        }
    }

    /* (non-Javadoc)
     * @see org.crosswire.ksword.passage.VerseKey#getVersification()
     */
    override fun getVersification(): Versification {
        return v11n
    }

    /* (non-Javadoc)
     * @see org.crosswire.ksword.passage.VerseKey#isWhole()
     */
    override fun isWhole(): Boolean {
        return store.all { it.isWhole() }
    }

    /* (non-Javadoc)
     * @see org.crosswire.ksword.passage.Passage#countVerses()
     */
    override fun countVerses(): Int {
        return store.sumOf { it.getCardinality() }
    }

    /* (non-Javadoc)
     * @see org.crosswire.ksword.passage.Passage#hasRanges(org.crosswire.ksword.passage.RestrictionType)
     */
    override fun hasRanges(restrict: RestrictionType): Boolean {
        return countRanges(restrict) > 1
    }

    /* (non-Javadoc)
     * @see org.crosswire.ksword.passage.Passage#countRanges(org.crosswire.ksword.passage.RestrictionType)
     */
    override fun countRanges(restrict: RestrictionType): Int {
        if (restrict == RestrictionType.NONE) {
            return store.size
        }

        // For other restriction types, count by iterating
        var count = 0
        val iter = rangeIterator(restrict)
        while (iter.hasNext()) {
            iter.next()
            count++
        }
        return count
    }

    /* (non-Javadoc)
     * @see org.crosswire.ksword.passage.Passage#trimVerses(int)
     */
    override fun trimVerses(count: Int): Passage? {
        throw UnsupportedOperationException("trimVerses not implemented")
    }

    /* (non-Javadoc)
     * @see org.crosswire.ksword.passage.Passage#trimRanges(int, org.crosswire.ksword.passage.RestrictionType)
     */
    override fun trimRanges(count: Int, restrict: RestrictionType?): Passage? {
        throw UnsupportedOperationException("trimRanges not implemented")
    }

    /* (non-Javadoc)
     * @see org.crosswire.ksword.passage.Passage#booksInPassage()
     */
    override fun booksInPassage(): Int {
        val books = store.map { it.start.book }.toSet()
        return books.size
    }


    /* (non-Javadoc)
     * @see org.crosswire.ksword.passage.Passage#rangeIterator(org.crosswire.ksword.passage.RestrictionType)
     */
    override fun rangeIterator(restrict: RestrictionType): Iterator<VerseRange> {
        if (restrict == RestrictionType.NONE) {
            return store.iterator()
        }

        // For other restriction types, we need to split ranges at boundaries
        return VerseRangeIterator(v11n, iterator(), restrict)
    }

    /* (non-Javadoc)
     * @see org.crosswire.ksword.passage.Key#contains(org.crosswire.ksword.passage.Key)
     */
    override fun contains(key: Key): Boolean {
        if (key is Verse) {
            return store.any { it.contains(key) }
        }

        if (key is VerseRange) {
            return store.any { it.contains(key) }
        }

        if (key is Passage) {
            // Check if all verses in the passage are contained
            val iter = key.iterator()
            while (iter.hasNext()) {
                val verse = iter.next()
                if (!contains(verse)) {
                    return false
                }
            }
            return true
        }

        return false
    }

    /* (non-Javadoc)
     * @see org.crosswire.ksword.passage.Passage#add(org.crosswire.ksword.passage.Key)
     */
    override fun add(that: Key?) {
        if (that == null) {
            return
        }

        when (that) {
            is Verse -> {
                addRange(VerseRange(v11n, that, that))
            }
            is VerseRange -> {
                addRange(that)
            }
            is Passage -> {
                val iter = that.rangeIterator(RestrictionType.NONE)
                while (iter.hasNext()) {
                    addRange(iter.next())
                }
            }
            else -> {
                // Try iterating over it
                val iter = that.iterator()
                while (iter.hasNext()) {
                    add(iter.next())
                }
            }
        }
    }

    /**
     * Add a VerseRange to the internal store and normalize
     */
    private fun addRange(range: VerseRange) {
        store.add(range)
        normalizeRanges()
    }

    /* (non-Javadoc)
     * @see org.crosswire.ksword.passage.Passage#remove(org.crosswire.ksword.passage.Key)
     */
    override fun remove(that: Key?) {
        if (that == null) {
            return
        }

        when (that) {
            is Verse -> {
                removeRange(VerseRange(v11n, that, that))
            }
            is VerseRange -> {
                removeRange(that)
            }
            is Passage -> {
                val iter = that.rangeIterator(RestrictionType.NONE)
                while (iter.hasNext()) {
                    removeRange(iter.next())
                }
            }
            else -> {
                // Try iterating over it
                val iter = that.iterator()
                while (iter.hasNext()) {
                    remove(iter.next())
                }
            }
        }
    }

    /**
     * Remove a VerseRange from the internal store
     */
    private fun removeRange(range: VerseRange) {
        val toRemove = mutableListOf<VerseRange>()
        val toAdd = mutableListOf<VerseRange>()

        for (existing in store) {
            if (existing.overlaps(range)) {
                toRemove.add(existing)
                val remainders = VerseRange.remainder(existing, range)
                toAdd.addAll(remainders)
            }
        }

        store.removeAll(toRemove)
        store.addAll(toAdd)
        normalizeRanges()
    }

    /* (non-Javadoc)
     * @see org.crosswire.ksword.passage.Passage#containsAll(org.crosswire.ksword.passage.Passage)
     */
    override fun containsAll(that: Passage?): Boolean {
        if (that == null) {
            return false
        }
        return contains(that as Key)
    }

    /* (non-Javadoc)
     * @see org.crosswire.ksword.passage.Key#iterator()
     */
    override fun iterator(): Iterator<Key> {
        return VerseIterator()
    }

    /* (non-Javadoc)
     * @see org.crosswire.ksword.passage.Key#isEmpty()
     */
    override fun isEmpty(): Boolean {
        return store.isEmpty()
    }

    /* (non-Javadoc)
     * @see org.crosswire.ksword.passage.Key#clear()
     */
    override fun clear() {
        store.clear()
    }

    /* (non-Javadoc)
     * @see org.crosswire.ksword.passage.Key#indexOf(org.crosswire.ksword.passage.Key)
     */
    override fun indexOf(that: Key): Int {
        // Optimized implementation for RangedPassage
        if (that is Verse) {
            var index = 0
            for (range in store) {
                if (range.contains(that)) {
                    return index + v11n.distance(range.start, that)
                }
                index += range.getCardinality()
            }
        }
        return -1
    }

    /* (non-Javadoc)
     * @see org.crosswire.ksword.passage.Passage#getOverview()
     */
    override val overview: String
        get() {
            val verses = countVerses()
            val books = booksInPassage()
            return "$verses verses in $books books"
        }

    /**
     * Normalize the store by merging adjacent verse ranges.
     * This keeps the storage compact and efficient.
     */
    private fun normalizeRanges() {
        if (store.size <= 1) {
            return
        }

        // Sort the ranges
        store.sortBy { it.start.ordinal }

        // Merge adjacent ranges
        val merged = mutableListOf<VerseRange>()
        var current: VerseRange? = null

        for (range in store) {
            if (current == null) {
                current = range
            } else if (current.adjacentTo(range)) {
                // Merge the ranges
                current = VerseRange(current, range)
            } else {
                merged.add(current)
                current = range
            }
        }

        if (current != null) {
            merged.add(current)
        }

        store.clear()
        store.addAll(merged)
    }

    /**
     * Iterator that expands VerseRanges into individual Verses
     */
    private inner class VerseIterator : Iterator<Key> {
        private var rangeIndex = 0
        private var verseOffset = 0
        private var currentRange: VerseRange? = if (store.isNotEmpty()) store[0] else null

        override fun hasNext(): Boolean {
            if (currentRange == null) {
                return false
            }

            if (verseOffset < currentRange!!.getCardinality()) {
                return true
            }

            // Check if there are more ranges
            return rangeIndex + 1 < store.size
        }

        override fun next(): Key {
            if (!hasNext()) {
                throw NoSuchElementException()
            }

            // Get the current verse
            val verse = v11n.decodeOrdinal(currentRange!!.start.ordinal + verseOffset)
            verseOffset++

            // Move to next range if needed
            if (verseOffset >= currentRange!!.getCardinality()) {
                rangeIndex++
                if (rangeIndex < store.size) {
                    currentRange = store[rangeIndex]
                    verseOffset = 0
                } else {
                    currentRange = null
                }
            }

            return verse
        }
    }

    /**
     * Iterator that splits VerseRanges at restriction boundaries
     */
    private class VerseRangeIterator(
        private val v11n: Versification,
        private val verseIterator: Iterator<Key>,
        private val restrict: RestrictionType
    ) : Iterator<VerseRange> {
        private var nextRange: VerseRange? = null
        private var hasCalculatedNext = false

        init {
            calculateNext()
        }

        override fun hasNext(): Boolean {
            if (!hasCalculatedNext) {
                calculateNext()
            }
            return nextRange != null
        }

        override fun next(): VerseRange {
            if (!hasNext()) {
                throw NoSuchElementException()
            }

            val result = nextRange!!
            hasCalculatedNext = false
            return result
        }

        private fun calculateNext() {
            if (!verseIterator.hasNext()) {
                nextRange = null
                hasCalculatedNext = true
                return
            }

            val start = verseIterator.next() as Verse
            var end = start

            // Keep adding verses while they're in the same restriction boundary
            while (verseIterator.hasNext()) {
                val peek = verseIterator.next() as Verse

                // Check if we should break here based on restriction type
                if (restrict == RestrictionType.CHAPTER && start.book != peek.book) {
                    // Different book, break
                    nextRange = VerseRange(v11n, start, end)
                    hasCalculatedNext = true
                    return
                } else if (restrict == RestrictionType.CHAPTER && start.chapter != peek.chapter) {
                    // Different chapter, break
                    nextRange = VerseRange(v11n, start, end)
                    hasCalculatedNext = true
                    return
                }

                end = peek
            }

            nextRange = VerseRange(v11n, start, end)
            hasCalculatedNext = true
        }
    }
}
