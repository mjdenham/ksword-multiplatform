package org.crosswire.ksword.book.passage

import org.crosswire.ksword.passage.RangedPassage
import org.crosswire.ksword.passage.RestrictionType
import org.crosswire.ksword.passage.Verse
import org.crosswire.ksword.passage.VerseRange
import org.crosswire.ksword.versification.BibleBook
import org.crosswire.ksword.versification.system.Versifications
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class RangedPassageTest {

    private val v11n = Versifications.defaultVersification

    @Test
    fun testEmptyPassage() {
        val passage = RangedPassage(v11n)
        assertTrue(passage.isEmpty())
        assertEquals(0, passage.countVerses())
        assertEquals(0, passage.countRanges(RestrictionType.NONE))
    }

    @Test
    fun testAddSingleVerse() {
        val passage = RangedPassage(v11n)
        val verse = Verse(v11n, BibleBook.JOHN, 1, 1)
        passage.add(verse)
        assertEquals(1, passage.countVerses())
        assertTrue(passage.contains(verse))
    }

    @Test
    fun testAddVerseRange() {
        val passage = RangedPassage(v11n)
        val start = Verse(v11n, BibleBook.GEN, 1, 1)
        val end = Verse(v11n, BibleBook.GEN, 1, 5)
        val range = VerseRange(v11n, start, end)
        passage.add(range)
        assertEquals(5, passage.countVerses())
        assertTrue(passage.contains(start))
        assertTrue(passage.contains(end))
    }

    @Test
    fun testAddMultipleRanges() {
        val passage = RangedPassage(v11n)
        val range1 = VerseRange(v11n, Verse(v11n, BibleBook.GEN, 1, 1), Verse(v11n, BibleBook.GEN, 1, 3))
        val range2 = VerseRange(v11n, Verse(v11n, BibleBook.JOHN, 1, 1), Verse(v11n, BibleBook.JOHN, 1, 2))
        passage.add(range1)
        passage.add(range2)

        assertEquals(5, passage.countVerses())
        assertEquals(2, passage.countRanges(RestrictionType.NONE))
    }

    @Test
    fun testNormalization_AdjacentRangesMerge() {
        val passage = RangedPassage(v11n)
        // Add Gen 1:1-3
        val range1 = VerseRange(v11n, Verse(v11n, BibleBook.GEN, 1, 1), Verse(v11n, BibleBook.GEN, 1, 3))
        passage.add(range1)

        // Add Gen 1:4-6 (adjacent to previous range)
        val range2 = VerseRange(v11n, Verse(v11n, BibleBook.GEN, 1, 4), Verse(v11n, BibleBook.GEN, 1, 6))
        passage.add(range2)

        // Should have merged into one range
        assertEquals(6, passage.countVerses())
        assertEquals(1, passage.countRanges(RestrictionType.NONE))
    }

    @Test
    fun testRemoveVerse() {
        val passage = RangedPassage(v11n)
        val range = VerseRange(v11n, Verse(v11n, BibleBook.GEN, 1, 1), Verse(v11n, BibleBook.GEN, 1, 5))
        passage.add(range)

        // Remove middle verse
        val verseToRemove = Verse(v11n, BibleBook.GEN, 1, 3)
        passage.remove(verseToRemove)

        assertEquals(4, passage.countVerses())
        assertFalse(passage.contains(verseToRemove))
        assertEquals(2, passage.countRanges(RestrictionType.NONE)) // Should split into two ranges
    }

    @Test
    fun testGetVerseAt() {
        val passage = RangedPassage(v11n)
        val range = VerseRange(v11n, Verse(v11n, BibleBook.GEN, 1, 1), Verse(v11n, BibleBook.GEN, 1, 3))
        passage.add(range)

        val verse0 = passage.getVerseAt(0)
        assertNotNull(verse0)
        assertEquals(1, (verse0 as Verse).verse)

        val verse1 = passage.getVerseAt(1)
        assertNotNull(verse1)
        assertEquals(2, (verse1 as Verse).verse)

        val verse2 = passage.getVerseAt(2)
        assertNotNull(verse2)
        assertEquals(3, (verse2 as Verse).verse)

        assertFailsWith<IndexOutOfBoundsException> {
            passage.getVerseAt(3)
        }
    }

    @Test
    fun testGetRangeAt() {
        val passage = RangedPassage(v11n)
        val range1 = VerseRange(v11n, Verse(v11n, BibleBook.GEN, 1, 1), Verse(v11n, BibleBook.GEN, 1, 3))
        val range2 = VerseRange(v11n, Verse(v11n, BibleBook.JOHN, 1, 1), Verse(v11n, BibleBook.JOHN, 1, 2))
        passage.add(range1)
        passage.add(range2)

        val retrievedRange0 = passage.getRangeAt(0, RestrictionType.NONE)
        assertNotNull(retrievedRange0)
        assertEquals(3, retrievedRange0!!.getCardinality())

        val retrievedRange1 = passage.getRangeAt(1, RestrictionType.NONE)
        assertNotNull(retrievedRange1)
        assertEquals(2, retrievedRange1!!.getCardinality())

        assertFailsWith<IndexOutOfBoundsException> {
            passage.getRangeAt(2, RestrictionType.NONE)
        }
    }

    @Test
    fun testIterator() {
        val passage = RangedPassage(v11n)
        val range = VerseRange(v11n, Verse(v11n, BibleBook.GEN, 1, 1), Verse(v11n, BibleBook.GEN, 1, 3))
        passage.add(range)

        val verses = mutableListOf<Verse>()
        val iterator = passage.iterator()
        while (iterator.hasNext()) {
            verses.add(iterator.next() as Verse)
        }

        assertEquals(3, verses.size)
        assertEquals(1, verses[0].verse)
        assertEquals(2, verses[1].verse)
        assertEquals(3, verses[2].verse)
    }

    @Test
    fun testRangeIterator() {
        val passage = RangedPassage(v11n)
        val range1 = VerseRange(v11n, Verse(v11n, BibleBook.GEN, 1, 1), Verse(v11n, BibleBook.GEN, 1, 3))
        val range2 = VerseRange(v11n, Verse(v11n, BibleBook.JOHN, 1, 1), Verse(v11n, BibleBook.JOHN, 1, 2))

        passage.add(range1)
        passage.add(range2)

        val ranges = mutableListOf<VerseRange>()
        val iterator = passage.rangeIterator(RestrictionType.NONE)
        while (iterator.hasNext()) {
            ranges.add(iterator.next())
        }

        assertEquals(2, ranges.size)
    }

    @Test
    fun testGetName() {
        val passage = RangedPassage(v11n)
        val range = VerseRange(v11n, Verse(v11n, BibleBook.GEN, 1, 1), Verse(v11n, BibleBook.GEN, 1, 3))
        passage.add(range)

        val name = passage.getName()
        assertTrue(name.contains("Gen") || name.contains("Genesis"))
        assertTrue(name.contains("1") && name.contains("3"))
    }

    @Test
    fun testGetOsisRef() {
        val passage = RangedPassage(v11n)
        val range = VerseRange(v11n, Verse(v11n, BibleBook.JOHN, 1, 1), Verse(v11n, BibleBook.JOHN, 1, 1))
        passage.add(range)

        val osisRef = passage.getOsisRef()
        assertNotNull(osisRef)
        assertTrue(osisRef.contains("John") || osisRef.contains("Joh"))
        assertTrue(osisRef.contains("1.1"))
    }

    @Test
    fun testContainsVerse() {
        val passage = RangedPassage(v11n)
        val range = VerseRange(v11n, Verse(v11n, BibleBook.GEN, 1, 1), Verse(v11n, BibleBook.GEN, 1, 5))
        passage.add(range)

        assertTrue(passage.contains(Verse(v11n, BibleBook.GEN, 1, 1)))
        assertTrue(passage.contains(Verse(v11n, BibleBook.GEN, 1, 3)))
        assertTrue(passage.contains(Verse(v11n, BibleBook.GEN, 1, 5)))
        assertFalse(passage.contains(Verse(v11n, BibleBook.GEN, 1, 6)))
    }

    @Test
    fun testClear() {
        val passage = RangedPassage(v11n)
        val verse = Verse(v11n, BibleBook.JOHN, 1, 1)
        passage.add(verse)
        assertFalse(passage.isEmpty())

        passage.clear()
        assertTrue(passage.isEmpty())
        assertEquals(0, passage.countVerses())
    }

    @Test
    fun testIndexOf() {
        val passage = RangedPassage(v11n)
        val range = VerseRange(v11n, Verse(v11n, BibleBook.GEN, 1, 1), Verse(v11n, BibleBook.GEN, 1, 3))
        passage.add(range)

        assertEquals(0, passage.indexOf(Verse(v11n, BibleBook.GEN, 1, 1)))
        assertEquals(1, passage.indexOf(Verse(v11n, BibleBook.GEN, 1, 2)))
        assertEquals(2, passage.indexOf(Verse(v11n, BibleBook.GEN, 1, 3)))
        assertEquals(-1, passage.indexOf(Verse(v11n, BibleBook.GEN, 1, 4)))
    }

    @Test
    fun testBooksInPassage() {
        val passage = RangedPassage(v11n)
        val range1 = VerseRange(v11n, Verse(v11n, BibleBook.GEN, 1, 1), Verse(v11n, BibleBook.GEN, 1, 3))
        val range2 = VerseRange(v11n, Verse(v11n, BibleBook.JOHN, 1, 1), Verse(v11n, BibleBook.JOHN, 1, 2))

        passage.add(range1)
        assertEquals(1, passage.booksInPassage())

        passage.add(range2)
        assertEquals(2, passage.booksInPassage())
    }
}
