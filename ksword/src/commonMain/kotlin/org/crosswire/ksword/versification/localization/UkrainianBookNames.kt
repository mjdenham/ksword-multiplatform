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
 * Ukrainian localized Bible book names.
 * Generated from BibleNames_uk.properties
 */
object UkrainianBookNames : BookNameLocalization {
    private val fullNames = mapOf(
        BibleBook.GEN to "Буття",
        BibleBook.EXOD to "Вихід",
        BibleBook.LEV to "Левит",
        BibleBook.NUM to "Числа",
        BibleBook.DEUT to "Повторення Закону",
        BibleBook.JOSH to "Ісус Навин",
        BibleBook.JUDG to "Судді",
        BibleBook.RUTH to "Рут",
        BibleBook.SAM1 to "1 Самуїла",
        BibleBook.SAM2 to "2 Самуїла",
        BibleBook.KGS1 to "1 Царів",
        BibleBook.KGS2 to "2 Царів",
        BibleBook.CHR1 to "1 Хроніки",
        BibleBook.CHR2 to "2 Хроніки",
        BibleBook.EZRA to "Ездра",
        BibleBook.NEH to "Неемії",
        BibleBook.ESTH to "Естер",
        BibleBook.JOB to "Йова",
        BibleBook.PS to "Псалмів",
        BibleBook.PROV to "Приповістей",
        BibleBook.ECCL to "Екклезіяст",
        BibleBook.SONG to "Пісня над піснями",
        BibleBook.ISA to "Ісаї",
        BibleBook.JER to "Єремії",
        BibleBook.LAM to "Плач Єремії",
        BibleBook.EZEK to "Єзекіїля",
        BibleBook.DAN to "Даниїла",
        BibleBook.HOS to "Осії",
        BibleBook.JOEL to "Йоіла",
        BibleBook.AMOS to "Амоса",
        BibleBook.OBAD to "Овдія",
        BibleBook.JONAH to "Йони",
        BibleBook.MIC to "Михея",
        BibleBook.NAH to "Наума",
        BibleBook.HAB to "Авакума",
        BibleBook.ZEPH to "Софонії",
        BibleBook.HAG to "Огія",
        BibleBook.ZECH to "Захарія",
        BibleBook.MAL to "Малахії",
        BibleBook.MATT to "Матфія",
        BibleBook.MARK to "Марка",
        BibleBook.LUKE to "Луки",
        BibleBook.JOHN to "Івана",
        BibleBook.ACTS to "Дії",
        BibleBook.ROM to "Римлян",
        BibleBook.COR1 to "1 Коринтян",
        BibleBook.COR2 to "2 Коринтян",
        BibleBook.GAL to "Галатів",
        BibleBook.EPH to "Ефесян",
        BibleBook.PHIL to "Филип'ян",
        BibleBook.COL to "Колосян",
        BibleBook.THESS1 to "1 Солунян",
        BibleBook.THESS2 to "2 Солунян",
        BibleBook.TIM1 to "1 Тимофія",
        BibleBook.TIM2 to "2 Тимофія",
        BibleBook.TITUS to "Тита",
        BibleBook.PHLM to "Филимона",
        BibleBook.HEB to "Євреїв",
        BibleBook.JAS to "Якова",
        BibleBook.PET1 to "1 Петра",
        BibleBook.PET2 to "2 Петра",
        BibleBook.JOHN1 to "1 Івана",
        BibleBook.JOHN2 to "2 Івана",
        BibleBook.JOHN3 to "3 Івана",
        BibleBook.JUDE to "Юди",
        BibleBook.REV to "Об'явлення"
    )

    private val shortNames = mapOf(
        BibleBook.GEN to "Бутт",
        BibleBook.EXOD to "Вихі",
        BibleBook.LEV to "Леви",
        BibleBook.NUM to "Числ",
        BibleBook.DEUT to "Повт",
        BibleBook.JOSH to "Ісус",
        BibleBook.JUDG to "Судд",
        BibleBook.RUTH to "Рут",
        BibleBook.SAM1 to "1 Сам",
        BibleBook.SAM2 to "2 Сам",
        BibleBook.KGS1 to "1 Цар",
        BibleBook.KGS2 to "2 Цар",
        BibleBook.CHR1 to "1 Хро",
        BibleBook.CHR2 to "2 Хро",
        BibleBook.EZRA to "Ездр",
        BibleBook.NEH to "Неем",
        BibleBook.ESTH to "Есте",
        BibleBook.JOB to "Йова",
        BibleBook.PS to "Псал",
        BibleBook.PROV to "Прип",
        BibleBook.ECCL to "Еккл",
        BibleBook.SONG to "Пісня",
        BibleBook.ISA to "Іса",
        BibleBook.JER to "Єрем",
        BibleBook.LAM to "Плач",
        BibleBook.EZEK to "Єзек",
        BibleBook.DAN to "Дан",
        BibleBook.HOS to "Осі",
        BibleBook.JOEL to "Йоі",
        BibleBook.AMOS to "Амо",
        BibleBook.OBAD to "Овд",
        BibleBook.JONAH to "Йон",
        BibleBook.MIC to "Мих",
        BibleBook.NAH to "Нау",
        BibleBook.HAB to "Ава",
        BibleBook.ZEPH to "Соф",
        BibleBook.HAG to "Огі",
        BibleBook.ZECH to "Зах",
        BibleBook.MAL to "Мал",
        BibleBook.MATT to "Мат",
        BibleBook.MARK to "Мар",
        BibleBook.LUKE to "Лук",
        BibleBook.JOHN to "Іва",
        BibleBook.ACTS to "Дії",
        BibleBook.ROM to "Рим",
        BibleBook.COR1 to "1 Кор",
        BibleBook.COR2 to "2 Кор",
        BibleBook.GAL to "Гал",
        BibleBook.EPH to "Еф",
        BibleBook.PHIL to "Фили",
        BibleBook.COL to "Кол",
        BibleBook.THESS1 to "1 Со",
        BibleBook.THESS2 to "2 Со",
        BibleBook.TIM1 to "1 Тим",
        BibleBook.TIM2 to "2 Тим",
        BibleBook.TITUS to "Тит",
        BibleBook.PHLM to "Филим",
        BibleBook.HEB to "Євр",
        BibleBook.JAS to "Яко",
        BibleBook.PET1 to "1 Пе",
        BibleBook.PET2 to "2 Пе",
        BibleBook.JOHN1 to "1 Ів",
        BibleBook.JOHN2 to "2 Ів",
        BibleBook.JOHN3 to "3 Ів",
        BibleBook.JUDE to "Юди",
        BibleBook.REV to "Об"
    )

    override fun getFullName(book: BibleBook): String? = fullNames[book]
    override fun getShortName(book: BibleBook): String? = shortNames[book]
    override fun getAlternateName(book: BibleBook): String? = null
}
