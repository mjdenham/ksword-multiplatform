package org.crosswire.ksword.versification.system

import okio.Path.Companion.toPath
import org.crosswire.ksword.book.sword.SwordBookPath
import org.crosswire.ksword.versification.BibleBook
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class VersificationsTest {

    @BeforeTest
    fun setUp() {
        SwordBookPath.swordBookPath = "../testFiles".toPath()
    }

    @Test
    fun getVersificationByName() {
        for (versification in listOf("KJV", "KJVA", "NRSV", "German")) {
            assertEquals(versification, Versifications.getVersification(versification).name)
        }
    }

    @Test
    fun getLastChapterForIntroNT() {
        val versification = Versifications.getVersification("KJV")
        assertEquals(0, versification.getLastChapter(BibleBook.INTRO_NT))
    }

    @Test
    fun getLastVerseForIntroNT() {
        val versification = Versifications.getVersification("KJV")
        assertEquals(0, versification.getLastVerse(BibleBook.INTRO_NT, 0))
    }
}
