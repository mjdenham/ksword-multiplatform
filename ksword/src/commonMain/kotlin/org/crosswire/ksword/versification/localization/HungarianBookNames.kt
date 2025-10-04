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
 * Hungarian localized Bible book names.
 * Generated from BibleNames_hu.properties
 */
object HungarianBookNames : BookNameLocalization {
    private val fullNames = mapOf(
        BibleBook.GEN to "Mózes I. könyve",
        BibleBook.EXOD to "Mózes II. könyve",
        BibleBook.LEV to "Mózes III. könyve",
        BibleBook.NUM to "Mózes IV. könyve",
        BibleBook.DEUT to "Mózes V. könyve",
        BibleBook.JOSH to "Józsué könyve",
        BibleBook.JUDG to "Bírák könyve",
        BibleBook.RUTH to "Ruth könyve",
        BibleBook.SAM1 to "Sámuel I. könyve",
        BibleBook.SAM2 to "Sámuel II. könyve",
        BibleBook.KGS1 to "Királyok I. könyve",
        BibleBook.KGS2 to "Királyok II. könyve",
        BibleBook.CHR1 to "Krónikák I. könyve",
        BibleBook.CHR2 to "Krónikák II. könyve",
        BibleBook.EZRA to "Ezsdrás könyve",
        BibleBook.NEH to "Nehémiás könyve",
        BibleBook.ESTH to "Eszter könyve",
        BibleBook.JOB to "Jób könyve",
        BibleBook.PS to "Zsoltárok könyve",
        BibleBook.PROV to "Példabeszédek",
        BibleBook.ECCL to "Prédikátor könyve",
        BibleBook.SONG to "Énekek éneke",
        BibleBook.ISA to "Ézsaiás próféta könyve",
        BibleBook.JER to "Jeremiás próféta könyve",
        BibleBook.LAM to "Jeremiás siralmai",
        BibleBook.EZEK to "Ezékiel próféta könyve",
        BibleBook.DAN to "Dániel próféta könyve",
        BibleBook.HOS to "Hóseás próféta könyve",
        BibleBook.JOEL to "Jóel próféta könyve",
        BibleBook.AMOS to "Ámós próféta könyve",
        BibleBook.OBAD to "Abdiás próféta könyve",
        BibleBook.JONAH to "Jónás próféta könyve",
        BibleBook.MIC to "Mikeás próféta könyve",
        BibleBook.NAH to "Náhum próféta könyve",
        BibleBook.HAB to "Habakuk próféta könyve",
        BibleBook.ZEPH to "Zofóniás próféta könyve",
        BibleBook.HAG to "Haggeus próféta könyve",
        BibleBook.ZECH to "Zakariás próféta könyve",
        BibleBook.MAL to "Malakiás próféta könyve",
        BibleBook.MATT to "Máté evangéliuma",
        BibleBook.MARK to "Márk evangéliuma",
        BibleBook.LUKE to "Lukács evangéliuma",
        BibleBook.JOHN to "János evangéliuma",
        BibleBook.ACTS to "Apostolok Cselekedetei",
        BibleBook.ROM to "Pál levele a rómaiakhoz",
        BibleBook.COR1 to "Pál első levele a korinthusiakhoz",
        BibleBook.COR2 to "Pál második levele a korinthusiakhoz",
        BibleBook.GAL to "Pál levele a galatákhoz",
        BibleBook.EPH to "Pál levele az efezusiakkoz",
        BibleBook.PHIL to "Pál levele a filippiekhez",
        BibleBook.COL to "Pál levele a kolosséiakhoz",
        BibleBook.THESS1 to "Pál első levele a thesszalonikaiakhoz",
        BibleBook.THESS2 to "Pál második levele a thesszalonikaiakhoz",
        BibleBook.TIM1 to "Pál első levele Timóteushoz",
        BibleBook.TIM2 to "Pál második levele Timóteushoz",
        BibleBook.TITUS to "Pál levele Tituszhoz",
        BibleBook.PHLM to "Pál levele Filemonhoz",
        BibleBook.HEB to "A zsidókhoz írt levél",
        BibleBook.JAS to "Jakab levele",
        BibleBook.PET1 to "Péter első levele",
        BibleBook.PET2 to "Péter második levele",
        BibleBook.JOHN1 to "János első levele",
        BibleBook.JOHN2 to "János második levele",
        BibleBook.JOHN3 to "János harmadik levele",
        BibleBook.JUDE to "Júdás levele",
        BibleBook.REV to "A jelenések könyve"
    )

    private val shortNames = mapOf(
        BibleBook.GEN to "1Móz",
        BibleBook.EXOD to "2Móz",
        BibleBook.LEV to "3Móz",
        BibleBook.NUM to "4Móz",
        BibleBook.DEUT to "5Móz",
        BibleBook.JOSH to "Józs",
        BibleBook.JUDG to "Bír",
        BibleBook.RUTH to "Ruth",
        BibleBook.SAM1 to "1Sám",
        BibleBook.SAM2 to "2Sám",
        BibleBook.KGS1 to "1Kir",
        BibleBook.KGS2 to "2Kir",
        BibleBook.CHR1 to "1Krón",
        BibleBook.CHR2 to "2Krón",
        BibleBook.EZRA to "Ezsd",
        BibleBook.NEH to "Neh",
        BibleBook.ESTH to "Eszt",
        BibleBook.JOB to "Jób",
        BibleBook.PS to "Zsolt",
        BibleBook.PROV to "Péld",
        BibleBook.ECCL to "Préd",
        BibleBook.SONG to "Én",
        BibleBook.ISA to "Ézs",
        BibleBook.JER to "Jer",
        BibleBook.LAM to "Jsir",
        BibleBook.EZEK to "Ez",
        BibleBook.DAN to "Dán",
        BibleBook.HOS to "Hós",
        BibleBook.JOEL to "Jóel",
        BibleBook.AMOS to "Ám",
        BibleBook.OBAD to "Abd",
        BibleBook.JONAH to "Jón",
        BibleBook.MIC to "Mik",
        BibleBook.NAH to "Náh",
        BibleBook.HAB to "Hab",
        BibleBook.ZEPH to "Zof",
        BibleBook.HAG to "Hag",
        BibleBook.ZECH to "Zak",
        BibleBook.MAL to "Mal",
        BibleBook.MATT to "Mt",
        BibleBook.MARK to "Mk",
        BibleBook.LUKE to "Lk",
        BibleBook.JOHN to "Jn",
        BibleBook.ACTS to "ApCs",
        BibleBook.ROM to "Róm",
        BibleBook.COR1 to "1Kor",
        BibleBook.COR2 to "2Kor",
        BibleBook.GAL to "Gal",
        BibleBook.EPH to "Ef",
        BibleBook.PHIL to "Fil",
        BibleBook.COL to "Kol",
        BibleBook.THESS1 to "1Thess",
        BibleBook.THESS2 to "2Thess",
        BibleBook.TIM1 to "1Tim",
        BibleBook.TIM2 to "2Tim",
        BibleBook.TITUS to "Tít",
        BibleBook.PHLM to "Filem",
        BibleBook.HEB to "Zsid",
        BibleBook.JAS to "Jak",
        BibleBook.PET1 to "1Pt",
        BibleBook.PET2 to "2Pt",
        BibleBook.JOHN1 to "1Jn",
        BibleBook.JOHN2 to "2Jn",
        BibleBook.JOHN3 to "3Jn",
        BibleBook.JUDE to "Júd",
        BibleBook.REV to "Jel"
    )

    override fun getFullName(book: BibleBook): String? = fullNames[book]
    override fun getShortName(book: BibleBook): String? = shortNames[book]
    override fun getAlternateName(book: BibleBook): String? = null
}
