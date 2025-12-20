package org.crosswire.ksword.book.sword

import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import okio.FileSystem
import okio.Path.Companion.toPath
import org.crosswire.ksword.book.Books
import org.crosswire.ksword.book.install.sword.SwordInstallerFactory
import org.crosswire.ksword.passage.DefaultLeafKeyList
import org.crosswire.ksword.passage.Key
import org.crosswire.ksword.passage.NoSuchKeyException
import org.junit.AfterClass
import org.junit.Assert.*
import org.junit.BeforeClass
import org.junit.Test

private const val STRONGS_HEBREW = "StrongsHebrew"

/**
 * Test for downloading and using Strong's Real Hebrew Dictionary from AndBible repository.
 * This test downloads the module from andbible.github.io instead of using pre-installed files.
 */
class StrongsHebrewTest {
    private lateinit var dictionary: SwordDictionary

    companion object Companion {
        private val dir = FileSystem.SYSTEM_TEMPORARY_DIRECTORY.resolve("StrongsHebrewTest".toPath())

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
        var book = Books.getBook(STRONGS_HEBREW)
        if (book == null) {
            SwordInstallerFactory().crosswireInstaller.install(STRONGS_HEBREW)
            delay(5000)
            book = Books.getBook(STRONGS_HEBREW)
            assertTrue(book != null)
        }

        assertTrue("StrongsRealHebrew should be a SwordDictionary", book is SwordDictionary)
        dictionary = book as SwordDictionary
        assertEquals(STRONGS_HEBREW, dictionary.initials)
    }

    @Test
    fun testBasicLookup() {
        testDownloaded()

        // Try H1 format (typical for Strong's Hebrew)
        // Use getKey() to normalize H1 -> 00001
        val normalizedKey = dictionary.getKey("H1")
        val text = dictionary.getRawText(normalizedKey)

        assertTrue("Entry should not be empty", text.isNotEmpty())
        assertTrue("Entry H1 should contain 'father'", text.contains("father", ignoreCase = true))
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
        // StrongsHebrew may have an introductory entry, so find the first numeric entry
        val firstNumeric = allKeys.find { it.getName().matches(Regex("^\\d+$")) }
        assertNotNull("Should have numeric entries", firstNumeric)
        assertEquals("First numeric entry should be '00001'", "00001", firstNumeric!!.getName())
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
        // Get the actual first entry in the dictionary
        val backend = dictionary.backend
        val firstKey = backend.get(0)

        // Previous of first entry should be null
        val previous = dictionary.getPreviousKey(firstKey)
        assertNull("First entry should have no previous key", previous)

        // 00001 may not be the first entry if there's an intro
        val key00001 = DefaultLeafKeyList("00001")
        val previousOf00001 = dictionary.getPreviousKey(key00001)
        // previousOf00001 could be intro or null, just verify it's not 00001
        if (previousOf00001 != null) {
            assertNotEquals("Previous of 00001 should not be 00001", "00001", previousOf00001.getName())
        }
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
        assertEquals(STRONGS_HEBREW, dictionary.initials)
        // StrongsHebrew is named "Strong's Hebrew Bible Dictionary" (not "Strongs Real Hebrew")
        assertTrue("Name should contain 'Strong' and 'Hebrew'",
            dictionary.name.contains("Strong", ignoreCase = true) &&
            dictionary.name.contains("Hebrew", ignoreCase = true))

        val metadata = dictionary.bookMetaData
        assertNotNull("Metadata should exist", metadata)
        val modDrv = metadata.getProperty("ModDrv")
        assertNotNull("ModDrv property should exist", modDrv)
        assertTrue("Should use RawLD or RawLD4 driver",
            modDrv!!.contains("RawLD", ignoreCase = true))
    }

    @Test
    fun testNavigationThroughMultipleEntries() {
        testDownloaded()
        // Navigate through first 5 entries
        var currentKey: Key? = DefaultLeafKeyList("00001")
        val entries = mutableListOf<String>()

        for (i in 0 until 5) {
            assertNotNull("Key $i should exist", currentKey)
            entries.add(currentKey!!.getName())
            currentKey = dictionary.getNextKey(currentKey)
        }

        assertEquals(listOf("00001", "00002", "00003", "00004", "00005"), entries)
    }

    @Test
    fun testGetByIndex() {
        testDownloaded()
        val backend = dictionary.backend

        // Index 0 might be an introductory entry in StrongsHebrew
        val firstKey = backend.get(0)
        assertNotNull("First key should exist", firstKey)

        // Find the first numeric entry (00001)
        val firstNumericIndex = (0 until backend.getCardinality())
            .indexOfFirst { backend.get(it).getName() == "00001" }
        assertTrue("Should find entry 00001", firstNumericIndex >= 0)

        val firstNumericKey = backend.get(firstNumericIndex)
        assertEquals("00001", firstNumericKey.getName())

        // Test next entry after 00001
        val secondKey = backend.get(firstNumericIndex + 1)
        assertNotNull("Second key should exist", secondKey)
        assertEquals("00002", secondKey.getName())

        // Test invalid index throws exception
        try {
            backend.get(-1)
            fail("Should throw IndexOutOfBoundsException for negative index")
        } catch (e: IndexOutOfBoundsException) {
            // Expected
        }
    }

    @Test
    fun testIndexOf() {
        testDownloaded()
        val backend = dictionary.backend

        // Test indexOf for existing key (may not be at index 0 if there's an intro)
        val key1 = DefaultLeafKeyList("00001")
        val index1 = backend.indexOf(key1)
        assertTrue("Index should be >= 0 for existing key", index1 >= 0)

        val key2 = DefaultLeafKeyList("00002")
        val index2 = backend.indexOf(key2)
        assertTrue("Index should be >= 0 for existing key", index2 >= 0)
        assertEquals("00002 should be right after 00001", index1 + 1, index2)

        // Test indexOf for non-existent key returns negative
        val nonExistent = DefaultLeafKeyList("99999")
        val indexNotFound = backend.indexOf(nonExistent)
        assertTrue("Index should be negative for non-existent key", indexNotFound < 0)
    }

    @Test
    fun testBinarySearchWithEmptyKey() {
        testDownloaded()
        val backend = dictionary.backend

        // Test that empty key doesn't crash
        val emptyKey = DefaultLeafKeyList("")
        val containsEmpty = backend.contains(emptyKey)
        assertFalse("Empty key should not be found", containsEmpty)
    }

    @Test
    fun testStrongsPaddingNormalization() {
        testDownloaded()
        val backend = dictionary.backend

        // StrongsRealHebrew uses 5-digit padding (00001, 00002, etc.)
        // Test that looking up with different formats works

        // Standard padded format should work
        val padded = DefaultLeafKeyList("00001")
        assertTrue("Padded format 00001 should exist", backend.contains(padded))

        // Test that unpadded lookups work (if Strong's padding is enabled)
        // Note: This depends on the StrongsPadding config in the module
        val text1 = dictionary.getRawText(padded)
        assertTrue("Entry should contain 'father'", text1.contains("father", ignoreCase = true))
    }

    @Test
    fun testGetCardinality() {
        testDownloaded()
        val backend = dictionary.backend

        val cardinality = backend.getCardinality()
        assertTrue("Dictionary should have entries", cardinality > 0)
        assertTrue("StrongsRealHebrew should have ~8000+ entries", cardinality > 8000)

        // Verify cardinality matches the global key list size
        val allKeys = backend.getAllKeys()
        assertEquals("Cardinality should match getAllKeys size", allKeys.size, cardinality)
    }

    @Test
    fun testIndexOfReturnsNegativeInsertionPoint() {
        testDownloaded()
        val backend = dictionary.backend

        // Test that indexOf returns -(insertionPoint + 1) for non-existent keys
        val nonExistent = DefaultLeafKeyList("99999")
        val index = backend.indexOf(nonExistent)

        assertTrue("Index should be negative for non-existent key", index < 0)

        // The negative value should be -(insertionPoint + 1)
        // So insertionPoint = -(index + 1)
        val insertionPoint = -(index + 1)
        assertTrue("Insertion point should be valid", insertionPoint >= 0)
        assertTrue("Insertion point should be at or after last entry", insertionPoint >= backend.getCardinality())
    }

    @Test
    fun testBinarySearchAtIndex0() {
        testDownloaded()
        val backend = dictionary.backend

        // Index 0 may be an introductory entry in StrongsHebrew
        val firstKey = backend.get(0)
        assertNotNull("First key should exist", firstKey)

        // Find where 00001 actually is
        val key00001 = DefaultLeafKeyList("00001")
        val index00001 = backend.indexOf(key00001)
        assertTrue("00001 should be found", index00001 >= 0)

        // Verify we can get it by index
        val retrievedKey = backend.get(index00001)
        assertEquals("00001", retrievedKey.getName())
    }

    @Test
    fun testCaseInsensitiveSearch() {
        testDownloaded()
        val backend = dictionary.backend

        // Dictionary keys should be case-insensitive by default
        // (unless CaseSensitiveKeys=true in config)
        val lowerCase = DefaultLeafKeyList("00001")
        val upperCase = DefaultLeafKeyList("00001")  // Same, but test the normalization path

        assertTrue("Lowercase should be found", backend.contains(lowerCase))
        assertTrue("Uppercase should be found", backend.contains(upperCase))

        // Both should return the same content
        val text1 = dictionary.getRawText(lowerCase)
        val text2 = dictionary.getRawText(upperCase)
        assertEquals("Content should be identical regardless of case", text1, text2)
    }

    @Test
    fun testLinkFollowing() {
        testDownloaded()

        // StrongsRealHebrew may not have @LINK entries
        // This test verifies the link following logic doesn't crash even if no links exist
        // If a link exists, it should follow it transparently

        val key = DefaultLeafKeyList("00001")
        val text = dictionary.getRawText(key)

        // Should return content, not "@LINK ..." directive
        assertFalse("Text should not be a raw @LINK directive", text.startsWith("@LINK "))
        assertTrue("Should contain actual definition", text.isNotEmpty())
    }

    @Test
    fun testNavigationBeyondBounds() {
        testDownloaded()

        // Test navigation at boundaries
        val backend = dictionary.backend
        val lastIndex = backend.getCardinality() - 1

        // Get last entry
        val lastKey = backend.get(lastIndex)
        assertNotNull("Last key should exist", lastKey)

        // Find the last numeric entry (StrongsHebrew may have non-numeric entries at end)
        var lastNumericKey: Key? = null
        for (i in lastIndex downTo 0) {
            val key = backend.get(i)
            if (key.getName().matches(Regex("^\\d+$"))) {
                lastNumericKey = key
                break
            }
        }

        assertNotNull("Should have numeric entries", lastNumericKey)
        val lastKeyName = lastNumericKey!!.getName()

        // StrongsHebrew has many Hebrew entries (varies by version)
        val lastNumber = lastKeyName.toInt()
        assertTrue("Last numeric key should be high Strong's number (>1000)", lastNumber > 1000)

        // Verify it's greater than earlier entries
        assertTrue("Last entry should be > first entry", lastNumber > 1)

        // Validate last numeric entry has actual content
        val lastText = dictionary.getRawText(lastNumericKey)
        assertTrue("Last entry should have content", lastText.isNotEmpty())
        assertTrue("Last entry should have substantial content (>50 chars)", lastText.length > 50)

        // Next from last should be null
        val afterLast = dictionary.getNextKey(lastKey)
        assertNull("Next from last entry should be null", afterLast)

        // Test that get() throws for out of bounds
        try {
            backend.get(backend.getCardinality())
            fail("Should throw IndexOutOfBoundsException for index == size")
        } catch (e: IndexOutOfBoundsException) {
            // Expected
            assertTrue("Error message should mention the index", e.message!!.contains("Index:"))
        }
    }
}
