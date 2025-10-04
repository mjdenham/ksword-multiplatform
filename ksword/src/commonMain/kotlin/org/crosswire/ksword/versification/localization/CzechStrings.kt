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

object CzechStrings : StringLocalization {
    private val strings = mapOf(
        // Division names
        "The Whole Bible" to "Celá Bible",
        "Old Testament" to "Starý zákon",
        "Pentateuch" to "Knihy Mojžíšovy",
        "History" to "Historie",
        "Poetry" to "Poezie",
        "All Prophecy" to "Všechna proroctví",
        "Major Prophets" to "Větší proroci",
        "Minor Prophets" to "Menší proroci",
        "New Testament" to "Nový zákon",
        "Gospels and Acts" to "Evangelia a Skutky",
        "Letters" to "Listy",
        "Letters to People" to "Listy lidem",
        "Letters from People" to "Listy od lidí",
        "Revelation" to "Zjevení"
    )

    override fun getString(key: String): String? = strings[key]
}
