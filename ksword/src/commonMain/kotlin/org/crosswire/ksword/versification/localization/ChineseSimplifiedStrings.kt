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

object ChineseSimplifiedStrings : StringLocalization {
    private val strings = mapOf(
        // Division names
        "The Whole Bible" to "新旧约圣经",
        "Old Testament" to "旧约",
        "Pentateuch" to "摩西五经",
        "History" to "历史书",
        "Poetry" to "诗歌书",
        "All Prophecy" to "所有先知书",
        "Major Prophets" to "大先知书",
        "Minor Prophets" to "小先知书",
        "New Testament" to "新约",
        "Gospels and Acts" to "福音书",
        "Letters" to "使徒书信",
        "Letters to People" to "给人的书信",
        "Letters from People" to "来自人的书信",
        "Revelation" to "启示录"
    )

    override fun getString(key: String): String? = strings[key]
}
