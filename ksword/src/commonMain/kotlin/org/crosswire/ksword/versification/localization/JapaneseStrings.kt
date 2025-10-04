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

object JapaneseStrings : StringLocalization {
    private val strings = mapOf(
        // Division names
        "The Whole Bible" to "聖書全体",
        "Old Testament" to "旧約聖書",
        "Pentateuch" to "モーセ五書",
        "History" to "歴史書",
        "Poetry" to "詩歌書",
        "All Prophecy" to "すべての預言書",
        "Major Prophets" to "大預言書",
        "Minor Prophets" to "小預言書",
        "New Testament" to "新約聖書",
        "Gospels and Acts" to "福音書と使徒行伝",
        "Letters" to "書簡",
        "Letters to People" to "人々への手紙",
        "Letters from People" to "人々からの手紙",
        "Revelation" to "黙示録"
    )

    override fun getString(key: String): String? = strings[key]
}
