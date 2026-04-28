package org.crosswire.ksword.book.sword

import kotlinx.coroutines.test.runTest
import okio.FileSystem
import okio.Path.Companion.toPath
import org.crosswire.ksword.book.Books
import org.crosswire.ksword.book.install.sword.SwordInstallerFactory
import org.crosswire.ksword.passage.DefaultLeafKeyList
import org.junit.AfterClass
import org.junit.BeforeClass
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

private const val ISBE = "ISBE"

/**
 * Integration test for the International Standard Bible Encyclopedia (ISBE).
 *
 * ISBE is a `zLD` module (compressed lexicon dictionary) with `CompressType=ZIP`,
 * UTF-8 TEI content, no cipher. Exercises the ZLDBackend end-to-end:
 * `.idx`/`.dat` key index → block reference → `.zdx` lookup → `.zdt` decompression
 * → in-block entry extraction.
 */
class IsbeTest {

    companion object {
        private val dir = FileSystem.SYSTEM_TEMPORARY_DIRECTORY.resolve("IsbeTest".toPath())

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

    private suspend fun loadDictionary(): SwordDictionary {
        var book = Books.getBook(ISBE)
        if (book == null) {
            SwordInstallerFactory().crosswireInstaller.install(ISBE)
            book = Books.getBook(ISBE)
            assertNotNull(book, "ISBE should install from the CrossWire repository")
        }
        assertTrue(book is SwordDictionary, "ISBE should be a SwordDictionary")
        return book
    }

    @Test
    fun testDownloaded() = runTest {
        val dictionary = loadDictionary()
        assertEquals(ISBE, dictionary.initials)
        assertEquals("zLD", dictionary.bookMetaData.getProperty("ModDrv"))
        assertEquals("ZIP", dictionary.bookMetaData.getProperty("CompressType"))
    }

    @Test
    fun testBasicLookup() = runTest {
        val dictionary = loadDictionary()
        val key = dictionary.getKey("Aaron")
        val text = dictionary.getRawText(key)

        assertTrue(text.isNotEmpty(), "Aaron entry should not be empty")
        assertTrue(
            text.length > 100 || text.contains("Moses", ignoreCase = true),
            "Aaron entry should mention Moses or be substantial (>100 chars)"
        )
    }

    @Test
    fun testMultipleEntries() = runTest {
        val dictionary = loadDictionary()
        val terms = listOf("Aaron", "Abraham", "David", "Moses", "Jerusalem")

        for (term in terms) {
            val key = dictionary.getKey(term)
            val text = dictionary.getRawText(key)
            assertTrue(text.isNotEmpty(), "Entry '$term' should have content")
            assertTrue(text.length > 50, "Entry '$term' should be substantial (>50 chars)")
        }
    }

    @Test
    fun testContains() = runTest {
        val dictionary = loadDictionary()
        assertTrue(dictionary.contains(DefaultLeafKeyList("Aaron")), "Should contain 'Aaron'")
        assertFalse(
            dictionary.contains(DefaultLeafKeyList("ZZZNonexistent")),
            "Should not contain 'ZZZNonexistent'"
        )
    }

    @Test
    fun testGetCardinality() = runTest {
        val dictionary = loadDictionary()
        val backend = dictionary.backend as ZLDBackend
        val cardinality = backend.getCardinality()
        assertTrue(cardinality > 1000, "ISBE should have many entries (>1000)")
    }

    @Test
    fun testGetGlobalKeyList() = runTest {
        val dictionary = loadDictionary()
        val allKeys = dictionary.getGlobalKeyList()
        assertNotNull(allKeys)
        assertTrue(allKeys.size > 1000, "ISBE should have many entries (>1000)")
        assertTrue(
            allKeys.any { it.getName().equals("Aaron", ignoreCase = true) },
            "Should contain a key matching 'Aaron' (case-insensitive)"
        )
    }

    @Test
    fun testNextKeyNavigation() = runTest {
        val dictionary = loadDictionary()
        val aaron = dictionary.getKey("Aaron")
        val next = dictionary.getNextKey(aaron)
        assertNotNull(next, "Aaron should have a next key")
        assertNotEquals("Aaron", next.getName(), "Next key should differ from Aaron")
    }

    @Test
    fun testPreviousKeyNavigation() = runTest {
        val dictionary = loadDictionary()
        val abraham = dictionary.getKey("Abraham")
        val prev = dictionary.getPreviousKey(abraham)
        assertNotNull(prev, "Abraham should have a previous key")
        assertNotEquals("Abraham", prev.getName(), "Previous key should differ from Abraham")
    }

    @Test
    fun testBlockCacheReuseAcrossLookups() = runTest {
        val dictionary = loadDictionary()
        // Same key twice — second lookup should hit the lastUncompressed cache.
        // We can't directly observe the cache, but we can confirm content is consistent.
        val key = dictionary.getKey("Aaron")
        val text1 = dictionary.getRawText(key)
        val text2 = dictionary.getRawText(key)
        assertEquals(text1, text2, "Repeated lookup should return identical content")
    }

    @Test
    fun testReadToOsis() = runTest {
        val dictionary = loadDictionary()
        val key = dictionary.getKey("Aaron")
        val osisContent = dictionary.readToOsis(key)
        println(osisContent)
        assertNotNull(osisContent)
        assertTrue(osisContent.isNotEmpty(), "readToOsis should return at least one entry")
        assertEquals("Aaron", osisContent[0].key.getName())
        assertTrue(osisContent[0].text.isNotEmpty(), "readToOsis text should be non-empty")
    }
}
