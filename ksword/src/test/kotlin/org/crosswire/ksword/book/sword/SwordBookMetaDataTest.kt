package org.crosswire.ksword.book.sword

import org.crosswire.TestConstants
import org.junit.Test
import kotlin.test.assertEquals

class SwordBookMetaDataTest {

    @Test
    fun can_create_sbmb_with_correct_values() {
        val sbmd = SwordBookMetaData.createFromFile(TestConstants.TEST_CONF_FILE_PATH, TestConstants.TEST_LIBRARY_PATH)
        assertEquals("BSB", sbmd.initials)
        assertEquals("Berean Standard Bible", sbmd.name)
        assertEquals("./modules/texts/ztext/bsb/", sbmd.getProperty(SwordBookMetaData.KEY_DATA_PATH))
        assertEquals("BOOK", sbmd.getProperty(SwordBookMetaData.KEY_BLOCK_TYPE))
    }
}