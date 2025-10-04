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
 * Romanian localized Bible book names.
 * Generated from BibleNames_ro.properties
 */
object RomanianBookNames : BookNameLocalization {
    private val fullNames = mapOf(
        BibleBook.GEN to "Geneza",
        BibleBook.EXOD to "Exodul",
        BibleBook.LEV to "Leviticul",
        BibleBook.NUM to "Numeri",
        BibleBook.DEUT to "Deuteronomul",
        BibleBook.JOSH to "Iosua",
        BibleBook.JUDG to "Judecatorii",
        BibleBook.RUTH to "Rut",
        BibleBook.SAM1 to "1 Samuel",
        BibleBook.SAM2 to "2 Samuel",
        BibleBook.KGS1 to "1 Împărați",
        BibleBook.KGS2 to "2 Împărați",
        BibleBook.CHR1 to "1 Cronici",
        BibleBook.CHR2 to "2 Cronici",
        BibleBook.EZRA to "Ezra",
        BibleBook.NEH to "Neemia",
        BibleBook.ESTH to "Estera",
        BibleBook.JOB to "Iov",
        BibleBook.PS to "Psalmii",
        BibleBook.PROV to "Proverbe",
        BibleBook.ECCL to "Eclesiastul",
        BibleBook.SONG to "Cîntarea Cîntărilor",
        BibleBook.ISA to "Isaia",
        BibleBook.JER to "Ieremia",
        BibleBook.LAM to "Plîngerile lui Ieremia",
        BibleBook.EZEK to "Ezechiel",
        BibleBook.DAN to "Daniel",
        BibleBook.HOS to "Osea",
        BibleBook.JOEL to "Ioel",
        BibleBook.AMOS to "Amos",
        BibleBook.OBAD to "Obadia",
        BibleBook.JONAH to "Iona",
        BibleBook.MIC to "Mica",
        BibleBook.NAH to "Naum",
        BibleBook.HAB to "Habacuc",
        BibleBook.ZEPH to "Țefania",
        BibleBook.HAG to "Hagai",
        BibleBook.ZECH to "Zaharia",
        BibleBook.MAL to "Maleahi",
        BibleBook.MATT to "Matei",
        BibleBook.MARK to "Marcu",
        BibleBook.LUKE to "Luca",
        BibleBook.JOHN to "Ioan",
        BibleBook.ACTS to "Faptele Apostolilor",
        BibleBook.ROM to "Romani",
        BibleBook.COR1 to "1 Corinteni",
        BibleBook.COR2 to "2 Corinteni",
        BibleBook.GAL to "Galateni",
        BibleBook.EPH to "Efeseni",
        BibleBook.PHIL to "Filipeni",
        BibleBook.COL to "Coloseni",
        BibleBook.THESS1 to "1 Tesaloniceni",
        BibleBook.THESS2 to "2 Tesaloniceni",
        BibleBook.TIM1 to "1 Timotei",
        BibleBook.TIM2 to "2 Timotei",
        BibleBook.TITUS to "Tit",
        BibleBook.PHLM to "Filimon",
        BibleBook.HEB to "Evrei",
        BibleBook.JAS to "Iacov",
        BibleBook.PET1 to "1 Petru",
        BibleBook.PET2 to "2 Petru",
        BibleBook.JOHN1 to "1 Ioan",
        BibleBook.JOHN2 to "2 Ioan",
        BibleBook.JOHN3 to "3 Ioan",
        BibleBook.JUDE to "Iuda",
        BibleBook.REV to "Apocalipsa lui Ioan"
    )

    private val shortNames = mapOf(
        BibleBook.GEN to "Gen",
        BibleBook.EXOD to "Exod",
        BibleBook.LEV to "Lev",
        BibleBook.NUM to "Num",
        BibleBook.DEUT to "Deut",
        BibleBook.JOSH to "Ios",
        BibleBook.JUDG to "Jud",
        BibleBook.RUTH to "Rut",
        BibleBook.SAM1 to "1 Sam",
        BibleBook.SAM2 to "2 Sam",
        BibleBook.KGS1 to "1 Împ",
        BibleBook.KGS2 to "2 Împ",
        BibleBook.CHR1 to "1 Cron",
        BibleBook.CHR2 to "2 Cron",
        BibleBook.EZRA to "Ezra",
        BibleBook.NEH to "Neem",
        BibleBook.ESTH to "Est",
        BibleBook.JOB to "Iov",
        BibleBook.PS to "Ps",
        BibleBook.PROV to "Prov",
        BibleBook.ECCL to "Ecl",
        BibleBook.SONG to "Cînt",
        BibleBook.ISA to "Isaia",
        BibleBook.JER to "Ier",
        BibleBook.LAM to "Plîng",
        BibleBook.EZEK to "Ezec",
        BibleBook.DAN to "Dan",
        BibleBook.HOS to "Osea",
        BibleBook.JOEL to "Ioel",
        BibleBook.AMOS to "Amos",
        BibleBook.OBAD to "Obad",
        BibleBook.JONAH to "Iona",
        BibleBook.MIC to "Mica",
        BibleBook.NAH to "Naum",
        BibleBook.HAB to "Hab",
        BibleBook.ZEPH to "Țef",
        BibleBook.HAG to "Hag",
        BibleBook.ZECH to "Zah",
        BibleBook.MAL to "Mal",
        BibleBook.MATT to "Mat",
        BibleBook.MARK to "Marc",
        BibleBook.LUKE to "Luca",
        BibleBook.JOHN to "Ioan",
        BibleBook.ACTS to "Fapte",
        BibleBook.ROM to "Rom",
        BibleBook.COR1 to "1 Cor",
        BibleBook.COR2 to "2 Cor",
        BibleBook.GAL to "Gal",
        BibleBook.EPH to "Efes",
        BibleBook.PHIL to "Filip",
        BibleBook.COL to "Col",
        BibleBook.THESS1 to "1 Tes",
        BibleBook.THESS2 to "2 Tes",
        BibleBook.TIM1 to "1 Tim",
        BibleBook.TIM2 to "2 Tim",
        BibleBook.TITUS to "Tit",
        BibleBook.PHLM to "Filim",
        BibleBook.HEB to "Evrei",
        BibleBook.JAS to "Iac",
        BibleBook.PET1 to "1 Pet",
        BibleBook.PET2 to "2 Pet",
        BibleBook.JOHN1 to "1 Ioan",
        BibleBook.JOHN2 to "2 Ioan",
        BibleBook.JOHN3 to "3 Ioan",
        BibleBook.JUDE to "Iuda",
        BibleBook.REV to "Apoc"
    )

    override fun getFullName(book: BibleBook): String? = fullNames[book]
    override fun getShortName(book: BibleBook): String? = shortNames[book]
    override fun getAlternateName(book: BibleBook): String? = null
}
