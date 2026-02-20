package org.crosswire.ksword.book.passage

import org.crosswire.ksword.passage.OsisParser
import org.crosswire.ksword.versification.system.Versifications
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class OsisParserTest {
    private val testV11n = Versifications.defaultVersification

    private val osisParser = OsisParser()

    @Test
    fun testVerseParsing() {
        assertEquals("Gen.1.1", osisParser.parseOsisID(testV11n, "Gen.1.1")!!.getOsisID())
        assertEquals("Gen.1.2", osisParser.parseOsisID(testV11n, "Gen.1.2")!!.getOsisID())
        assertEquals("Gen.2.1", osisParser.parseOsisID(testV11n, "Gen.2.1")!!.getOsisID())
        assertEquals("Exod.2.1", osisParser.parseOsisID(testV11n, "Exod.2.1")!!.getOsisID())
        assertEquals("3John.1.1", osisParser.parseOsisID(testV11n, "3John.1.1")!!.getOsisID())
    }

    @Test
    fun testVerseParsingErrors() {
        assertEquals(null, osisParser.parseOsisID(testV11n, "Gen.1.1.4"))
        assertEquals(null, osisParser.parseOsisID(testV11n, "Gen."))
        assertEquals(null, osisParser.parseOsisID(testV11n, "Gen21"))
        assertEquals(null, osisParser.parseOsisID(testV11n, ""))
        assertEquals(null, osisParser.parseOsisID(testV11n, null))
    }

    @Test
    fun testVerseRangeParsing() {
        assertEquals("Gen.1.1-Gen.1.3", osisParser.parseOsisRef(testV11n, "Gen.1.1-Gen.1.3")!!.getOsisRef())
        assertEquals("Gen.1.2-Gen.1.4", osisParser.parseOsisRef(testV11n, "Gen.1.2-Gen.1.4")!!.getOsisRef())
        assertEquals("Gen.2.2-Gen.3.4", osisParser.parseOsisRef(testV11n, "Gen.2.2-Gen.3.4")!!.getOsisRef())
        assertEquals("Exod.2.2-Lev.1.1", osisParser.parseOsisRef(testV11n, "Exod.2.2-Lev.1.1")!!.getOsisRef())
        assertEquals("3John.1.2-3John.1.10", osisParser.parseOsisRef(testV11n, "3John.1.2-3John.1.10")!!.getOsisRef())
    }

    @Test
    fun testChapterParsing() {
        assertEquals("Gen.1", osisParser.parseOsisRef(testV11n, "Gen.1")!!.getOsisRef())
        assertEquals("Mark.10", osisParser.parseOsisRef(testV11n, "Mark.10")!!.getOsisRef())
        assertEquals("Gen.1-Gen.3", osisParser.parseOsisRef(testV11n, "Gen.1-Gen.3")!!.getOsisRef())
        assertEquals("Obad", osisParser.parseOsisRef(testV11n, "Obad.1")!!.getOsisRef())
        assertEquals("Obad", osisParser.parseOsisRef(testV11n, "Obad")!!.getOsisRef())
        assertEquals("Gen", osisParser.parseOsisRef(testV11n, "Gen")!!.getOsisRef())
    }

    @Test
    fun testMixedLengthParsing() {
        assertEquals("Gen-Exod.1", osisParser.parseOsisRef(testV11n, "Gen-Exod.1")!!.getOsisRef())
        assertEquals("Gen.2-Gen.3", osisParser.parseOsisRef(testV11n, "Gen.2.1-Gen.3")!!.getOsisRef())
        assertEquals("Gen.1-Gen.3", osisParser.parseOsisRef(testV11n, "Gen-Gen.3")!!.getOsisRef())
        assertEquals("Gen.1-Gen.3.4", osisParser.parseOsisRef(testV11n, "Gen-Gen.3.4")!!.getOsisRef())
        assertEquals("Gen.3.4-Exod", osisParser.parseOsisRef(testV11n, "Gen.3.4-Exod")!!.getOsisRef())
    }

    @Test
    fun testVerseRangeParsingErrors() {
        assertEquals(null, osisParser.parseOsisRef(testV11n, "Gen.1.1.4"))
        assertEquals(null, osisParser.parseOsisRef(testV11n, "Gen."))
        assertEquals(null, osisParser.parseOsisRef(testV11n, "Gen.1."))
        assertEquals(null, osisParser.parseOsisRef(testV11n, "Gen21"))
        assertEquals(null, osisParser.parseOsisRef(testV11n, "Gen.2.1-"))
        assertEquals(null, osisParser.parseOsisRef(testV11n, "Gen1-Gen.3"))
        assertEquals(null, osisParser.parseOsisRef(testV11n, "-Gen.3"))
        assertEquals(null, osisParser.parseOsisRef(testV11n, ""))
    }

    @Test
    fun testVerseWithPart() {
        assertEquals("3John.1.14!a", osisParser.parseOsisID(testV11n, "3John.1.14!a")!!.getOsisID())
    }
}
