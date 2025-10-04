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
     * Get localization for a specific language code.
     * Defaults to English if language not found.
     *
     * @param languageCode ISO 639-1 language code (e.g., "en", "es", "fr")
     * @return the localization for that language, or English as fallback
     */
    fun forLanguage(languageCode: String): BookNameLocalization {
        return when (languageCode.lowercase()) {
            "en" -> en
            "es" -> es
            else -> en // Default to English
        }
    }

    /**
     * Get list of all supported language codes
     */
    fun supportedLanguages(): List<String> = listOf("en", "es")
}
