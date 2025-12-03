package org.crosswire.ksword.book.passage

import org.crosswire.ksword.passage.NoSuchVerseException
import org.crosswire.ksword.passage.Verse
import org.crosswire.ksword.passage.VerseRange
import org.crosswire.ksword.passage.VerseRangeFactory
import org.crosswire.ksword.versification.BibleBook
import org.crosswire.ksword.versification.system.Versifications
import org.junit.Assert
import org.junit.Test

class VerseRangeFactoryTest {

    @Test
    fun testSimpleRange() {
        Assert.assertEquals(rangeGenC1V1, VerseRangeFactory.fromString(v11n, "Gen 1:1-1"))
        Assert.assertEquals(rangeGenC1V12, VerseRangeFactory.fromString(v11n, "Gen 1:1-2"))
        Assert.assertEquals(rangeGenC1V12, VerseRangeFactory.fromString(v11n, "Gen 1:1-1:2"))
    }

    @Test
    fun testBookRange() {
        Assert.assertEquals(rangeGenMal, VerseRangeFactory.fromString(v11n, "Gen-Mal"))
    }

    @Test
    fun testSingleVerseRange() {
        Assert.assertEquals(rangeJohnC3V16, VerseRangeFactory.fromString(v11n, "John 3:16"))
    }

    @Test
    fun testWholeChapterRange() {
        // Psa 23 has 6 verses in KJV.
        // AccuracyType logic should expand "Psa 23" to the whole chapter.
        val expected = rangePsaC23
        val actual = VerseRangeFactory.fromString(v11n, "Ps 23")!!
        Assert.assertEquals("Expected start verse to be Psa 23:0", expected.start, actual.start)
        Assert.assertEquals("Expected end verse to be Psa 23:6", expected.end, actual.end)
        Assert.assertEquals("Expected range for Psa 23", expected, actual)
        Assert.assertTrue("Expected isWholeChapter to be true for Psa 23", actual.isWholeChapter)
    }

    @Test
    fun testVerseOrderSwap() {
        Assert.assertEquals(rangeGenC1V2To10, VerseRangeFactory.fromString(v11n, "Gen 1:10-Gen 1:2"))
    }

    @Test
    fun testInvalidRange_TooManyParts_returnsNull() {
        Assert.assertNull(VerseRangeFactory.fromString(v11n, "Gen 1:1-Gen 1:5-Gen 1:10"))
    }

    companion object {
        private val v11n = Versifications.getVersification("KJV")

        private val genC0V0 = Verse(v11n, BibleBook.GEN, 0, 0)
        private val genC1V1 = Verse(v11n, BibleBook.GEN, 1, 1)
        private val genC1V2 = Verse(v11n, BibleBook.GEN, 1, 2)
        private val genC1V10 = Verse(v11n, BibleBook.GEN, 1, 10)
        private val malEnd = Verse(v11n, BibleBook.MAL, 4, 6)

        // For John 3:16
        private val johnC3V16 = Verse(v11n, BibleBook.JOHN, 3, 16)
        private val rangeJohnC3V16 = VerseRange(v11n, johnC3V16, johnC3V16)

        // For Psa 23
        private val psaC23V0 = Verse(v11n, BibleBook.PS, 23, 0)
        private val psaC23V6 = Verse(v11n, BibleBook.PS, 23, 6) // Psa 23 has 6 verses in KJV
        private val rangePsaC23 = VerseRange(v11n, psaC23V0, psaC23V6)

        private val rangeGenC1V1 = VerseRange(v11n, genC1V1, genC1V1)
        private val rangeGenC1V12 = VerseRange(v11n, genC1V1, genC1V2)
        private val rangeGenC1V2To10 = VerseRange(v11n, genC1V2, genC1V10)
        // Adjusted to use genC1V1 as start for "Gen-Mal" range
        private val rangeGenMal = VerseRange(v11n, genC0V0, malEnd)
    }
}