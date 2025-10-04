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
 * Cantonese localized Bible book names.
 * Generated from BibleNames_yue.properties
 */
object CantoneseBookNames : BookNameLocalization {
    private val fullNames = mapOf(
        BibleBook.GEN to "創世記",
        BibleBook.EXOD to "出埃及記",
        BibleBook.LEV to "利未記",
        BibleBook.NUM to "民數記",
        BibleBook.DEUT to "申命記",
        BibleBook.JOSH to "約書亞記",
        BibleBook.JUDG to "士師記",
        BibleBook.RUTH to "路得記",
        BibleBook.SAM1 to "撒母耳記上",
        BibleBook.SAM2 to "撒母耳記下",
        BibleBook.KGS1 to "列王紀上",
        BibleBook.KGS2 to "列王紀下",
        BibleBook.CHR1 to "歷代志上",
        BibleBook.CHR2 to "歷代志下",
        BibleBook.EZRA to "以斯拉記",
        BibleBook.NEH to "尼希米記",
        BibleBook.ESTH to "以斯帖記",
        BibleBook.JOB to "約伯記",
        BibleBook.PS to "詩篇",
        BibleBook.PROV to "箴言",
        BibleBook.ECCL to "傳道書",
        BibleBook.SONG to "雅歌",
        BibleBook.ISA to "以賽亞書",
        BibleBook.JER to "耶利米書",
        BibleBook.LAM to "耶利米哀歌",
        BibleBook.EZEK to "以西結書",
        BibleBook.DAN to "但以理書",
        BibleBook.HOS to "何西阿書",
        BibleBook.JOEL to "約珥書",
        BibleBook.AMOS to "阿摩司書",
        BibleBook.OBAD to "俄巴底亞書",
        BibleBook.JONAH to "約拿書",
        BibleBook.MIC to "彌迦書",
        BibleBook.NAH to "那鴻書",
        BibleBook.HAB to "哈巴谷書",
        BibleBook.ZEPH to "西番雅書",
        BibleBook.HAG to "哈該書",
        BibleBook.ZECH to "撒迦利亞書",
        BibleBook.MAL to "瑪拉基書",
        BibleBook.MATT to "馬太福音",
        BibleBook.MARK to "馬可福音",
        BibleBook.LUKE to "路加福音",
        BibleBook.JOHN to "約翰福音",
        BibleBook.ACTS to "使徒行傳",
        BibleBook.ROM to "羅馬書",
        BibleBook.COR1 to "哥林多前書",
        BibleBook.COR2 to "哥林多後書",
        BibleBook.GAL to "加拉太書",
        BibleBook.EPH to "以弗所書",
        BibleBook.PHIL to "腓立比書",
        BibleBook.COL to "歌羅西書",
        BibleBook.THESS1 to "帖撒羅尼迦前書",
        BibleBook.THESS2 to "帖撒羅尼迦後書",
        BibleBook.TIM1 to "提摩太前書",
        BibleBook.TIM2 to "提摩太後書",
        BibleBook.TITUS to "提多書",
        BibleBook.PHLM to "腓利門書",
        BibleBook.HEB to "希伯來書",
        BibleBook.JAS to "雅各書",
        BibleBook.PET1 to "彼得前書",
        BibleBook.PET2 to "彼得後書",
        BibleBook.JOHN1 to "約翰一書",
        BibleBook.JOHN2 to "約翰二書",
        BibleBook.JOHN3 to "約翰三書",
        BibleBook.JUDE to "猶大書",
        BibleBook.REV to "啟示錄"
    )

    private val shortNames = mapOf(
        BibleBook.GEN to "創",
        BibleBook.EXOD to "出",
        BibleBook.LEV to "利",
        BibleBook.NUM to "民",
        BibleBook.DEUT to "申",
        BibleBook.JOSH to "書",
        BibleBook.JUDG to "士",
        BibleBook.RUTH to "得",
        BibleBook.SAM1 to "撒上",
        BibleBook.SAM2 to "撒下",
        BibleBook.KGS1 to "王上",
        BibleBook.KGS2 to "王下",
        BibleBook.CHR1 to "代上",
        BibleBook.CHR2 to "代下",
        BibleBook.EZRA to "拉",
        BibleBook.NEH to "尼",
        BibleBook.ESTH to "斯",
        BibleBook.JOB to "伯",
        BibleBook.PS to "詩",
        BibleBook.PROV to "箴",
        BibleBook.ECCL to "傳",
        BibleBook.SONG to "歌",
        BibleBook.ISA to "賽",
        BibleBook.JER to "耶",
        BibleBook.LAM to "哀",
        BibleBook.EZEK to "結",
        BibleBook.DAN to "但",
        BibleBook.HOS to "何",
        BibleBook.JOEL to "珥",
        BibleBook.AMOS to "摩",
        BibleBook.OBAD to "俄",
        BibleBook.JONAH to "拿",
        BibleBook.MIC to "彌",
        BibleBook.NAH to "鴻",
        BibleBook.HAB to "哈",
        BibleBook.ZEPH to "番",
        BibleBook.HAG to "該",
        BibleBook.ZECH to "亞",
        BibleBook.MAL to "瑪",
        BibleBook.MATT to "太",
        BibleBook.MARK to "可",
        BibleBook.LUKE to "路",
        BibleBook.JOHN to "約",
        BibleBook.ACTS to "徒",
        BibleBook.ROM to "羅",
        BibleBook.COR1 to "林前",
        BibleBook.COR2 to "林後",
        BibleBook.GAL to "加",
        BibleBook.EPH to "弗",
        BibleBook.PHIL to "腓",
        BibleBook.COL to "西",
        BibleBook.THESS1 to "帖前",
        BibleBook.THESS2 to "帖後",
        BibleBook.TIM1 to "提前",
        BibleBook.TIM2 to "提後",
        BibleBook.TITUS to "多",
        BibleBook.PHLM to "門",
        BibleBook.HEB to "來",
        BibleBook.JAS to "雅",
        BibleBook.PET1 to "彼前",
        BibleBook.PET2 to "彼後",
        BibleBook.JOHN1 to "約一",
        BibleBook.JOHN2 to "約二",
        BibleBook.JOHN3 to "約三",
        BibleBook.JUDE to "猶",
        BibleBook.REV to "啟"
    )

    override fun getFullName(book: BibleBook): String? = fullNames[book]
    override fun getShortName(book: BibleBook): String? = shortNames[book]
    override fun getAlternateName(book: BibleBook): String? = null
}
