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
 * Croatian localized Bible book names.
 * Generated from BibleNames_hr.properties
 */
object CroatianBookNames : BookNameLocalization {
    private val fullNames = mapOf(
        BibleBook.GEN to "Postanak",
        BibleBook.EXOD to "Izlazak",
        BibleBook.LEV to "Levitski zakonik",
        BibleBook.NUM to "Brojevi",
        BibleBook.DEUT to "Ponovljeni Zakon",
        BibleBook.JOSH to "Jošua",
        BibleBook.JUDG to "Suci",
        BibleBook.RUTH to "Ruta",
        BibleBook.SAM1 to "Prva knjiga o Samuelu",
        BibleBook.SAM2 to "Druga knjiga o Samuelu",
        BibleBook.KGS1 to "Prva knjiga o kraljevima",
        BibleBook.KGS2 to "Druga knjiga o kraljevima",
        BibleBook.CHR1 to "Prva knjiga Ljetopisa",
        BibleBook.CHR2 to "Druga knjiga Ljetopisa",
        BibleBook.EZRA to "Ezra",
        BibleBook.NEH to "Nehemija",
        BibleBook.ESTH to "Estera",
        BibleBook.JOB to "Knjiga o Jobu",
        BibleBook.PS to "Psalmi",
        BibleBook.PROV to "Izreke",
        BibleBook.ECCL to "Propovjednik",
        BibleBook.SONG to "Pjesma nad pjesmama",
        BibleBook.ISA to "Izaija",
        BibleBook.JER to "Jeremija",
        BibleBook.LAM to "Tužaljke",
        BibleBook.EZEK to "Ezekiel",
        BibleBook.DAN to "Daniel",
        BibleBook.HOS to "Hošea",
        BibleBook.JOEL to "Joel",
        BibleBook.AMOS to "Amos",
        BibleBook.OBAD to "Obadija",
        BibleBook.JONAH to "Jona",
        BibleBook.MIC to "Mihej",
        BibleBook.NAH to "Nahum",
        BibleBook.HAB to "Habakuk",
        BibleBook.ZEPH to "Sefanija",
        BibleBook.HAG to "Hagaj",
        BibleBook.ZECH to "Zaharija",
        BibleBook.MAL to "Malahija",
        BibleBook.MATT to "Evanđelje po Mateju",
        BibleBook.MARK to "Evanđelje po Marku",
        BibleBook.LUKE to "Evanđelje po Luki",
        BibleBook.JOHN to "Evanđelje po Ivanu",
        BibleBook.ACTS to "Djela apostolska",
        BibleBook.ROM to "Rimljanina",
        BibleBook.COR1 to "Prva Korinćanima",
        BibleBook.COR2 to "Druga Korinćanima",
        BibleBook.GAL to "Galaćanima",
        BibleBook.EPH to "Efežanima",
        BibleBook.PHIL to "Filipljanima",
        BibleBook.COL to "Kološanima",
        BibleBook.THESS1 to "Prva Solunjanima",
        BibleBook.THESS2 to "Druga Solunjanima",
        BibleBook.TIM1 to "Prva Timoteju",
        BibleBook.TIM2 to "Druga Timoteju",
        BibleBook.TITUS to "Titu",
        BibleBook.PHLM to "Filemonu",
        BibleBook.HEB to "Hebrejima",
        BibleBook.JAS to "Jakovljeva",
        BibleBook.PET1 to "Prva Petrova",
        BibleBook.PET2 to "Druga Petrova",
        BibleBook.JOHN1 to "Prva Ivanova",
        BibleBook.JOHN2 to "Druga Ivanova",
        BibleBook.JOHN3 to "Treća Ivanova",
        BibleBook.JUDE to "Judina",
        BibleBook.REV to "Otkrivenje"
    )

    private val shortNames = mapOf(
        BibleBook.GEN to "Pos",
        BibleBook.EXOD to "Izl",
        BibleBook.LEV to "Lev",
        BibleBook.NUM to "Br",
        BibleBook.DEUT to "Pnz",
        BibleBook.JOSH to "Jš",
        BibleBook.JUDG to "Su",
        BibleBook.RUTH to "Ru",
        BibleBook.SAM1 to "1Sam",
        BibleBook.SAM2 to "2Sam",
        BibleBook.KGS1 to "1Kr",
        BibleBook.KGS2 to "2Kr",
        BibleBook.CHR1 to "1Ljet",
        BibleBook.CHR2 to "2Ljet",
        BibleBook.EZRA to "Ezr",
        BibleBook.NEH to "Neh",
        BibleBook.ESTH to "Est",
        BibleBook.JOB to "Job",
        BibleBook.PS to "Ps",
        BibleBook.PROV to "Izr",
        BibleBook.ECCL to "Pro",
        BibleBook.SONG to "Pj",
        BibleBook.ISA to "Iz",
        BibleBook.JER to "Jer",
        BibleBook.LAM to "Tuž",
        BibleBook.EZEK to "Ez",
        BibleBook.DAN to "Dan",
        BibleBook.HOS to "Hoš",
        BibleBook.JOEL to "Jl",
        BibleBook.AMOS to "Am",
        BibleBook.OBAD to "Ob",
        BibleBook.JONAH to "Jon",
        BibleBook.MIC to "Mih",
        BibleBook.NAH to "Nah",
        BibleBook.HAB to "Hab",
        BibleBook.ZEPH to "Sef",
        BibleBook.HAG to "Hag",
        BibleBook.ZECH to "Zah",
        BibleBook.MAL to "Mal",
        BibleBook.MATT to "Mt",
        BibleBook.MARK to "Mk",
        BibleBook.LUKE to "Lk",
        BibleBook.JOHN to "Iv",
        BibleBook.ACTS to "Dj",
        BibleBook.ROM to "Rim",
        BibleBook.COR1 to "1Kor",
        BibleBook.COR2 to "2Kor",
        BibleBook.GAL to "Gal",
        BibleBook.EPH to "Ef",
        BibleBook.PHIL to "Fil",
        BibleBook.COL to "Kol",
        BibleBook.THESS1 to "1Sol",
        BibleBook.THESS2 to "2Sol",
        BibleBook.TIM1 to "1Tim",
        BibleBook.TIM2 to "2Tim",
        BibleBook.TITUS to "Tit",
        BibleBook.PHLM to "Flm",
        BibleBook.HEB to "Heb",
        BibleBook.JAS to "Jak",
        BibleBook.PET1 to "1Pt",
        BibleBook.PET2 to "2Pt",
        BibleBook.JOHN1 to "1Iv",
        BibleBook.JOHN2 to "2Iv",
        BibleBook.JOHN3 to "3Iv",
        BibleBook.JUDE to "Jd",
        BibleBook.REV to "Otk"
    )

    private val alternateNames = mapOf(
        BibleBook.DEUT to "dt",
        BibleBook.JUDG to "sdj,sud",
        BibleBook.RUTH to "rut",
        BibleBook.SAM1 to "1sam",
        BibleBook.SAM2 to "2sam",
        BibleBook.KGS1 to "1kr,1kraljevima",
        BibleBook.KGS2 to "2kr,2raljevima",
        BibleBook.CHR1 to "1lje",
        BibleBook.CHR2 to "2lje",
        BibleBook.PS to "ps,psm",
        BibleBook.ECCL to "prop",
        BibleBook.SONG to "ss,songofsongs,sos,canticleofcanticles,canticle,can,coc,sng",
        BibleBook.ISA to "isa",
        BibleBook.EZEK to "Ezk",
        BibleBook.JOEL to "Jol",
        BibleBook.JONAH to "jona",
        BibleBook.NAH to "Nam",
        BibleBook.MATT to "mt",
        BibleBook.MARK to "mk",
        BibleBook.LUKE to "lk",
        BibleBook.JOHN to "jn",
        BibleBook.COR1 to "1kor",
        BibleBook.COR2 to "2kor",
        BibleBook.PHIL to "flp",
        BibleBook.COL to "kol",
        BibleBook.THESS1 to "1sol",
        BibleBook.THESS2 to "2sol",
        BibleBook.TIM1 to "1tim",
        BibleBook.TIM2 to "2tim",
        BibleBook.PHLM to "flm",
        BibleBook.JAS to "jak",
        BibleBook.PET1 to "1pt",
        BibleBook.PET2 to "2pt",
        BibleBook.JOHN1 to "1jn",
        BibleBook.JOHN2 to "2jn",
        BibleBook.JOHN3 to "3jn",
        BibleBook.REV to "otk",
        BibleBook.EP_JER to "pjer",
        BibleBook.BEL to "bel",
        BibleBook.MACC1 to "1mak",
        BibleBook.MACC2 to "2mak",
        BibleBook.MACC3 to "3mak",
        BibleBook.MACC4 to "4mak",
        BibleBook.PSALM_SOL to "pssol",
        BibleBook.EP_LAO to "laod",
        BibleBook.EN1 to "1hen",
        BibleBook.CLEM1 to "1klement,1klem",
        BibleBook.CLEM2 to "2klement,3klem"
    )

    override fun getFullName(book: BibleBook): String? = fullNames[book]
    override fun getShortName(book: BibleBook): String? = shortNames[book]
    override fun getAlternateName(book: BibleBook): String? = alternateNames[book]
}
