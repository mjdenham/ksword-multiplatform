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

object NorwegianStrings : StringLocalization {
    private val strings = mapOf(
        // Division names
        "The Whole Bible" to "Hele bibelen",
        "Old Testament" to "Det gamle testamente",
        "Pentateuch" to "Mosebøkene",
        "History" to "Historiske bøker",
        "Poetry" to "Poesi bøker",
        "All Prophecy" to "Alle profeter",
        "Major Prophets" to "Store profeter",
        "Minor Prophets" to "Små profeter",
        "New Testament" to "Det nye testamente",
        "Gospels and Acts" to "Evangeliene og Apostelenes gjerninger",
        "Letters" to "Brever",
        "Letters to People" to "Brev til folk",
        "Letters from People" to "Brev fra folk",
        "Revelation" to "Åpenbaring"
    )

    override fun getString(key: String): String? = strings[key]
}
