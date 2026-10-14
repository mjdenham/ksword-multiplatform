package org.crosswire.ksword.book.sword

import kotlinx.coroutines.test.runTest
import okio.FileSystem
import okio.Path.Companion.toPath
import org.crosswire.ksword.book.Book
import org.crosswire.ksword.book.Books
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

/**
 * Barnes' NT Notes as distributed points each book's chapter-1 verse-0 index entry into the
 * previous book's block (e.g. Col 1:0 holds Phil 3:21's note). The reader must not show it,
 * while RWP's verse-0 entries, also chapter-blocked, must still read.
 */
class ChapterBlockIntroTest {

    companion object {
        private val dir = FileSystem.SYSTEM_TEMPORARY_DIRECTORY.resolve("ChapterBlockIntroTest".toPath())

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
}
