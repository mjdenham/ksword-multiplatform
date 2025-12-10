package org.crosswire.ksword.book.sword

import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import okio.FileSystem
import okio.Path.Companion.toPath
import org.crosswire.ksword.book.Books
import org.crosswire.ksword.book.install.sword.SwordInstallerFactory
import org.crosswire.ksword.passage.DefaultLeafKeyList
import org.crosswire.ksword.passage.NoSuchKeyException
import org.junit.AfterClass
import org.junit.Assert.*
import org.junit.BeforeClass
import org.junit.Test

/**
 * Test for downloading and using Strong's Real Hebrew Dictionary from AndBible repository.
 * This test downloads the module from andbible.github.io instead of using pre-installed files.
 */
class StrongsRealHebrewTest {
    private lateinit var dictionary: SwordDictionary

    companion object Companion {
        private val dir = FileSystem.SYSTEM_TEMPORARY_DIRECTORY.resolve("StrongsRealHebrewTest".toPath())

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
    }

    @Test
    fun testDownloaded() = runTest {
        var book = Books.getBook("StrongsRealHebrew")
        if (book == null) {
            SwordInstallerFactory().andBibleInstaller.install("StrongsRealHebrew")
            delay(5000)
            book = Books.getBook("StrongsRealHebrew")
            assertTrue(book != null)
        }

        assertTrue("StrongsRealHebrew should be a SwordDictionary", book is SwordDictionary)
        dictionary = book as SwordDictionary
        assertEquals("StrongsRealHebrew", dictionary.initials)
    }

    @Test
    fun testBasicLookup() {
        testDownloaded()
        val key = DefaultLeafKeyList("00001")
        val text = dictionary.getRawText(key)

        assertTrue("Entry should not be empty", text.isNotEmpty())
        assertTrue("Entry 00001 should contain 'father'", text.contains("father", ignoreCase = true))
        assertTrue("Entry should contain Hebrew transliteration 'awb'", text.contains("awb", ignoreCase = true))
    }

    @Test
    fun testMultipleEntries() {
        testDownloaded()
        val testCases = listOf(
            "00001" to "father",
            "00002" to "father",  // Chaldee
            "00003" to "green",
            "00006" to "perish"
        )

        for ((strongsNum, expectedWord) in testCases) {
            val key = DefaultLeafKeyList(strongsNum)
            val text = dictionary.getRawText(key)
            assertTrue(
                "Entry $strongsNum should contain '$expectedWord'",
                text.contains(expectedWord, ignoreCase = true)
            )
        }
    }

    @Test
    fun testStrongsNumberNormalization() {
        testDownloaded()
        // Test that the dictionary keys are stored as "00001", "00002", etc.
        val key1 = DefaultLeafKeyList("00001")
        assertTrue("Entry 00001 should exist", dictionary.contains(key1))

        val text1 = dictionary.getRawText(key1)
        assertTrue("Entry should not be empty", text1.isNotEmpty())
    }

    @Test
    fun testGetKey() {
        testDownloaded()
        // Test getKey() with existing entry
        val key = dictionary.getKey("00001")
        assertNotNull("getKey should return a key", key)
        assertEquals("00001", key.getName())
    }

    @Test
    fun testGetKeyThrowsOnInvalidEntry() {
        testDownloaded()
        // Test getKey() with non-existent entry
        try {
            dictionary.getKey("99999")
            fail("Should throw NoSuchKeyException for invalid key")
        } catch (e: NoSuchKeyException) {
            // Expected
            assertTrue(e.message!!.contains("99999"))
        }
    }

    @Test
    fun testGetValidKey() {
        testDownloaded()
        // Test getValidKey() with existing entry
        val validKey = dictionary.getValidKey("00001")
        assertNotNull("getValidKey should return a key", validKey)
        assertEquals("00001", validKey.getName())

        // Test getValidKey() with non-existent entry (should return empty key)
        val invalidKey = dictionary.getValidKey("99999")
        assertNotNull("getValidKey should not throw", invalidKey)
        assertEquals("", invalidKey.getName())
    }

    @Test
    fun testCreateEmptyKeyList() {
        testDownloaded()
        val emptyKey = dictionary.createEmptyKeyList()
        assertNotNull("createEmptyKeyList should return a key", emptyKey)
        assertEquals("", emptyKey.getName())
    }

    @Test
    fun testContains() {
        testDownloaded()
        val existingKey = DefaultLeafKeyList("00001")
        assertTrue("Dictionary should contain entry 00001", dictionary.contains(existingKey))

        val nonExistentKey = DefaultLeafKeyList("99999")
        assertFalse("Dictionary should not contain entry 99999", dictionary.contains(nonExistentKey))
    }

    @Test
    fun testGetGlobalKeyList() {
        testDownloaded()
        val allKeys = dictionary.getGlobalKeyList()
        assertNotNull("getGlobalKeyList should return keys", allKeys)
        assertTrue("Dictionary should have entries", allKeys.isNotEmpty())
        assertTrue("Should have many entries (>1000)", allKeys.size > 1000)
        assertEquals("First entry should be '00001'", "00001", allKeys[0].getName())
    }

    @Test
    fun testNextKeyNavigation() {
        testDownloaded()
        val key1 = DefaultLeafKeyList("00001")
        val key2 = dictionary.getNextKey(key1)

        assertNotNull("Next key should exist", key2)
        assertEquals("00002", key2!!.getName())
    }

    @Test
    fun testPreviousKeyNavigation() {
        testDownloaded()
        val key2 = DefaultLeafKeyList("00002")
        val key1 = dictionary.getPreviousKey(key2)

        assertNotNull("Previous key should exist", key1)
        assertEquals("00001", key1!!.getName())
    }

    @Test
    fun testPreviousKeyAtBeginning() {
        testDownloaded()
        // First entry should have no previous
        val key1 = DefaultLeafKeyList("00001")
        val previous = dictionary.getPreviousKey(key1)

        assertNull("First entry should have no previous key", previous)
    }

    @Test
    fun testReadToOsis() {
        testDownloaded()
        val key = DefaultLeafKeyList("00001")
        val osisContent = dictionary.readToOsis(key)

        assertNotNull("OSIS content should be returned", osisContent)
        assertTrue("Should have at least one KeyText entry", osisContent.isNotEmpty())
        assertEquals("Key should match", "00001", osisContent[0].key.getName())
        assertTrue("Text should contain 'father'", osisContent[0].text.contains("father", ignoreCase = true))
    }

    @Test
    fun testDictionaryMetadata() {
        testDownloaded()
        assertEquals("StrongsRealHebrew", dictionary.initials)
        assertEquals("Strongs Real Hebrew Bible Dictionary", dictionary.name)

        val metadata = dictionary.bookMetaData
        assertNotNull("Metadata should exist", metadata)
        val modDrv = metadata.getProperty("ModDrv")
        assertNotNull("ModDrv property should exist", modDrv)
        assertTrue("Should use RawLD4 driver", modDrv!!.contains("RawLD4", ignoreCase = true))
    }

    @Test
    fun testNavigationThroughMultipleEntries() {
        testDownloaded()
        // Navigate through first 5 entries
        var currentKey: org.crosswire.ksword.passage.Key? = DefaultLeafKeyList("00001")
        val entries = mutableListOf<String>()

        for (i in 0 until 5) {
            assertNotNull("Key $i should exist", currentKey)
            entries.add(currentKey!!.getName())
            currentKey = dictionary.getNextKey(currentKey)
        }

        assertEquals(listOf("00001", "00002", "00003", "00004", "00005"), entries)
    }
}
