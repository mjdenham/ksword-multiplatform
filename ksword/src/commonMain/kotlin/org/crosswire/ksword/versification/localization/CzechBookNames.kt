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

import org.crosswire.ksword.versification.BibleBook

/**
 * Czech localized Bible book names.
 * Generated from BibleNames_cs.properties
 */
object CzechBookNames : BookNameLocalization {
    private val fullNames = mapOf(
        BibleBook.GEN to "Genesis",
        BibleBook.EXOD to "Exodus",
        BibleBook.LEV to "Leviticus",
        BibleBook.NUM to "Numeri",
        BibleBook.DEUT to "Deuteronomium",
        BibleBook.JOSH to "Jozue",
        BibleBook.JUDG to "Soudců",
        BibleBook.RUTH to "Rút",
        BibleBook.SAM1 to "1. Samuelova",
        BibleBook.SAM2 to "2. Samuelova",
        BibleBook.KGS1 to "1. Královská",
        BibleBook.KGS2 to "2. Královská",
        BibleBook.CHR1 to "1. Paralipomenon",
        BibleBook.CHR2 to "2. Paralipomenon",
        BibleBook.EZRA to "Ezdráš",
        BibleBook.NEH to "Nehemiáš",
        BibleBook.ESTH to "Ester",
        BibleBook.JOB to "Jób",
        BibleBook.PS to "Žalmy",
        BibleBook.PROV to "Přísloví",
        BibleBook.ECCL to "Kazatel",
        BibleBook.SONG to "Píseň písní",
        BibleBook.ISA to "Izaiáš",
        BibleBook.JER to "Jeremiáš",
        BibleBook.LAM to "Pláč Jeremiášův",
        BibleBook.EZEK to "Ezechiel",
        BibleBook.DAN to "Daniel",
        BibleBook.HOS to "Ozeáš",
        BibleBook.JOEL to "Jóel",
        BibleBook.AMOS to "Ámos",
        BibleBook.OBAD to "Abdiáš",
        BibleBook.JONAH to "Jonáš",
        BibleBook.MIC to "Micheáš",
        BibleBook.NAH to "Nahum",
        BibleBook.HAB to "Abakuk",
        BibleBook.ZEPH to "Sofoniáš",
        BibleBook.HAG to "Ageus",
        BibleBook.ZECH to "Zachariáš",
        BibleBook.MAL to "Malachiáš",
        BibleBook.MATT to "Matouš",
        BibleBook.MARK to "Marek",
        BibleBook.LUKE to "Lukáš",
        BibleBook.JOHN to "Jan",
        BibleBook.ACTS to "Skutky apoštolů",
        BibleBook.ROM to "Římanům",
        BibleBook.COR1 to "1. Korintským",
        BibleBook.COR2 to "2. Korintským",
        BibleBook.GAL to "Galatským",
        BibleBook.EPH to "Efezským",
        BibleBook.PHIL to "Filipským",
        BibleBook.COL to "Koloským",
        BibleBook.THESS1 to "1. Tesalonickým",
        BibleBook.THESS2 to "2. Tesalonickým",
        BibleBook.TIM1 to "1. Timoteovi",
        BibleBook.TIM2 to "2. Timoteovi",
        BibleBook.TITUS to "Titovi",
        BibleBook.PHLM to "Filemonovi",
        BibleBook.HEB to "Židům",
        BibleBook.JAS to "Jakubova",
        BibleBook.PET1 to "1. Petrova",
        BibleBook.PET2 to "2. Petrova",
        BibleBook.JOHN1 to "1. Janova",
        BibleBook.JOHN2 to "2. Janova",
        BibleBook.JOHN3 to "3. Janova",
        BibleBook.JUDE to "Judova",
        BibleBook.REV to "Zjevení Janovo"
    )

    private val shortNames = mapOf(
        BibleBook.GEN to "1Moj",
        BibleBook.EXOD to "2Moj",
        BibleBook.LEV to "3Moj",
        BibleBook.NUM to "4Moj",
        BibleBook.DEUT to "5Moj",
        BibleBook.JOSH to "Jozue",
        BibleBook.JUDG to "Soud",
        BibleBook.RUTH to "Rút",
        BibleBook.SAM1 to "1Sam",
        BibleBook.SAM2 to "2Sam",
        BibleBook.KGS1 to "1Kr",
        BibleBook.KGS2 to "2Kr",
        BibleBook.CHR1 to "1Par",
        BibleBook.CHR2 to "2Par",
        BibleBook.EZRA to "Ezd",
        BibleBook.NEH to "Neh",
        BibleBook.ESTH to "Est",
        BibleBook.JOB to "Jób",
        BibleBook.PS to "Žalm",
        BibleBook.PROV to "Přísl",
        BibleBook.ECCL to "Kaz",
        BibleBook.SONG to "Píseň",
        BibleBook.ISA to "Iz",
        BibleBook.JER to "Jer",
        BibleBook.LAM to "Pláč",
        BibleBook.EZEK to "Ezech",
        BibleBook.DAN to "Dan",
        BibleBook.HOS to "Oz",
        BibleBook.JOEL to "Jóel",
        BibleBook.AMOS to "Ámos",
        BibleBook.OBAD to "Abd",
        BibleBook.JONAH to "Jon",
        BibleBook.MIC to "Mich",
        BibleBook.NAH to "Nah",
        BibleBook.HAB to "Abak",
        BibleBook.ZEPH to "Sof",
        BibleBook.HAG to "Ag",
        BibleBook.ZECH to "Zach",
        BibleBook.MAL to "Mal",
        BibleBook.MATT to "Mat",
        BibleBook.MARK to "Mar",
        BibleBook.LUKE to "Luk",
        BibleBook.JOHN to "Jan",
        BibleBook.ACTS to "Skut",
        BibleBook.ROM to "Řím",
        BibleBook.COR1 to "1Kor",
        BibleBook.COR2 to "2Kor",
        BibleBook.GAL to "Gal",
        BibleBook.EPH to "Efez",
        BibleBook.PHIL to "Filip",
        BibleBook.COL to "Kolos",
        BibleBook.THESS1 to "1Tes",
        BibleBook.THESS2 to "2Tes",
        BibleBook.TIM1 to "1Tim",
        BibleBook.TIM2 to "2Tim",
        BibleBook.TITUS to "Tit",
        BibleBook.PHLM to "Filem",
        BibleBook.HEB to "Žid",
        BibleBook.JAS to "Jakub",
        BibleBook.PET1 to "1Petr",
        BibleBook.PET2 to "2Petr",
        BibleBook.JOHN1 to "1Jan",
        BibleBook.JOHN2 to "2Jan",
        BibleBook.JOHN3 to "3Jan",
        BibleBook.JUDE to "Jud",
        BibleBook.REV to "Zjev"
    )

    override fun getFullName(book: BibleBook): String? = fullNames[book]
    override fun getShortName(book: BibleBook): String? = shortNames[book]
    override fun getAlternateName(book: BibleBook): String? = null
}
