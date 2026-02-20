package org.crosswire.ksword.book.passage

import org.crosswire.ksword.passage.NoSuchKeyException
import org.crosswire.ksword.passage.PassageKeyFactory
import org.crosswire.ksword.passage.RestrictionType
import org.crosswire.ksword.versification.system.Versifications
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class PassageKeyFactoryTest {
    private val v11n = Versifications.defaultVersification

    @Test
    fun testCreateEmptyKeyList() {
        val passage = PassageKeyFactory.createEmptyKeyList(v11n)
        assertTrue(passage.isEmpty())
        assertEquals(0, passage.countVerses())
    }

    @Test
    fun testParseSingleVerse() {
        val passage = PassageKeyFactory.getKey(v11n, "John 1:1")
        assertEquals(1, passage.countVerses())
        assertEquals("John 1:1", passage.getName())
    }

    @Test
    fun testParseSingleVerseWithAbbreviation() {
        // Test THML-style abbreviation
        val passage = PassageKeyFactory.getKey(v11n, "Joh 1:1")
        assertEquals(1, passage.countVerses())
        assertTrue(passage.getName().startsWith("John"))
    }

    @Test
    fun testParseVerseRange() {
        val passage = PassageKeyFactory.getKey(v11n, "Gen 1:1-5")
        assertEquals(5, passage.countVerses())
        assertTrue(passage.getName().contains("Gen"))
    }

    @Test
    fun testParseMultipleReferences() {
        // Semicolon separated
        val passage1 = PassageKeyFactory.getKey(v11n, "Gen 1:1; Ps 23:1")
        assertEquals(2, passage1.countVerses())
        assertTrue(passage1.countRanges(RestrictionType.NONE) >= 2)

        // Comma separated
        val passage2 = PassageKeyFactory.getKey(v11n, "Gen 1:1, Ps 23:1")
        assertEquals(2, passage2.countVerses())
        assertTrue(passage2.countRanges(RestrictionType.NONE) >= 2)
    }

    @Test
    fun testParseMultipleRanges() {
        val passage = PassageKeyFactory.getKey(v11n, "Gen 1:1-3; Ps 23:1-2")
        assertEquals(5, passage.countVerses())
    }

    @Test
    fun testParseWithVariousAbbreviations() {
        // Test different book abbreviations
        val passage1 = PassageKeyFactory.getValidKey(v11n, "Ge 1:1")
        assertFalse(passage1.isEmpty())

        val passage2 = PassageKeyFactory.getValidKey(v11n, "Gen 1:1")
        assertFalse(passage2.isEmpty())

        val passage3 = PassageKeyFactory.getValidKey(v11n, "Genesis 1:1")
        assertFalse(passage3.isEmpty())
    }

    @Test
    fun testGetValidKeyWithInvalidReference() {
        // getValidKey should return empty passage on error, not throw
        val passage = PassageKeyFactory.getValidKey(v11n, "InvalidBook 1:1")
        assertTrue(passage.isEmpty())
    }

    @Test
    fun testGetValidKeyWithBlankReference() {
        val passage = PassageKeyFactory.getValidKey(v11n, "")
        assertTrue(passage.isEmpty())
    }

    @Test
    fun testParseWithWhitespace() {
        val passage = PassageKeyFactory.getKey(v11n, "  Gen 1:1  ;  Ps 23:1  ")
        assertEquals(2, passage.countVerses())
    }

    @Test
    fun testOsisRefOutput() {
        val passage = PassageKeyFactory.getKey(v11n, "John 1:1")
        val osisRef = passage.getOsisRef()
        assertNotNull(osisRef)
        assertTrue(osisRef!!.contains("John") || osisRef.contains("Joh"))
        assertTrue(osisRef.contains("1.1"))
    }

    @Test
    fun testGetKeyThrowsOnInvalidReference() {
        assertFailsWith<NoSuchKeyException> {
            PassageKeyFactory.getKey(v11n, "InvalidBook 1:1")
        }
    }

    @Test
    fun testParseWithAlternateName() {
        // Test parsing with alternate book name "ex" for Exodus
        val passage = PassageKeyFactory.getKey(v11n, "ex 1:1")
        assertEquals(1, passage.countVerses())
        assertTrue(passage.getName().contains("Exod") || passage.getName().contains("Exodus"))
    }

    @Test
    fun testParseWithMultipleAlternates() {
        // Test that multiple alternates for the same book work
        val passage1 = PassageKeyFactory.getKey(v11n, "pss 23:1")
        assertEquals(1, passage1.countVerses())
        assertTrue(passage1.getName().contains("Ps"))

        val passage2 = PassageKeyFactory.getKey(v11n, "psm 23:1")
        assertEquals(1, passage2.countVerses())
        assertTrue(passage2.getName().contains("Ps"))

        val passage3 = PassageKeyFactory.getKey(v11n, "psalm 23:1")
        assertEquals(1, passage3.countVerses())
        assertTrue(passage3.getName().contains("Ps"))
    }

    @Test
    fun testParseHistoricalName() {
        // Test parsing with historical name "apocalypse" for Revelation
        val passage = PassageKeyFactory.getKey(v11n, "apocalypse 1:1")
        assertEquals(1, passage.countVerses())
        assertTrue(passage.getName().contains("Rev"))
    }

    @Test
    fun testParseWithSpellingVariations() {
        // Test that spelling variations work
        val passage1 = PassageKeyFactory.getKey(v11n, "mk 1:1")
        assertEquals(1, passage1.countVerses())
        assertTrue(passage1.getName().contains("Mark"))

        val passage2 = PassageKeyFactory.getKey(v11n, "mrk 1:1")
        assertEquals(1, passage2.countVerses())
        assertTrue(passage2.getName().contains("Mark"))
    }

    @Test
    fun testParseWithNumberVariations() {
        // Test number format variations
        val passage1 = PassageKeyFactory.getKey(v11n, "isamuel 1:1")
        assertEquals(1, passage1.countVerses())
        assertTrue(passage1.getName().contains("1") && passage1.getName().contains("Sam"))

        val passage2 = PassageKeyFactory.getKey(v11n, "1 sam 1:1")
        assertEquals(1, passage2.countVerses())
        assertTrue(passage2.getName().contains("1") && passage2.getName().contains("Sam"))
    }

    @Test
    fun testParseAlternateInComplexReference() {
        // Test using alternate names in complex references with context
        val passage = PassageKeyFactory.getKey(v11n, "mt 1:1; lk 1:1; jn 1:1")
        assertEquals(3, passage.countVerses())
        assertEquals(3, passage.booksInPassage())
    }

    @Test
    fun testParseComplexReferenceWithContext() {
        // Test parsing "Re 1:2,8,11; 2:8; 21:6; 22:13"
        // This requires context-based parsing where "2:8" should be understood as "Re 2:8"
        val passage = PassageKeyFactory.getKey(v11n, "Re 1:2,8,11; 2:8; 21:6; 22:13")

        // Should have 6 verses total: Re 1:2, 1:8, 1:11, 2:8, 21:6, 22:13
        assertEquals(6, passage.countVerses())

        // All verses should be from Revelation
        assertEquals(1, passage.booksInPassage())

        // Check the OSIS reference contains Revelation
        val osisRef = passage.getOsisRef()
        assertTrue(osisRef!!.contains("Rev") || osisRef.contains("Re"))

        // Verify the exact verses by iterating
        val verses = mutableListOf<String>()
        val iterator = passage.iterator()
        while (iterator.hasNext()) {
            val verse = iterator.next()
            verses.add(verse.getName())
        }

        // Check we have the correct verses
        assertEquals(6, verses.size)
        assertTrue(verses[0].contains("1:2") || verses[0].contains("1.2"))
        assertTrue(verses[1].contains("1:8") || verses[1].contains("1.8"))
        assertTrue(verses[2].contains("1:11") || verses[2].contains("1.11"))
        assertTrue(verses[3].contains("2:8") || verses[3].contains("2.8"))
        assertTrue(verses[4].contains("21:6") || verses[4].contains("21.6"))
        assertTrue(verses[5].contains("22:13") || verses[5].contains("22.13"))
    }
}
