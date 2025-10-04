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
 * Indonesian localized Bible book names.
 * Generated from BibleNames_id.properties
 */
object IndonesianBookNames : BookNameLocalization {
    private val fullNames = mapOf(
        BibleBook.GEN to "Kejadian",
        BibleBook.EXOD to "Keluaran",
        BibleBook.LEV to "Imamat",
        BibleBook.NUM to "Bilangan",
        BibleBook.DEUT to "Ulangan",
        BibleBook.JOSH to "Yosua",
        BibleBook.JUDG to "Hakim Hakim",
        BibleBook.RUTH to "Rut",
        BibleBook.SAM1 to "1 Samuel",
        BibleBook.SAM2 to "2 Samuel",
        BibleBook.KGS1 to "1 Raja Raja",
        BibleBook.KGS2 to "2 Raja Raja",
        BibleBook.CHR1 to "1 Tawarikh",
        BibleBook.CHR2 to "2 Tawarikh",
        BibleBook.EZRA to "Ezra",
        BibleBook.NEH to "Nehemia",
        BibleBook.ESTH to "Ester",
        BibleBook.JOB to "Ayub",
        BibleBook.PS to "Mazmur",
        BibleBook.PROV to "Amsal",
        BibleBook.ECCL to "Pengkhotbah",
        BibleBook.SONG to "Kidung Agung",
        BibleBook.ISA to "Yesaya",
        BibleBook.JER to "Yeremia",
        BibleBook.LAM to "Ratapan",
        BibleBook.EZEK to "Yehezkiel",
        BibleBook.DAN to "Daniel",
        BibleBook.HOS to "Hosea",
        BibleBook.JOEL to "Yoel",
        BibleBook.AMOS to "Amos",
        BibleBook.OBAD to "Obaja",
        BibleBook.JONAH to "Yunus",
        BibleBook.MIC to "Mikha",
        BibleBook.NAH to "Nahum",
        BibleBook.HAB to "Habakuk",
        BibleBook.ZEPH to "Zefanya",
        BibleBook.HAG to "Hagai",
        BibleBook.ZECH to "Zakharia",
        BibleBook.MAL to "Maleakhi",
        BibleBook.MATT to "Matius",
        BibleBook.MARK to "Markus",
        BibleBook.LUKE to "Lukas",
        BibleBook.JOHN to "Yohanes",
        BibleBook.ACTS to "Kisah Para Rasul",
        BibleBook.ROM to "Roma",
        BibleBook.COR1 to "1 Korintus",
        BibleBook.COR2 to "2 Korintus",
        BibleBook.GAL to "Galatia",
        BibleBook.EPH to "Efesus",
        BibleBook.PHIL to "Filipi",
        BibleBook.COL to "Kolose",
        BibleBook.THESS1 to "1 Tesalonika",
        BibleBook.THESS2 to "2 Tesalonika",
        BibleBook.TIM1 to "1 Timotius",
        BibleBook.TIM2 to "2 Timotius",
        BibleBook.TITUS to "Titus",
        BibleBook.PHLM to "Filemon",
        BibleBook.HEB to "Ibrani",
        BibleBook.JAS to "Yakobus",
        BibleBook.PET1 to "1 Petrus",
        BibleBook.PET2 to "2 Petrus",
        BibleBook.JOHN1 to "1 Yohanes",
        BibleBook.JOHN2 to "2 Yohanes",
        BibleBook.JOHN3 to "3 Yohanes",
        BibleBook.JUDE to "Yudas",
        BibleBook.REV to "Wahyu"
    )

    private val shortNames = mapOf(
        BibleBook.GEN to "Kej",
        BibleBook.EXOD to "Kel",
        BibleBook.LEV to "Im",
        BibleBook.NUM to "Bil",
        BibleBook.DEUT to "Ul",
        BibleBook.JOSH to "Yos",
        BibleBook.JUDG to "Hak",
        BibleBook.RUTH to "Rut",
        BibleBook.SAM1 to "1Sam",
        BibleBook.SAM2 to "2Sam",
        BibleBook.KGS1 to "1Raj",
        BibleBook.KGS2 to "2Raj",
        BibleBook.CHR1 to "1Taw",
        BibleBook.CHR2 to "2Taw",
        BibleBook.EZRA to "Ezr",
        BibleBook.NEH to "Neh",
        BibleBook.ESTH to "Est",
        BibleBook.JOB to "Ayb",
        BibleBook.PS to "Mzm",
        BibleBook.PROV to "Ams",
        BibleBook.ECCL to "Pkh",
        BibleBook.SONG to "Kid",
        BibleBook.ISA to "Yes",
        BibleBook.JER to "Yer",
        BibleBook.LAM to "Rat",
        BibleBook.EZEK to "Yeh",
        BibleBook.DAN to "Dan",
        BibleBook.HOS to "Hos",
        BibleBook.JOEL to "Yl",
        BibleBook.AMOS to "Am",
        BibleBook.OBAD to "Obd",
        BibleBook.JONAH to "Yun",
        BibleBook.MIC to "Mi",
        BibleBook.NAH to "Nah",
        BibleBook.HAB to "Hab",
        BibleBook.ZEPH to "Zef",
        BibleBook.HAG to "Hag",
        BibleBook.ZECH to "Za",
        BibleBook.MAL to "Mal",
        BibleBook.MATT to "Mat",
        BibleBook.MARK to "Mrk",
        BibleBook.LUKE to "Luk",
        BibleBook.JOHN to "Yoh",
        BibleBook.ACTS to "Kis",
        BibleBook.ROM to "Rm",
        BibleBook.COR1 to "1Kor",
        BibleBook.COR2 to "2Kor",
        BibleBook.GAL to "Gal",
        BibleBook.EPH to "Ef",
        BibleBook.PHIL to "Flp",
        BibleBook.COL to "Kol",
        BibleBook.THESS1 to "1Tes",
        BibleBook.THESS2 to "2Tes",
        BibleBook.TIM1 to "1Tim",
        BibleBook.TIM2 to "2Tim",
        BibleBook.TITUS to "Tit",
        BibleBook.PHLM to "Flm",
        BibleBook.HEB to "Ibr",
        BibleBook.JAS to "Yak",
        BibleBook.PET1 to "1Ptr",
        BibleBook.PET2 to "2Pet",
        BibleBook.JOHN1 to "1Yoh",
        BibleBook.JOHN2 to "2Yoh",
        BibleBook.JOHN3 to "3Yoh",
        BibleBook.JUDE to "Yud",
        BibleBook.REV to "Why"
    )

    override fun getFullName(book: BibleBook): String? = fullNames[book]
    override fun getShortName(book: BibleBook): String? = shortNames[book]
    override fun getAlternateName(book: BibleBook): String? = null
}
