package org.crosswire.ksword.book.passage

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

    companion object {
        private val v11n = Versifications.getVersification("KJV")

        private val genC0V0 = Verse(v11n, BibleBook.GEN, 0, 0);
        private val genC1V1 = Verse(v11n, BibleBook.GEN, 1, 1);
        private val genC1V2 = Verse(v11n, BibleBook.GEN, 1, 2);
        private val malEnd = Verse(v11n, BibleBook.MAL, 4, 6);

        private val rangeGenC1V1 = VerseRange(v11n, genC1V1, genC1V1)
        private val rangeGenC1V12 = VerseRange(v11n, genC1V1, genC1V2)
        private val rangeGenMal = VerseRange(v11n, genC0V0, malEnd)
    }
}