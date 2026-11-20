package org.crosswire.ksword.passage

import org.crosswire.common.util.Locale
import org.crosswire.ksword.versification.BibleBook
import org.crosswire.ksword.versification.BibleNames
import org.crosswire.ksword.versification.system.Versifications
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

/** A module's references are in the module's language, so reading them must not depend on the reader's. */
class ReferenceLanguageTest {

    private val v11n = Versifications.getVersification("KJV")

    @AfterTest
    fun resetLocale() = Locale.setDefaultLocale("en", null)

    private fun readerSpeaks(language: String) = when (language) {
        "zh-TW" -> Locale.setDefaultLocale("zh", "TW")
        "zh-CN" -> Locale.setDefaultLocale("zh", "CN")
        else -> Locale.setDefaultLocale(language, null)
    }

    @Test
    fun tskAbbreviationsReadAsEnglishAreTheSameBookForEveryReader() {
        readerSpeaks("en")
        val english = TSK_ABBREVIATIONS.associateWith { BibleNames.instance().getBook(it, language = Locale.ENGLISH) }
        assertEquals(66, english.values.filterNotNull().toSet().size, "each abbreviation is its own book")

        for (language in LANGUAGES_WITH_BOOK_NAMES) {
            readerSpeaks(language)
            for (abbreviation in TSK_ABBREVIATIONS) {
                assertEquals(english[abbreviation], BibleNames.instance().getBook(abbreviation, language = Locale.ENGLISH), "$abbreviation, reader in $language")
            }
        }
    }

    /** Each of these was read as another book, or not at all, by readers in several languages. */
    @Test
    fun abbreviationsThatWereMisreadAreTheirEnglishBooks() {
        val expected = mapOf(
            "Jud" to BibleBook.JUDG, "Jos" to BibleBook.JOSH, "Le" to BibleBook.LEV, "Pr" to BibleBook.PROV,
            "Es" to BibleBook.ESTH, "Ec" to BibleBook.ECCL, "So" to BibleBook.SONG, "Ezr" to BibleBook.EZRA,
        )
        for (language in listOf("nl", "zh-TW", "fi", "pl", "de", "fr", "ar")) {
            readerSpeaks(language)
            for ((abbreviation, book) in expected) {
                assertEquals(book, BibleNames.instance().getBook(abbreviation, language = Locale.ENGLISH), "$abbreviation, reader in $language")
            }
        }
    }

    @Test
    fun aListOfReferencesReadAsEnglishIsTheSameForAChineseReader() {
        readerSpeaks("zh-TW")

        // TSK, Exodus 20:2: for this reader Le was lost and its 13 landed in Genesis 17
        val passage = PassageKeyFactory.getKey(v11n, "Ge 17:7,8; Le 26:1,13; De 5:6", language = Locale.ENGLISH)

        assertEquals("Gen.17.7-Gen.17.8 Lev.26.1 Lev.26.13 Deut.5.6", passage.getOsisRef())
    }

    @Test
    fun aGermanReferenceReadAsGermanDoesNotNeedAGermanReader() {
        readerSpeaks("en")

        assertEquals("Gen.3.15", PassageKeyFactory.getKey(v11n, "1Mo 3:15", language = Locale.GERMAN).getOsisRef())
    }

    @Test
    fun aLanguageFallsBackToEnglish() {
        readerSpeaks("en")

        assertEquals("Lev.26.1", PassageKeyFactory.getKey(v11n, "Le 26:1", language = Locale.GERMAN).getOsisRef())
    }

    @Test
    fun withNoLanguageGivenTheReadersLanguageIsUsed() {
        readerSpeaks("de")
        assertEquals("Gen.3.15", PassageKeyFactory.getKey(v11n, "1Mo 3:15").getOsisRef())

        readerSpeaks("en")
        assertFailsWith<NoSuchKeyException> { PassageKeyFactory.getKey(v11n, "1Mo 3:15") }
    }

    private companion object {
        val TSK_ABBREVIATIONS = ("Ge Ex Le Nu De Jos Jud Ru 1Sa 2Sa 1Ki 2Ki 1Ch 2Ch Ezr Ne Es Job Ps Pr Ec So Isa Jer La Eze Da Ho Joe Am Ob " +
            "Jon Mic Na Hab Zep Hag Zec Mal Mt Mr Lu Joh Ac Ro 1Co 2Co Ga Eph Php Col 1Th 2Th 1Ti 2Ti Tit Phm Heb Jas 1Pe 2Pe 1Jo 2Jo 3Jo Jude Re").split(" ")

        val LANGUAGES_WITH_BOOK_NAMES = listOf(
            "es", "fr", "de", "pt", "it", "nl", "ru", "zh-CN", "zh-TW", "pl", "ko", "ja", "ar", "cs", "fi", "fa", "hi", "hr", "hu",
            "id", "lt", "lv", "he", "nb", "ro", "sk", "sl", "sv", "ta", "te", "th", "tr", "uk", "vi", "yue",
        )
    }
}
