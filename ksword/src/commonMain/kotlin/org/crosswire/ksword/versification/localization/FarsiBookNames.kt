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
 * Farsi localized Bible book names.
 * Generated from BibleNames_fa.properties
 */
object FarsiBookNames : BookNameLocalization {
    private val fullNames = mapOf(
        BibleBook.GEN to "پیدایش",
        BibleBook.EXOD to "خروج",
        BibleBook.LEV to "لاویان",
        BibleBook.NUM to "اعداد",
        BibleBook.DEUT to "تثنیه",
        BibleBook.JOSH to "یوشع",
        BibleBook.JUDG to "داوران",
        BibleBook.RUTH to "روت",
        BibleBook.SAM1 to "اول سموﺋ‌یل",
        BibleBook.SAM2 to "دوم سموﺋیل",
        BibleBook.KGS1 to "اول پادشاهان",
        BibleBook.KGS2 to "نوم پادشاهان",
        BibleBook.CHR1 to "اول تواریخ",
        BibleBook.CHR2 to "دوم تواریخ",
        BibleBook.EZRA to "عزرا",
        BibleBook.NEH to "نحمیا",
        BibleBook.ESTH to "استر",
        BibleBook.JOB to "ایوب",
        BibleBook.PS to "مزامیر",
        BibleBook.PROV to "امثال سلیمان",
        BibleBook.ECCL to "جامعه",
        BibleBook.SONG to "غزل غزلهای سلیمان",
        BibleBook.ISA to "اشعیا",
        BibleBook.JER to "ارمیا",
        BibleBook.LAM to "مراثی ارمیا",
        BibleBook.EZEK to "حزقیال",
        BibleBook.DAN to "دانیال",
        BibleBook.HOS to "هوشع",
        BibleBook.JOEL to "یوﺋ‌یل",
        BibleBook.AMOS to "عاموس",
        BibleBook.OBAD to "عوبدیا",
        BibleBook.JONAH to "یونس",
        BibleBook.MIC to "میکاه",
        BibleBook.NAH to "ناحوم",
        BibleBook.HAB to "حبقوق",
        BibleBook.ZEPH to "صفنیا",
        BibleBook.HAG to "حجی",
        BibleBook.ZECH to "زکریا",
        BibleBook.MAL to "ملاکی",
        BibleBook.MATT to "متی",
        BibleBook.MARK to "مرقس",
        BibleBook.LUKE to "لوقا",
        BibleBook.JOHN to "یوحنا",
        BibleBook.ACTS to "اعمال رسولان",
        BibleBook.ROM to "رومیان",
        BibleBook.COR1 to "اول قرنتیان",
        BibleBook.COR2 to "دوم قرنتیان",
        BibleBook.GAL to "غلاطیان",
        BibleBook.EPH to "افسسیان",
        BibleBook.PHIL to "فیلیپیان",
        BibleBook.COL to "کولسیان",
        BibleBook.THESS1 to "اول تسالونکیان",
        BibleBook.THESS2 to "دوم تسالونکیان",
        BibleBook.TIM1 to "اول تیموتاؤس",
        BibleBook.TIM2 to "دوم تیموتاؤس",
        BibleBook.TITUS to "تیطوس",
        BibleBook.PHLM to "فلیمون",
        BibleBook.HEB to "عبرانیان",
        BibleBook.JAS to "یعقوب",
        BibleBook.PET1 to "اول پطرس",
        BibleBook.PET2 to "دوم پطرس",
        BibleBook.JOHN1 to "اول یوحنا",
        BibleBook.JOHN2 to "دوم یوحنا",
        BibleBook.JOHN3 to "سوم یوحنا",
        BibleBook.JUDE to "یهودا",
        BibleBook.REV to "مکاشفه"
    )

    private val shortNames = mapOf(
        BibleBook.GEN to "پید",
        BibleBook.EXOD to "خرو",
        BibleBook.LEV to "لاو",
        BibleBook.NUM to "اعد",
        BibleBook.DEUT to "تثن",
        BibleBook.JOSH to "یوش",
        BibleBook.JUDG to "داو",
        BibleBook.RUTH to "روت",
        BibleBook.SAM1 to "۱ سمو",
        BibleBook.SAM2 to "۲ سمو",
        BibleBook.KGS1 to "۱ پاد",
        BibleBook.KGS2 to "۲ پاد",
        BibleBook.CHR1 to "۱ توا",
        BibleBook.CHR2 to "۲ توا",
        BibleBook.EZRA to "عزر",
        BibleBook.NEH to "نحم",
        BibleBook.ESTH to "است",
        BibleBook.JOB to "ایو",
        BibleBook.PS to "مزا",
        BibleBook.PROV to "امث",
        BibleBook.ECCL to "جام",
        BibleBook.SONG to "غزل",
        BibleBook.ISA to "اشح",
        BibleBook.JER to "ارم",
        BibleBook.LAM to "مرا",
        BibleBook.EZEK to "عزق",
        BibleBook.DAN to "دان",
        BibleBook.HOS to "هوش",
        BibleBook.JOEL to "یوئیل",
        BibleBook.AMOS to "عام",
        BibleBook.OBAD to "عوب",
        BibleBook.JONAH to "یون",
        BibleBook.MIC to "میک",
        BibleBook.NAH to "ناح",
        BibleBook.HAB to "حبق",
        BibleBook.ZEPH to "صفن",
        BibleBook.HAG to "حج",
        BibleBook.ZECH to "زکر",
        BibleBook.MAL to "ملا",
        BibleBook.MATT to "متی",
        BibleBook.MARK to "مرق",
        BibleBook.LUKE to "لوق",
        BibleBook.JOHN to "یوح",
        BibleBook.ACTS to "اعم",
        BibleBook.ROM to "روم",
        BibleBook.COR1 to "۱ قر",
        BibleBook.COR2 to "۲ قر",
        BibleBook.GAL to "غلا",
        BibleBook.EPH to "افس",
        BibleBook.PHIL to "فیل",
        BibleBook.COL to "کقل",
        BibleBook.THESS1 to "۱ تسا",
        BibleBook.THESS2 to "۲ تسا",
        BibleBook.TIM1 to "۱ تیم",
        BibleBook.TIM2 to "۲ تیم",
        BibleBook.TITUS to "تیط",
        BibleBook.PHLM to "فلی",
        BibleBook.HEB to "عبر",
        BibleBook.JAS to "یعق",
        BibleBook.PET1 to "۱ پطر",
        BibleBook.PET2 to "۲ پطر",
        BibleBook.JOHN1 to "۱ یوح",
        BibleBook.JOHN2 to "۲ یوح",
        BibleBook.JOHN3 to "۳ یوح",
        BibleBook.JUDE to "یهو",
        BibleBook.REV to "مکا"
    )

    override fun getFullName(book: BibleBook): String? = fullNames[book]
    override fun getShortName(book: BibleBook): String? = shortNames[book]
    override fun getAlternateName(book: BibleBook): String? = null
}
