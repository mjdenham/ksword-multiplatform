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

object FrenchStrings : StringLocalization {
    private val strings = mapOf(
        // Division names
        "The Whole Bible" to "La Bible entier",
        "Old Testament" to "Ancien Testament",
        "Pentateuch" to "Pentateuque",
        "History" to "Livres historiques",
        "Poetry" to "Poésie",
        "All Prophecy" to "Toutes les prophéties",
        "Major Prophets" to "Prophètes majeurs",
        "Minor Prophets" to "Prophètes mineurs",
        "New Testament" to "Nouveau Testament",
        "Gospels and Acts" to "Évangiles et les Actes",
        "Letters" to "Lettres",
        "Letters to People" to "Lettres à des personnes",
        "Letters from People" to "Lettres de personnes",
        "Revelation" to "Apocalypse"
    )

    override fun getString(key: String): String? = strings[key]
}
