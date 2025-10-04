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

object SlovenianStrings : StringLocalization {
    private val strings = mapOf(
        // Division names
        "The Whole Bible" to "Celotna Biblija",
        "Old Testament" to "Stara zaveza",
        "Pentateuch" to "Petoknjižje",
        "History" to "Zgodovina",
        "Poetry" to "Poezija",
        "All Prophecy" to "Vse prerokbe",
        "Major Prophets" to "Veliki preroki",
        "Minor Prophets" to "Mali preroki",
        "New Testament" to "Nova zaveza",
        "Gospels and Acts" to "Evangeliji in Apostolska dela",
        "Letters" to "Pisma",
        "Letters to People" to "Pisma ljudem",
        "Letters from People" to "Pisma od ljudi",
        "Revelation" to "Razodetje"
    )

    override fun getString(key: String): String? = strings[key]
}
