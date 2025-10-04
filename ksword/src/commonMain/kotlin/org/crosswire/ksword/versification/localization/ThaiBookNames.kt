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
 * Thai localized Bible book names.
 * Generated from BibleNames_th.properties
 */
object ThaiBookNames : BookNameLocalization {
    private val fullNames = mapOf(
        BibleBook.GEN to "ปฐมกาล",
        BibleBook.EXOD to "อพยพ",
        BibleBook.LEV to "เลวีนิติ",
        BibleBook.NUM to "กันดารวิถี",
        BibleBook.DEUT to "เฉลยธรรมบัญญัติ",
        BibleBook.JOSH to "โยชูวา",
        BibleBook.JUDG to "ผู้วินิจฉัย",
        BibleBook.RUTH to "นางรูธ",
        BibleBook.SAM1 to "1ซามูเอล",
        BibleBook.SAM2 to "2ซามูเอล",
        BibleBook.KGS1 to "1พงศ์กษัตริย์",
        BibleBook.KGS2 to "2 พงศ์กษัตริย์",
        BibleBook.CHR1 to "1พงศาวดาร",
        BibleBook.CHR2 to "2พงศาวดาร",
        BibleBook.EZRA to "เอสรา",
        BibleBook.NEH to "เนหะมีย์",
        BibleBook.ESTH to "เอสเธอร์",
        BibleBook.JOB to "โยบ",
        BibleBook.PS to "สดุดี",
        BibleBook.PROV to "สุภาษิต",
        BibleBook.ECCL to "ปัญญาจารย์",
        BibleBook.SONG to "เพลงซาโลมอน",
        BibleBook.ISA to "อิสยาห์",
        BibleBook.JER to "เยเรมีย์",
        BibleBook.LAM to "เพลงคร่ำครวญ",
        BibleBook.EZEK to "เอเสเคียล",
        BibleBook.DAN to "ดาเนียล",
        BibleBook.HOS to "โฮเชยา",
        BibleBook.JOEL to "โยเอล",
        BibleBook.AMOS to "อาโมส",
        BibleBook.OBAD to "โอบาดีห์",
        BibleBook.JONAH to "โยนาห์",
        BibleBook.MIC to "มีคาห์",
        BibleBook.NAH to "นาฮูม",
        BibleBook.HAB to "ฮาบากุก",
        BibleBook.ZEPH to "เศฟันยาห์",
        BibleBook.HAG to "ฮักกัย",
        BibleBook.ZECH to "เศคาริยาห์",
        BibleBook.MAL to "มาลาคี",
        BibleBook.MATT to "มัทธิว",
        BibleBook.MARK to "มาระโก",
        BibleBook.LUKE to "ลูกา",
        BibleBook.JOHN to "ยอห์น",
        BibleBook.ACTS to "กิจการของอัครทูต",
        BibleBook.ROM to "โรม",
        BibleBook.COR1 to "1โครินธ์",
        BibleBook.COR2 to "2โครินธ์",
        BibleBook.GAL to "กาลาเทีย",
        BibleBook.EPH to "เอเฟซัส",
        BibleBook.PHIL to "ฟีลิปปี",
        BibleBook.COL to "โคโลสี",
        BibleBook.THESS1 to "1เธสะโลนิกา",
        BibleBook.THESS2 to "2เธสะโลนิกา",
        BibleBook.TIM1 to "1ทิโมธี",
        BibleBook.TIM2 to "2ทิโมธี",
        BibleBook.TITUS to "ทิตัส",
        BibleBook.PHLM to "ฟีเลโมน",
        BibleBook.HEB to "ฮีบรู",
        BibleBook.JAS to "ยากอบ",
        BibleBook.PET1 to "1เปโตร",
        BibleBook.PET2 to "2เปโตร",
        BibleBook.JOHN1 to "1ยอห์น",
        BibleBook.JOHN2 to "2ยอห์น",
        BibleBook.JOHN3 to "3ยอห์น",
        BibleBook.JUDE to "ยูดา",
        BibleBook.REV to "วิวรณ์"
    )

    private val shortNames = mapOf(
        BibleBook.GEN to "ปฐก.",
        BibleBook.EXOD to "อพย.",
        BibleBook.LEV to "ลนต.",
        BibleBook.NUM to "กดว.",
        BibleBook.DEUT to "ฉธบ.",
        BibleBook.JOSH to "ยชว.",
        BibleBook.JUDG to "วนฉ.",
        BibleBook.RUTH to "นรธ.",
        BibleBook.SAM1 to "1ซมอ.",
        BibleBook.SAM2 to "2ซมอ.",
        BibleBook.KGS1 to "1พกษ.",
        BibleBook.KGS2 to "2พกษ.",
        BibleBook.CHR1 to "1พศด.",
        BibleBook.CHR2 to "2พศด.",
        BibleBook.EZRA to "อสร.",
        BibleBook.NEH to "นหม.",
        BibleBook.ESTH to "อสธ.",
        BibleBook.JOB to "โยบ",
        BibleBook.PS to "สดด.",
        BibleBook.PROV to "สภษ.",
        BibleBook.ECCL to "ปญจ.",
        BibleBook.SONG to "พซม.",
        BibleBook.ISA to "อสย.",
        BibleBook.JER to "ยรม.",
        BibleBook.LAM to "พคค.",
        BibleBook.EZEK to "อสค.",
        BibleBook.DAN to "ดนล.",
        BibleBook.HOS to "ฮชย.",
        BibleBook.JOEL to "ยอล.",
        BibleBook.AMOS to "อมส.",
        BibleBook.OBAD to "อบด.",
        BibleBook.JONAH to "ยนา.",
        BibleBook.MIC to "มคา.",
        BibleBook.NAH to "นฮม.",
        BibleBook.HAB to "ฮบก.",
        BibleBook.ZEPH to "ศฟย.",
        BibleBook.HAG to "ฮกก.",
        BibleBook.ZECH to "ศคย.",
        BibleBook.MAL to "มลค.",
        BibleBook.MATT to "มธ.",
        BibleBook.MARK to "มก.",
        BibleBook.LUKE to "ลก.",
        BibleBook.JOHN to "ยน.",
        BibleBook.ACTS to "กจ.",
        BibleBook.ROM to "รม.",
        BibleBook.COR1 to "1คร.",
        BibleBook.COR2 to "2คร.",
        BibleBook.GAL to "กท.",
        BibleBook.EPH to "อฟ.",
        BibleBook.PHIL to "ฟป.",
        BibleBook.COL to "คส.",
        BibleBook.THESS1 to "1ธส.",
        BibleBook.THESS2 to "2ธส.",
        BibleBook.TIM1 to "1ทธ.",
        BibleBook.TIM2 to "2ทธ.",
        BibleBook.TITUS to "ทต.",
        BibleBook.PHLM to "ฟม.",
        BibleBook.HEB to "ฮบ.",
        BibleBook.JAS to "ยก.",
        BibleBook.PET1 to "1ปต.",
        BibleBook.PET2 to "2ปต.",
        BibleBook.JOHN1 to "1ยน.",
        BibleBook.JOHN2 to "2ยน.",
        BibleBook.JOHN3 to "3ยน.",
        BibleBook.JUDE to "ยด.",
        BibleBook.REV to "วว."
    )

    override fun getFullName(book: BibleBook): String? = fullNames[book]
    override fun getShortName(book: BibleBook): String? = shortNames[book]
    override fun getAlternateName(book: BibleBook): String? = null
}
