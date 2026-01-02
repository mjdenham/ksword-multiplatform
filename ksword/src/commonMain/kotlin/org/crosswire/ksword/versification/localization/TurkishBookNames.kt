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
 * Turkish localized Bible book names.
 * Generated from BibleNames_tr.properties
 */
object TurkishBookNames : BookNameLocalization {
    private val fullNames = mapOf(
        BibleBook.GEN to "Yaratılış",
        BibleBook.EXOD to "Mısır’dan Çıkış",
        BibleBook.LEV to "Levililer",
        BibleBook.NUM to "Çölde Sayım",
        BibleBook.DEUT to "Yasa’nın Tekrarı",
        BibleBook.JOSH to "Yeşu",
        BibleBook.JUDG to "Hâkimler",
        BibleBook.RUTH to "Rut",
        BibleBook.SAM1 to "1. Samuel",
        BibleBook.SAM2 to "2. Samuel",
        BibleBook.KGS1 to "1. Krallar",
        BibleBook.KGS2 to "2. Krallar",
        BibleBook.CHR1 to "1. Tarihler",
        BibleBook.CHR2 to "2. Tarihler",
        BibleBook.EZRA to "Ezra",
        BibleBook.NEH to "Nehemya",
        BibleBook.ESTH to "Ester",
        BibleBook.JOB to "Eyüp",
        BibleBook.PS to "Mezmurlar (Zebur)",
        BibleBook.PROV to "Süleyman'ın Özdeyişleri",
        BibleBook.ECCL to "Vaiz",
        BibleBook.SONG to "Ezgiler Ezgisi",
        BibleBook.ISA to "Yeşaya",
        BibleBook.JER to "Yeremya",
        BibleBook.LAM to "Ağıtlar",
        BibleBook.EZEK to "Hezekiel",
        BibleBook.DAN to "Daniel",
        BibleBook.HOS to "Hoşea",
        BibleBook.JOEL to "Yoel",
        BibleBook.AMOS to "Amos",
        BibleBook.OBAD to "Ovadya",
        BibleBook.JONAH to "Yunus",
        BibleBook.MIC to "Mika",
        BibleBook.NAH to "Nahum",
        BibleBook.HAB to "Habakkuk",
        BibleBook.ZEPH to "Sefanya",
        BibleBook.HAG to "Hagay",
        BibleBook.ZECH to "Zekeriya",
        BibleBook.MAL to "Malaki",
        BibleBook.MATT to "Matta",
        BibleBook.MARK to "Markos",
        BibleBook.LUKE to "Luka",
        BibleBook.JOHN to "Yuhanna",
        BibleBook.ACTS to "Elçilerin İşleri",
        BibleBook.ROM to "Romalılar",
        BibleBook.COR1 to "1. Korintliler",
        BibleBook.COR2 to "2. Korintliler",
        BibleBook.GAL to "Galatyalılar",
        BibleBook.EPH to "Efesliler",
        BibleBook.PHIL to "Filipililer",
        BibleBook.COL to "Koloseliler",
        BibleBook.THESS1 to "1. Selanikliler",
        BibleBook.THESS2 to "2. Selanikliler",
        BibleBook.TIM1 to "1. Timoteos",
        BibleBook.TIM2 to "2. Timoteos",
        BibleBook.TITUS to "Titus",
        BibleBook.PHLM to "Filimon",
        BibleBook.HEB to "İbraniler",
        BibleBook.JAS to "Yakup",
        BibleBook.PET1 to "1. Petrus",
        BibleBook.PET2 to "2. Petrus",
        BibleBook.JOHN1 to "1. Yuhanna",
        BibleBook.JOHN2 to "2. Yuhanna",
        BibleBook.JOHN3 to "3. Yuhanna",
        BibleBook.JUDE to "Yahuda",
        BibleBook.REV to "Vahiy"
    )

    private val shortNames = mapOf(
        BibleBook.GEN to "Yar",
        BibleBook.EXOD to "Çık",
        BibleBook.LEV to "Lev",
        BibleBook.NUM to "Say",
        BibleBook.DEUT to "Yas",
        BibleBook.JOSH to "Yşu",
        BibleBook.JUDG to "Hâk",
        BibleBook.RUTH to "Rut",
        BibleBook.SAM1 to "1Sa",
        BibleBook.SAM2 to "2Sa",
        BibleBook.KGS1 to "1Kr",
        BibleBook.KGS2 to "2Kr",
        BibleBook.CHR1 to "1Ta",
        BibleBook.CHR2 to "2Ta",
        BibleBook.EZRA to "Ezr",
        BibleBook.NEH to "Neh",
        BibleBook.ESTH to "Est",
        BibleBook.JOB to "Eyü",
        BibleBook.PS to "Mez",
        BibleBook.PROV to "Özd",
        BibleBook.ECCL to "Vai",
        BibleBook.SONG to "Ezg",
        BibleBook.ISA to "Yşa",
        BibleBook.JER to "Yer",
        BibleBook.LAM to "Ağı",
        BibleBook.EZEK to "Hez",
        BibleBook.DAN to "Dan",
        BibleBook.HOS to "Hoş",
        BibleBook.JOEL to "Yoe",
        BibleBook.AMOS to "Amo",
        BibleBook.OBAD to "Ova",
        BibleBook.JONAH to "Yun",
        BibleBook.MIC to "Mik",
        BibleBook.NAH to "Nah",
        BibleBook.HAB to "Hab",
        BibleBook.ZEPH to "Sef",
        BibleBook.HAG to "Hag",
        BibleBook.ZECH to "Zek",
        BibleBook.MAL to "Mal",
        BibleBook.MATT to "Mat",
        BibleBook.MARK to "Mar",
        BibleBook.LUKE to "Luk",
        BibleBook.JOHN to "Yu",
        BibleBook.ACTS to "Elç",
        BibleBook.ROM to "Rom",
        BibleBook.COR1 to "1Ko",
        BibleBook.COR2 to "2Ko",
        BibleBook.GAL to "Gal",
        BibleBook.EPH to "Ef",
        BibleBook.PHIL to "Flp",
        BibleBook.COL to "Kol",
        BibleBook.THESS1 to "1Se",
        BibleBook.THESS2 to "2Se",
        BibleBook.TIM1 to "1Ti",
        BibleBook.TIM2 to "2Ti",
        BibleBook.TITUS to "Tit",
        BibleBook.PHLM to "Flm",
        BibleBook.HEB to "İbr",
        BibleBook.JAS to "Yak",
        BibleBook.PET1 to "1Pe",
        BibleBook.PET2 to "2Pe",
        BibleBook.JOHN1 to "1Yu",
        BibleBook.JOHN2 to "2Yu",
        BibleBook.JOHN3 to "3Yu",
        BibleBook.JUDE to "Yah",
        BibleBook.REV to "Va"
    )

    private val alternateNames = mapOf(
        BibleBook.DEUT to "dt",
        BibleBook.JUDG to "jdg,jud",
        BibleBook.RUTH to "rth",
        BibleBook.SAM1 to "isamuel",
        BibleBook.SAM2 to "iisamuel",
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
