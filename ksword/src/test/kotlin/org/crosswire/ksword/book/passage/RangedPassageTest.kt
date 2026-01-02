package org.crosswire.ksword.book.passage

import org.crosswire.ksword.passage.RangedPassage
import org.crosswire.ksword.passage.RestrictionType
import org.crosswire.ksword.passage.Verse
import org.crosswire.ksword.passage.VerseRange
import org.crosswire.ksword.versification.BibleBook
import org.crosswire.ksword.versification.system.Versifications
import org.junit.Assert
import org.junit.Test

class RangedPassageTest {

    private val v11n = Versifications.defaultVersification

    @Test
    fun testEmptyPassage() {
        val passage = RangedPassage(v11n)
        Assert.assertTrue(passage.isEmpty())
        Assert.assertEquals(0, passage.countVerses())
        Assert.assertEquals(0, passage.countRanges(RestrictionType.NONE))
    }

    @Test
    fun testAddSingleVerse() {
        val passage = RangedPassage(v11n)
        val verse = Verse(v11n, BibleBook.JOHN, 1, 1)
        passage.add(verse)
        Assert.assertEquals(1, passage.countVerses())
        Assert.assertTrue(passage.contains(verse))
    }

    @Test
    fun testAddVerseRange() {
        val passage = RangedPassage(v11n)
        val start = Verse(v11n, BibleBook.GEN, 1, 1)
        val end = Verse(v11n, BibleBook.GEN, 1, 5)
        val range = VerseRange(v11n, start, end)
        passage.add(range)
        Assert.assertEquals(5, passage.countVerses())
        Assert.assertTrue(passage.contains(start))
        Assert.assertTrue(passage.contains(end))
    }

    @Test
    fun testAddMultipleRanges() {
        val passage = RangedPassage(v11n)
        val range1 = VerseRange(v11n, Verse(v11n, BibleBook.GEN, 1, 1), Verse(v11n, BibleBook.GEN, 1, 3))
        val range2 = VerseRange(v11n, Verse(v11n, BibleBook.JOHN, 1, 1), Verse(v11n, BibleBook.JOHN, 1, 2))
        passage.add(range1)
        passage.add(range2)

        Assert.assertEquals(5, passage.countVerses())
        Assert.assertEquals(2, passage.countRanges(RestrictionType.NONE))
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
        Assert.assertEquals(6, passage.countVerses())
        Assert.assertEquals(1, passage.countRanges(RestrictionType.NONE))
    }

    @Test
    fun testRemoveVerse() {
        val passage = RangedPassage(v11n)
        val range = VerseRange(v11n, Verse(v11n, BibleBook.GEN, 1, 1), Verse(v11n, BibleBook.GEN, 1, 5))
        passage.add(range)

        // Remove middle verse
        val verseToRemove = Verse(v11n, BibleBook.GEN, 1, 3)
        passage.remove(verseToRemove)

        Assert.assertEquals(4, passage.countVerses())
        Assert.assertFalse(passage.contains(verseToRemove))
        Assert.assertEquals(2, passage.countRanges(RestrictionType.NONE)) // Should split into two ranges
    }

    @Test
    fun testGetVerseAt() {
        val passage = RangedPassage(v11n)
        val range = VerseRange(v11n, Verse(v11n, BibleBook.GEN, 1, 1), Verse(v11n, BibleBook.GEN, 1, 3))
        passage.add(range)

        val verse0 = passage.getVerseAt(0)
        Assert.assertNotNull(verse0)
        Assert.assertEquals(1, (verse0 as Verse).verse)

        val verse1 = passage.getVerseAt(1)
        Assert.assertNotNull(verse1)
        Assert.assertEquals(2, (verse1 as Verse).verse)

        val verse2 = passage.getVerseAt(2)
        Assert.assertNotNull(verse2)
        Assert.assertEquals(3, (verse2 as Verse).verse)

        // Out of bounds throws exception in AbstractPassage
        try {
            passage.getVerseAt(3)
            Assert.fail("Should have thrown IndexOutOfBoundsException")
        } catch (e: IndexOutOfBoundsException) {
            // Expected
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
        Assert.assertNotNull(retrievedRange0)
        Assert.assertEquals(3, retrievedRange0!!.getCardinality())

        val retrievedRange1 = passage.getRangeAt(1, RestrictionType.NONE)
        Assert.assertNotNull(retrievedRange1)
        Assert.assertEquals(2, retrievedRange1!!.getCardinality())

        // Out of bounds throws exception in AbstractPassage
        try {
            passage.getRangeAt(2, RestrictionType.NONE)
            Assert.fail("Should have thrown IndexOutOfBoundsException")
        } catch (e: IndexOutOfBoundsException) {
            // Expected
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

        Assert.assertEquals(3, verses.size)
        Assert.assertEquals(1, verses[0].verse)
        Assert.assertEquals(2, verses[1].verse)
        Assert.assertEquals(3, verses[2].verse)
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

        Assert.assertEquals(2, ranges.size)
    }

    @Test
    fun testGetName() {
        val passage = RangedPassage(v11n)
        val range = VerseRange(v11n, Verse(v11n, BibleBook.GEN, 1, 1), Verse(v11n, BibleBook.GEN, 1, 3))
        passage.add(range)

        val name = passage.getName()
        Assert.assertTrue(name.contains("Gen") || name.contains("Genesis"))
        Assert.assertTrue(name.contains("1") && name.contains("3"))
    }

    @Test
    fun testGetOsisRef() {
        val passage = RangedPassage(v11n)
        val range = VerseRange(v11n, Verse(v11n, BibleBook.JOHN, 1, 1), Verse(v11n, BibleBook.JOHN, 1, 1))
        passage.add(range)

        val osisRef = passage.getOsisRef()
        Assert.assertNotNull(osisRef)
        Assert.assertTrue(osisRef.contains("John") || osisRef.contains("Joh"))
        Assert.assertTrue(osisRef.contains("1.1"))
    }

    @Test
    fun testContainsVerse() {
        val passage = RangedPassage(v11n)
        val range = VerseRange(v11n, Verse(v11n, BibleBook.GEN, 1, 1), Verse(v11n, BibleBook.GEN, 1, 5))
        passage.add(range)

        Assert.assertTrue(passage.contains(Verse(v11n, BibleBook.GEN, 1, 1)))
        Assert.assertTrue(passage.contains(Verse(v11n, BibleBook.GEN, 1, 3)))
        Assert.assertTrue(passage.contains(Verse(v11n, BibleBook.GEN, 1, 5)))
        Assert.assertFalse(passage.contains(Verse(v11n, BibleBook.GEN, 1, 6)))
    }

    @Test
    fun testClear() {
        val passage = RangedPassage(v11n)
        val verse = Verse(v11n, BibleBook.JOHN, 1, 1)
        passage.add(verse)
        Assert.assertFalse(passage.isEmpty())

        passage.clear()
        Assert.assertTrue(passage.isEmpty())
        Assert.assertEquals(0, passage.countVerses())
    }

    @Test
    fun testIndexOf() {
        val passage = RangedPassage(v11n)
        val range = VerseRange(v11n, Verse(v11n, BibleBook.GEN, 1, 1), Verse(v11n, BibleBook.GEN, 1, 3))
        passage.add(range)

        Assert.assertEquals(0, passage.indexOf(Verse(v11n, BibleBook.GEN, 1, 1)))
        Assert.assertEquals(1, passage.indexOf(Verse(v11n, BibleBook.GEN, 1, 2)))
        Assert.assertEquals(2, passage.indexOf(Verse(v11n, BibleBook.GEN, 1, 3)))
        Assert.assertEquals(-1, passage.indexOf(Verse(v11n, BibleBook.GEN, 1, 4)))
    }

    @Test
    fun testBooksInPassage() {
        val passage = RangedPassage(v11n)
        val range1 = VerseRange(v11n, Verse(v11n, BibleBook.GEN, 1, 1), Verse(v11n, BibleBook.GEN, 1, 3))
        val range2 = VerseRange(v11n, Verse(v11n, BibleBook.JOHN, 1, 1), Verse(v11n, BibleBook.JOHN, 1, 2))

        passage.add(range1)
        Assert.assertEquals(1, passage.booksInPassage())

        passage.add(range2)
        Assert.assertEquals(2, passage.booksInPassage())
    }
}