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

object RomanianStrings : StringLocalization {
    private val strings = mapOf(
        // Division names
        "The Whole Bible" to "Întreaga Biblie",
        "Old Testament" to "Vechiul Testament",
        "Pentateuch" to "Pentateuh",
        "History" to "Istorie",
        "Poetry" to "Poezie",
        "All Prophecy" to "Toată Profeția",
        "Major Prophets" to "Profeți Mari",
        "Minor Prophets" to "Profeți Mici",
        "New Testament" to "Noul Testament",
        "Gospels and Acts" to "Evanghelii și Faptele Apostolilor",
        "Letters" to "Epistole",
        "Letters to People" to "Epistole către Oameni",
        "Letters from People" to "Epistole de la Oameni",
        "Revelation" to "Apocalipsa"
    )

    override fun getString(key: String): String? = strings[key]
}
