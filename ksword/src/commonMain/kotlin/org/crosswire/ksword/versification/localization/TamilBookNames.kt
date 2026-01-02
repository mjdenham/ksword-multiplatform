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
 * Tamil localized Bible book names.
 * Generated from BibleNames_ta.properties
 */
object TamilBookNames : BookNameLocalization {
    private val fullNames = mapOf(
        BibleBook.GEN to "தொடக்க நூல்",
        BibleBook.EXOD to "விடுதலைப் பயணம்",
        BibleBook.LEV to "லேவியர்",
        BibleBook.NUM to "எண்ணிக்கை",
        BibleBook.DEUT to "இணைச் சட்டம்",
        BibleBook.JOSH to "யோசுவா",
        BibleBook.JUDG to "நீதித் தலைவர்கள்",
        BibleBook.RUTH to "ரூத்து",
        BibleBook.SAM1 to "1 சாமுவேல்",
        BibleBook.SAM2 to "2 சாமுவேல்",
        BibleBook.KGS1 to "1 அரசர்கள்",
        BibleBook.KGS2 to "2 அரசர்கள்",
        BibleBook.CHR1 to "1 குறிப்பேடு",
        BibleBook.CHR2 to "2 குறிப்பேடு",
        BibleBook.EZRA to "எஸ்ரா",
        BibleBook.NEH to "நெகேமியா",
        BibleBook.ESTH to "எஸ்தர்",
        BibleBook.JOB to "யோபு",
        BibleBook.PS to "திருப்பாடல்கள்",
        BibleBook.PROV to "நீதிமொழிகள்",
        BibleBook.ECCL to "சபை உரையாளர்",
        BibleBook.SONG to "இனிமைமிகு பாடல்",
        BibleBook.ISA to "எசாயா",
        BibleBook.JER to "எரேமியா",
        BibleBook.LAM to "புலம்பல்",
        BibleBook.EZEK to "எசேக்கியேல்",
        BibleBook.DAN to "தானியேல்",
        BibleBook.HOS to "ஓசேயா",
        BibleBook.JOEL to "யோவேல்",
        BibleBook.AMOS to "ஆமோஸ்",
        BibleBook.OBAD to "ஒபதியா",
        BibleBook.JONAH to "யோனா",
        BibleBook.MIC to "மீக்கா",
        BibleBook.NAH to "நாகூம்",
        BibleBook.HAB to "அபக்கூக்கு",
        BibleBook.ZEPH to "செப்பனியா",
        BibleBook.HAG to "ஆகாய்",
        BibleBook.ZECH to "செக்கரியா",
        BibleBook.MAL to "மலாக்கி",
        BibleBook.MATT to "மத்தேயு",
        BibleBook.MARK to "மாற்கு",
        BibleBook.LUKE to "லூக்கா",
        BibleBook.JOHN to "யோவான்",
        BibleBook.ACTS to "திருத்தூதர் பணிகள்",
        BibleBook.ROM to "உரோமையர்",
        BibleBook.COR1 to "1 கொரிந்தியர்",
        BibleBook.COR2 to "2 கொரிந்தியர்",
        BibleBook.GAL to "கலாத்தியர்",
        BibleBook.EPH to "எபேசியர்",
        BibleBook.PHIL to "பிலிப்பியர்",
        BibleBook.COL to "கொலோசையர்",
        BibleBook.THESS1 to "1 தெசலோனிக்கர்",
        BibleBook.THESS2 to "2 தெசலோனிக்கர்",
        BibleBook.TIM1 to "1 திமொத்தேயு",
        BibleBook.TIM2 to "2 திமொத்தேயு",
        BibleBook.TITUS to "தீத்து",
        BibleBook.PHLM to "பிலமோன்",
        BibleBook.HEB to "எபிரேயர்",
        BibleBook.JAS to "யாக்கோபு",
        BibleBook.PET1 to "1 பேதுரு",
        BibleBook.PET2 to "2 பேதுரு",
        BibleBook.JOHN1 to "1 யோவான்",
        BibleBook.JOHN2 to "2 யோவான்",
        BibleBook.JOHN3 to "3 யோவான்",
        BibleBook.JUDE to "யூதா",
        BibleBook.REV to "திருவெளிப்பாடு"
    )

    private val shortNames = mapOf(
        BibleBook.GEN to "தொநூ",
        BibleBook.EXOD to "விப",
        BibleBook.LEV to "லேவி",
        BibleBook.NUM to "எண்",
        BibleBook.DEUT to "இச",
        BibleBook.JOSH to "யோசு",
        BibleBook.JUDG to "நீத",
        BibleBook.RUTH to "ரூத்",
        BibleBook.SAM1 to "1 சாமு",
        BibleBook.SAM2 to "2 சாமு",
        BibleBook.KGS1 to "1 அர",
        BibleBook.KGS2 to "2 அர",
        BibleBook.CHR1 to "1 குறி",
        BibleBook.CHR2 to "2 குறி",
        BibleBook.EZRA to "எஸ்ரா",
        BibleBook.NEH to "நெகே",
        BibleBook.ESTH to "எஸ்",
        BibleBook.JOB to "யோபு",
        BibleBook.PS to "திபா",
        BibleBook.PROV to "நீதி",
        BibleBook.ECCL to "சஉ",
        BibleBook.SONG to "இபா",
        BibleBook.ISA to "எசா",
        BibleBook.JER to "எரே",
        BibleBook.LAM to "புல",
        BibleBook.EZEK to "எசே",
        BibleBook.DAN to "தானி",
        BibleBook.HOS to "ஓசே",
        BibleBook.JOEL to "யோவே",
        BibleBook.AMOS to "ஆமோ",
        BibleBook.OBAD to "ஒப",
        BibleBook.JONAH to "யோனா",
        BibleBook.MIC to "மீக்",
        BibleBook.NAH to "நாகூ",
        BibleBook.HAB to "அப",
        BibleBook.ZEPH to "செப்",
        BibleBook.HAG to "ஆகா",
        BibleBook.ZECH to "செக்",
        BibleBook.MAL to "மலா",
        BibleBook.MATT to "மத்",
        BibleBook.MARK to "மாற்",
        BibleBook.LUKE to "லூக்",
        BibleBook.JOHN to "யோவா",
        BibleBook.ACTS to "திப",
        BibleBook.ROM to "உரோ",
        BibleBook.COR1 to "1 கொரி",
        BibleBook.COR2 to "2 கொரி",
        BibleBook.GAL to "கலா",
        BibleBook.EPH to "எபேசி",
        BibleBook.PHIL to "பிலி",
        BibleBook.COL to "கொலோ",
        BibleBook.THESS1 to "1 தெச",
        BibleBook.THESS2 to "2 தெச",
        BibleBook.TIM1 to "1 திமொ",
        BibleBook.TIM2 to "2 திமொ",
        BibleBook.TITUS to "தீத்",
        BibleBook.PHLM to "பில",
        BibleBook.HEB to "எபி",
        BibleBook.JAS to "யாக்",
        BibleBook.PET1 to "1 பேது",
        BibleBook.PET2 to "2 பேது",
        BibleBook.JOHN1 to "1 யோவா",
        BibleBook.JOHN2 to "2 யோவா",
        BibleBook.JOHN3 to "3 யோவா",
        BibleBook.JUDE to "யூதா",
        BibleBook.REV to "திவெ"
    )

    private val alternateNames = mapOf(
        BibleBook.GEN to "ஆதியாகமம்",
        BibleBook.EXOD to "யாத்திராகமம்,யாத்",
        BibleBook.LEV to "லேவியராகமம்",
        BibleBook.NUM to "எண்ணாகமம்",
        BibleBook.DEUT to "உபாகமம்",
        BibleBook.JUDG to "நியாயாதிபதிகள்",
        BibleBook.KGS1 to "1 இராஜாக்கள்",
        BibleBook.KGS2 to "2 இராஜாக்கள்",
        BibleBook.CHR1 to "1 நாளாகமம்",
        BibleBook.CHR2 to "2 நாளாகமம்",
        BibleBook.EZRA to "எஸ்றா",
        BibleBook.ISA to "ஏசாயா,ஏசா",
        BibleBook.HOS to "ஓசியா,ஓசி",
        BibleBook.MIC to "மீகா",
        BibleBook.HAB to "ஆபகூக்",
        BibleBook.ZECH to "சகரியா",
        BibleBook.MAL to "மல்கியா",
        BibleBook.PS to "சங்கீதம்,சங்கீதங்கள்",
        BibleBook.PROV to "பழமொழி ஆகமம்",
        BibleBook.ECCL to "பிரசங்கி,சங்கத் திருவுரை ஆகமம்",
        BibleBook.SONG to "உன்னதப்பாட்டு,உன்னத சங்கீதம்",
        BibleBook.ACTS to "அப்போஸ்தலருடைய நடபடிகள்,அப்போஸ்தலர் பணி",
        BibleBook.ROM to "ரோமர்",
        BibleBook.COL to "கொலோசெயர்",
        BibleBook.THESS1 to "1 தெசலோனிக்கேயர்",
        BibleBook.THESS2 to "2 தெசலோனிக்கேயர்",
        BibleBook.TIM1 to "1 தீமோத்தேயு,1 தீமோ",
        BibleBook.TIM2 to "2 தீமோத்தேயு,2 தீமோ",
        BibleBook.PHLM to "பிலேமோன்",
        BibleBook.HEB to "எபிரெயர்",
        BibleBook.PET1 to "1 இராயப்பர்",
        BibleBook.PET2 to "2 இராயப்பர்",
        BibleBook.JOHN1 to "1 அருளப்பர்,1 அரு",
        BibleBook.JOHN2 to "2 அருளப்பர்,2 அரு",
        BibleBook.JOHN3 to "3 அருளப்பர்,3 அரு",
        BibleBook.SIR to "சீராக்கின் ஞானம்",
        BibleBook.WIS to "சாலமோனின் ஞானம்",
        BibleBook.EP_JER to "எரேமியாவின் மடல்",
        BibleBook.BEL to "பேல் தெய்வமும் அரக்கப் பாம்பும்"
    )

    override fun getFullName(book: BibleBook): String? = fullNames[book]
    override fun getShortName(book: BibleBook): String? = shortNames[book]
    override fun getAlternateName(book: BibleBook): String? = alternateNames[book]
}
