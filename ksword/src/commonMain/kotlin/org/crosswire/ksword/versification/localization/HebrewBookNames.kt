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
 * Hebrew localized Bible book names.
 * Generated from BibleNames_he.properties
 */
object HebrewBookNames : BookNameLocalization {
    private val fullNames = mapOf(
        BibleBook.GEN to "בראשית",
        BibleBook.EXOD to "שמות",
        BibleBook.LEV to "ויקרא",
        BibleBook.NUM to "במדבר",
        BibleBook.DEUT to "דברים",
        BibleBook.JOSH to "יהושע",
        BibleBook.JUDG to "שופטים",
        BibleBook.RUTH to "רות",
        BibleBook.SAM1 to "שמואל א",
        BibleBook.SAM2 to "שמואל ב",
        BibleBook.KGS1 to "מלכים א",
        BibleBook.KGS2 to "מלכים ב",
        BibleBook.CHR1 to "דברי הימים א",
        BibleBook.CHR2 to "דברי הימים ב",
        BibleBook.EZRA to "עזרא",
        BibleBook.NEH to "נחמיה",
        BibleBook.ESTH to "אסתר",
        BibleBook.JOB to "איוב",
        BibleBook.PS to "תהלים",
        BibleBook.PROV to "משלי",
        BibleBook.ECCL to "קהלת",
        BibleBook.SONG to "שיר השירים",
        BibleBook.ISA to "ישעיהו",
        BibleBook.JER to "ירמיהו",
        BibleBook.LAM to "איכה",
        BibleBook.EZEK to "יחזקאל",
        BibleBook.DAN to "דניאל",
        BibleBook.HOS to "הושע",
        BibleBook.JOEL to "יואל",
        BibleBook.AMOS to "עמוס",
        BibleBook.OBAD to "עובדיה",
        BibleBook.JONAH to "יונה",
        BibleBook.MIC to "מיכה",
        BibleBook.NAH to "נחום",
        BibleBook.HAB to "חבקוק",
        BibleBook.ZEPH to "צפניה",
        BibleBook.HAG to "חגי",
        BibleBook.ZECH to "זכריה",
        BibleBook.MAL to "מלאכי",
        BibleBook.MATT to "מתי",
        BibleBook.MARK to "מרקוס",
        BibleBook.LUKE to "לוקס",
        BibleBook.JOHN to "יוחנן",
        BibleBook.ACTS to "מעשי השליחים",
        BibleBook.ROM to "אל הרומיים",
        BibleBook.COR1 to "הראשונה לקורינתיים",
        BibleBook.COR2 to "השניה לקורינתיים",
        BibleBook.GAL to "אל הגלטיים",
        BibleBook.EPH to "אל האפסיים",
        BibleBook.PHIL to "פיליפיים",
        BibleBook.COL to "אל הקולסיים",
        BibleBook.THESS1 to "הראשונה לתסלוניקים",
        BibleBook.THESS2 to "השניה לתסלוניקים",
        BibleBook.TIM1 to "הראשונה לטימותיוס",
        BibleBook.TIM2 to "השניה לטימותיוס",
        BibleBook.TITUS to "טיטוס",
        BibleBook.PHLM to "פילימון",
        BibleBook.HEB to "אל העברים",
        BibleBook.JAS to "יעקב",
        BibleBook.PET1 to "הראשונה לפטרוס",
        BibleBook.PET2 to "השניה לפטרוס",
        BibleBook.JOHN1 to "הראשונה ליוחנן",
        BibleBook.JOHN2 to "השניה ליוחנן",
        BibleBook.JOHN3 to "השלישית ליוחנן",
        BibleBook.JUDE to "אגרת יהודה",
        BibleBook.REV to "חזון יוחנן"
    )

    private val shortNames = mapOf(
        BibleBook.GEN to "בראש",
        BibleBook.EXOD to "שמות",
        BibleBook.LEV to "ויקרא",
        BibleBook.NUM to "במדבר",
        BibleBook.DEUT to "דברים",
        BibleBook.JOSH to "יהושע",
        BibleBook.JUDG to "שופטים",
        BibleBook.RUTH to "רות",
        BibleBook.SAM1 to "שמ.א",
        BibleBook.SAM2 to "שמ.ב",
        BibleBook.KGS1 to "מלכ.א",
        BibleBook.KGS2 to "מלכ.ב",
        BibleBook.CHR1 to "דבה.א",
        BibleBook.CHR2 to "דבה.ב",
        BibleBook.EZRA to "עזרא",
        BibleBook.NEH to "נחמיה",
        BibleBook.ESTH to "אסתר",
        BibleBook.JOB to "איוב",
        BibleBook.PS to "תהלים",
        BibleBook.PROV to "משלי",
        BibleBook.ECCL to "קהלת",
        BibleBook.SONG to "שהש",
        BibleBook.ISA to "ישעיה",
        BibleBook.JER to "ירמיה",
        BibleBook.LAM to "איכה",
        BibleBook.EZEK to "יחזק",
        BibleBook.DAN to "דניאל",
        BibleBook.HOS to "הושע",
        BibleBook.JOEL to "יואל",
        BibleBook.AMOS to "עמוס",
        BibleBook.OBAD to "עובדיה",
        BibleBook.JONAH to "יונה",
        BibleBook.MIC to "מיכה",
        BibleBook.NAH to "נחום",
        BibleBook.HAB to "חבקוק",
        BibleBook.ZEPH to "צפניה",
        BibleBook.HAG to "חגי",
        BibleBook.ZECH to "זכריה",
        BibleBook.MAL to "מלאכי",
        BibleBook.MATT to "מתי",
        BibleBook.MARK to "מרקוס",
        BibleBook.LUKE to "לוקס",
        BibleBook.JOHN to "יוחנן",
        BibleBook.ACTS to "מהש",
        BibleBook.ROM to "רומ",
        BibleBook.COR1 to "קור1",
        BibleBook.COR2 to "קור2",
        BibleBook.GAL to "גלטים",
        BibleBook.EPH to "אפס",
        BibleBook.PHIL to "פיליפ",
        BibleBook.COL to "קולס",
        BibleBook.THESS1 to "תס1",
        BibleBook.THESS2 to "תס2",
        BibleBook.TIM1 to "טימ1",
        BibleBook.TIM2 to "טימ2",
        BibleBook.TITUS to "טיטוס",
        BibleBook.PHLM to "פילימ",
        BibleBook.HEB to "עברים",
        BibleBook.JAS to "יעקב",
        BibleBook.PET1 to "פטר1",
        BibleBook.PET2 to "פטר2",
        BibleBook.JOHN1 to "יוח1",
        BibleBook.JOHN2 to "יוח2",
        BibleBook.JOHN3 to "יוח3",
        BibleBook.JUDE to "יהודה",
        BibleBook.REV to "חזון"
    )

    override fun getFullName(book: BibleBook): String? = fullNames[book]
    override fun getShortName(book: BibleBook): String? = shortNames[book]
    override fun getAlternateName(book: BibleBook): String? = null
}
