package org.crosswire.ksword.book.sword

import okio.Buffer
import okio.Path.Companion.toPath
import org.crosswire.TestConstants
import org.crosswire.ksword.book.BookCategory
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SwordBookMetaDataTest {

    @BeforeTest
    fun setUp() {
        SwordBookPath.swordBookPath = "../testFiles".toPath()
    }

    private fun fromConf(body: String) =
        SwordBookMetaData.createFromSource(Buffer().also { it.writeUtf8(body.trimIndent()) })

    @Test
    fun can_create_sbmb_with_correct_values() {
        val sbmd = SwordBookMetaData.createFromFile(TestConstants.TEST_CONF_FILE_PATH, TestConstants.TEST_LIBRARY_PATH)
        assertEquals("BSBTEST", sbmd.initials)
        assertEquals("Berean Standard Bible", sbmd.name)
        assertEquals("./modules/texts/ztext/bsb/", sbmd.getProperty(SwordBookMetaData.KEY_DATA_PATH))
        assertEquals("BOOK", sbmd.getProperty(SwordBookMetaData.KEY_BLOCK_TYPE))
    }

    @Test
    fun unenciphered_module_is_not_locked_and_is_supported() {
        val sbmd = fromConf("[T]\nModDrv=zText\nSourceType=OSIS")
        assertFalse(sbmd.isEnciphered)
        assertFalse(sbmd.isLocked)
        assertTrue(sbmd.isSupported)
    }

    @Test
    fun empty_cipher_key_is_locked_and_unsupported() {
        val sbmd = fromConf("[T]\nModDrv=zText\nSourceType=OSIS\nCipherKey=")
        assertTrue(sbmd.isEnciphered)
        assertTrue(sbmd.isLocked)
        assertFalse(sbmd.isSupported)
    }

    @Test
    fun present_cipher_key_is_enciphered_but_not_locked() {
        val sbmd = fromConf("[T]\nModDrv=zText\nSourceType=OSIS\nCipherKey=abc123")
        assertTrue(sbmd.isEnciphered)
        assertFalse(sbmd.isLocked)
        assertTrue(sbmd.isSupported)
    }

    @Test
    fun unlock_sets_the_key_and_clears_locked() {
        val sbmd = fromConf("[T]\nModDrv=zText\nSourceType=OSIS\nCipherKey=")
        assertTrue(sbmd.unlock("abc123"))
        assertFalse(sbmd.isLocked)
        assertEquals("abc123", sbmd.unlockKey)
    }

    @Test
    fun repeated_key_returns_every_value() {
        val sbmd = fromConf("[ISV]\nModDrv=zText\nGlobalOptionFilter=ThMLFootnotes\nGlobalOptionFilter=ThMLScripref")
        assertEquals("ThMLFootnotes", sbmd.getProperty(SwordBookMetaData.KEY_GLOBAL_OPTION_FILTER))
        assertEquals(listOf("ThMLFootnotes", "ThMLScripref"), sbmd.getValues(SwordBookMetaData.KEY_GLOBAL_OPTION_FILTER))
        assertEquals(emptyList(), sbmd.getValues("Feature"))
    }

    @Test
    fun general_book_is_supported() {
        val sbmd = fromConf("[Pilgrim]\nModDrv=RawGenBook\nSourceType=ThML")
        assertTrue(sbmd.isSupported)
        assertEquals(BookCategory.GENERAL_BOOK, sbmd.bookCategory)
    }

    @Test
    fun and_bible_addon_is_unsupported() {
        val sbmd = fromConf("[FontPack]\nModDrv=RawGenBook\nCategory=And Bible\nDataPath=./modules/texts/ztext/FontPack/")
        assertFalse(sbmd.isSupported)
    }

    @Test
    fun sorts_by_category_then_abbreviation_ignoring_case() {
        val dictionary = fromConf("[AAA]\nModDrv=RawLD")
        val kjv = fromConf("[KJV]\nModDrv=zText\nAbbreviation=kjv")
        val bsb = fromConf("[BSB]\nModDrv=zText")
        val esv = fromConf("[ESV2011]\nModDrv=zText\nAbbreviation=ESV")
        assertEquals(listOf(bsb, esv, kjv, dictionary), listOf(dictionary, kjv, esv, bsb).sorted())
    }

    @Test
    fun property_keys_are_the_conf_keys() {
        val sbmd = fromConf("[T]\nModDrv=zText\nSourceType=OSIS")
        assertTrue(sbmd.propertyKeys.containsAll(listOf("ModDrv", "SourceType")))
    }
}
