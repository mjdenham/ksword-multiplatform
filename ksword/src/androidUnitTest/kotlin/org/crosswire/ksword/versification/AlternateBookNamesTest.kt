package org.crosswire.ksword.versification

import org.crosswire.ksword.versification.localization.EnglishBookNames
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * Test alternate book name functionality
 */
class AlternateBookNamesTest {

    @Test
    fun testAlternateNamesExist() {
        // Verify that EnglishBookNames returns alternate names for books that have them
        val exodusAlts = EnglishBookNames.getAlternateName(BibleBook.EXOD)
        assertNotNull(exodusAlts)
        assertEquals("ex", exodusAlts)

        val psalmsAlts = EnglishBookNames.getAlternateName(BibleBook.PS)
        assertNotNull(psalmsAlts)
        assertTrue(psalmsAlts.contains("pss"))
    }

    @Test
    fun testSingleAlternate() {
        val names = BibleNames.instance()
        val book = names.getBook("ex")
        assertNotNull(book, "Should find Exodus using alternate 'ex'")
        assertEquals(BibleBook.EXOD, book)
    }

    @Test
    fun testMultipleAlternates() {
        val names = BibleNames.instance()

        // Psalms has multiple alternates: pss, psm, psalm
        assertEquals(BibleBook.PS, names.getBook("pss"))
        assertEquals(BibleBook.PS, names.getBook("psm"))
        assertEquals(BibleBook.PS, names.getBook("psalm"))
    }

    @Test
    fun testHistoricalNames() {
        val names = BibleNames.instance()

        // Revelation can be called "apocalypse"
        assertEquals(BibleBook.REV, names.getBook("apocalypse"))

        // Song of Solomon has historical alternates
        assertEquals(BibleBook.SONG, names.getBook("songofsongs"))
        assertEquals(BibleBook.SONG, names.getBook("canticleofcanticles"))
    }

    @Test
    fun testSpellingVariations() {
        val names = BibleNames.instance()

        // Mark has spelling variations
        assertEquals(BibleBook.MARK, names.getBook("mk"))
        assertEquals(BibleBook.MARK, names.getBook("mrk"))

        // John has variations
        assertEquals(BibleBook.JOHN, names.getBook("jn"))
        assertEquals(BibleBook.JOHN, names.getBook("jhn"))
    }

    @Test
    fun testNumberVariations() {
        val names = BibleNames.instance()

        // 1 Samuel variations
        assertEquals(BibleBook.SAM1, names.getBook("1sam"))
        assertEquals(BibleBook.SAM1, names.getBook("isamuel"))

        // 2 Kings variations
        assertEquals(BibleBook.KGS2, names.getBook("2kgs"))
        assertEquals(BibleBook.KGS2, names.getBook("iikings"))
    }

    @Test
    fun testCaseInsensitive() {
        val names = BibleNames.instance()

        // Alternates should be case-insensitive
        assertEquals(BibleBook.EXOD, names.getBook("EX"))
        assertEquals(BibleBook.EXOD, names.getBook("Ex"))
        assertEquals(BibleBook.EXOD, names.getBook("ex"))
    }

    @Test
    fun testNormalization() {
        val names = BibleNames.instance()

        // Normalization should remove dots and spaces
        assertEquals(BibleBook.PS, names.getBook("p.s.s"))
        assertEquals(BibleBook.PS, names.getBook("p s s"))
    }

    @Test
    fun testPrefixMatching() {
        val names = BibleNames.instance()

        // "apoc" should match "apocalypse" (prefix)
        assertEquals(BibleBook.REV, names.getBook("apoc"))

        // "rv" should match Revelation
        assertEquals(BibleBook.REV, names.getBook("rv"))
    }

    @Test
    fun testBookWithoutAlternates() {
        val names = BibleNames.instance()

        // Books without alternates should still work with full and short names
        assertNotNull(names.getBook("Genesis"))
        assertNotNull(names.getBook("Gen"))
    }

    @Test
    fun testApocryphaAlternates() {
        val names = BibleNames.instance()

        // Sirach is also known as Ecclesiasticus
        assertEquals(BibleBook.SIR, names.getBook("ecclesiasticus"))

        // Letter of Jeremiah variations
        assertEquals(BibleBook.EP_JER, names.getBook("letterofjeremiah"))
        assertEquals(BibleBook.EP_JER, names.getBook("epjeremiah"))
    }
}
