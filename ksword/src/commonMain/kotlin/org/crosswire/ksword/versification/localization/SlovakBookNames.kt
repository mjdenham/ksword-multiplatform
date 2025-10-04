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
 * Slovak localized Bible book names.
 * Generated from BibleNames_sk.properties
 */
object SlovakBookNames : BookNameLocalization {
    private val fullNames = mapOf(
        BibleBook.GEN to "Genezis",
        BibleBook.EXOD to "Exodus",
        BibleBook.LEV to "Levitikus",
        BibleBook.NUM to "Numeri",
        BibleBook.DEUT to "Deuteronómium",
        BibleBook.JOSH to "Jozua",
        BibleBook.JUDG to "Sudcov",
        BibleBook.RUTH to "Rút",
        BibleBook.SAM1 to "1 Samuelova",
        BibleBook.SAM2 to "2 Samuelova",
        BibleBook.KGS1 to "1. kniha kráľov",
        BibleBook.KGS2 to "2. kniha kráľov",
        BibleBook.CHR1 to "1. kniha kroník",
        BibleBook.CHR2 to "2. kniha kroník",
        BibleBook.EZRA to "Ezdráš",
        BibleBook.NEH to "Nehemiáš",
        BibleBook.ESTH to "Ester",
        BibleBook.JOB to "Jób",
        BibleBook.PS to "Žalmy",
        BibleBook.PROV to "Príslovia",
        BibleBook.ECCL to "Kohelet - kazateľ",
        BibleBook.SONG to "Veľpieseň",
        BibleBook.ISA to "Izaiáš",
        BibleBook.JER to "Jeremiáš",
        BibleBook.LAM to "Náreky",
        BibleBook.EZEK to "Ezechiel",
        BibleBook.DAN to "Daniel",
        BibleBook.HOS to "Ozeáš",
        BibleBook.JOEL to "Joel",
        BibleBook.AMOS to "Ámos",
        BibleBook.OBAD to "Abdiáš",
        BibleBook.JONAH to "Jonáš",
        BibleBook.MIC to "Micheáš",
        BibleBook.NAH to "Náhum",
        BibleBook.HAB to "Habakuk",
        BibleBook.ZEPH to "Sofoniáš",
        BibleBook.HAG to "Aggeus",
        BibleBook.ZECH to "Zachariáš",
        BibleBook.MAL to "Malachiáš",
        BibleBook.MATT to "Matúš",
        BibleBook.MARK to "Marek",
        BibleBook.LUKE to "Lukáš",
        BibleBook.JOHN to "Ján",
        BibleBook.ACTS to "Skutky",
        BibleBook.ROM to "Rimanom",
        BibleBook.COR1 to "1. Korinťanom",
        BibleBook.COR2 to "2. Korinťanom",
        BibleBook.GAL to "Galaťanom",
        BibleBook.EPH to "Efezanom",
        BibleBook.PHIL to "Filipanom",
        BibleBook.COL to "Kolosanom",
        BibleBook.THESS1 to "1. Tesaloničanom",
        BibleBook.THESS2 to "2. Tesaloničanom",
        BibleBook.TIM1 to "1. Timotejovi",
        BibleBook.TIM2 to "2. Timotejovi",
        BibleBook.TITUS to "Títovi",
        BibleBook.PHLM to "Filemonovi",
        BibleBook.HEB to "Hebrejom",
        BibleBook.JAS to "Jakubov",
        BibleBook.PET1 to "1. Petrov",
        BibleBook.PET2 to "2. Petrov",
        BibleBook.JOHN1 to "1. Jánov",
        BibleBook.JOHN2 to "2. Jánov",
        BibleBook.JOHN3 to "3. Jánov",
        BibleBook.JUDE to "Júdov",
        BibleBook.REV to "Zjavenie Jána"
    )

    private val shortNames = mapOf(
        BibleBook.GEN to "Gn",
        BibleBook.EXOD to "Ex",
        BibleBook.LEV to "Lv",
        BibleBook.NUM to "Nm",
        BibleBook.DEUT to "Dt",
        BibleBook.JOSH to "Joz",
        BibleBook.JUDG to "Sdc",
        BibleBook.RUTH to "Rút",
        BibleBook.SAM1 to "1Sam",
        BibleBook.SAM2 to "2Sam",
        BibleBook.KGS1 to "1Krľ",
        BibleBook.KGS2 to "2Krľ",
        BibleBook.CHR1 to "1Krn",
        BibleBook.CHR2 to "2Krn",
        BibleBook.EZRA to "Ezd",
        BibleBook.NEH to "Neh",
        BibleBook.ESTH to "Est",
        BibleBook.JOB to "Jób",
        BibleBook.PS to "Ž",
        BibleBook.PROV to "Prís",
        BibleBook.ECCL to "Kaz",
        BibleBook.SONG to "Vľp",
        BibleBook.ISA to "Iz",
        BibleBook.JER to "Jer",
        BibleBook.LAM to "Nár",
        BibleBook.EZEK to "Ez",
        BibleBook.DAN to "Dan",
        BibleBook.HOS to "Oz",
        BibleBook.JOEL to "Joel",
        BibleBook.AMOS to "Am",
        BibleBook.OBAD to "Abd",
        BibleBook.JONAH to "Jon",
        BibleBook.MIC to "Mich",
        BibleBook.NAH to "Nah",
        BibleBook.HAB to "Hab",
        BibleBook.ZEPH to "Sof",
        BibleBook.HAG to "Ag",
        BibleBook.ZECH to "Zach",
        BibleBook.MAL to "Mal",
        BibleBook.MATT to "Mt",
        BibleBook.MARK to "Mk",
        BibleBook.LUKE to "Lk",
        BibleBook.JOHN to "Jn",
        BibleBook.ACTS to "Sk",
        BibleBook.ROM to "Rm",
        BibleBook.COR1 to "1Kor",
        BibleBook.COR2 to "2Kor",
        BibleBook.GAL to "Ga",
        BibleBook.EPH to "Ef",
        BibleBook.PHIL to "Flp",
        BibleBook.COL to "Kol",
        BibleBook.THESS1 to "1Tes",
        BibleBook.THESS2 to "2Tes",
        BibleBook.TIM1 to "1Tim",
        BibleBook.TIM2 to "2Tim",
        BibleBook.TITUS to "Tít",
        BibleBook.PHLM to "Flm",
        BibleBook.HEB to "Heb",
        BibleBook.JAS to "Jk",
        BibleBook.PET1 to "1Pt",
        BibleBook.PET2 to "2Pt",
        BibleBook.JOHN1 to "1Jn",
        BibleBook.JOHN2 to "2Jn",
        BibleBook.JOHN3 to "3Jn",
        BibleBook.JUDE to "Júd",
        BibleBook.REV to "Zj"
    )

    override fun getFullName(book: BibleBook): String? = fullNames[book]
    override fun getShortName(book: BibleBook): String? = shortNames[book]
    override fun getAlternateName(book: BibleBook): String? = null
}
