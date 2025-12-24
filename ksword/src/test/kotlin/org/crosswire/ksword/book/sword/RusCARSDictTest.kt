package org.crosswire.ksword.book.sword

import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import okio.FileSystem
import okio.Path.Companion.toPath
import org.crosswire.ksword.book.Books
import org.crosswire.ksword.book.install.sword.SwordInstallerFactory
import org.junit.AfterClass
import org.junit.Assert.*
import org.junit.BeforeClass
import org.junit.Test

/**
 * Integration test for RusCARSDict (Russian CARS Dictionary).
 * Downloads the dictionary from Crosswire repository before running tests.
 *
 * This tests the RawLD4 backend with:
 * - Cyrillic (Russian) characters
 * - UTF-8 encoding
 * - Case-sensitive keys
 * - Mixed line terminators (CRLF and LF)
 */
class RusCARSDictTest {

    companion object {
        private val dir = FileSystem.SYSTEM_TEMPORARY_DIRECTORY.resolve("RusCARSDictTest".toPath())

        @JvmStatic
        @BeforeClass
        fun setUp() {
            SwordBookPath.swordBookPath = dir
            FileSystem.SYSTEM.createDirectories(SwordBookPath.swordBookPath)
            Books.refresh()
        }

        @JvmStatic
        @AfterClass
        fun tearDown() {
            FileSystem.SYSTEM.deleteRecursively(SwordBookPath.swordBookPath)
            SwordBookPath.swordBookPath = "../testFiles".toPath()
        }

        /**
         * Helper function to check if text contains Cyrillic characters.
         * Russian text should contain Cyrillic characters.
         */
        private fun containsCyrillic(text: String): Boolean {
            return text.any { it in '\u0400'..'\u04FF' }
        }
    }

    @Test
    fun testDictionaryDownloaded() = runTest {
        var dictionary = Books.getBook("RusCARSDict")
        if (dictionary == null) {
            SwordInstallerFactory().crosswireInstaller.install("RusCARSDict")
            dictionary = Books.getBook("RusCARSDict")
            assertNotNull("RusCARSDict module should be installed", dictionary)
        }

        assertNotNull("RusCARSDict should be available", dictionary)
        assertTrue("RusCARSDict should be a SwordDictionary", dictionary is SwordDictionary)
    }

    @Test
    fun testBasicLookup() = runTest {
        testDictionaryDownloaded()
        val dictionary = Books.getBook("RusCARSDict") as? SwordDictionary
        assertNotNull(dictionary)

        // Test lookup of first entry "АД" (Hell)
        val key = dictionary!!.getKey("АД")
        val text = dictionary.getRawText(key)
        assertTrue("Entry should not be empty", text.isNotEmpty())
        assertTrue("Entry should contain Cyrillic text", containsCyrillic(text))
        assertTrue("Entry should contain 'греч.' (Greek)", text.contains("греч."))
    }

    @Test
    fun testMultipleEntries() = runTest {
        testDictionaryDownloaded()
        val dictionary = Books.getBook("RusCARSDict") as? SwordDictionary
        assertNotNull(dictionary)

        // Test several entries
        val entries = listOf("АД", "АДАМ", "АНГЕЛ", "АРАБЫ")

        for (entry in entries) {
            val key = dictionary!!.getKey(entry)
            val text = dictionary.getRawText(key)
            assertTrue("Should find entry for '$entry'", text.isNotEmpty())
            assertTrue("Entry '$entry' should contain Cyrillic text", containsCyrillic(text))
        }
    }

    @Test
    fun testAdamEntry() = runTest {
        testDictionaryDownloaded()
        val dictionary = Books.getBook("RusCARSDict") as? SwordDictionary
        assertNotNull(dictionary)

        // Test АДАМ (Adam) - should contain reference to Eve and Eden
        val key = dictionary!!.getKey("АДАМ")
        val text = dictionary.getRawText(key)
        assertTrue("АДАМ entry should not be empty", text.isNotEmpty())
        assertTrue("АДАМ entry should contain 'человек' (man)", text.contains("человек"))
        assertTrue("АДАМ entry should contain 'Евой' (Eve)", text.contains("Евой"))
        assertTrue("АДАМ entry should contain 'Эдемском' (Eden)", text.contains("Эдемском"))
    }

    @Test
    fun testContains() = runTest {
        testDictionaryDownloaded()
        val dictionary = Books.getBook("RusCARSDict") as? SwordDictionary
        assertNotNull(dictionary)

        // Test that known entries exist
        assertTrue("Should contain 'АД'", dictionary!!.contains(dictionary.getValidKey("АД")))
        assertTrue("Should contain 'АДАМ'", dictionary.contains(dictionary.getValidKey("АДАМ")))
        assertTrue("Should contain 'АНГЕЛ'", dictionary.contains(dictionary.getValidKey("АНГЕЛ")))

        // Test that non-existent entry doesn't exist
        assertFalse("Should not contain 'NONEXISTENT'", dictionary.contains(dictionary.getValidKey("NONEXISTENT")))
    }

    @Test
    fun testGetGlobalKeyList() = runTest {
        testDictionaryDownloaded()
        val dictionary = Books.getBook("RusCARSDict") as? SwordDictionary
        assertNotNull(dictionary)

        val allKeys = dictionary!!.getGlobalKeyList()
        assertNotNull("getGlobalKeyList should return keys", allKeys)
        assertTrue("Dictionary should have entries", allKeys.isNotEmpty())

        // Should have 258 entries
        assertEquals("Should have 258 entries", 258, allKeys.size)

        // Check that first entry is "АД"
        assertEquals("First entry should be 'АД'", "АД", allKeys[0].getName())
    }

    @Test
    fun testKeyNavigation() = runTest {
        testDictionaryDownloaded()
        val dictionary = Books.getBook("RusCARSDict") as? SwordDictionary
        assertNotNull(dictionary)

        // Get a key
        val startKey = dictionary!!.getKey("АДАМ")
        assertNotNull("Should find key 'АДАМ'", startKey)
        assertEquals("Key name should be 'АДАМ'", "АДАМ", startKey.getName())

        // Get next key
        val nextKey = dictionary.getNextKey(startKey)
        assertNotNull("Should have next key after 'АДАМ'", nextKey)
        assertEquals("Next key should be 'АЖАР'", "АЖАР", nextKey?.getName())

        // Get previous key
        val prevKey = dictionary.getPreviousKey(nextKey!!)
        assertNotNull("Should have previous key before 'АЖАР'", prevKey)
        assertEquals("Previous key should be 'АДАМ'", "АДАМ", prevKey?.getName())
    }

    @Test
    fun testDictionaryMetadata() = runTest {
        testDictionaryDownloaded()
        val dictionary = Books.getBook("RusCARSDict") as? SwordDictionary
        assertNotNull(dictionary)

        // Check metadata
        assertEquals("Should be RawLD4 driver", "RawLD4", dictionary!!.bookMetaData.getProperty("ModDrv"))
        assertEquals("Should be UTF-8 encoding", "UTF-8", dictionary.bookMetaData.getProperty("Encoding"))
        assertEquals("Should be Russian language", "ru", dictionary.bookMetaData.getProperty("Lang"))
    }

    @Test
    fun testEntriesWithParentheses() = runTest {
        testDictionaryDownloaded()
        val dictionary = Books.getBook("RusCARSDict") as? SwordDictionary
        assertNotNull(dictionary)

        // Test entries that have parentheses in their names
        val key1 = dictionary!!.getKey("АМАЛИК (АМАЛИКИТЯНЕ)")
        val text1 = dictionary.getRawText(key1)
        assertTrue("Should find 'АМАЛИК (АМАЛИКИТЯНЕ)'", text1.isNotEmpty())
        assertTrue("Entry should contain Cyrillic text", containsCyrillic(text1))

        val key2 = dictionary.getKey("АММОН (АММОНИТЯНЕ)")
        val text2 = dictionary.getRawText(key2)
        assertTrue("Should find 'АММОН (АММОНИТЯНЕ)'", text2.isNotEmpty())
        assertTrue("Entry should contain Cyrillic text", containsCyrillic(text2))
    }
}
