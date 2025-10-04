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

object EnglishStrings : StringLocalization {
    private val strings = mapOf(
        // Division names
        "The Whole Bible" to "The Whole Bible",
        "Old Testament" to "Old Testament",
        "Pentateuch" to "Pentateuch",
        "History" to "History",
        "Poetry" to "Poetry",
        "All Prophecy" to "All Prophecy",
        "Major Prophets" to "Major Prophets",
        "Minor Prophets" to "Minor Prophets",
        "New Testament" to "New Testament",
        "Gospels and Acts" to "Gospels and Acts",
        "Letters" to "Letters",
        "Letters to People" to "Letters to People",
        "Letters from People" to "Letters from People",
        "Revelation" to "Revelation"
    )

    override fun getString(key: String): String? = strings[key]
}
