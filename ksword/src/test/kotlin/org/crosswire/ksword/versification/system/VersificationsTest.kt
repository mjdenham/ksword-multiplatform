package org.crosswire.ksword.versification.system

import okio.Path.Companion.toPath
import org.crosswire.ksword.book.sword.SwordBookPath
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class VersificationsTest {

    @Before
    fun setUp() {
        SwordBookPath.swordBookPath = "../testFiles".toPath()
    }

    @Test
    fun getVersificationByName() {
        for (versification in listOf("KJV", "KJVA")) {
            assertEquals(versification, Versifications.getVersification(versification).name)
        }
    }
}