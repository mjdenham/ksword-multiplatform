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

object TurkishStrings : StringLocalization {
    private val strings = mapOf(
        // Division names
        "The Whole Bible" to "Tüm İncil",
        "Old Testament" to "Eski Antlaşma",
        "Pentateuch" to "Tevrat",
        "History" to "Tarih",
        "Poetry" to "Şiir",
        "All Prophecy" to "Tüm Peygamberlik",
        "Major Prophets" to "Büyük Peygamberler",
        "Minor Prophets" to "Küçük Peygamberler",
        "New Testament" to "Yeni Antlaşma",
        "Gospels and Acts" to "İnciller ve Elçilerin İşleri",
        "Letters" to "Mektuplar",
        "Letters to People" to "İnsanlara Mektuplar",
        "Letters from People" to "İnsanlardan Mektuplar",
        "Revelation" to "Vahiy"
    )

    override fun getString(key: String): String? = strings[key]
}
