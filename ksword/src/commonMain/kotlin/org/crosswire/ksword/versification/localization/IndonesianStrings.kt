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

object IndonesianStrings : StringLocalization {
    private val strings = mapOf(
        // Division names
        "The Whole Bible" to "Seluruh Alkitab",
        "Old Testament" to "Perjanjian Lama",
        "Pentateuch" to "Taurat",
        "History" to "Sejarah",
        "Poetry" to "Puisi",
        "All Prophecy" to "Semua Nubuat",
        "Major Prophets" to "Nabi-nabi Besar",
        "Minor Prophets" to "Nabi-nabi Kecil",
        "New Testament" to "Perjanjian Baru",
        "Gospels and Acts" to "Injil dan Kisah Para Rasul",
        "Letters" to "Surat-surat",
        "Letters to People" to "Surat kepada Orang-orang",
        "Letters from People" to "Surat dari Orang-orang",
        "Revelation" to "Wahyu"
    )

    override fun getString(key: String): String? = strings[key]
}
