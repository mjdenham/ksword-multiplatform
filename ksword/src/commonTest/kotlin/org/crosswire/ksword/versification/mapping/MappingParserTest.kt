package org.crosswire.ksword.versification.mapping

import org.crosswire.ksword.passage.Verse
import org.crosswire.ksword.versification.BibleBook
import org.crosswire.ksword.versification.system.Versifications
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class MappingParserTest {

    private val kjva = Versifications.getVersification("KJVA")
    private val synodal = Versifications.getVersification("Synodal")

    private fun ordinal(v11n: org.crosswire.ksword.versification.Versification, book: BibleBook, chapter: Int, verse: Int) =
        Verse(v11n, book, chapter, verse).ordinal

    @Test
    fun commentsAndBlankLinesAreSkipped() {
        val entries = MappingParser.parseMappingSource("# comment\n\nGen.1.1=Gen.1.2\n# another\n\n")
        assertEquals(listOf(MappingEntry("Gen.1.1", "Gen.1.2")), entries)
    }

    @Test
    fun splitsAtFirstEqualsOnly() {
        val entries = MappingParser.parseMappingSource("A=B=C")
        assertEquals(listOf(MappingEntry("A", "B=C")), entries)
    }

    @Test
    fun lineWithoutEqualsHasNullRight() {
        val entries = MappingParser.parseMappingSource("!zerosUnmapped\nGen.1.1=Gen.1.2")
        assertEquals(MappingEntry("!zerosUnmapped", null), entries[0])
        assertNull(entries[0].right)
    }

    @Test
    fun questionMarkOnSourceSideIsAbsentInLeft() {
        assertEquals(ParsedSide.AbsentInLeft, MappingParser.parseSide(synodal, "?", isSourceSide = true))
    }

    @Test
    fun questionMarkOnKjvaSideIsSectionIncludingTheQuestionMark() {
        assertEquals(
            ParsedSide.Section("?SongOfThreeChildren"),
            MappingParser.parseSide(kjva, "?SongOfThreeChildren", isSourceSide = false)
        )
        assertEquals(ParsedSide.Section("?"), MappingParser.parseSide(kjva, "?", isSourceSide = false))
    }

    @Test
    fun offsetSyntaxIsRejected() {
        for (offset in listOf("+1", "-1", "+23")) {
            assertFailsWith<MappingSyntaxException>("offset '$offset' should be rejected") {
                MappingParser.parseSide(kjva, offset, isSourceSide = false)
            }
        }
    }

    @Test
    fun singleVerseParses() {
        val side = MappingParser.parseSide(kjva, "Gen.1.3", isSourceSide = false)
        val expected = ordinal(kjva, BibleBook.GEN, 1, 3)
        assertEquals(ParsedSide.Verses(expected, expected, null), side)
    }

    @Test
    fun singleVerseKeepsPart() {
        val side = MappingParser.parseSide(kjva, "1Kgs.18.33!a", isSourceSide = false)
        val expected = ordinal(kjva, BibleBook.KGS1, 18, 33)
        assertEquals(ParsedSide.Verses(expected, expected, "a"), side)
    }

    @Test
    fun verseZeroIsValid() {
        val side = MappingParser.parseSide(kjva, "Ps.3.0-Ps.3.8", isSourceSide = false)
        assertEquals(ordinal(kjva, BibleBook.PS, 3, 0), (side as ParsedSide.Verses).startOrdinal)
        assertEquals(9, side.cardinality)
    }

    @Test
    fun rangeDropsPartsOnEndpoints() {
        // Synodal.properties: 1Kgs.18.34=1Kgs.18.33!b-1Kgs.18.34 — JSword ClassCastExceptions on unmap of this
        val side = MappingParser.parseSide(kjva, "1Kgs.18.33!b-1Kgs.18.34", isSourceSide = false)
        val start = ordinal(kjva, BibleBook.KGS1, 18, 33)
        val end = ordinal(kjva, BibleBook.KGS1, 18, 34)
        assertEquals(ParsedSide.Verses(start, end, null), side)
        assertEquals(2, (side as ParsedSide.Verses).cardinality)
    }

    @Test
    fun invalidReferencesAreRejected() {
        for (bad in listOf("", "NotABook.1.1", "Gen.1.99", "Gen.99.1")) {
            assertFailsWith<MappingSyntaxException>("'$bad' should be rejected") {
                MappingParser.parseSide(kjva, bad, isSourceSide = false)
            }
        }
    }

    @Test
    fun realSynodalLinesParse() {
        // Representative real lines from Synodal.properties
        for (line in listOf(
            "Num.13.1-Num.13.34=Num.12.16-Num.13.33",
            "Ps.9.22-Ps.9.39=Ps.10.1-Ps.10.18",
            "Ps.10.1=Ps.11.0-Ps.11.1",
            "1Sam.20.43=1Sam.20.42"
        )) {
            val entry = MappingParser.parseMappingSource(line).single()
            val left = MappingParser.parseSide(synodal, entry.left, isSourceSide = true)
            val right = MappingParser.parseSide(kjva, entry.right, isSourceSide = false)
            assertEquals(true, left is ParsedSide.Verses, "left of '$line'")
            assertEquals(true, right is ParsedSide.Verses, "right of '$line'")
        }
    }
}
