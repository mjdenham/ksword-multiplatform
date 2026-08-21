package org.crosswire.ksword.versification.mapping

import org.crosswire.ksword.passage.OsisParser
import org.crosswire.ksword.versification.Versification
import org.crosswire.ksword.versification.system.Versifications
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Port of JSword's VersificationsMapperTest — the two-hop (source -> KJVA -> target) behaviour
 * against the real mapping data. Assertion literals match the Java test.
 */
class VersificationsMapperTest {

    private val kjva: Versification = Versifications.getVersification("KJVA")
    private val catholic: Versification = Versifications.getVersification("Catholic")
    private val catholic2: Versification = Versifications.getVersification("Catholic2")
    private val synodal: Versification = Versifications.getVersification("Synodal")

    private fun doTest(source: Versification, sourceKey: String, target: Versification, targetKey: String) {
        val verse = OsisParser().parseOsisID(source, sourceKey)!!
        val result = VersificationsMapper.mapVerse(verse, target)
        assertEquals(targetKey, osisRef(target, result), "$sourceKey (${source.name} -> ${target.name})")
    }

    @Test
    fun testTwoStepVersification() {
        doTest(catholic2, "1Sam.20.42", synodal, "1Sam.20.43")
        doTest(catholic2, "Ps.35.1", synodal, "Ps.34.1")
    }

    @Test
    fun testTwoStepVersificationUsesParts() {
        doTest(catholic, "Tob.7.11", catholic2, "Tob.7.11")
        doTest(catholic, "Tob.7.12", catholic2, "Tob.7.12")
        doTest(catholic, "Tob.10.14", catholic2, "Tob.10.14")
        doTest(catholic, "Dan.3.52", catholic2, "Dan.3.52")
        doTest(catholic, "Ps.35.1", catholic2, "Ps.35.1")
    }

    @Test
    fun testSingleStepToKJV() {
        doTest(catholic2, "Gen.32.1", kjva, "Gen.31.55")
        doTest(catholic2, "Dan.3.60", kjva, "PrAzar.1.38")
        doTest(catholic2, "Esth.15.5", kjva, "AddEsth.15.2")
        doTest(catholic2, "Dan.3.52", kjva, "PrAzar.1.29-PrAzar.1.30")
    }

    @Test
    fun testMissingMappings() {
        doTest(catholic, "Rev.1.1", catholic2, "Rev.1.1")
    }

    @Test
    fun testSameKey() {
        doTest(catholic, "Rev.1.1", catholic, "Rev.1.1")
    }

    @Test
    fun testSingleStepFromKJV() {
        doTest(kjva, "Gen.31.55", catholic, "Gen.32.1")
        doTest(kjva, "AddEsth.13.5", catholic2, "Esth.13.5")
        doTest(kjva, "Tob.11.1", catholic, "Tob.10.14 Tob.11.1")
    }

    @Test
    fun testMapVerseZero() {
        doTest(kjva, "Gen.1.0", kjva, "Gen.1.0")
        doTest(kjva, "Gen.1.0", synodal, "Gen.1.0")
        doTest(kjva, "Ps.50.0", kjva, "Ps.50.0")
        doTest(kjva, "Ps.50.0", catholic, "Ps.50.1")
        doTest(kjva, "Ps.50.0", synodal, "Ps.49.1")
        doTest(synodal, "Ps.49.1", kjva, "Ps.50.0-Ps.50.1")
    }

    /** Replaces JSword's Passage-based testPassageResolves: every verse of Catholic Gen 32 resolves in Catholic2. */
    @Test
    fun testChapterResolves() {
        for (v in 1..catholic.getLastVerse(org.crosswire.ksword.versification.BibleBook.GEN, 32)) {
            val verse = org.crosswire.ksword.passage.Verse(
                catholic, org.crosswire.ksword.versification.BibleBook.GEN, 32, v
            )
            val result = VersificationsMapper.mapVerse(verse, catholic2)
            assertEquals("Gen.32.$v", osisRef(catholic2, result), "Gen.32.$v should map identically")
        }
    }

    /** Chapter intros: untitled round-trip exactly, titled converge to verse 1, splices shift one chapter. */
    @Test
    fun testChapterIntroMapping() {
        val kjv = Versifications.getVersification("KJV")
        doTest(synodal, "Ps.103.0", kjv, "Ps.104.0")
        doTest(kjv, "Ps.104.0", synodal, "Ps.103.0")
        doTest(synodal, "Ps.3.0", kjv, "Ps.3.0")
        doTest(kjv, "Ps.3.0", synodal, "Ps.3.1")
        doTest(synodal, "Num.13.0", kjv, "Num.12.0")
    }

    /** The corrected Hosea line (upstream typo, see MappingData.KNOWN_UPSTREAM_FIXES) maps as intended. */
    @Test
    fun testCatholicHoseaFix() {
        doTest(catholic, "Hos.12.1", kjva, "Hos.11.12")
        doTest(catholic, "Hos.12.2", kjva, "Hos.12.1")
        doTest(catholic, "Hos.12.15", kjva, "Hos.12.14")
        doTest(kjva, "Hos.12.14", catholic, "Hos.12.15")
    }
}
