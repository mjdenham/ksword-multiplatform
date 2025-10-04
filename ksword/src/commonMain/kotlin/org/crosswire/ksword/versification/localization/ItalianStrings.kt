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

object ItalianStrings : StringLocalization {
    private val strings = mapOf(
        // Division names
        "The Whole Bible" to "Tutta la Bibbia",
        "Old Testament" to "Antico Testamento",
        "Pentateuch" to "Pentateuco",
        "History" to "Storia",
        "Poetry" to "Poesia",
        "All Prophecy" to "Tutta la Profezia",
        "Major Prophets" to "Profeti Maggiori",
        "Minor Prophets" to "Profeti Minori",
        "New Testament" to "Nuovo Testamento",
        "Gospels and Acts" to "Vangeli e Atti",
        "Letters" to "Lettere",
        "Letters to People" to "Lettere alle Persone",
        "Letters from People" to "Lettere dalle Persone",
        "Revelation" to "Apocalisse"
    )

    override fun getString(key: String): String? = strings[key]
}
