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

object RussianStrings : StringLocalization {
    private val strings = mapOf(
        // Division names
        "The Whole Bible" to "Вся Библия",
        "Old Testament" to "Ветхий Завет",
        "Pentateuch" to "Пятикнижие Моисея",
        "History" to "Исторические Книги",
        "Poetry" to "Писания",
        "All Prophecy" to "Все Пророчества",
        "Major Prophets" to "Большие Пророки",
        "Minor Prophets" to "Малые Пророки",
        "New Testament" to "Новый Завет",
        "Gospels and Acts" to "Евангелия и Деяния Апостолов",
        "Letters" to "Послания",
        "Letters to People" to "Послания к Людям",
        "Letters from People" to "Послания от Людей",
        "Revelation" to "Откровение Иоанна Богослова"
    )

    override fun getString(key: String): String? = strings[key]
}
