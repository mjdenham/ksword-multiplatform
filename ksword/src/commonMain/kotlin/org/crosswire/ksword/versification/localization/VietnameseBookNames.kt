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
 * Vietnamese localized Bible book names.
 * Generated from BibleNames_vi.properties
 */
object VietnameseBookNames : BookNameLocalization {
    private val fullNames = mapOf(
        BibleBook.GEN to "Sáng Thế Ký",
        BibleBook.EXOD to "Xuất Êdíptô Ký",
        BibleBook.LEV to "Lêvi Ký",
        BibleBook.NUM to "Dân Số Ký",
        BibleBook.DEUT to "Phục Truyền Luât Lệ Ký",
        BibleBook.JOSH to "Giôsuê",
        BibleBook.JUDG to "Các Quan Xét",
        BibleBook.RUTH to "Rutơ",
        BibleBook.SAM1 to "1 Samuên",
        BibleBook.SAM2 to "2 Samuên",
        BibleBook.KGS1 to "1 Các Vua",
        BibleBook.KGS2 to "2 Các Vua",
        BibleBook.CHR1 to "1 Sử ký",
        BibleBook.CHR2 to "2 Sử ký",
        BibleBook.EZRA to "Exơra",
        BibleBook.NEH to "Nêhêmi",
        BibleBook.ESTH to "Êxơtê",
        BibleBook.JOB to "Gióp",
        BibleBook.PS to "Thi Thiên",
        BibleBook.PROV to "Châm Ngôn",
        BibleBook.ECCL to "Truyền Đạo",
        BibleBook.SONG to "Nhã Ca",
        BibleBook.ISA to "Êsai",
        BibleBook.JER to "Giêrêmi",
        BibleBook.LAM to "Ca Thương",
        BibleBook.EZEK to "Êxêchiên",
        BibleBook.DAN to "Đaniên",
        BibleBook.HOS to "Ôsê",
        BibleBook.JOEL to "Giôên",
        BibleBook.AMOS to "Amốt",
        BibleBook.OBAD to "Ápđia",
        BibleBook.JONAH to "Giôna",
        BibleBook.MIC to "Michê",
        BibleBook.NAH to "Nahum",
        BibleBook.HAB to "Habacúc",
        BibleBook.ZEPH to "Sôphôni",
        BibleBook.HAG to "Aghê",
        BibleBook.ZECH to "Xachari",
        BibleBook.MAL to "Malachi",
        BibleBook.MATT to "Mathiơ",
        BibleBook.MARK to "Mác",
        BibleBook.LUKE to "Luca",
        BibleBook.JOHN to "Giăng",
        BibleBook.ACTS to "Công Vụ Các Sứ Đồ",
        BibleBook.ROM to "Rôma",
        BibleBook.COR1 to "1 Côrinhtô",
        BibleBook.COR2 to "2 Côrinhtô",
        BibleBook.GAL to "Galati",
        BibleBook.EPH to "Êphêsô",
        BibleBook.PHIL to "Philíp",
        BibleBook.COL to "Côlôse",
        BibleBook.THESS1 to "1 Têsalônica",
        BibleBook.THESS2 to "2 Têsalônica",
        BibleBook.TIM1 to "1 Timôthê",
        BibleBook.TIM2 to "2 Timôthê",
        BibleBook.TITUS to "Tít",
        BibleBook.PHLM to "Philêmôn",
        BibleBook.HEB to "Hêbơrơ",
        BibleBook.JAS to "Giacơ",
        BibleBook.PET1 to "1 Phierơ",
        BibleBook.PET2 to "2 Phierơ",
        BibleBook.JOHN1 to "1 Giăng",
        BibleBook.JOHN2 to "2 Giăng",
        BibleBook.JOHN3 to "3 Giăng",
        BibleBook.JUDE to "Giuđe",
        BibleBook.REV to "Khải Huyền"
    )

    private val shortNames = mapOf(
        BibleBook.GEN to "Sa",
        BibleBook.EXOD to "Xu",
        BibleBook.LEV to "Le",
        BibleBook.NUM to "Dan",
        BibleBook.DEUT to "Phu",
        BibleBook.JOSH to "Gios",
        BibleBook.JUDG to "Cac",
        BibleBook.RUTH to "Ru",
        BibleBook.SAM1 to "1 Sa",
        BibleBook.SAM2 to "2 Sa",
        BibleBook.KGS1 to "1 Vua",
        BibleBook.KGS2 to "2 Vua",
        BibleBook.CHR1 to "1 Su",
        BibleBook.CHR2 to "2 Su",
        BibleBook.EZRA to "Exo",
        BibleBook.NEH to "Ne",
        BibleBook.ESTH to "Et",
        BibleBook.JOB to "Giop",
        BibleBook.PS to "Thi",
        BibleBook.PROV to "Ch",
        BibleBook.ECCL to "Tr",
        BibleBook.SONG to "Nha",
        BibleBook.ISA to "Es",
        BibleBook.JER to "Gie",
        BibleBook.LAM to "Ca",
        BibleBook.EZEK to "Exe",
        BibleBook.DAN to "Da",
        BibleBook.HOS to "Os",
        BibleBook.JOEL to "Gio",
        BibleBook.AMOS to "Am",
        BibleBook.OBAD to "Ap",
        BibleBook.JONAH to "Gion",
        BibleBook.MIC to "Mi",
        BibleBook.NAH to "Na",
        BibleBook.HAB to "Ha",
        BibleBook.ZEPH to "So",
        BibleBook.HAG to "Ag",
        BibleBook.ZECH to "Xa",
        BibleBook.MAL to "Ma",
        BibleBook.MATT to "Mat",
        BibleBook.MARK to "Mac",
        BibleBook.LUKE to "Lu",
        BibleBook.JOHN to "Gi",
        BibleBook.ACTS to "Cong",
        BibleBook.ROM to "Ro",
        BibleBook.COR1 to "1 Co",
        BibleBook.COR2 to "2 Co",
        BibleBook.GAL to "Ga",
        BibleBook.EPH to "Eph",
        BibleBook.PHIL to "Phi",
        BibleBook.COL to "Co",
        BibleBook.THESS1 to "1 Te",
        BibleBook.THESS2 to "2 Te",
        BibleBook.TIM1 to "1 Ti",
        BibleBook.TIM2 to "2 Ti",
        BibleBook.TITUS to "Tit",
        BibleBook.PHLM to "Phil",
        BibleBook.HEB to "He",
        BibleBook.JAS to "Gia",
        BibleBook.PET1 to "1 Phi",
        BibleBook.PET2 to "2 Phi",
        BibleBook.JOHN1 to "1 Gi",
        BibleBook.JOHN2 to "2 Gi",
        BibleBook.JOHN3 to "3 Gi",
        BibleBook.JUDE to "Giu",
        BibleBook.REV to "Kh"
    )

    override fun getFullName(book: BibleBook): String? = fullNames[book]
    override fun getShortName(book: BibleBook): String? = shortNames[book]
    override fun getAlternateName(book: BibleBook): String? = null
}
