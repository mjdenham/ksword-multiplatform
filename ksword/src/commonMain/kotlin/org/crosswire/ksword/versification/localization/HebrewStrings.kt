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

object HebrewStrings : StringLocalization {
    private val strings = mapOf(
        // Division names
        "The Whole Bible" to "כל התנ״ך",
        "Old Testament" to "תנ״ך",
        "Pentateuch" to "תורה",
        "History" to "ספרי היסטוריה",
        "Poetry" to "שירה",
        "All Prophecy" to "כל הנבואה",
        "Major Prophets" to "נביאים ראשונים",
        "Minor Prophets" to "תרי עשר",
        "New Testament" to "הברית החדשה",
        "Gospels and Acts" to "הבשורות ומעשי השליחים",
        "Letters" to "האיגרות",
        "Letters to People" to "איגרות לאנשים",
        "Letters from People" to "איגרות מאנשים",
        "Revelation" to "התגלות"
    )

    override fun getString(key: String): String? = strings[key]
}
