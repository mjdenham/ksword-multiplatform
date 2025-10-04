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

object TeluguStrings : StringLocalization {
    private val strings = mapOf(
        // Division names
        "The Whole Bible" to "మొత్తం బైబిల్",
        "Old Testament" to "పాత నిబంధన",
        "Pentateuch" to "మోషే యొక్క ఐదు పుస్తకాలు",
        "History" to "చరిత్ర",
        "Poetry" to "కావ్యం",
        "All Prophecy" to "అన్ని ప్రవచనాలు",
        "Major Prophets" to "ప్రధాన ప్రవక్తలు",
        "Minor Prophets" to "చిన్న ప్రవక్తలు",
        "New Testament" to "కొత్త నిబంధన",
        "Gospels and Acts" to "సువార్తలు మరియు అపొస్తలుల కార్యములు",
        "Letters" to "లేఖలు",
        "Letters to People" to "ప్రజలకు లేఖలు",
        "Letters from People" to "ప్రజల నుండి లేఖలు",
        "Revelation" to "ప్రకటన"
    )

    override fun getString(key: String): String? = strings[key]
}
