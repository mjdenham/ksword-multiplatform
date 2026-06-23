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

/** Traditional Chinese (Mandarin) division/section names — the traditional-script form of [ChineseSimplifiedStrings]. */
object ChineseTraditionalStrings : StringLocalization {
    private val strings = mapOf(
        // Division names
        "The Whole Bible" to "新舊約聖經",
        "Old Testament" to "舊約",
        "Pentateuch" to "摩西五經",
        "History" to "歷史書",
        "Poetry" to "詩歌書",
        "All Prophecy" to "所有先知書",
        "Major Prophets" to "大先知書",
        "Minor Prophets" to "小先知書",
        "New Testament" to "新約",
        "Gospels and Acts" to "福音書",
        "Letters" to "使徒書信",
        "Letters to People" to "給人的書信",
        "Letters from People" to "來自人的書信",
        "Revelation" to "啟示錄"
    )

    override fun getString(key: String): String? = strings[key]
}
