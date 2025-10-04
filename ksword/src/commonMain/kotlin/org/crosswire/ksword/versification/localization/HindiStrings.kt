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

object HindiStrings : StringLocalization {
    private val strings = mapOf(
        // Division names
        "The Whole Bible" to "सम्पूर्ण बाइबिल",
        "Old Testament" to "पुराना नियम",
        "Pentateuch" to "मूसा की पाँच पुस्तकें",
        "History" to "इतिहास",
        "Poetry" to "काव्य",
        "All Prophecy" to "सभी भविष्यवाणियाँ",
        "Major Prophets" to "बड़े नबी",
        "Minor Prophets" to "छोटे नबी",
        "New Testament" to "नया नियम",
        "Gospels and Acts" to "सुसमाचार और प्रेरितों के काम",
        "Letters" to "पत्रियाँ",
        "Letters to People" to "लोगों को पत्रियाँ",
        "Letters from People" to "लोगों से पत्रियाँ",
        "Revelation" to "प्रकाशितवाक्य"
    )

    override fun getString(key: String): String? = strings[key]
}
