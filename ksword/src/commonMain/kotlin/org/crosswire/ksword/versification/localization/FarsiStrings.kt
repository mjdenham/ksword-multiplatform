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

object FarsiStrings : StringLocalization {
    private val strings = mapOf(
        // Division names
        "The Whole Bible" to "کتاب مقدس کامل",
        "Old Testament" to "عهد عتیق",
        "Pentateuch" to "تورات",
        "History" to "تریخ",
        "Poetry" to "شاعر",
        "All Prophecy" to "همه نبوت‌ها",
        "Major Prophets" to "نبیهای بزرگ",
        "Minor Prophets" to "نبیهای کوچک",
        "New Testament" to "عهد جدید",
        "Gospels and Acts" to "ایجیل و اعمال رسولان",
        "Letters" to "نامه‌ها",
        "Letters to People" to "نمه‌ها پ پولاس",
        "Letters from People" to "نامه‌ها رسولان دیگار",
        "Revelation" to "مکاشفه"
    )

    override fun getString(key: String): String? = strings[key]
}
