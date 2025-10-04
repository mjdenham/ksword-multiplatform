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

object ThaiStrings : StringLocalization {
    private val strings = mapOf(
        // Division names
        "The Whole Bible" to "พระคัมภีร์ทั้งเล่ม",
        "Old Testament" to "พันธสัญญาเดิม",
        "Pentateuch" to "เบญจบรรณ",
        "History" to "ประวัติศาสตร์",
        "Poetry" to "วรรณกรรม",
        "All Prophecy" to "หมวดผู้เผยพระวจนะทั้งหมด",
        "Major Prophets" to "ผู้เผยพระวจนะใหญ่",
        "Minor Prophets" to "ผู้เผยพระวจนะน้อย",
        "New Testament" to "พันธสัญญาใหม่",
        "Gospels and Acts" to "พระกิตติคุณและกิจการ",
        "Letters" to "จดหมาย",
        "Letters to People" to "จดหมายไปถึงบุคคล",
        "Letters from People" to "จดหมายจากบุคคล",
        "Revelation" to "วิวรณ์"
    )

    override fun getString(key: String): String? = strings[key]
}
