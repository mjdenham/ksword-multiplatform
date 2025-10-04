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
 * Norwegian localized Bible book names.
 * Generated from BibleNames_nb.properties
 */
object NorwegianBookNames : BookNameLocalization {
    private val fullNames = mapOf(
        BibleBook.GEN to "1 Mosebok",
        BibleBook.EXOD to "2 Mosebok",
        BibleBook.LEV to "3 Mosebok",
        BibleBook.NUM to "4 Mosebok",
        BibleBook.DEUT to "5 Mosebok",
        BibleBook.JOSH to "Josva",
        BibleBook.JUDG to "Dommerne",
        BibleBook.RUTH to "Rut",
        BibleBook.SAM1 to "1 Samuelsbok",
        BibleBook.SAM2 to "2 Samuelsbok",
        BibleBook.KGS1 to "1 Kongebok",
        BibleBook.KGS2 to "2 Kongebok",
        BibleBook.CHR1 to "1 Krønikebok",
        BibleBook.CHR2 to "2 Krønikebok",
        BibleBook.EZRA to "Esra",
        BibleBook.NEH to "Nehemja",
        BibleBook.ESTH to "Ester",
        BibleBook.JOB to "Job",
        BibleBook.PS to "Salmene",
        BibleBook.PROV to "Ordspråkene",
        BibleBook.ECCL to "Forkynneren",
        BibleBook.SONG to "Høysangen",
        BibleBook.ISA to "Jesaja",
        BibleBook.JER to "Jeremia",
        BibleBook.LAM to "Klagesangene",
        BibleBook.EZEK to "Esekiel",
        BibleBook.DAN to "Daniel",
        BibleBook.HOS to "Hosea",
        BibleBook.JOEL to "Joel",
        BibleBook.AMOS to "Amos",
        BibleBook.OBAD to "Obadja",
        BibleBook.JONAH to "Jona",
        BibleBook.MIC to "Mika",
        BibleBook.NAH to "Nahum",
        BibleBook.HAB to "Habakkuk",
        BibleBook.ZEPH to "Sefanja",
        BibleBook.HAG to "Haggai",
        BibleBook.ZECH to "Sakarja",
        BibleBook.MAL to "Malaki",
        BibleBook.MATT to "Matteus",
        BibleBook.MARK to "Markus",
        BibleBook.LUKE to "Lukas",
        BibleBook.JOHN to "Johannes",
        BibleBook.ACTS to "Apostlenes gjerninger",
        BibleBook.ROM to "Romerne",
        BibleBook.COR1 to "1 Korinterne",
        BibleBook.COR2 to "2 Korinter",
        BibleBook.GAL to "Galaterne",
        BibleBook.EPH to "Efeserne",
        BibleBook.PHIL to "Filipperne",
        BibleBook.COL to "Kolosserne",
        BibleBook.THESS1 to "1 Tessaloniker",
        BibleBook.THESS2 to "2 Tessaloniker",
        BibleBook.TIM1 to "1 Timoteus",
        BibleBook.TIM2 to "2 Timoteus",
        BibleBook.TITUS to "Titus",
        BibleBook.PHLM to "Filemon",
        BibleBook.HEB to "Hebreerne",
        BibleBook.JAS to "Jakob",
        BibleBook.PET1 to "1 Peter",
        BibleBook.PET2 to "2 Peter",
        BibleBook.JOHN1 to "1 Johannes",
        BibleBook.JOHN2 to "2 Johannes",
        BibleBook.JOHN3 to "3 Johannes",
        BibleBook.JUDE to "Judas",
        BibleBook.REV to "Johannes åpenbaring"
    )

    private val shortNames = mapOf(
        BibleBook.GEN to "1 Mos",
        BibleBook.EXOD to "2 Mos",
        BibleBook.LEV to "3 Mos",
        BibleBook.NUM to "4 Mos",
        BibleBook.DEUT to "5 Mos",
        BibleBook.JOSH to "Jos",
        BibleBook.JUDG to "Dom",
        BibleBook.RUTH to "Rut",
        BibleBook.SAM1 to "1 Sam",
        BibleBook.SAM2 to "2 Sam",
        BibleBook.KGS1 to "1 Kong",
        BibleBook.KGS2 to "2 Kong",
        BibleBook.CHR1 to "1 Krøn",
        BibleBook.CHR2 to "2 Krøn",
        BibleBook.EZRA to "Esra",
        BibleBook.NEH to "Neh",
        BibleBook.ESTH to "Est",
        BibleBook.JOB to "Job",
        BibleBook.PS to "Sal",
        BibleBook.PROV to "Ord",
        BibleBook.ECCL to "Fork",
        BibleBook.SONG to "Høys",
        BibleBook.ISA to "Jes",
        BibleBook.JER to "Jer",
        BibleBook.LAM to "Klag",
        BibleBook.EZEK to "Esek",
        BibleBook.DAN to "Dan",
        BibleBook.HOS to "Hos",
        BibleBook.JOEL to "Joel",
        BibleBook.AMOS to "Amos",
        BibleBook.OBAD to "Ob",
        BibleBook.JONAH to "Jon",
        BibleBook.MIC to "Mika",
        BibleBook.NAH to "Nah",
        BibleBook.HAB to "Hab",
        BibleBook.ZEPH to "Sef",
        BibleBook.HAG to "Hag",
        BibleBook.ZECH to "Sak",
        BibleBook.MAL to "Mal",
        BibleBook.MATT to "Matt",
        BibleBook.MARK to "Mark",
        BibleBook.LUKE to "Luk",
        BibleBook.JOHN to "Joh",
        BibleBook.ACTS to "Apg",
        BibleBook.ROM to "Rom",
        BibleBook.COR1 to "1 Kor",
        BibleBook.COR2 to "2 Kor",
        BibleBook.GAL to "Gal",
        BibleBook.EPH to "Ef",
        BibleBook.PHIL to "Fil",
        BibleBook.COL to "Kol",
        BibleBook.THESS1 to "1 Tess",
        BibleBook.THESS2 to "2 Tess",
        BibleBook.TIM1 to "1 Tim",
        BibleBook.TIM2 to "2 Tim",
        BibleBook.TITUS to "Tit",
        BibleBook.PHLM to "Filem",
        BibleBook.HEB to "Hebr",
        BibleBook.JAS to "Jak",
        BibleBook.PET1 to "1 Pet",
        BibleBook.PET2 to "2 Pet",
        BibleBook.JOHN1 to "1 Joh",
        BibleBook.JOHN2 to "2 Joh",
        BibleBook.JOHN3 to "3 Joh",
        BibleBook.JUDE to "Jud",
        BibleBook.REV to "Åp"
    )

    override fun getFullName(book: BibleBook): String? = fullNames[book]
    override fun getShortName(book: BibleBook): String? = shortNames[book]
    override fun getAlternateName(book: BibleBook): String? = null
}
