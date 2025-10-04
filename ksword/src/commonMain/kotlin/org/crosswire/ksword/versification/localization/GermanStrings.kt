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

object GermanStrings : StringLocalization {
    private val strings = mapOf(
        // Division names
        "The Whole Bible" to "Die ganze Bibel",
        "Old Testament" to "Altes Testament",
        "Pentateuch" to "5 Bücher Mose",
        "History" to "Geschichtschreibung",
        "Poetry" to "Poesie",
        "All Prophecy" to "Alle Propheten",
        "Major Prophets" to "Große Propheten",
        "Minor Prophets" to "Kleine Propheten",
        "New Testament" to "Neues Testament",
        "Gospels and Acts" to "Evangelien und Apostelgeschichte",
        "Letters" to "Briefe",
        "Letters to People" to "Pauslusbriefe",
        "Letters from People" to "Briefe von anderen Aposteln",
        "Revelation" to "Offenbarung"
    )

    override fun getString(key: String): String? = strings[key]
}
