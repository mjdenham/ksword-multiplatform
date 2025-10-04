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

object ArabicStrings : StringLocalization {
    private val strings = mapOf(
        // Division names
        "The Whole Bible" to "الكتاب المقدس بأكمله",
        "Old Testament" to "العهد القديم",
        "Pentateuch" to "أسفار موسى الخمسة",
        "History" to "التاريخ",
        "Poetry" to "الشعر",
        "All Prophecy" to "كل النبوات",
        "Major Prophets" to "الأنبياء الكبار",
        "Minor Prophets" to "الأنبياء الصغار",
        "New Testament" to "العهد الجديد",
        "Gospels and Acts" to "الأناجيل وأعمال الرسل",
        "Letters" to "الرسائل",
        "Letters to People" to "رسائل إلى الناس",
        "Letters from People" to "رسائل من الناس",
        "Revelation" to "الرؤيا"
    )

    override fun getString(key: String): String? = strings[key]
}
