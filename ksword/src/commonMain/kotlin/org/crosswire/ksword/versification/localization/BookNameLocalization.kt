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

import org.crosswire.ksword.versification.BibleBook

/**
 * Interface for localized Bible book names.
 * Each language implementation provides full, short, and alternate names for each Bible book.
 */
interface BookNameLocalization {
    /**
     * Get the full name for a book (e.g., "Genesis", "Génesis")
     */
    fun getFullName(book: BibleBook): String?

    /**
     * Get the short name for a book (e.g., "Gen", "Gén")
     */
    fun getShortName(book: BibleBook): String?

    /**
     * Get alternate names for a book (comma-separated)
     */
    fun getAlternateName(book: BibleBook): String? = null
}

/**
 * Central registry for all available localizations.
 * Uses lazy initialization so only requested languages are loaded into memory.
 */
object LocalizedBookNames {
    /**
     * English localization (loaded lazily)
     */
    val en: BookNameLocalization by lazy { EnglishBookNames }

    /**
     * Spanish localization (loaded lazily)
     */
    val es: BookNameLocalization by lazy { SpanishBookNames }

    /**
     * French localization (loaded lazily)
     */
    val fr: BookNameLocalization by lazy { FrenchBookNames }

    /**
     * German localization (loaded lazily)
     */
    val de: BookNameLocalization by lazy { GermanBookNames }

    /**
     * Portuguese localization (loaded lazily)
     */
    val pt: BookNameLocalization by lazy { PortugueseBookNames }

    /**
     * Italian localization (loaded lazily)
     */
    val it: BookNameLocalization by lazy { ItalianBookNames }

    /**
     * Dutch localization (loaded lazily)
     */
    val nl: BookNameLocalization by lazy { DutchBookNames }

    /**
     * Russian localization (loaded lazily)
     */
    val ru: BookNameLocalization by lazy { RussianBookNames }

    /**
     * Chinese Simplified localization (loaded lazily)
     */
    val zh: BookNameLocalization by lazy { ChineseSimplifiedBookNames }

    /**
     * Polish localization (loaded lazily)
     */
    val pl: BookNameLocalization by lazy { PolishBookNames }

    /**
     * Korean localization (loaded lazily)
     */
    val ko: BookNameLocalization by lazy { KoreanBookNames }

    /**
     * Japanese localization (loaded lazily)
     */
    val ja: BookNameLocalization by lazy { JapaneseBookNames }

    /**
     * Arabic localization (loaded lazily)
     */
    val ar: BookNameLocalization by lazy { ArabicBookNames }

    /**
     * Czech localization (loaded lazily)
     */
    val cs: BookNameLocalization by lazy { CzechBookNames }

    /**
     * Finnish localization (loaded lazily)
     */
    val fi: BookNameLocalization by lazy { FinnishBookNames }

    /**
     * Farsi/Persian localization (loaded lazily)
     */
    val fa: BookNameLocalization by lazy { FarsiBookNames }

    /**
     * Hindi localization (loaded lazily)
     */
    val hi: BookNameLocalization by lazy { HindiBookNames }

    /**
     * Croatian localization (loaded lazily)
     */
    val hr: BookNameLocalization by lazy { CroatianBookNames }

    /**
     * Hungarian localization (loaded lazily)
     */
    val hu: BookNameLocalization by lazy { HungarianBookNames }

    /**
     * Indonesian localization (loaded lazily)
     */
    val id: BookNameLocalization by lazy { IndonesianBookNames }

    /**
     * Lithuanian localization (loaded lazily)
     */
    val lt: BookNameLocalization by lazy { LithuanianBookNames }

    /**
     * Latvian localization (loaded lazily)
     */
    val lv: BookNameLocalization by lazy { LatvianBookNames }

    /**
     * Hebrew localization (loaded lazily)
     */
    val he: BookNameLocalization by lazy { HebrewBookNames }

    /**
     * Norwegian localization (loaded lazily)
     */
    val nb: BookNameLocalization by lazy { NorwegianBookNames }

    /**
     * Romanian localization (loaded lazily)
     */
    val ro: BookNameLocalization by lazy { RomanianBookNames }

    /**
     * Slovak localization (loaded lazily)
     */
    val sk: BookNameLocalization by lazy { SlovakBookNames }

    /**
     * Slovenian localization (loaded lazily)
     */
    val sl: BookNameLocalization by lazy { SlovenianBookNames }

    /**
     * Swedish localization (loaded lazily)
     */
    val sv: BookNameLocalization by lazy { SwedishBookNames }

    /**
     * Tamil localization (loaded lazily)
     */
    val ta: BookNameLocalization by lazy { TamilBookNames }

    /**
     * Telugu localization (loaded lazily)
     */
    val te: BookNameLocalization by lazy { TeluguBookNames }

    /**
     * Thai localization (loaded lazily)
     */
    val th: BookNameLocalization by lazy { ThaiBookNames }

    /**
     * Turkish localization (loaded lazily)
     */
    val tr: BookNameLocalization by lazy { TurkishBookNames }

    /**
     * Ukrainian localization (loaded lazily)
     */
    val uk: BookNameLocalization by lazy { UkrainianBookNames }

    /**
     * Vietnamese localization (loaded lazily)
     */
    val vi: BookNameLocalization by lazy { VietnameseBookNames }

    /**
     * Cantonese localization (loaded lazily)
     */
    val yue: BookNameLocalization by lazy { CantoneseBookNames }

    /**
     * Get localization for a specific language code.
     * Defaults to English if language not found.
     *
     * @param languageCode ISO 639-1 language code (e.g., "en", "es", "fr", "de", "pt", "it", "nl", "ru", "zh", "pl", "ko", "ja", "ar", "cs", "fi", "fa", "hi", "hr", "hu", "id", "lt", "lv", "he", "nb", "ro", "sk", "sl", "sv", "ta", "te", "th", "tr", "uk", "vi", "yue")
     * @return the localization for that language, or English as fallback
     */
    fun forLanguage(languageCode: String): BookNameLocalization {
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
    fun supportedLanguages(): List<String> = listOf("en", "es", "fr", "de", "pt", "it", "nl", "ru", "zh", "pl", "ko", "ja", "ar", "cs", "fi", "fa", "hi", "hr", "hu", "id", "lt", "lv", "he", "nb", "ro", "sk", "sl", "sv", "ta", "te", "th", "tr", "uk", "vi", "yue")
}
