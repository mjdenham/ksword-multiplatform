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

object TamilStrings : StringLocalization {
    private val strings = mapOf(
        // Division names
        "The Whole Bible" to "முழு பைபிள்",
        "Old Testament" to "பழைய ஏற்பாடு",
        "Pentateuch" to "மோசேயின் ஐந்து புத்தகங்கள்",
        "History" to "வரலாறு",
        "Poetry" to "கவிதை",
        "All Prophecy" to "அனைத்து தீர்க்கதரிசனங்கள்",
        "Major Prophets" to "பெரிய தீர்க்கதரிசிகள்",
        "Minor Prophets" to "சிறிய தீர்க்கதரிசிகள்",
        "New Testament" to "புதிய ஏற்பாடு",
        "Gospels and Acts" to "சுவிசேஷங்கள் மற்றும் அப்போஸ்தலர் நடபடிகள்",
        "Letters" to "நிருபங்கள்",
        "Letters to People" to "மக்களுக்கு நிருபங்கள்",
        "Letters from People" to "மக்களிடமிருந்து நிருபங்கள்",
        "Revelation" to "வெளிப்படுத்தல்"
    )

    override fun getString(key: String): String? = strings[key]
}
