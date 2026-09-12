package org.crosswire.ksword.passage

import org.crosswire.ksword.versification.system.Versifications
import kotlin.test.Test
import kotlin.test.assertEquals

/** rangeIterator(CHAPTER) is what AbstractBackend uses to read a passage, so it must lose no verse. */
class RangedPassageChapterSplitTest {

    private val v11n = Versifications.getVersification("KJV")

    @Test
    fun chapterRestrictedRangesCoverEveryVerse() {
        val passage = RangedPassage(v11n, "Gen 1:30-2:3")

        val ranges = passage.rangeIterator(RestrictionType.CHAPTER).asSequence().toList()

        assertEquals(
            passage.getCardinality(),
            ranges.sumOf { it.getCardinality() },
            "ranges $ranges should cover all ${passage.getCardinality()} verses",
        )
    }

    @Test
    fun unrestrictedRangesCoverEveryVerse() {
        val passage = RangedPassage(v11n, "Gen 1:30-2:3")

        val ranges = passage.rangeIterator(RestrictionType.NONE).asSequence().toList()

        assertEquals(passage.getCardinality(), ranges.sumOf { it.getCardinality() }, "ranges $ranges")
    }
}
