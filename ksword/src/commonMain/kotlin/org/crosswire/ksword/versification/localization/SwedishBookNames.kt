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
 * Swedish localized Bible book names.
 * Generated from BibleNames_sv.properties
 */
object SwedishBookNames : BookNameLocalization {
    private val fullNames = mapOf(
        BibleBook.GEN to "1. Mosebok",
        BibleBook.EXOD to "2. Mosebok",
        BibleBook.LEV to "3. Mosebok",
        BibleBook.NUM to "4. Mosebok",
        BibleBook.DEUT to "5. Mosebok",
        BibleBook.JOSH to "Josua",
        BibleBook.JUDG to "Domarboken",
        BibleBook.RUTH to "Rut",
        BibleBook.SAM1 to "1. Samuelsboken",
        BibleBook.SAM2 to "2. Samuelsboken",
        BibleBook.KGS1 to "1. Kungaboken",
        BibleBook.KGS2 to "2. Kungaboken",
        BibleBook.CHR1 to "1. Krönikeboken",
        BibleBook.CHR2 to "2. Krönikeboken",
        BibleBook.EZRA to "Esra",
        BibleBook.NEH to "Nehemja",
        BibleBook.ESTH to "Ester",
        BibleBook.JOB to "Job",
        BibleBook.PS to "Psaltaren",
        BibleBook.PROV to "Ordspråksboken",
        BibleBook.ECCL to "Predikaren",
        BibleBook.SONG to "Höga Visan",
        BibleBook.ISA to "Jesaja",
        BibleBook.JER to "Jeremia",
        BibleBook.LAM to "Klagovisorna",
        BibleBook.EZEK to "Hesekiel",
        BibleBook.DAN to "Daniel",
        BibleBook.HOS to "Hosea",
        BibleBook.JOEL to "Joel",
        BibleBook.AMOS to "Amos",
        BibleBook.OBAD to "Obadja",
        BibleBook.JONAH to "Jona",
        BibleBook.MIC to "Mika",
        BibleBook.NAH to "Nahum",
        BibleBook.HAB to "Habackuk",
        BibleBook.ZEPH to "Sefanja",
        BibleBook.HAG to "Haggai",
        BibleBook.ZECH to "Sakarja",
        BibleBook.MAL to "Malaki",
        BibleBook.MATT to "Matteus",
        BibleBook.MARK to "Markus",
        BibleBook.LUKE to "Lukas",
        BibleBook.JOHN to "Johannes",
        BibleBook.ACTS to "Apostlagärningarna",
        BibleBook.ROM to "Romarbrevet",
        BibleBook.COR1 to "1. Korintierbrevet",
        BibleBook.COR2 to "2. Korintierbrevet",
        BibleBook.GAL to "Galaterbrevet",
        BibleBook.EPH to "Efesierbrevet",
        BibleBook.PHIL to "Filipperbrevet",
        BibleBook.COL to "Kolosserbrevet",
        BibleBook.THESS1 to "1. Tessalonikerbrevet",
        BibleBook.THESS2 to "2. Tessalonikerbrevet",
        BibleBook.TIM1 to "1. Timoteusbrevet",
        BibleBook.TIM2 to "2. Timoteusbrevet",
        BibleBook.TITUS to "Brevet till Titus",
        BibleBook.PHLM to "Brevet till Filemon",
        BibleBook.HEB to "Hebreerbrevet",
        BibleBook.JAS to "Jakobs brev",
        BibleBook.PET1 to "1. Petrusbrevet",
        BibleBook.PET2 to "2. Petrusbrevet",
        BibleBook.JOHN1 to "1. Johannesbrevet",
        BibleBook.JOHN2 to "2. Johannesbrevet",
        BibleBook.JOHN3 to "3. Johannesbrevet",
        BibleBook.JUDE to "Judas brev",
        BibleBook.REV to "Uppenbarelseboken"
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
        BibleBook.KGS1 to "1 Ki",
        BibleBook.KGS2 to "2 Ki",
        BibleBook.CHR1 to "1 Ch",
        BibleBook.CHR2 to "2 Ch",
        BibleBook.EZRA to "Ezr",
        BibleBook.NEH to "Neh",
        BibleBook.ESTH to "Est",
        BibleBook.JOB to "Job",
        BibleBook.PS to "Psa",
        BibleBook.PROV to "Pro",
        BibleBook.ECCL to "Ecc",
        BibleBook.SONG to "Song",
        BibleBook.ISA to "Isa",
        BibleBook.JER to "Jer",
        BibleBook.LAM to "Lam",
        BibleBook.EZEK to "Eze",
        BibleBook.DAN to "Dan",
        BibleBook.HOS to "Hos",
        BibleBook.JOEL to "Joe",
        BibleBook.AMOS to "Amo",
        BibleBook.OBAD to "Obd",
        BibleBook.JONAH to "Jon",
        BibleBook.MIC to "Mic",
        BibleBook.NAH to "Nah",
        BibleBook.HAB to "Hab",
        BibleBook.ZEPH to "Zep",
        BibleBook.HAG to "Hag",
        BibleBook.ZECH to "Zec",
        BibleBook.MAL to "Mal",
        BibleBook.MATT to "Mat",
        BibleBook.MARK to "Mar",
        BibleBook.LUKE to "Luk",
        BibleBook.JOHN to "Joh",
        BibleBook.ACTS to "Act",
        BibleBook.ROM to "Rom",
        BibleBook.COR1 to "1 Cor",
        BibleBook.COR2 to "2 Cor",
        BibleBook.GAL to "Gal",
        BibleBook.EPH to "Eph",
        BibleBook.PHIL to "Phili",
        BibleBook.COL to "Col",
        BibleBook.THESS1 to "1 Th",
        BibleBook.THESS2 to "2 Th",
        BibleBook.TIM1 to "1 Tim",
        BibleBook.TIM2 to "2 Tim",
        BibleBook.TITUS to "Tit",
        BibleBook.PHLM to "Phile",
        BibleBook.HEB to "Heb",
        BibleBook.JAS to "Jam",
        BibleBook.PET1 to "1 Pe",
        BibleBook.PET2 to "2 Pe",
        BibleBook.JOHN1 to "1 Jo",
        BibleBook.JOHN2 to "2 Jo",
        BibleBook.JOHN3 to "3 Jo",
        BibleBook.JUDE to "Judas brev",
        BibleBook.REV to "Rev"
    )

    override fun getFullName(book: BibleBook): String? = fullNames[book]
    override fun getShortName(book: BibleBook): String? = shortNames[book]
    override fun getAlternateName(book: BibleBook): String? = null
}
