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

object LatvianStrings : StringLocalization {
    private val strings = mapOf(
        // Division names
        "The Whole Bible" to "Visa Bībele",
        "Old Testament" to "Vecā Derība",
        "Pentateuch" to "Pentatehs",
        "History" to "Vēsture",
        "Poetry" to "Poēzija",
        "All Prophecy" to "Visas pravietojumi",
        "Major Prophets" to "Lielie pravieši",
        "Minor Prophets" to "Mazie pravieši",
        "New Testament" to "Jaunā Derība",
        "Gospels and Acts" to "Evaņģēliji un Apustuļu darbi",
        "Letters" to "Vēstules",
        "Letters to People" to "Vēstules cilvēkiem",
        "Letters from People" to "Vēstules no cilvēkiem",
        "Revelation" to "Atklāsme"
    )

    override fun getString(key: String): String? = strings[key]
}
