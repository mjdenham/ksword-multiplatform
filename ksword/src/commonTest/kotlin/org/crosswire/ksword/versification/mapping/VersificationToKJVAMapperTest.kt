package org.crosswire.ksword.versification.mapping

import org.crosswire.ksword.passage.OsisParser
import org.crosswire.ksword.passage.Verse
import org.crosswire.ksword.versification.Versification
import org.crosswire.ksword.versification.system.Versifications
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Port of JSword's VersificationToKJVMapperTest — the executable specification of the mapping DSL.
 * Assertion literals are kept identical to the Java test where the behaviour is unchanged.
 */
class VersificationToKJVAMapperTest {

    private val kjva: Versification = Versifications.getVersification("KJVA")
    private val nonKjv: Versification = Versifications.getVersification("Catholic")
    private val osisParser = OsisParser()
    private val entries = mutableListOf<MappingEntry>()
    private lateinit var mapper: VersificationToKJVAMapper

    private fun addProperty(left: String, right: String) {
        entries.add(MappingEntry(left, right))
    }

    private fun init(source: Versification = nonKjv) {
        mapper = VersificationToKJVAMapper(source, entries)
        assertTrue(mapper.errors.isEmpty(), "unexpected load errors: ${mapper.errors}")
    }

    private fun parseRef(v11n: Versification, key: String): QualifiedRef {
        val range = osisParser.parseOsisRef(v11n, key)!!
        return if (range.start.ordinal == range.end.ordinal) {
            QualifiedRef.Single(range.start.ordinal, range.start.subIdentifier?.takeIf { it.isNotEmpty() })
        } else {
            QualifiedRef.Range(range.start.ordinal, range.end.ordinal)
        }
    }

    /** Port of the Java test's map(): maps and renders whole KJVA verses, skipping sections. */
    private fun map(key: String): String {
        val source = mapper.sourceV11n
        val verse = osisParser.parseOsisRef(source, key)!!.start
        val ordinals = mapper.map(verse.ordinal)
            .flatMap { ref ->
                when (ref) {
                    is QualifiedRef.Single -> listOf(ref.ordinal)
                    is QualifiedRef.Range -> (ref.startOrdinal..ref.endOrdinal).toList()
                    is QualifiedRef.Section -> emptyList()
                }
            }
        return osisRef(kjva, ordinals.toIntArray())
    }

    /** Port of the Java test's unmap(): KJVA ref string -> source osis ref. */
    private fun unmap(kjvVerse: String): String =
        osisRef(mapper.sourceV11n, mapper.unmap(parseRef(kjva, kjvVerse)))

    /** Port of the Java test's mapToQualifiedKey(): qualified refs joined by ' ', parts and sections kept. */
    private fun mapToQualifiedKey(verseKey: String): String {
        val source = mapper.sourceV11n
        val verse = osisParser.parseOsisRef(source, verseKey)!!.start
        return mapper.map(verse.ordinal).joinToString(" ") { ref ->
            when (ref) {
                is QualifiedRef.Single ->
                    Verse(kjva, ref.ordinal).getOsisID() + (ref.part?.let { "!$it" } ?: "")
                is QualifiedRef.Range ->
                    "${Verse(kjva, ref.startOrdinal).getOsisID()}-${Verse(kjva, ref.endOrdinal).getOsisID()}"
                is QualifiedRef.Section -> ref.name
            }
        }
    }

    @Test
    fun testSimpleMapping() {
        addProperty("Gen.1.1", "Gen.1.2")
        init()
        assertEquals("Gen.1.2", map("Gen.1.1"))
        assertEquals("Gen.1.1", unmap("Gen.1.2"))
    }

    @Test
    fun testTwoLeftMappings() {
        addProperty("Gen.1.1", "Gen.1.1")
        addProperty("Gen.1.2", "Gen.1.1")
        init()

        // map always go to 1.1
        assertEquals("Gen.1.1", map("Gen.1.1"))
        assertEquals("Gen.1.1", map("Gen.1.2"))
        assertEquals("Gen.1.1-Gen.1.2", unmap("Gen.1.1"))
    }

    @Test
    fun testLeftRangeMappings() {
        addProperty("Gen.1.1-Gen.1.2", "Gen.1.1")
        init()
        assertEquals("Gen.1.1", map("Gen.1.1"))
        assertEquals("Gen.1.1", map("Gen.1.2"))
        assertEquals("Gen.1.1-Gen.1.2", unmap("Gen.1.1"))
    }

    @Test
    fun testTwoRightMappings() {
        addProperty("Gen.1.1", "Gen.1.1")
        addProperty("Gen.1.1", "Gen.1.2")
        init()
        assertEquals("Gen.1.1-Gen.1.2", map("Gen.1.1"))
    }

    @Test
    fun testRightRangeMappings() {
        addProperty("Gen.1.1", "Gen.1.1-Gen.1.2")
        init()

        assertEquals("Gen.1.1-Gen.1.2", map("Gen.1.1"))
        assertEquals("Gen.1.1", unmap("Gen.1.1"))
        assertEquals("Gen.1.1", unmap("Gen.1.2"))
    }

    @Test
    fun testMissingMapping() {
        init()

        assertEquals("Gen.1.1", map("Gen.1.1"))
        assertEquals("Gen.1.1", unmap("Gen.1.1"))
    }

    @Test
    fun testRangeToRange() {
        addProperty("Gen.1.1-Gen.1.3", "Gen.1.2-Gen.1.4")
        init()

        assertEquals("Gen.1.2", map("Gen.1.1"))
        assertEquals("Gen.1.3", map("Gen.1.2"))
        assertEquals("Gen.1.4", map("Gen.1.3"))
        assertEquals("Gen.1.1", unmap("Gen.1.2"))
        assertEquals("Gen.1.2", unmap("Gen.1.3"))
        assertEquals("Gen.1.3", unmap("Gen.1.4"))
    }

    /** Replaces JSword's testMappingWithPositiveOffset: the offset syntax is deliberately unsupported. */
    @Test
    fun offsetSyntaxIsRejected() {
        addProperty("Gen.1.1-Gen.1.2", "+1")
        mapper = VersificationToKJVAMapper(nonKjv, entries)
        assertEquals(1, mapper.errors.size, "offset entry should be recorded as an error: ${mapper.errors}")
        // and the mapper still behaves as if the entry were absent
        assertEquals("Gen.1.1", map("Gen.1.1"))
    }

    @Test
    fun testPartsAreReturned() {
        addProperty("Gen.1.1", "Gen.1.3!a")
        addProperty("Gen.1.2", "Gen.1.3!b")
        init()

        assertEquals("Gen.1.3!a", mapToQualifiedKey("Gen.1.1"))
        assertEquals("Gen.1.3!b", mapToQualifiedKey("Gen.1.2"))
        assertEquals("Gen.1.3", map("Gen.1.1"))
        assertEquals("Gen.1.3", map("Gen.1.2"))
        assertEquals("Gen.1.1", unmap("Gen.1.3!a"))
        assertEquals("Gen.1.2", unmap("Gen.1.3!b"))
        assertEquals("Gen.1.1-Gen.1.2", unmap("Gen.1.3"))
    }

    @Test
    fun testExtraUnmappedVersesSingle() {
        addProperty("Dan.3.32", "?StoryOfThreeYoungMen.1.1")
        init()

        assertEquals("?StoryOfThreeYoungMen.1.1", mapToQualifiedKey("Dan.3.32"))
        assertEquals(
            "Dan.3.32",
            osisRef(mapper.sourceV11n, mapper.unmap(QualifiedRef.Section("?StoryOfThreeYoungMen.1.1")))
        )
    }

    @Test
    fun testExtraUnmappedVersesWithRange() {
        addProperty("Dan.3.31-Dan.3.68", "?StoryOfThreeYoungMen")
        init()

        assertEquals("?StoryOfThreeYoungMen", mapToQualifiedKey("Dan.3.31"))
        assertEquals("?StoryOfThreeYoungMen", mapToQualifiedKey("Dan.3.32"))
        assertEquals("?StoryOfThreeYoungMen", mapToQualifiedKey("Dan.3.45"))
        assertEquals(
            "Dan.3.31-Dan.3.68",
            osisRef(mapper.sourceV11n, mapper.unmap(QualifiedRef.Section("?StoryOfThreeYoungMen")))
        )
    }

    @Test
    fun testAbsentVerses() {
        addProperty("?", "Gen.1.1")
        init()
        assertEquals("", unmap("Gen.1.1"))
    }

    @Test
    fun testAbsentVersesWithRange() {
        addProperty("?", "Gen.1.1-Gen.1.3")
        init()
        assertEquals("", unmap("Gen.1.1"))
        assertEquals("", unmap("Gen.1.2"))
        assertEquals("", unmap("Gen.1.3"))
        assertEquals("Gen.1.4", unmap("Gen.1.4"))
    }

    /** The verse-0 alignment: cardinalities differ by one because the left range includes a chapter intro. */
    @Test
    fun testSkipVerse0Alignment() {
        // Real line from Segond.properties. Segond Exod 7 ends at v29; the left range spans the
        // Exod 8 chapter intro (verse 0), so left cardinality is one greater than the right.
        addProperty("Exod.7.26-Exod.8.28", "Exod.8.1-Exod.8.32")
        init(Versifications.getVersification("Segond"))

        assertEquals("Exod.8.1", map("Exod.7.26"))
        assertEquals("Exod.8.4", map("Exod.7.29"))
        // the extra verse 0 and its neighbour are both mapped to the same KJVA verse
        assertEquals("Exod.8.5", map("Exod.8.0"))
        assertEquals("Exod.8.5", map("Exod.8.1"))
        assertEquals("Exod.8.0-Exod.8.1", unmap("Exod.8.5"))
        assertEquals("Exod.8.32", map("Exod.8.28"))
        assertEquals("Exod.8.28", unmap("Exod.8.32"))
    }

    /** JSword throws ClassCastException unmapping this real Synodal entry; the port drops endpoint parts instead. */
    @Test
    fun testRangeWithPartOnEndpointDoesNotCrash() {
        addProperty("1Kgs.18.33", "1Kgs.18.33!a")
        addProperty("1Kgs.18.34", "1Kgs.18.33!b-1Kgs.18.34")
        init(Versifications.getVersification("Synodal"))

        assertEquals("1Kgs.18.33!a", mapToQualifiedKey("1Kgs.18.33"))
        assertEquals("1Kgs.18.33-1Kgs.18.34", mapToQualifiedKey("1Kgs.18.34"))
        assertEquals("1Kgs.18.33", unmap("1Kgs.18.33!a"))
        assertEquals("1Kgs.18.33-1Kgs.18.34", unmap("1Kgs.18.33"))
        // unmapping the range ref itself must not crash
        assertEquals(
            "1Kgs.18.33-1Kgs.18.34",
            osisRef(mapper.sourceV11n, mapper.unmap(parseRef(kjva, "1Kgs.18.33-1Kgs.18.34")))
        )
    }

    @Test
    fun testCardinalityMismatchIsRecordedNotThrown() {
        addProperty("Gen.1.1-Gen.1.3", "Gen.1.5-Gen.1.9")
        addProperty("Gen.2.1", "Gen.2.2")
        mapper = VersificationToKJVAMapper(nonKjv, entries)

        assertEquals(1, mapper.errors.size, "expected one cardinality error: ${mapper.errors}")
        // the valid entry after the bad one still loaded
        assertEquals("Gen.2.2", map("Gen.2.1"))
    }

    @Test
    fun testNonNumericLineIsRecordedNotThrown() {
        addProperty("Gen.1.x", "Gen.1.2")
        addProperty("Gen.1.3", "Gen.1.4 ")   // trailing space in the number
        addProperty("Gen.2.1", "Gen.2.2")
        mapper = VersificationToKJVAMapper(nonKjv, entries)

        assertEquals(2, mapper.errors.size, "expected two recorded errors: ${mapper.errors}")
        assertEquals("Gen.2.2", map("Gen.2.1"))
    }

    /**
     * An entry erroring mid-walk (diff==1 but no verse 0 where expected) is recorded, and may
     * leave a partial prefix of its pairs applied. Acceptable because MappingDataIntegrityTest
     * guarantees shipped data never has unexpected errors, so this path is unreachable in
     * production; this test just pins that such an entry is at least reported.
     */
    @Test
    fun testMidWalkCardinalityErrorIsRecorded() {
        addProperty("Gen.1.1-Gen.1.4", "Gen.1.1-Gen.1.3")
        mapper = VersificationToKJVAMapper(nonKjv, entries)

        assertEquals(1, mapper.errors.size, "expected one cardinality error: ${mapper.errors}")
    }
}
