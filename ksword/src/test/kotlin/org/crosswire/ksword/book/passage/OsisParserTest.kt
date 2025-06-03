package org.crosswire.ksword.book.passage

import org.crosswire.ksword.passage.OsisParser
import org.crosswire.ksword.versification.system.Versifications
import org.junit.Assert
import org.junit.Test

class OsisParserTest {
    private val testV11n = Versifications.defaultVersification

    private val osisParser = OsisParser()

    @Test
    fun testVerseParsing() {
        Assert.assertEquals("Gen.1.1", osisParser.parseOsisID(testV11n, "Gen.1.1")!!.getOsisID())
        Assert.assertEquals("Gen.1.2", osisParser.parseOsisID(testV11n, "Gen.1.2")!!.getOsisID())
        Assert.assertEquals("Gen.2.1", osisParser.parseOsisID(testV11n, "Gen.2.1")!!.getOsisID())
        Assert.assertEquals("Exod.2.1", osisParser.parseOsisID(testV11n, "Exod.2.1")!!.getOsisID())
        Assert.assertEquals("3John.1.1", osisParser.parseOsisID(testV11n, "3John.1.1")!!.getOsisID())
    }

    @Test
    fun testVerseParsingErrors() {
        Assert.assertEquals(null, osisParser.parseOsisID(testV11n, "Gen.1.1.4"))
        Assert.assertEquals(null, osisParser.parseOsisID(testV11n, "Gen."))
        Assert.assertEquals(null, osisParser.parseOsisID(testV11n, "Gen21"))
        Assert.assertEquals(null, osisParser.parseOsisID(testV11n, ""))
        Assert.assertEquals(null, osisParser.parseOsisID(testV11n, null))
    }

    @Test
    fun testVerseRangeParsing() {
        Assert.assertEquals("Gen.1.1-Gen.1.3", osisParser.parseOsisRef(testV11n, "Gen.1.1-Gen.1.3")!!.getOsisRef())
        Assert.assertEquals("Gen.1.2-Gen.1.4", osisParser.parseOsisRef(testV11n, "Gen.1.2-Gen.1.4")!!.getOsisRef())
        Assert.assertEquals("Gen.2.2-Gen.3.4", osisParser.parseOsisRef(testV11n, "Gen.2.2-Gen.3.4")!!.getOsisRef())
        Assert.assertEquals("Exod.2.2-Lev.1.1", osisParser.parseOsisRef(testV11n, "Exod.2.2-Lev.1.1")!!.getOsisRef())
        Assert.assertEquals("3John.1.2-3John.1.10", osisParser.parseOsisRef(testV11n, "3John.1.2-3John.1.10")!!.getOsisRef())
    }

    @Test
    fun testChapterParsing() {
        Assert.assertEquals("Gen.1", osisParser.parseOsisRef(testV11n, "Gen.1")!!.getOsisRef())
        Assert.assertEquals("Mark.10", osisParser.parseOsisRef(testV11n, "Mark.10")!!.getOsisRef())
        Assert.assertEquals("Gen.1-Gen.3", osisParser.parseOsisRef(testV11n, "Gen.1-Gen.3")!!.getOsisRef())
        Assert.assertEquals("Obad", osisParser.parseOsisRef(testV11n, "Obad.1")!!.getOsisRef())
        Assert.assertEquals("Obad", osisParser.parseOsisRef(testV11n, "Obad")!!.getOsisRef())
        Assert.assertEquals("Gen", osisParser.parseOsisRef(testV11n, "Gen")!!.getOsisRef())
    }

    @Test
    fun testMixedLengthParsing() {
        Assert.assertEquals("Gen-Exod.1", osisParser.parseOsisRef(testV11n, "Gen-Exod.1")!!.getOsisRef())
        Assert.assertEquals("Gen.2-Gen.3", osisParser.parseOsisRef(testV11n, "Gen.2.1-Gen.3")!!.getOsisRef())
        Assert.assertEquals("Gen.1-Gen.3", osisParser.parseOsisRef(testV11n, "Gen-Gen.3")!!.getOsisRef())
        Assert.assertEquals("Gen.1-Gen.3.4", osisParser.parseOsisRef(testV11n, "Gen-Gen.3.4")!!.getOsisRef())
        Assert.assertEquals("Gen.3.4-Exod", osisParser.parseOsisRef(testV11n, "Gen.3.4-Exod")!!.getOsisRef())
    }

    @Test
    fun testVerseRangeParsingErrors() {
        Assert.assertEquals(null, osisParser.parseOsisRef(testV11n, "Gen.1.1.4"))
        Assert.assertEquals(null, osisParser.parseOsisRef(testV11n, "Gen."))
        Assert.assertEquals(null, osisParser.parseOsisRef(testV11n, "Gen.1."))
        Assert.assertEquals(null, osisParser.parseOsisRef(testV11n, "Gen21"))
        Assert.assertEquals(null, osisParser.parseOsisRef(testV11n, "Gen.2.1-"))
        Assert.assertEquals(null, osisParser.parseOsisRef(testV11n, "Gen1-Gen.3"))
        Assert.assertEquals(null, osisParser.parseOsisRef(testV11n, "-Gen.3"))
        Assert.assertEquals(null, osisParser.parseOsisRef(testV11n, ""))
    }

    @Test
    fun testVerseWithPart() {
        Assert.assertEquals("3John.1.14!a", osisParser.parseOsisID(testV11n, "3John.1.14!a")!!.getOsisID())
    }
}
