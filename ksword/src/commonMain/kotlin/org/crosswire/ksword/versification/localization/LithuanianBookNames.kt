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
 * Lithuanian localized Bible book names.
 * Generated from BibleNames_lt.properties
 */
object LithuanianBookNames : BookNameLocalization {
    private val fullNames = mapOf(
        BibleBook.GEN to "Pradžios",
        BibleBook.EXOD to "Išėjimo",
        BibleBook.LEV to "Kunigų",
        BibleBook.NUM to "Skaičių",
        BibleBook.DEUT to "Pakartoto Įstatymo",
        BibleBook.JOSH to "Jozuės",
        BibleBook.JUDG to "Teisėjų",
        BibleBook.RUTH to "Rūtos",
        BibleBook.SAM1 to "Pirmoji Samuelio",
        BibleBook.SAM2 to "Antroji Samuelio",
        BibleBook.KGS1 to "Pirmoji Karalių",
        BibleBook.KGS2 to "Antroji Karalių",
        BibleBook.CHR1 to "Pirmoji Kronikų",
        BibleBook.CHR2 to "Antroji Kronikų",
        BibleBook.EZRA to "Ezdro",
        BibleBook.NEH to "Nehemijo",
        BibleBook.ESTH to "Esteros",
        BibleBook.JOB to "Jobo",
        BibleBook.PS to "Psalmynas",
        BibleBook.PROV to "Patarlių",
        BibleBook.ECCL to "Ekleziasto",
        BibleBook.SONG to "Giesmių giesmė",
        BibleBook.ISA to "Izaijo",
        BibleBook.JER to "Jeremijo",
        BibleBook.LAM to "Raudų",
        BibleBook.EZEK to "Ezekielio",
        BibleBook.DAN to "Danielio",
        BibleBook.HOS to "Ozėjo",
        BibleBook.JOEL to "Joelio",
        BibleBook.AMOS to "Amoso",
        BibleBook.OBAD to "Abdijo",
        BibleBook.JONAH to "Jonos",
        BibleBook.MIC to "Michėjo",
        BibleBook.NAH to "Nahumo",
        BibleBook.HAB to "Habakuko",
        BibleBook.ZEPH to "Sofonijo",
        BibleBook.HAG to "Agėjo",
        BibleBook.ZECH to "Zacharijo",
        BibleBook.MAL to "Malachijo",
        BibleBook.MATT to "Mato",
        BibleBook.MARK to "Morkaus",
        BibleBook.LUKE to "Luko",
        BibleBook.JOHN to "Jono",
        BibleBook.ACTS to "Apaštalų darbai",
        BibleBook.ROM to "Romiečiams",
        BibleBook.COR1 to "1 Korintiečiams",
        BibleBook.COR2 to "2 Korintiečiams",
        BibleBook.GAL to "Galatams",
        BibleBook.EPH to "Efeziečiams",
        BibleBook.PHIL to "Filipiečiams",
        BibleBook.COL to "Kolosiečiams",
        BibleBook.THESS1 to "1 Tesalonikiečiams",
        BibleBook.THESS2 to "2 Tesalonikiečiams",
        BibleBook.TIM1 to "1 Timotiejui",
        BibleBook.TIM2 to "2 Timotiejui",
        BibleBook.TITUS to "Titui",
        BibleBook.PHLM to "Filemonui",
        BibleBook.HEB to "Žydams",
        BibleBook.JAS to "Jokūbo",
        BibleBook.PET1 to "1 Petro",
        BibleBook.PET2 to "2 Petro",
        BibleBook.JOHN1 to "1 Jono",
        BibleBook.JOHN2 to "2 Jono",
        BibleBook.JOHN3 to "3 Jono",
        BibleBook.JUDE to "Judo",
        BibleBook.REV to "Apreiškimas Jonui"
    )

    private val shortNames = mapOf(
        BibleBook.GEN to "Pr",
        BibleBook.EXOD to "Iš",
        BibleBook.LEV to "Kun",
        BibleBook.NUM to "Sk",
        BibleBook.DEUT to "Įst",
        BibleBook.JOSH to "Joz",
        BibleBook.JUDG to "Ts",
        BibleBook.RUTH to "Rūt",
        BibleBook.SAM1 to "1 Sam",
        BibleBook.SAM2 to "2 Sam",
        BibleBook.KGS1 to "1 Kar",
        BibleBook.KGS2 to "2 Kar",
        BibleBook.CHR1 to "1 Kr",
        BibleBook.CHR2 to "2 Kr",
        BibleBook.EZRA to "Ezd",
        BibleBook.NEH to "Neh",
        BibleBook.ESTH to "Est",
        BibleBook.JOB to "Job",
        BibleBook.PS to "Ps",
        BibleBook.PROV to "Pat",
        BibleBook.ECCL to "Ekk",
        BibleBook.SONG to "Giesmė",
        BibleBook.ISA to "Iz",
        BibleBook.JER to "Jer",
        BibleBook.LAM to "Rd",
        BibleBook.EZEK to "Ez",
        BibleBook.DAN to "Dan",
        BibleBook.HOS to "Oz",
        BibleBook.JOEL to "Jl",
        BibleBook.AMOS to "Am",
        BibleBook.OBAD to "Abd",
        BibleBook.JONAH to "Jon",
        BibleBook.MIC to "Mch",
        BibleBook.NAH to "Nah",
        BibleBook.HAB to "Hab",
        BibleBook.ZEPH to "Sof",
        BibleBook.HAG to "Ag",
        BibleBook.ZECH to "Zch",
        BibleBook.MAL to "Mal",
        BibleBook.MATT to "Mt",
        BibleBook.MARK to "Mk",
        BibleBook.LUKE to "Lk",
        BibleBook.JOHN to "Jn",
        BibleBook.ACTS to "Apd",
        BibleBook.ROM to "Rom",
        BibleBook.COR1 to "1 Kor",
        BibleBook.COR2 to "2 Kor",
        BibleBook.GAL to "Gal",
        BibleBook.EPH to "Ef",
        BibleBook.PHIL to "Fil",
        BibleBook.COL to "Kol",
        BibleBook.THESS1 to "1 Tes",
        BibleBook.THESS2 to "2 Tes",
        BibleBook.TIM1 to "1 Tim",
        BibleBook.TIM2 to "2 Tim",
        BibleBook.TITUS to "Tit",
        BibleBook.PHLM to "Fm",
        BibleBook.HEB to "Žyd",
        BibleBook.JAS to "Jok",
        BibleBook.PET1 to "1 Pt",
        BibleBook.PET2 to "2 Pt",
        BibleBook.JOHN1 to "1 Jn",
        BibleBook.JOHN2 to "2 Jn",
        BibleBook.JOHN3 to "3 Jn",
        BibleBook.JUDE to "Jud",
        BibleBook.REV to "Apr"
    )

    private val alternateNames = mapOf(
        BibleBook.DEUT to "dt",
        BibleBook.JUDG to "jdg,jud",
        BibleBook.RUTH to "rut",
        BibleBook.SAM1 to "isamuel",
        BibleBook.SAM2 to "isamuel",
        BibleBook.KGS1 to "1kg,ikings,ikgs",
        BibleBook.KGS2 to "2kgs,iikings,iikgs",
        BibleBook.CHR1 to "ichronicles",
        BibleBook.CHR2 to "iichronicles",
        BibleBook.PS to "pss,psm",
        BibleBook.ECCL to "qohelet",
        BibleBook.SONG to "ss,songofsongs,sos,canticleofcanticles,canticle,can,coc,sng",
        BibleBook.ISA to "is",
        BibleBook.EZEK to "Ezk",
        BibleBook.JOEL to "Jol",
        BibleBook.JONAH to "jnh",
        BibleBook.NAH to "Nam",
        BibleBook.MATT to "mt",
        BibleBook.MARK to "mk,mrk",
        BibleBook.LUKE to "lk",
        BibleBook.JOHN to "jn,jhn",
        BibleBook.COR1 to "icorinthians",
        BibleBook.COR2 to "iicorinthians",
        BibleBook.PHIL to "php",
        BibleBook.COL to "co",
        BibleBook.THESS1 to "ith",
        BibleBook.THESS2 to "iith",
        BibleBook.TIM1 to "1tm,iti,itm",
        BibleBook.TIM2 to "2tm,iiti,iitm",
        BibleBook.PHLM to "phm,phlm",
        BibleBook.JAS to "jas",
        BibleBook.PET1 to "1ptr,ip",
        BibleBook.PET2 to "2pt,iip",
        BibleBook.JOHN1 to "1jn,1jh,ijo,ijn,ijh",
        BibleBook.JOHN2 to "2jn,2jh,iijo,iijn,iijh",
        BibleBook.JOHN3 to "3jn,3jh,iiijo,iiijn,iiijh",
        BibleBook.REV to "rv,apocalypse"
    )

    override fun getFullName(book: BibleBook): String? = fullNames[book]
    override fun getShortName(book: BibleBook): String? = shortNames[book]
    override fun getAlternateName(book: BibleBook): String? = alternateNames[book]
}
