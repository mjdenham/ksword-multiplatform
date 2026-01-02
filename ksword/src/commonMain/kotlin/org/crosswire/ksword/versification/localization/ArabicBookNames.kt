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
 * Arabic localized Bible book names.
 * Generated from BibleNames_ar.properties
 */
object ArabicBookNames : BookNameLocalization {
    private val fullNames = mapOf(
        BibleBook.GEN to "تكوين",
        BibleBook.EXOD to "خروج",
        BibleBook.LEV to "لاويين",
        BibleBook.NUM to "عدد",
        BibleBook.DEUT to "تثنية",
        BibleBook.JOSH to "يشوع",
        BibleBook.JUDG to "قضاه",
        BibleBook.RUTH to "راعوث",
        BibleBook.SAM1 to "صموئيل 1",
        BibleBook.SAM2 to "صموئيل 2",
        BibleBook.KGS1 to "ملوك 1",
        BibleBook.KGS2 to "ملوك 2",
        BibleBook.CHR1 to "أخبار الأيام الأول",
        BibleBook.CHR2 to "أخبار الأيام الثاني",
        BibleBook.EZRA to "عزرا",
        BibleBook.NEH to "نحميا",
        BibleBook.ESTH to "أستير",
        BibleBook.JOB to "أيوب",
        BibleBook.PS to "مزامير",
        BibleBook.PROV to "أمثال",
        BibleBook.ECCL to "الجامعة",
        BibleBook.SONG to "نشيد الأنشاد",
        BibleBook.ISA to "إشعياء",
        BibleBook.JER to "إرميا",
        BibleBook.LAM to "مراثي إرميا",
        BibleBook.EZEK to "حزقيال",
        BibleBook.DAN to "دانيال",
        BibleBook.HOS to "هوشع",
        BibleBook.JOEL to "يوئيل",
        BibleBook.AMOS to "عاموس",
        BibleBook.OBAD to "عوبديا",
        BibleBook.JONAH to "يونان",
        BibleBook.MIC to "ميخا",
        BibleBook.NAH to "ناحوم",
        BibleBook.HAB to "حبقوق",
        BibleBook.ZEPH to "صفنيا",
        BibleBook.HAG to "حجي",
        BibleBook.ZECH to "زكريا",
        BibleBook.MAL to "ملاخي",
        BibleBook.MATT to "متى",
        BibleBook.MARK to "مرقس",
        BibleBook.LUKE to "لوقا",
        BibleBook.JOHN to "يوحنا",
        BibleBook.ACTS to "أعمال الرسل",
        BibleBook.ROM to "رومية",
        BibleBook.COR1 to "كورنثوس الأولى",
        BibleBook.COR2 to "كورنثوس 2",
        BibleBook.GAL to "غلاطية",
        BibleBook.EPH to "أفسس",
        BibleBook.PHIL to "فيلبي",
        BibleBook.COL to "كولوسي",
        BibleBook.THESS1 to "تسالونيكي الأولى",
        BibleBook.THESS2 to "تسالونيكي 2",
        BibleBook.TIM1 to "تيموثاوس 1",
        BibleBook.TIM2 to "تيموثاوس 2",
        BibleBook.TITUS to "تيطس",
        BibleBook.PHLM to "فيلمون",
        BibleBook.HEB to "العبرانيين",
        BibleBook.JAS to "يعقوب",
        BibleBook.PET1 to "بطرس 1",
        BibleBook.PET2 to "بطرس 2",
        BibleBook.JOHN1 to "يوحنا الأولى",
        BibleBook.JOHN2 to "يوحنا الثانية",
        BibleBook.JOHN3 to "يوحنا 3",
        BibleBook.JUDE to "يهوذا",
        BibleBook.REV to "الرؤيا"
    )

    private val shortNames = mapOf(
        BibleBook.GEN to "تك",
        BibleBook.EXOD to "خر",
        BibleBook.LEV to "لا",
        BibleBook.NUM to "عد",
        BibleBook.DEUT to "تث",
        BibleBook.JOSH to "يش",
        BibleBook.JUDG to "قض",
        BibleBook.RUTH to "را",
        BibleBook.SAM1 to "1صم",
        BibleBook.SAM2 to "2صم",
        BibleBook.KGS1 to "1مل",
        BibleBook.KGS2 to "2مل",
        BibleBook.CHR1 to "1أخ",
        BibleBook.CHR2 to "2أخ",
        BibleBook.EZRA to "عز",
        BibleBook.NEH to "نح",
        BibleBook.ESTH to "اس",
        BibleBook.JOB to "أي",
        BibleBook.PS to "مز",
        BibleBook.PROV to "أم",
        BibleBook.ECCL to "جا",
        BibleBook.SONG to "نش",
        BibleBook.ISA to "إش",
        BibleBook.JER to "إر",
        BibleBook.LAM to "مرا",
        BibleBook.EZEK to "حز",
        BibleBook.DAN to "دا",
        BibleBook.HOS to "هو",
        BibleBook.JOEL to "يؤ",
        BibleBook.AMOS to "عا",
        BibleBook.OBAD to "عو",
        BibleBook.JONAH to "يون",
        BibleBook.MIC to "مي",
        BibleBook.NAH to "نا",
        BibleBook.HAB to "حب",
        BibleBook.ZEPH to "صف",
        BibleBook.HAG to "حج",
        BibleBook.ZECH to "زك",
        BibleBook.MAL to "ملا",
        BibleBook.MATT to "مت",
        BibleBook.MARK to "مر",
        BibleBook.LUKE to "لو",
        BibleBook.JOHN to "يو",
        BibleBook.ACTS to "أع",
        BibleBook.ROM to "رو",
        BibleBook.COR1 to "1كو",
        BibleBook.COR2 to "2كو",
        BibleBook.GAL to "غل",
        BibleBook.EPH to "أف",
        BibleBook.PHIL to "في",
        BibleBook.COL to "كو",
        BibleBook.THESS1 to "1تس",
        BibleBook.THESS2 to "2تس",
        BibleBook.TIM1 to "1تي",
        BibleBook.TIM2 to "2تي",
        BibleBook.TITUS to "تي",
        BibleBook.PHLM to "فل",
        BibleBook.HEB to "عب",
        BibleBook.JAS to "يع",
        BibleBook.PET1 to "1بط",
        BibleBook.PET2 to "2بط",
        BibleBook.JOHN1 to "1يو",
        BibleBook.JOHN2 to "2يو",
        BibleBook.JOHN3 to "3يو",
        BibleBook.JUDE to "يه",
        BibleBook.REV to "رؤ"
    )

    private val alternateNames = mapOf(
        BibleBook.DEUT to "dt",
        BibleBook.JUDG to "jdg,jud",
        BibleBook.RUTH to "rth",
        BibleBook.SAM1 to "isamuel",
        BibleBook.SAM2 to "iisamuel",
        BibleBook.KGS1 to "1kg,ikings,ikgs",
        BibleBook.KGS2 to "2kgs,iikings,iikgs",
        BibleBook.CHR1 to "ichronicles",
        BibleBook.CHR2 to "iichronicles",
        BibleBook.PS to "pss,psm",
        BibleBook.ECCL to "qohelet",
        BibleBook.SONG to "ss,songofsongs,sos,canticleofcanticles,canticle,can,coc,sng",
        BibleBook.ISA to "is",
        BibleBook.EZEK to "Ezk",
        BibleBook.JOEL to "Jol",
        BibleBook.JONAH to "jnh",
        BibleBook.NAH to "Nam",
        BibleBook.MATT to "mt",
        BibleBook.MARK to "mk,mrk",
        BibleBook.LUKE to "lk",
        BibleBook.JOHN to "jn,jhn",
        BibleBook.COR1 to "icorinthians",
        BibleBook.COR2 to "iicorinthians",
        BibleBook.PHIL to "php",
        BibleBook.COL to "co",
        BibleBook.THESS1 to "ith",
        BibleBook.THESS2 to "iith",
        BibleBook.TIM1 to "1tm,iti,itm",
        BibleBook.TIM2 to "2tm,iiti,iitm",
        BibleBook.PHLM to "phm,phlm",
        BibleBook.JAS to "jas",
        BibleBook.PET1 to "1ptr,ip",
        BibleBook.PET2 to "2pt,iip",
        BibleBook.JOHN1 to "1jn,1jh,ijo,ijn,ijh",
        BibleBook.JOHN2 to "2jn,2jh,iijo,iijn,iijh",
        BibleBook.JOHN3 to "3jn,3jh,iiijo,iiijn,iiijh",
        BibleBook.REV to "rv,apocalypse",
        BibleBook.MACC1 to "imaccabees",
        BibleBook.MACC2 to "iimacabees",
        BibleBook.EP_JER to "letterofjeremiah,letterjeremiah,epistlejeremiah,epjeremiah",
        BibleBook.NAH to "Nam",
        BibleBook.EN1 to "ienoch",
        BibleBook.CLEM1 to "iiclement",
        BibleBook.CLEM2 to "iiclement"
    )

    override fun getFullName(book: BibleBook): String? = fullNames[book]
    override fun getShortName(book: BibleBook): String? = shortNames[book]
    override fun getAlternateName(book: BibleBook): String? = alternateNames[book]
}
