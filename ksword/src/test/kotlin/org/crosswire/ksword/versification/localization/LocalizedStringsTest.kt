package org.crosswire.ksword.versification.localization

import org.junit.Assert.*
import org.junit.Test

class LocalizedStringsTest {

    @Test
    fun testForLanguageReturnsCorrectLocalization() {
        // Test a few key languages
        assertEquals(EnglishStrings, LocalizedStrings.forLanguage("en"))
        assertEquals(SpanishStrings, LocalizedStrings.forLanguage("es"))
        assertEquals(FrenchStrings, LocalizedStrings.forLanguage("fr"))
        assertEquals(GermanStrings, LocalizedStrings.forLanguage("de"))
        assertEquals(RussianStrings, LocalizedStrings.forLanguage("ru"))
        assertEquals(ChineseSimplifiedStrings, LocalizedStrings.forLanguage("zh"))
    }

    @Test
    fun testForLanguageFallsBackToEnglish() {
        // Unknown language codes should return English
        assertEquals(EnglishStrings, LocalizedStrings.forLanguage("unknown"))
        assertEquals(EnglishStrings, LocalizedStrings.forLanguage("xyz"))
        assertEquals(EnglishStrings, LocalizedStrings.forLanguage(""))
    }

    @Test
    fun testForLanguageIsCaseInsensitive() {
        assertEquals(EnglishStrings, LocalizedStrings.forLanguage("EN"))
        assertEquals(EnglishStrings, LocalizedStrings.forLanguage("En"))
        assertEquals(FrenchStrings, LocalizedStrings.forLanguage("FR"))
        assertEquals(GermanStrings, LocalizedStrings.forLanguage("DE"))
    }

    @Test
    fun testSupportedLanguagesCount() {
        val languages = LocalizedStrings.supportedLanguages()
        assertEquals(35, languages.size)
    }

    @Test
    fun testAllSupportedLanguagesAreAccessible() {
        // Ensure all listed languages can be retrieved without error
        val languages = LocalizedStrings.supportedLanguages()
        for (langCode in languages) {
            val localization = LocalizedStrings.forLanguage(langCode)
            assertNotNull("Language $langCode should be accessible", localization)
        }
    }

    @Test
    fun testCriticalKeysExistInUpdatedLanguages() {
        // Test the 13 languages we updated have all critical keys
        val languagesToTest = listOf(
            "de" to GermanStrings,
            "fr" to FrenchStrings,
            "cs" to CzechStrings,
            "pl" to PolishStrings,
            "ru" to RussianStrings,
            "fa" to FarsiStrings,
            "fi" to FinnishStrings,
            "id" to IndonesianStrings,
            "lt" to LithuanianStrings,
            "nb" to NorwegianStrings,
            "th" to ThaiStrings,
            "vi" to VietnameseStrings,
            "zh" to ChineseSimplifiedStrings
        )

        val criticalKeys = listOf(
            "The Whole Bible",
            "Old Testament",
            "New Testament",
            "Pentateuch",
            "History",
            "Poetry",
            "Major Prophets",
            "Minor Prophets",
            "Gospels and Acts",
            "Letters",
            "Revelation"
        )

        for ((langCode, localization) in languagesToTest) {
            for (key in criticalKeys) {
                val translation = localization.getString(key)
                assertNotNull("Language $langCode should have translation for '$key'", translation)
                assertNotEquals("Language $langCode should not have empty translation for '$key'", "", translation)
            }
        }
    }

    @Test
    fun testSpecificTranslationsFromGitHub() {
        // Verify some specific translations we updated match GitHub sources

        // German
        assertEquals("5 Bücher Mose", GermanStrings.getString("Pentateuch"))
        assertEquals("Geschichtschreibung", GermanStrings.getString("History"))

        // French
        assertEquals("Livres historiques", FrenchStrings.getString("History"))
        assertEquals("Prophètes majeurs", FrenchStrings.getString("Major Prophets"))

        // Russian
        assertEquals("Пятикнижие Моисея", RussianStrings.getString("Pentateuch"))
        assertEquals("Исторические Книги", RussianStrings.getString("History"))
        assertEquals("Откровение Иоанна Богослова", RussianStrings.getString("Revelation"))

        // Norwegian
        assertEquals("Mosebøkene", NorwegianStrings.getString("Pentateuch"))
        assertEquals("Hele bibelen", NorwegianStrings.getString("The Whole Bible"))

        // Chinese Simplified
        assertEquals("新旧约圣经", ChineseSimplifiedStrings.getString("The Whole Bible"))
        assertEquals("诗歌书", ChineseSimplifiedStrings.getString("Poetry"))
    }

    @Test
    fun testEnglishAsBaseline() {
        // English should have all keys
        val keys = listOf(
            "The Whole Bible",
            "Old Testament",
            "Pentateuch",
            "History",
            "Poetry",
            "All Prophecy",
            "Major Prophets",
            "Minor Prophets",
            "New Testament",
            "Gospels and Acts",
            "Letters",
            "Letters to People",
            "Letters from People",
            "Revelation"
        )

        for (key in keys) {
            val translation = EnglishStrings.getString(key)
            assertNotNull("English should have translation for '$key'", translation)
            assertEquals("English translation for '$key' should match key", key, translation)
        }
    }
}