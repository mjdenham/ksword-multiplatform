package org.crosswire.ksword.book.sword

import kotlinx.coroutines.test.runTest
import okio.FileSystem
import okio.Path.Companion.toPath
import org.crosswire.ksword.book.Book
import org.crosswire.ksword.book.Books
import org.crosswire.ksword.book.TestBooks
import org.crosswire.ksword.book.install.sword.SwordInstallerFactory
import org.crosswire.ksword.passage.Verse
import org.crosswire.ksword.versification.BibleBook
import org.crosswire.ksword.versification.system.Versifications
import org.junit.AfterClass
import org.junit.BeforeClass
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * Barnes, ISV and TSK copy an earlier note's index entry into each book's chapter-1 verse 0
 * (e.g. Barnes' Col 1:0 holds Phil 3:21's note). The reader must not show these, while real
 * verse-0 entries (RWP, Vulgate) and verse 0 inside a note linked across chapters must still read.
 */
class CopiedVerseZeroTest {

    companion object {
        private val dir = FileSystem.SYSTEM_TEMPORARY_DIRECTORY.resolve("CopiedVerseZeroTest".toPath())

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
            TestBooks.reset()
        }
    }

    private val kjv = Versifications.getVersification("KJV")

    private suspend fun load(initials: String): Book {
        Books.getBook(initials)?.let { return it }
        SwordInstallerFactory().crosswireInstaller.install(initials)
        return assertNotNull(Books.getBook(initials), "$initials should install from the CrossWire repository")
    }

    @Test
    fun bookFirstChapterIntroHasNoPreviousBookText() = runTest {
        val barnes = load("Barnes")
        assertEquals("", barnes.getRawText(Verse(kjv, BibleBook.COL, 1, 0)))
        assertEquals("", barnes.getRawText(Verse(kjv, BibleBook.PET1, 1, 0)))
    }

    @Test
    fun verseNotesStillRead() = runTest {
        val barnes = load("Barnes")
        assertContains(barnes.getRawText(Verse(kjv, BibleBook.COL, 1, 1)), "COLOSSIANS")
        assertContains(barnes.getRawText(Verse(kjv, BibleBook.COL, 1, 4)), "faith in Christ Jesus")
        assertContains(barnes.getRawText(Verse(kjv, BibleBook.PHIL, 3, 21)), "vile body")
    }

    @Test
    fun realChapterIntrosStillRead() = runTest {
        val rwp = load("RWP")
        assertContains(rwp.getRawText(Verse(kjv, BibleBook.MATT, 27, 0)), "osisID=\"Matt.27\"")
    }

    @Test
    fun copiesInBookBlockedModulesAreSkipped() = runTest {
        val isv = load("ISV")
        assertEquals("", isv.getRawText(Verse(kjv, BibleBook.JOHN, 1, 0)))
        assertEquals("", isv.getRawText(Verse(kjv, BibleBook.REV, 1, 0)))
        assertContains(isv.getRawText(Verse(kjv, BibleBook.JOHN, 1, 1)), "In the beginning")
        assertContains(isv.getRawText(Verse(kjv, BibleBook.LUKE, 23, 56)), "prepared spices")
        assertEquals("", load("TSK").getRawText(Verse(kjv, BibleBook.EXOD, 1, 0)))
    }

    @Test
    fun bookBlockedRealIntrosStillRead() = runTest {
        val vulgate = load("Vulgate")
        val vulg = vulgate.bookMetaData.versification
        assertTrue(vulgate.getRawText(Verse(vulg, BibleBook.PS, 2, 0)).isNotBlank())
    }

    @Test
    fun verseZeroInsideLinkedNoteStillReads() = runTest {
        val luther = load("Luther")
        val genesisTwo = luther.getRawText(Verse(kjv, BibleBook.GEN, 2, 0))
        assertTrue(genesisTwo.isNotBlank())
        assertEquals(luther.getRawText(Verse(kjv, BibleBook.GEN, 1, 31)), genesisTwo)
    }
}
