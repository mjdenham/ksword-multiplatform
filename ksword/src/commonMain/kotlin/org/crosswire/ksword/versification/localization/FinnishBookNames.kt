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
 * Finnish localized Bible book names.
 * Generated from BibleNames_fi.properties
 */
object FinnishBookNames : BookNameLocalization {
    private val fullNames = mapOf(
        BibleBook.GEN to "1. Mooseksen kirja",
        BibleBook.EXOD to "2. Mooseksen kirja",
        BibleBook.LEV to "3. Mooseksen kirja",
        BibleBook.NUM to "4. Mooseksen kirja",
        BibleBook.DEUT to "5. Mooseksen kirja",
        BibleBook.JOSH to "Joosua",
        BibleBook.JUDG to "Tuomarien kirja",
        BibleBook.RUTH to "Ruut",
        BibleBook.SAM1 to "1. Samuelin kirja",
        BibleBook.SAM2 to "2. Samuelin kirja",
        BibleBook.KGS1 to "1. Kuninkaiden kirja",
        BibleBook.KGS2 to "2. Kuninkaiden kirja",
        BibleBook.CHR1 to "1. Aikakirja",
        BibleBook.CHR2 to "2. Aikakirja",
        BibleBook.EZRA to "Esra",
        BibleBook.NEH to "Nehemia",
        BibleBook.ESTH to "Ester",
        BibleBook.JOB to "Job",
        BibleBook.PS to "Psalmit",
        BibleBook.PROV to "Sananlaskut",
        BibleBook.ECCL to "Saarnaaja",
        BibleBook.SONG to "Laulujen laulu",
        BibleBook.ISA to "Jesaja",
        BibleBook.JER to "Jeremia",
        BibleBook.LAM to "Valitusvirret",
        BibleBook.EZEK to "Hesekiel",
        BibleBook.DAN to "Daniel",
        BibleBook.HOS to "Hoosea",
        BibleBook.JOEL to "Joel",
        BibleBook.AMOS to "Aamos",
        BibleBook.OBAD to "Obadja",
        BibleBook.JONAH to "Joona",
        BibleBook.MIC to "Miika",
        BibleBook.NAH to "Naahum",
        BibleBook.HAB to "Habakuk",
        BibleBook.ZEPH to "Sefanja",
        BibleBook.HAG to "Haggai",
        BibleBook.ZECH to "Sakarja",
        BibleBook.MAL to "Malakia",
        BibleBook.MATT to "Matteus",
        BibleBook.MARK to "Markus",
        BibleBook.LUKE to "Luukas",
        BibleBook.JOHN to "Johannes",
        BibleBook.ACTS to "Apostolien teot",
        BibleBook.ROM to "Roomalaiskirje",
        BibleBook.COR1 to "1. Korinttolaiskirje",
        BibleBook.COR2 to "2. Korinttolaiskirje",
        BibleBook.GAL to "Galatalaiskirje",
        BibleBook.EPH to "Efesolaiskirje",
        BibleBook.PHIL to "Filippiläiskirje",
        BibleBook.COL to "Kolossalaiskirje",
        BibleBook.THESS1 to "1. Tessalonikalaiskirje",
        BibleBook.THESS2 to "2. Tessalonikalaiskirje",
        BibleBook.TIM1 to "1. kirje Timoteukselle",
        BibleBook.TIM2 to "2. kirje Timoteukselle",
        BibleBook.TITUS to "Kirje Titukselle",
        BibleBook.PHLM to "Kirje Filemonille",
        BibleBook.HEB to "Heprealaiskirje",
        BibleBook.JAS to "Jaakobin kirje",
        BibleBook.PET1 to "1. Pietarin kirje",
        BibleBook.PET2 to "2. Pietarin kirje",
        BibleBook.JOHN1 to "1. Johanneksen kirje",
        BibleBook.JOHN2 to "2. Johanneksen kirje",
        BibleBook.JOHN3 to "3. Johanneksen kirje",
        BibleBook.JUDE to "Juudaksen kirje",
        BibleBook.REV to "Johanneksen ilmestys"
    )

    private val shortNames = mapOf(
        BibleBook.GEN to "1Moos",
        BibleBook.EXOD to "2Moos",
        BibleBook.LEV to "3Moos",
        BibleBook.NUM to "4Moos",
        BibleBook.DEUT to "5Moos",
        BibleBook.JOSH to "Joos",
        BibleBook.JUDG to "Tuom",
        BibleBook.RUTH to "Ruut",
        BibleBook.SAM1 to "1Sam",
        BibleBook.SAM2 to "2Sam",
        BibleBook.KGS1 to "1Kun",
        BibleBook.KGS2 to "2Kun",
        BibleBook.CHR1 to "1Aik",
        BibleBook.CHR2 to "2Aik",
        BibleBook.EZRA to "Esra",
        BibleBook.NEH to "Neh",
        BibleBook.ESTH to "Est",
        BibleBook.JOB to "Job",
        BibleBook.PS to "Ps",
        BibleBook.PROV to "Sananl",
        BibleBook.ECCL to "Saarn",
        BibleBook.SONG to "Laul",
        BibleBook.ISA to "Jes",
        BibleBook.JER to "Jer",
        BibleBook.LAM to "Valit",
        BibleBook.EZEK to "Hes",
        BibleBook.DAN to "Dan",
        BibleBook.HOS to "Hoos",
        BibleBook.JOEL to "Joel",
        BibleBook.AMOS to "Aam",
        BibleBook.OBAD to "Obad",
        BibleBook.JONAH to "Joona",
        BibleBook.MIC to "Miika",
        BibleBook.NAH to "Naah",
        BibleBook.HAB to "Hab",
        BibleBook.ZEPH to "Sef",
        BibleBook.HAG to "Hagg",
        BibleBook.ZECH to "Sak",
        BibleBook.MAL to "Mal",
        BibleBook.MATT to "Matt",
        BibleBook.MARK to "Mark",
        BibleBook.LUKE to "Luuk",
        BibleBook.JOHN to "Joh",
        BibleBook.ACTS to "Apt",
        BibleBook.ROM to "Room",
        BibleBook.COR1 to "1Kor",
        BibleBook.COR2 to "2Kor",
        BibleBook.GAL to "Gal",
        BibleBook.EPH to "Ef",
        BibleBook.PHIL to "Fil",
        BibleBook.COL to "Kol",
        BibleBook.THESS1 to "1Tess",
        BibleBook.THESS2 to "2Tess",
        BibleBook.TIM1 to "1Tim",
        BibleBook.TIM2 to "2Tim",
        BibleBook.TITUS to "Tiit",
        BibleBook.PHLM to "Filem",
        BibleBook.HEB to "Hebr",
        BibleBook.JAS to "Jaak",
        BibleBook.PET1 to "1Piet",
        BibleBook.PET2 to "2Piet",
        BibleBook.JOHN1 to "1Joh",
        BibleBook.JOHN2 to "2Joh",
        BibleBook.JOHN3 to "3Joh",
        BibleBook.JUDE to "Juud",
        BibleBook.REV to "Ilm"
    )

    private val alternateNames = mapOf(
        BibleBook.GEN to "1 moos,1. moos,1 mos,1.mos,mos",
        BibleBook.EXOD to "2 moos,2. moos,2 mos,2.mos",
        BibleBook.LEV to "3 moos,3. moos,3 mos,3.mos",
        BibleBook.NUM to "4 moos,4. moos,4 mos,4.mos",
        BibleBook.DEUT to "5 moos,5. moos,5 mos,5.mos",
        BibleBook.SAM1 to "1. sam,1.sam",
        BibleBook.KGS1 to "1. Kun",
        BibleBook.PROV to "snl,san",
        BibleBook.SONG to "korkv,kork,kv",
        BibleBook.EZEK to "Hesek",
        BibleBook.JOEL to "Jol",
        BibleBook.MIC to "Mik",
        BibleBook.NAH to "Nam",
        BibleBook.COR1 to "1. Kor,1.kor",
        BibleBook.COR2 to "2. Kor,2.kor",
        BibleBook.EPH to "Efes",
        BibleBook.THESS1 to "1. Tess,1.tess",
        BibleBook.THESS2 to "2. Tess,2.tess",
        BibleBook.TIM1 to "1. Tim,1.tim",
        BibleBook.TIM2 to "2. Tim,2.Tim",
        BibleBook.JAS to "Jak",
        BibleBook.PET1 to "1. Piet,1.piet",
        BibleBook.PET2 to "2. Piet,2.piet",
        BibleBook.JOHN1 to "1. Joh,1.Joh",
        BibleBook.JOHN2 to "2. Joh,2.Joh",
        BibleBook.JOHN3 to "3. Joh,3.Joh",
        BibleBook.JUDE to "Jud",
        BibleBook.ESD1 to "Kr Esr"
    )

    override fun getFullName(book: BibleBook): String? = fullNames[book]
    override fun getShortName(book: BibleBook): String? = shortNames[book]
    override fun getAlternateName(book: BibleBook): String? = alternateNames[book]
}
