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

object HungarianStrings : StringLocalization {
    private val strings = mapOf(
        // Division names
        "The Whole Bible" to "Az Egész Biblia",
        "Old Testament" to "Ószövetség",
        "Pentateuch" to "Pentateuch",
        "History" to "Történelem",
        "Poetry" to "Költészet",
        "All Prophecy" to "Minden Prófécia",
        "Major Prophets" to "Nagy Próféták",
        "Minor Prophets" to "Kis Próféták",
        "New Testament" to "Újszövetség",
        "Gospels and Acts" to "Evangéliumok és Apostolok Cselekedetei",
        "Letters" to "Levelek",
        "Letters to People" to "Levelek Emberekhez",
        "Letters from People" to "Levelek Emberektől",
        "Revelation" to "Jelenések"
    )

    override fun getString(key: String): String? = strings[key]
}
