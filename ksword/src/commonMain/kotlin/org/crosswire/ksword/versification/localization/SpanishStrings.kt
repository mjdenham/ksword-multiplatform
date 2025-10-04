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

object SpanishStrings : StringLocalization {
    private val strings = mapOf(
        // Division names
        "The Whole Bible" to "Toda la Biblia",
        "Old Testament" to "Antiguo Testamento",
        "Pentateuch" to "Pentateuco",
        "History" to "Historia",
        "Poetry" to "Poesía",
        "All Prophecy" to "Toda Profecía",
        "Major Prophets" to "Profetas Mayores",
        "Minor Prophets" to "Profetas Menores",
        "New Testament" to "Nuevo Testamento",
        "Gospels and Acts" to "Evangelios y Hechos",
        "Letters" to "Cartas",
        "Letters to People" to "Cartas a Personas",
        "Letters from People" to "Cartas de Personas",
        "Revelation" to "Apocalipsis"
    )

    override fun getString(key: String): String? = strings[key]
}
