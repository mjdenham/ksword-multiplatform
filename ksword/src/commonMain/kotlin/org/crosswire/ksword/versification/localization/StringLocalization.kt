/**
 * Distribution License:
 * KSword is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License, version 2.1 or later
 * as published by the Free Software Foundation. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * The License is available on the internet at:
 * http://www.gnu.org/copyleft/lgpl.html
 * or by writing to:
 * Free Software Foundation, Inc.
 * 59 Temple Place - Suite 330
 * Boston, MA 02111-1307, USA
 *
 * © CrossWire Bible Society, 2005 - 2016
 *
 */
package org.crosswire.ksword.versification.localization

/**
 * Interface for localized general strings (division names, messages, etc.).
 * Each language implementation provides translations for common strings used throughout ksword.
 */
interface StringLocalization {
    /**
     * Get a localized string by key.
     * @param key The string key (e.g., "Old Testament", "The Whole Bible")
     * @return The localized string, or null if not found
     */
    fun getString(key: String): String?
}

/**
 * Central registry for all available string localizations.
 * Uses lazy initialization so only requested languages are loaded into memory.
 */
object LocalizedStrings {
    /**
     * English string localization (loaded lazily)
     */
    val en: StringLocalization by lazy { EnglishStrings }

    /**
     * Spanish string localization (loaded lazily)
     */
    val es: StringLocalization by lazy { SpanishStrings }

    /**
     * French string localization (loaded lazily)
     */
    val fr: StringLocalization by lazy { FrenchStrings }

    /**
     * German string localization (loaded lazily)
     */
    val de: StringLocalization by lazy { GermanStrings }

    /**
     * Portuguese string localization (loaded lazily)
     */
    val pt: StringLocalization by lazy { PortugueseStrings }

    /**
     * Italian string localization (loaded lazily)
     */
    val it: StringLocalization by lazy { ItalianStrings }

    /**
     * Dutch string localization (loaded lazily)
     */
    val nl: StringLocalization by lazy { DutchStrings }

    /**
     * Russian string localization (loaded lazily)
     */
    val ru: StringLocalization by lazy { RussianStrings }

    /**
     * Chinese Simplified string localization (loaded lazily)
     */
    val zh: StringLocalization by lazy { ChineseSimplifiedStrings }

    /**
     * Polish string localization (loaded lazily)
     */
    val pl: StringLocalization by lazy { PolishStrings }

    /**
     * Korean string localization (loaded lazily)
     */
    val ko: StringLocalization by lazy { KoreanStrings }

    /**
     * Japanese string localization (loaded lazily)
     */
    val ja: StringLocalization by lazy { JapaneseStrings }

    /**
     * Arabic string localization (loaded lazily)
     */
    val ar: StringLocalization by lazy { ArabicStrings }

    /**
     * Czech string localization (loaded lazily)
     */
    val cs: StringLocalization by lazy { CzechStrings }

    /**
     * Finnish string localization (loaded lazily)
     */
    val fi: StringLocalization by lazy { FinnishStrings }

    /**
     * Farsi/Persian string localization (loaded lazily)
     */
    val fa: StringLocalization by lazy { FarsiStrings }

    /**
     * Hindi string localization (loaded lazily)
     */
    val hi: StringLocalization by lazy { HindiStrings }

    /**
     * Croatian string localization (loaded lazily)
     */
    val hr: StringLocalization by lazy { CroatianStrings }

    /**
     * Hungarian string localization (loaded lazily)
     */
    val hu: StringLocalization by lazy { HungarianStrings }

    /**
     * Indonesian string localization (loaded lazily)
     */
    val id: StringLocalization by lazy { IndonesianStrings }

    /**
     * Lithuanian string localization (loaded lazily)
     */
    val lt: StringLocalization by lazy { LithuanianStrings }

    /**
     * Latvian string localization (loaded lazily)
     */
    val lv: StringLocalization by lazy { LatvianStrings }

    /**
     * Hebrew string localization (loaded lazily)
     */
    val he: StringLocalization by lazy { HebrewStrings }

    /**
     * Norwegian string localization (loaded lazily)
     */
    val nb: StringLocalization by lazy { NorwegianStrings }

    /**
     * Romanian string localization (loaded lazily)
     */
    val ro: StringLocalization by lazy { RomanianStrings }

    /**
     * Slovak string localization (loaded lazily)
     */
    val sk: StringLocalization by lazy { SlovakStrings }

    /**
     * Slovenian string localization (loaded lazily)
     */
    val sl: StringLocalization by lazy { SlovenianStrings }

    /**
     * Swedish string localization (loaded lazily)
     */
    val sv: StringLocalization by lazy { SwedishStrings }

    /**
     * Tamil string localization (loaded lazily)
     */
    val ta: StringLocalization by lazy { TamilStrings }

    /**
     * Telugu string localization (loaded lazily)
     */
    val te: StringLocalization by lazy { TeluguStrings }

    /**
     * Thai string localization (loaded lazily)
     */
    val th: StringLocalization by lazy { ThaiStrings }

    /**
     * Turkish string localization (loaded lazily)
     */
    val tr: StringLocalization by lazy { TurkishStrings }

    /**
     * Ukrainian string localization (loaded lazily)
     */
    val uk: StringLocalization by lazy { UkrainianStrings }

    /**
     * Vietnamese string localization (loaded lazily)
     */
    val vi: StringLocalization by lazy { VietnameseStrings }

    /**
     * Cantonese string localization (loaded lazily)
     */
    val yue: StringLocalization by lazy { CantoneseStrings }

    /**
     * Get string localization for a specific language code.
     * Defaults to English if language not found.
     *
     * @param languageCode ISO 639-1 language code
     * @return the string localization for that language, or English as fallback
     */
    fun forLanguage(languageCode: String): StringLocalization {
        return when (languageCode.lowercase()) {
            "en" -> en
            "es" -> es
            "fr" -> fr
            "de" -> de
            "pt" -> pt
            "it" -> it
            "nl" -> nl
            "ru" -> ru
            "zh", "zh_cn" -> zh
            "pl" -> pl
            "ko" -> ko
            "ja" -> ja
            "ar" -> ar
            "cs" -> cs
            "fi" -> fi
            "fa" -> fa
            "hi" -> hi
            "hr" -> hr
            "hu" -> hu
            "id" -> id
            "lt" -> lt
            "lv" -> lv
            "he" -> he
            "nb", "no" -> nb
            "ro" -> ro
            "sk" -> sk
            "sl" -> sl
            "sv" -> sv
            "ta" -> ta
            "te" -> te
            "th" -> th
            "tr" -> tr
            "uk" -> uk
            "vi" -> vi
            "yue" -> yue
            else -> en // Default to English
        }
    }

    /**
     * Get list of all supported language codes
     */
    fun supportedLanguages(): List<String> = listOf(
        "en", "es", "fr", "de", "pt", "it", "nl", "ru", "zh", "pl", "ko", "ja",
        "ar", "cs", "fi", "fa", "hi", "hr", "hu", "id", "lt", "lv", "he", "nb",
        "ro", "sk", "sl", "sv", "ta", "te", "th", "tr", "uk", "vi", "yue"
    )
}
