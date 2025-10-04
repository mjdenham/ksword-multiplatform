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
 * Latvian localized Bible book names.
 * Generated from BibleNames_lv.properties
 */
object LatvianBookNames : BookNameLocalization {
    private val fullNames = mapOf(
        BibleBook.GEN to "1 Mozus",
        BibleBook.EXOD to "2 Mozus",
        BibleBook.LEV to "3 Mozus",
        BibleBook.NUM to "4 Mozus",
        BibleBook.DEUT to "5 Mozus",
        BibleBook.JOSH to "Jozua",
        BibleBook.JUDG to "Soģu",
        BibleBook.RUTH to "Rutes",
        BibleBook.SAM1 to "1 Samuēla",
        BibleBook.SAM2 to "2 Samuēla",
        BibleBook.KGS1 to "1 Ķēniņu",
        BibleBook.KGS2 to "2 Ķēniņu",
        BibleBook.CHR1 to "1 Laiku",
        BibleBook.CHR2 to "2 Laiku",
        BibleBook.EZRA to "Ezras",
        BibleBook.NEH to "Nehemijas",
        BibleBook.ESTH to "Esterse",
        BibleBook.JOB to "Ījaba",
        BibleBook.PS to "Psalmi",
        BibleBook.PROV to "Sakāmvārdi",
        BibleBook.ECCL to "Mācītājs",
        BibleBook.SONG to "Dziesmu Dziesma",
        BibleBook.ISA to "Jesajas",
        BibleBook.JER to "Jeremijas",
        BibleBook.LAM to "Vaimanu Dziesmas",
        BibleBook.EZEK to "Ecehiāla",
        BibleBook.DAN to "Daniēla",
        BibleBook.HOS to "Hozejas",
        BibleBook.JOEL to "Joēla",
        BibleBook.AMOS to "Amosa",
        BibleBook.OBAD to "Obadjas",
        BibleBook.JONAH to "Jonas",
        BibleBook.MIC to "Mihas",
        BibleBook.NAH to "Nahūma",
        BibleBook.HAB to "Habakuka",
        BibleBook.ZEPH to "Cefanjas",
        BibleBook.HAG to "Hagaja",
        BibleBook.ZECH to "Zaharijas",
        BibleBook.MAL to "Maleahija",
        BibleBook.MATT to "Mateja",
        BibleBook.MARK to "Marka",
        BibleBook.LUKE to "Lūkas",
        BibleBook.JOHN to "Jāņa",
        BibleBook.ACTS to "Apustuļu darbi",
        BibleBook.ROM to "Romiešiem",
        BibleBook.COR1 to "1 Korintiešiem",
        BibleBook.COR2 to "2 Korintiešiem",
        BibleBook.GAL to "Galatiešiem",
        BibleBook.EPH to "Efeziešiem",
        BibleBook.PHIL to "Filipiešiem",
        BibleBook.COL to "Kolosiešiem",
        BibleBook.THESS1 to "1 Tesaloniķiešiem",
        BibleBook.THESS2 to "2 Tesaloniķiešiem",
        BibleBook.TIM1 to "1 Timotejam",
        BibleBook.TIM2 to "2 Timotejam",
        BibleBook.TITUS to "Titam",
        BibleBook.PHLM to "Filemonam",
        BibleBook.HEB to "Ebrejiem",
        BibleBook.JAS to "Jēkaba",
        BibleBook.PET1 to "1 Pētera",
        BibleBook.PET2 to "2 Pētera",
        BibleBook.JOHN1 to "1 Jāņa",
        BibleBook.JOHN2 to "2 Jāņa",
        BibleBook.JOHN3 to "3 Jāņa",
        BibleBook.JUDE to "Jūdas",
        BibleBook.REV to "Atklāsmes"
    )

    private val shortNames = mapOf(
        BibleBook.GEN to "1Moz",
        BibleBook.EXOD to "2Moz",
        BibleBook.LEV to "3Moz",
        BibleBook.NUM to "4Moz",
        BibleBook.DEUT to "5Moz",
        BibleBook.JOSH to "Joz",
        BibleBook.JUDG to "Soģ",
        BibleBook.RUTH to "Rut",
        BibleBook.SAM1 to "1Sam",
        BibleBook.SAM2 to "2Sam",
        BibleBook.KGS1 to "1Ķēn",
        BibleBook.KGS2 to "2 Ķēn",
        BibleBook.CHR1 to "1L",
        BibleBook.CHR2 to "2L",
        BibleBook.EZRA to "Ezr",
        BibleBook.NEH to "Neh",
        BibleBook.ESTH to "Est",
        BibleBook.JOB to "Īj",
        BibleBook.PS to "Ps",
        BibleBook.PROV to "Sak",
        BibleBook.ECCL to "Māc",
        BibleBook.SONG to "Dz",
        BibleBook.ISA to "Jes",
        BibleBook.JER to "Jer",
        BibleBook.LAM to "Vdz",
        BibleBook.EZEK to "Ech",
        BibleBook.DAN to "Dan",
        BibleBook.HOS to "Hoz",
        BibleBook.JOEL to "Jl",
        BibleBook.AMOS to "Am",
        BibleBook.OBAD to "Ob",
        BibleBook.JONAH to "Jon",
        BibleBook.MIC to "Mih",
        BibleBook.NAH to "Nah",
        BibleBook.HAB to "Hab",
        BibleBook.ZEPH to "Cef",
        BibleBook.HAG to "Hag",
        BibleBook.ZECH to "Zah",
        BibleBook.MAL to "Mal",
        BibleBook.MATT to "Mat",
        BibleBook.MARK to "Mar",
        BibleBook.LUKE to "Lūk",
        BibleBook.JOHN to "Jāņ",
        BibleBook.ACTS to "Apd",
        BibleBook.ROM to "Rom",
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
        BibleBook.PHLM to "Fil",
        BibleBook.HEB to "Ebr",
        BibleBook.JAS to "Jēk",
        BibleBook.PET1 to "1Pēt",
        BibleBook.PET2 to "2Pēt",
        BibleBook.JOHN1 to "1Jāņ",
        BibleBook.JOHN2 to "2Jāņ",
        BibleBook.JOHN3 to "3Jāņ",
        BibleBook.JUDE to "Jūd",
        BibleBook.REV to "Atkl"
    )

    override fun getFullName(book: BibleBook): String? = fullNames[book]
    override fun getShortName(book: BibleBook): String? = shortNames[book]
    override fun getAlternateName(book: BibleBook): String? = null
}
