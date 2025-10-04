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

object FinnishStrings : StringLocalization {
    private val strings = mapOf(
        // Division names
        "The Whole Bible" to "Koko Raamattu",
        "Old Testament" to "Vanha testamentti",
        "Pentateuch" to "Pentateukki",
        "History" to "Historia",
        "Poetry" to "Lyriikka",
        "All Prophecy" to "Kaikki profetia",
        "Major Prophets" to "Suuret profeetat",
        "Minor Prophets" to "Pienet profeetat",
        "New Testament" to "Uusi testamentti",
        "Gospels and Acts" to "Evankeliumit ja Apostolien teot",
        "Letters" to "Kirjeet",
        "Letters to People" to "Kirjeitä ihmisille",
        "Letters from People" to "Kirjeitä ihmisiltä",
        "Revelation" to "Ilmestys"
    )

    override fun getString(key: String): String? = strings[key]
}
