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

object SwedishStrings : StringLocalization {
    private val strings = mapOf(
        // Division names
        "The Whole Bible" to "Hela Bibeln",
        "Old Testament" to "Gamla testamentet",
        "Pentateuch" to "Pentateuken",
        "History" to "Historia",
        "Poetry" to "Poesi",
        "All Prophecy" to "All profetia",
        "Major Prophets" to "Stora profeterna",
        "Minor Prophets" to "Små profeterna",
        "New Testament" to "Nya testamentet",
        "Gospels and Acts" to "Evangelierna och Apostlagärningarna",
        "Letters" to "Brev",
        "Letters to People" to "Brev till människor",
        "Letters from People" to "Brev från människor",
        "Revelation" to "Uppenbarelseboken"
    )

    override fun getString(key: String): String? = strings[key]
}
