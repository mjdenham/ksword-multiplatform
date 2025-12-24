package org.crosswire.ksword.book.sword

import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import okio.FileSystem
import okio.Path.Companion.toPath
import org.crosswire.ksword.book.BookMetaData
import org.crosswire.ksword.book.Books
import org.crosswire.ksword.book.install.sword.SwordInstallerFactory
import org.crosswire.ksword.book.sword.state.ZVerseBackendState
import org.crosswire.ksword.passage.KeyText
import org.crosswire.ksword.passage.Verse
import org.crosswire.ksword.passage.VerseRange
import org.crosswire.ksword.versification.BibleBook
import org.crosswire.ksword.versification.system.Versifications
import org.junit.AfterClass
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.BeforeClass
import org.junit.Test
import kotlin.test.assertContains
import kotlin.test.assertNotNull
import kotlin.time.measureTime

class ZVerseBackendTest {
    private lateinit var bookMetaData: SwordBookMetaData
    private lateinit var backendState: ZVerseBackendState
    private lateinit var backend: ZVerseBackend

    companion object {
        private const val MODULE_NAME = "BSB"
        private val dir = FileSystem.SYSTEM_TEMPORARY_DIRECTORY.resolve("ZVerseBackendTest".toPath())

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
    fun readRawContent_readFirstVerse() {
        testDownloaded()
        val v11nName = bookMetaData.getProperty(BookMetaData.KEY_VERSIFICATION) ?: Versifications.DEFAULT_V11N
        val v11n = Versifications.getVersification(v11nName)
        val result = backend.readRawContent(backendState, Verse(v11n, BibleBook.GEN, 1, 1))
        println(result)
        kotlin.test.assertEquals(
            "<div type=\"x-milestone\" subType=\"x-preverse\" sID=\"pv1\"/><title type=\"parallel\"> (<reference osisRef=\"John.1.1-John.1.5\" type=\"parallel\">John 1:1–5</reference>; <reference osisRef=\"Heb.11.1-Heb.11.3\" type=\"parallel\">Hebrews 11:1–3</reference>) </title> <div sID=\"gen17\" type=\"x-p\"/><div type=\"x-milestone\" subType=\"x-preverse\" eID=\"pv1\"/><w lemma=\"lemma.BSBlex:בְּרֵאשִׁ֖ית strong:H7225\" xlit=\"Latn:bə·rê·šîṯ\">In the beginning</w> <w lemma=\"lemma.BSBlex:אֱלֹהִ֑ים strong:H430\" xlit=\"Latn:’ĕ·lō·hîm\">God</w><w lemma=\"lemma.BSBlex:אֵ֥ת strong:H853\" xlit=\"Latn:’êṯ\"/> <w lemma=\"lemma.BSBlex:בָּרָ֣א strong:H1254\" xlit=\"Latn:bā·rā\">created</w> <w lemma=\"lemma.BSBlex:הַשָּׁמַ֖יִם strong:H8064\" xlit=\"Latn:haš·šā·ma·yim\">the heavens</w> <w lemma=\"lemma.BSBlex:וְאֵ֥ת strong:H853\" xlit=\"Latn:wə·’êṯ\">and</w> <w lemma=\"lemma.BSBlex:הָאָֽרֶץ׃ strong:H776\" xlit=\"Latn:hā·’ā·reṣ\">the earth</w>. <div eID=\"gen17\" type=\"x-p\"/>",
            result
        )
    }

    @Test
    fun readRawContent_readLastVerseInChapter() {
        testDownloaded()
        val v11nName = "KJV" //getBookMetaData().getProperty(BookMetaData.KEY_VERSIFICATION);
        val v11n = Versifications.getVersification(v11nName)
        val result = backend.readRawContent(backendState, Verse(v11n, BibleBook.GEN, 1, 31))
        listOf("And God looked upon all that he had made and indeed it was very good".split(" ").forEach { word: String ->
            assertContains(result, word)
        })
    }

    @Test
    fun readRawContent_readNtVerse() {
        testDownloaded()
        val v11nName = "KJV" //getBookMetaData().getProperty(BookMetaData.KEY_VERSIFICATION);
        val v11n = Versifications.getVersification(v11nName)
        val result = backend.readRawContent(backendState, Verse(v11n, BibleBook.JOHN, 1, 1))
        println(result)
        listOf("In the beginning ws the Word and the Word was with God and the Word was God".split(" ").forEach { word: String ->
            assertContains(result, word)
        })
    }

    @Test
    fun readToOsis_readChapter() {
        testDownloaded()
        val v11nName = "KJV" //getBookMetaData().getProperty(BookMetaData.KEY_VERSIFICATION);
        val v11n = Versifications.getVersification(v11nName)
        val start = Verse(v11n, BibleBook.GEN, 1, 1)
        val end = Verse(v11n, BibleBook.GEN, 1, 31)
        val result: List<KeyText>
        val time = measureTime {
            result = backend.readToOsis(VerseRange(v11n, start, end))
        }
        println("Time taken: ${time.inWholeMilliseconds} ms")
        listOf("In the beginning God created the heavens and the earth".split(" ").forEach { word: String ->
            assertContains(result.first().text, word)
        })
        listOf("And God looked upon all that he had made and indeed it was very good".split(" ").forEach { word: String ->
            assertContains(result.last().text, word)
        })
    }

    @Test
    fun readToOsis_readIntroductionToNT() {
        testDownloaded()
        val v11nName = "KJV" //getBookMetaData().getProperty(BookMetaData.KEY_VERSIFICATION);
        val v11n = Versifications.getVersification(v11nName)
        val bibleBook = BibleBook.INTRO_NT
        val start = Verse(v11n, bibleBook, 0, 0)
        val lastChapter = v11n.getLastChapter(bibleBook)
        val lastVerse = v11n.getLastVerse(bibleBook, lastChapter)
        val end = Verse(v11n, bibleBook, lastChapter, lastVerse)
        val verseRange = VerseRange(v11n, start, end)
        val result: List<KeyText>
        val time = measureTime {
            result = backend.readToOsis(verseRange)
        }
        println("Time taken: ${time.inWholeMilliseconds} ms")
        println(result)
        assertTrue(result.size < 200)
    }

    @Test
    fun contains_returnsTrueForExistingVerseAndFalseForMissingVerse() {
        // GIVEN a downloaded and initialized backend
        testDownloaded()
        val v11nName = bookMetaData.getProperty(BookMetaData.KEY_VERSIFICATION) ?: Versifications.DEFAULT_V11N
        val v11n = Versifications.getVersification(v11nName)

        // WHEN checking for verses that are known to exist
        val firstVerse = Verse(v11n, BibleBook.GEN, 1, 1)
        val ntVerse = Verse(v11n, BibleBook.JOHN, 3, 16)

        // AND WHEN checking for a verse that does not exist (Genesis has only 50 chapters)
        val nonExistentVerse = Verse(v11n, BibleBook.GEN, 51, 1)

        // THEN the existing verses should return true
        assertTrue("contains() should return true for Genesis 1:1", backend.contains(firstVerse))
        assertTrue("contains() should return true for John 3:16", backend.contains(ntVerse))

        // AND THEN the non-existent verse should return false
        assertFalse("contains() should return false for Genesis 51:1", backend.contains(nonExistentVerse))
    }
    @Test
    fun testDownloaded() = runTest {
        println("testDownloaded")
        var book = Books.getBook(MODULE_NAME)
        if (book == null) {
            println("Installing $MODULE_NAME")
            SwordInstallerFactory().crosswireInstaller.install(MODULE_NAME)
            delay(5000)
            book = Books.getBook(MODULE_NAME)
            assertNotNull(book, "$MODULE_NAME module should be installed")
        }

        bookMetaData = book!!.bookMetaData as SwordBookMetaData
        backendState = ZVerseBackendState(bookMetaData, BlockType.BLOCK_BOOK)
        backend = ZVerseBackend(bookMetaData, BlockType.BLOCK_BOOK, 2)
    }
}