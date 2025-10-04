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
 * Slovenian localized Bible book names.
 * Generated from BibleNames_sl.properties
 */
object SlovenianBookNames : BookNameLocalization {
    private val fullNames = mapOf(
        BibleBook.GEN to "Geneza",
        BibleBook.EXOD to "Eksodus",
        BibleBook.LEV to "Levitik",
        BibleBook.NUM to "Numeri",
        BibleBook.DEUT to "Devteronomij",
        BibleBook.JOSH to "Jozue",
        BibleBook.JUDG to "Sodniki",
        BibleBook.RUTH to "Ruta",
        BibleBook.SAM1 to "1 Samuel",
        BibleBook.SAM2 to "2 Samuel",
        BibleBook.KGS1 to "1 Kraljev",
        BibleBook.KGS2 to "2 Kraljev",
        BibleBook.CHR1 to "1 Kroniška",
        BibleBook.CHR2 to "2 Kroniška",
        BibleBook.EZRA to "Ezra",
        BibleBook.NEH to "Nehemija",
        BibleBook.ESTH to "Estera",
        BibleBook.JOB to "Job",
        BibleBook.PS to "Psalmi",
        BibleBook.PROV to "Pregovori",
        BibleBook.ECCL to "Pridigar",
        BibleBook.SONG to "Pesem pesmi",
        BibleBook.ISA to "Izaija",
        BibleBook.JER to "Jeremija",
        BibleBook.LAM to "Žalostinke",
        BibleBook.EZEK to "Ezekiel",
        BibleBook.DAN to "Daniel",
        BibleBook.HOS to "Ozej",
        BibleBook.JOEL to "Joel",
        BibleBook.AMOS to "Amos",
        BibleBook.OBAD to "Abdija",
        BibleBook.JONAH to "Jona",
        BibleBook.MIC to "Mihej",
        BibleBook.NAH to "Nahum",
        BibleBook.HAB to "Habakuk",
        BibleBook.ZEPH to "Sofonija",
        BibleBook.HAG to "Agej",
        BibleBook.ZECH to "Zaharija",
        BibleBook.MAL to "Malahija",
        BibleBook.MATT to "Matej",
        BibleBook.MARK to "Marko",
        BibleBook.LUKE to "Luka",
        BibleBook.JOHN to "Janez",
        BibleBook.ACTS to "Apostolska dela",
        BibleBook.ROM to "Rimljanom",
        BibleBook.COR1 to "1 Korinčanom",
        BibleBook.COR2 to "2 Korinčanom",
        BibleBook.GAL to "Galačanom",
        BibleBook.EPH to "Efežanom",
        BibleBook.PHIL to "Filipljanom",
        BibleBook.COL to "Kološanom",
        BibleBook.THESS1 to "1 Tesaloničanom",
        BibleBook.THESS2 to "2 Tesaloničanom",
        BibleBook.TIM1 to "1 Timoteju",
        BibleBook.TIM2 to "2 Timoteju",
        BibleBook.TITUS to "Titu",
        BibleBook.PHLM to "Filemonu",
        BibleBook.HEB to "Hebrejcem",
        BibleBook.JAS to "Jakob",
        BibleBook.PET1 to "1  Peter",
        BibleBook.PET2 to "2 Peter",
        BibleBook.JOHN1 to "1 Janez",
        BibleBook.JOHN2 to "2 Janez",
        BibleBook.JOHN3 to "3 Janez",
        BibleBook.JUDE to "Juda",
        BibleBook.REV to "Razodetje"
    )

    private val shortNames = mapOf(
        BibleBook.GEN to "Gen",
        BibleBook.EXOD to "Eks",
        BibleBook.LEV to "Lev",
        BibleBook.NUM to "Num",
        BibleBook.DEUT to "Dev",
        BibleBook.JOSH to "Joz",
        BibleBook.JUDG to "Sod",
        BibleBook.RUTH to "Rut",
        BibleBook.SAM1 to "1 Sam",
        BibleBook.SAM2 to "2 Sam",
        BibleBook.KGS1 to "1 Kr",
        BibleBook.KGS2 to "2 Kr",
        BibleBook.CHR1 to "1 Krn",
        BibleBook.CHR2 to "2 Krn",
        BibleBook.EZRA to "Ezr",
        BibleBook.NEH to "Neh",
        BibleBook.ESTH to "Est",
        BibleBook.JOB to "Job",
        BibleBook.PS to "Ps",
        BibleBook.PROV to "Prg",
        BibleBook.ECCL to "Prd",
        BibleBook.SONG to "Pp",
        BibleBook.ISA to "Iz",
        BibleBook.JER to "Jer",
        BibleBook.LAM to "Žal",
        BibleBook.EZEK to "Ezk",
        BibleBook.DAN to "Dan",
        BibleBook.HOS to "Oz",
        BibleBook.JOEL to "Jl",
        BibleBook.AMOS to "Am",
        BibleBook.OBAD to "Abd",
        BibleBook.JONAH to "Jon",
        BibleBook.MIC to "Mih",
        BibleBook.NAH to "Nah",
        BibleBook.HAB to "Hab",
        BibleBook.ZEPH to "Sof",
        BibleBook.HAG to "Ag",
        BibleBook.ZECH to "Zah",
        BibleBook.MAL to "Mal",
        BibleBook.MATT to "Mt",
        BibleBook.MARK to "Mr",
        BibleBook.LUKE to "Lk",
        BibleBook.JOHN to "Jn",
        BibleBook.ACTS to "Apd",
        BibleBook.ROM to "Rim",
        BibleBook.COR1 to "1 Kor",
        BibleBook.COR2 to "2 Kor",
        BibleBook.GAL to "Gal",
        BibleBook.EPH to "Ef",
        BibleBook.PHIL to "Flp",
        BibleBook.COL to "Kol",
        BibleBook.THESS1 to "1 Tes",
        BibleBook.THESS2 to "2 Tes",
        BibleBook.TIM1 to "1 Tim",
        BibleBook.TIM2 to "2 Tim",
        BibleBook.TITUS to "Tit",
        BibleBook.PHLM to "Flm",
        BibleBook.HEB to "Heb",
        BibleBook.JAS to "Jak",
        BibleBook.PET1 to "1 Pt",
        BibleBook.PET2 to "2 Pt",
        BibleBook.JOHN1 to "1 Jn",
        BibleBook.JOHN2 to "2 Jn",
        BibleBook.JOHN3 to "3 Jn",
        BibleBook.JUDE to "Jud",
        BibleBook.REV to "Raz"
    )

    override fun getFullName(book: BibleBook): String? = fullNames[book]
    override fun getShortName(book: BibleBook): String? = shortNames[book]
    override fun getAlternateName(book: BibleBook): String? = null
}
