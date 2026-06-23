package org.crosswire.ksword.versification.localization

import org.crosswire.common.util.Locale
import org.crosswire.ksword.versification.BibleBook
import org.crosswire.ksword.versification.DivisionName
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals

class LocalizedBookNamesTest {

    @AfterTest
    fun resetLocale() = Locale.setDefaultLocale("en", null)

    // Module Lang fields carry BCP-47 script/region subtags (zh-Hans/zh-Hant/pt-BR); these must
    // resolve to the base language rather than UNKNOWN, or catalog locale filtering drops them.
    @Test
    fun findLocaleResolvesScriptAndRegionSubtags() {
        assertEquals(Locale.CHINESE_SIMPLIFIED, Locale.findLocale("zh-Hans"))
        assertEquals(Locale.CHINESE_TRADITIONAL_HANT, Locale.findLocale("zh-Hant"))
        assertEquals("zh", Locale.findLocale("zh-Hant").languageCode)
        assertEquals(Locale.PORTUGUESE, Locale.findLocale("pt-BR"))
        assertEquals(Locale.ENGLISH, Locale.findLocale("en-US"))
        assertEquals(Locale.UNKNOWN, Locale.findLocale("xx-YY"))
    }

    @Test
    fun setDefaultLocalePicksTraditionalEnumByRegion() {
        Locale.setDefaultLocale("zh", "TW")
        assertEquals(Locale.CHINESE_TRADITIONAL_HANT, Locale.current)
        Locale.setDefaultLocale("yue", "HK")
        assertEquals(Locale.CHINESE_TRADITIONAL_HANT, Locale.current)
        Locale.setDefaultLocale("zh", "CN")
        assertEquals(Locale.CHINESE_SIMPLIFIED, Locale.current)
    }

    @Test
    fun scriptLanguageCodeQualifiesChineseByRegion() {
        assertEquals("zh_tw", Locale.scriptLanguageCode("zh", "TW"))
        assertEquals("zh_tw", Locale.scriptLanguageCode("zh", "hk"))
        assertEquals("zh_tw", Locale.scriptLanguageCode("yue", "MO"))
        assertEquals("zh", Locale.scriptLanguageCode("zh", "CN"))
        assertEquals("zh", Locale.scriptLanguageCode("zh", null))
        assertEquals("zh", Locale.scriptLanguageCode("yue", null))
        assertEquals("ru", Locale.scriptLanguageCode("ru", "TW"))
    }

    @Test
    fun divisionNamesQualifyChineseByRegion() {
        Locale.setDefaultLocale("zh", "TW")
        assertEquals("舊約", DivisionName.OLD_TESTAMENT.getName())
        assertEquals("新約", DivisionName.NEW_TESTAMENT.getName())
        Locale.setDefaultLocale("zh", "CN")
        assertEquals("旧约", DivisionName.OLD_TESTAMENT.getName())
        assertEquals("新约", DivisionName.NEW_TESTAMENT.getName())
    }

    // BookNameProvider drives every Verse.getName() — it must qualify Chinese book names by region.
    @Test
    fun bookNameProviderQualifiesChineseByRegion() {
        Locale.setDefaultLocale("zh", "TW")
        assertEquals("創世記", BookNameProvider(Locale.CHINESE_SIMPLIFIED).getName(BibleBook.GEN, BookNameProvider.NameType.FULL))
        Locale.setDefaultLocale("zh", "CN")
        assertEquals("创世记", BookNameProvider(Locale.CHINESE_SIMPLIFIED).getName(BibleBook.GEN, BookNameProvider.NameType.FULL))
        Locale.setDefaultLocale("zh", null)
        assertEquals("创世记", BookNameProvider(Locale.CHINESE_SIMPLIFIED).getName(BibleBook.GEN, BookNameProvider.NameType.FULL))
    }

    @Test
    fun bookNameProviderQualifiesCantoneseByRegion() {
        Locale.setDefaultLocale("yue", "HK")
        assertEquals("創世記", BookNameProvider(Locale.CANTONESE).getName(BibleBook.GEN, BookNameProvider.NameType.FULL))
        Locale.setDefaultLocale("yue", null)
        assertEquals("创世记", BookNameProvider(Locale.CANTONESE).getName(BibleBook.GEN, BookNameProvider.NameType.FULL))
    }

    @Test
    fun simplifiedChineseUsesSimplifiedNames() {
        val zh = LocalizedBookNames.forLanguage("zh")
        assertEquals("创世记", zh.getFullName(BibleBook.GEN))
        assertEquals("启示录", zh.getFullName(BibleBook.REV))
    }

    @Test
    fun traditionalChineseUsesTraditionalNames() {
        for (code in listOf("zh_tw", "zh_hant", "zh_hk", "zh_mo")) {
            val zhTw = LocalizedBookNames.forLanguage(code)
            assertEquals("創世記", zhTw.getFullName(BibleBook.GEN), "full name for $code")
            assertEquals("啟示錄", zhTw.getFullName(BibleBook.REV), "full name for $code")
            assertEquals("創", zhTw.getShortName(BibleBook.GEN), "short name for $code")
        }
    }

    @Test
    fun traditionalDiffersFromSimplified() {
        assertEquals(
            LocalizedBookNames.forLanguage("yue").getFullName(BibleBook.GEN),
            LocalizedBookNames.forLanguage("zh_tw").getFullName(BibleBook.GEN),
        )
        assert(
            LocalizedBookNames.forLanguage("zh").getFullName(BibleBook.GEN) !=
                LocalizedBookNames.forLanguage("zh_tw").getFullName(BibleBook.GEN)
        )
    }
}
