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

object VietnameseStrings : StringLocalization {
    private val strings = mapOf(
        // Division names
        "The Whole Bible" to "Toàn Bộ Kinh Thánh",
        "Old Testament" to "Cựu Ước",
        "Pentateuch" to "Ngũ Kinh",
        "History" to "Lịch Sử",
        "Poetry" to "Thơ Ca",
        "All Prophecy" to "Tất Cả Tiên Tri",
        "Major Prophets" to "Các Tiên Tri Lớn",
        "Minor Prophets" to "Các Tiên Tri Nhỏ",
        "New Testament" to "Tân Ước",
        "Gospels and Acts" to "Phúc Âm và Công Vụ",
        "Letters" to "Thư Tín",
        "Letters to People" to "Thư Gửi Con Người",
        "Letters from People" to "Thư Từ Con Người",
        "Revelation" to "Khải Huyền"
    )

    override fun getString(key: String): String? = strings[key]
}
