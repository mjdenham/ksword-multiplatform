package org.crosswire.ksword.book.sword

import okio.Path.Companion.toPath
import org.crosswire.TestConstants
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class SwordBookMetaDataTest {

    @Before
    fun setUp() {
        SwordBookPath.swordBookPath = "../testFiles".toPath()
    }

    @Test
    fun can_create_sbmb_with_correct_values() {
        val sbmd = SwordBookMetaData.createFromFile(TestConstants.TEST_CONF_FILE_PATH, TestConstants.TEST_LIBRARY_PATH)
        assertEquals("BSBTEST", sbmd.initials)
        assertEquals("Berean Standard Bible", sbmd.name)
        assertEquals("./modules/texts/ztext/bsb/", sbmd.getProperty(SwordBookMetaData.KEY_DATA_PATH))
        assertEquals("BOOK", sbmd.getProperty(SwordBookMetaData.KEY_BLOCK_TYPE))
    }
}