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

object KoreanStrings : StringLocalization {
    private val strings = mapOf(
        // Division names
        "The Whole Bible" to "성경 전체",
        "Old Testament" to "구약",
        "Pentateuch" to "모세오경",
        "History" to "역사서",
        "Poetry" to "시가서",
        "All Prophecy" to "모든 예언서",
        "Major Prophets" to "대선지서",
        "Minor Prophets" to "소선지서",
        "New Testament" to "신약",
        "Gospels and Acts" to "복음서와 사도행전",
        "Letters" to "서신",
        "Letters to People" to "사람들에게 보낸 편지",
        "Letters from People" to "사람들로부터 온 편지",
        "Revelation" to "요한계시록"
    )

    override fun getString(key: String): String? = strings[key]
}
