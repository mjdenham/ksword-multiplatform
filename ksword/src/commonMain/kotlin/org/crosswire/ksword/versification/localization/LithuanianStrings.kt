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

object LithuanianStrings : StringLocalization {
    private val strings = mapOf(
        // Division names
        "The Whole Bible" to "Visa Biblija",
        "Old Testament" to "Senasis Testamentas",
        "Pentateuch" to "Pentateuchas",
        "History" to "Istorija",
        "Poetry" to "Poezija",
        "All Prophecy" to "Visos pranašystės",
        "Major Prophets" to "Didieji pranašai",
        "Minor Prophets" to "Mažieji pranašai",
        "New Testament" to "Naujasis Testamentas",
        "Gospels and Acts" to "Evangelijos ir Apaštalų darbai",
        "Letters" to "Laiškai",
        "Letters to People" to "Laiškai žmonėms",
        "Letters from People" to "Laiškai nuo žmonių",
        "Revelation" to "Apreiškimas"
    )

    override fun getString(key: String): String? = strings[key]
}
