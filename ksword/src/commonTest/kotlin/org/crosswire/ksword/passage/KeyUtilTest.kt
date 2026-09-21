package org.crosswire.ksword.passage

import org.crosswire.ksword.versification.BibleBook
import org.crosswire.ksword.versification.system.Versifications
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertSame

class KeyUtilTest {

    private val v11n = Versifications.getVersification("KJV")
    private fun gen(chapter: Int, verse: Int) = Verse(v11n, BibleBook.GEN, chapter, verse)

    @Test
    fun getPassageHoldsAVerseOrRange() {
        assertEquals(RangedPassage(v11n, "Gen 1:1"), KeyUtil.getPassage(gen(1, 1)))
        assertEquals(RangedPassage(v11n, "Gen 1:1-3"), KeyUtil.getPassage(VerseRange(v11n, gen(1, 1), gen(1, 3))))
    }

    @Test
    fun getPassageReturnsAPassageAsItIs() {
        val passage = RangedPassage(v11n, "Gen 1:1-3")
        assertSame(passage, KeyUtil.getPassage(passage))
    }

    @Test
    fun getPassageRejectsOtherKeys() {
        assertFailsWith<ClassCastException> { KeyUtil.getPassage(DefaultLeafKeyList("Aaron")) }
    }

    @Test
    fun getVerseRangeWidensAVerseToARangeOfOne() {
        assertEquals(VerseRange(v11n, gen(1, 1), gen(1, 1)), KeyUtil.getVerseRange(gen(1, 1)))
    }

    @Test
    fun getVerseRangeReturnsARangeAsItIs() {
        val range = VerseRange(v11n, gen(1, 1), gen(1, 3))
        assertSame(range, KeyUtil.getVerseRange(range))
    }

    @Test
    fun getVerseRangeSpansAPassageIncludingItsGaps() {
        assertEquals(VerseRange(v11n, gen(1, 1), gen(1, 5)), KeyUtil.getVerseRange(RangedPassage(v11n, "Gen 1:1-2, 5")))
    }

    @Test
    fun getVerseRangeRejectsAnEmptyPassageAndOtherKeys() {
        assertFailsWith<ClassCastException> { KeyUtil.getVerseRange(PassageKeyFactory.createEmptyKeyList(v11n)) }
        assertFailsWith<ClassCastException> { KeyUtil.getVerseRange(DefaultLeafKeyList("Aaron")) }
    }
}
