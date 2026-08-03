package org.crosswire.ksword.book.sword

import okio.Buffer
import org.crosswire.ksword.book.BookMetaData
import org.crosswire.ksword.book.sword.state.OpenFileState
import org.crosswire.ksword.passage.Key
import org.crosswire.ksword.passage.KeyUtil
import org.crosswire.ksword.passage.Verse
import org.crosswire.ksword.passage.VerseRange
import org.crosswire.ksword.versification.BibleBook
import org.crosswire.ksword.versification.Versification
import org.crosswire.ksword.versification.system.Versifications
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Linked commentary verses are recovered from the index, so these drive a fake module whose
 * index entries are declared directly — no real module file or network needed.
 */
class AbstractBackendVerseRangeTest {

    private val v11n: Versification = Versifications.getVersification("KJV")

    private fun verse(book: BibleBook, chapter: Int, verse: Int) = Verse(v11n, book, chapter, verse)

    @Test
    fun multiVerseCommentReadsAsOneRangeEntry() {
        val backend = module {
            comment(verse(BibleBook.MARK, 1, 1), text = "On verse one.")
            comment(verse(BibleBook.MARK, 1, 2), verse(BibleBook.MARK, 1, 3), text = "On verses two and three.")
            comment(verse(BibleBook.MARK, 1, 4), text = "On verse four.")
        }

        val result = backend.readToOsis(range(1, 4))

        assertEquals(3, result.size)
        assertEquals(verse(BibleBook.MARK, 1, 1), result[0].key)
        assertEquals(VerseRange(v11n, verse(BibleBook.MARK, 1, 2), verse(BibleBook.MARK, 1, 3)), result[1].key)
        assertEquals("On verses two and three.", result[1].text)
        assertEquals(verse(BibleBook.MARK, 1, 4), result[2].key)
    }

    @Test
    fun singleVerseCommentKeepsAPlainVerseKey() {
        val backend = module {
            comment(verse(BibleBook.MARK, 1, 1), text = "On verse one.")
        }

        val result = backend.readToOsis(range(1, 1))

        assertEquals(1, result.size)
        assertEquals(verse(BibleBook.MARK, 1, 1), result[0].key)
        assertEquals("On verse one.", result[0].text)
    }

    @Test
    fun identicalTextStoredSeparatelyIsNotMerged() {
        val backend = module {
            comment(verse(BibleBook.MARK, 1, 1), text = "Same words.")
            comment(verse(BibleBook.MARK, 1, 2), text = "Same words.")
        }

        val result = backend.readToOsis(range(1, 2))

        assertEquals(2, result.size)
        assertEquals(verse(BibleBook.MARK, 1, 1), result[0].key)
        assertEquals(verse(BibleBook.MARK, 1, 2), result[1].key)
    }

    @Test
    fun runLongerThanTheFormerCapMergesWhole() {
        val backend = module {
            comment(verse(BibleBook.MARK, 1, 1), verse(BibleBook.MARK, 1, 45), text = "One comment on the chapter.")
        }

        val result = backend.readToOsis(range(1, 45))

        assertEquals(1, result.size)
        assertEquals(VerseRange(v11n, verse(BibleBook.MARK, 1, 1), verse(BibleBook.MARK, 1, 45)), result[0].key)
    }

    @Test
    fun bibleStyleVersesStayIndividuallyKeyed() {
        val backend = module {
            comment(verse(BibleBook.MARK, 1, 1), text = "Verse one text.")
            comment(verse(BibleBook.MARK, 1, 2), text = "Verse two text.")
            comment(verse(BibleBook.MARK, 1, 3), text = "Verse three text.")
        }

        val result = backend.readToOsis(range(1, 3))

        assertEquals(3, result.size)
        result.forEachIndexed { i, keyText -> assertEquals(verse(BibleBook.MARK, 1, i + 1), keyText.key) }
    }

    @Test
    fun emptyVersesAreNotMergedTogether() {
        val backend = module {
            comment(verse(BibleBook.MARK, 1, 1), text = "On verse one.")
            empty(verse(BibleBook.MARK, 1, 2), verse(BibleBook.MARK, 1, 4))
        }

        val result = backend.readToOsis(range(1, 4))

        assertEquals(4, result.size)
        assertEquals(verse(BibleBook.MARK, 1, 4), result[3].key)
        assertEquals("", result[3].text)
    }

    @Test
    fun findNextKeyCrossesAGapLongerThanTheFormerCap() {
        val backend = module {
            comment(verse(BibleBook.PS, 119, 1), text = "On the first verse.")
            empty(verse(BibleBook.PS, 119, 2), verse(BibleBook.PS, 119, 50))
            comment(verse(BibleBook.PS, 119, 51), text = "On the fifty-first verse.")
        }

        val result = backend.findNextKey(verse(BibleBook.PS, 119, 1))

        assertEquals(verse(BibleBook.PS, 119, 51), result)
    }

    @Test
    fun findNextKeySkipsTheRestOfALinkedRun() {
        val backend = module {
            comment(verse(BibleBook.MARK, 1, 1), text = "On verse one.")
            comment(verse(BibleBook.MARK, 1, 2), verse(BibleBook.MARK, 1, 5), text = "On verses two to five.")
            comment(verse(BibleBook.MARK, 1, 6), text = "On verse six.")
        }

        assertEquals(verse(BibleBook.MARK, 1, 2), backend.findNextKey(verse(BibleBook.MARK, 1, 1)))
        assertEquals(verse(BibleBook.MARK, 1, 6), backend.findNextKey(verse(BibleBook.MARK, 1, 2)))
        assertEquals(verse(BibleBook.MARK, 1, 6), backend.findNextKey(verse(BibleBook.MARK, 1, 4)))
    }

    @Test
    fun findPreviousKeyLandsOnTheStartOfALinkedRun() {
        val backend = module {
            comment(verse(BibleBook.MARK, 1, 1), text = "On verse one.")
            comment(verse(BibleBook.MARK, 1, 2), verse(BibleBook.MARK, 1, 5), text = "On verses two to five.")
            comment(verse(BibleBook.MARK, 1, 6), text = "On verse six.")
        }

        assertEquals(verse(BibleBook.MARK, 1, 2), backend.findPreviousKey(verse(BibleBook.MARK, 1, 6)))
        assertEquals(verse(BibleBook.MARK, 1, 1), backend.findPreviousKey(verse(BibleBook.MARK, 1, 2)))
    }

    private fun range(from: Int, to: Int) =
        VerseRange(v11n, verse(BibleBook.MARK, 1, from), verse(BibleBook.MARK, 1, to))

    private fun module(build: FakeModule.() -> Unit): FakeBackend {
        val fakeModule = FakeModule(v11n).apply(build)
        return FakeBackend(commentaryMetaData(), fakeModule.entries, fakeModule.texts)
    }

    private fun commentaryMetaData(): SwordBookMetaData =
        SwordBookMetaData.createFromSource(
            Buffer().writeUtf8(
                """
                [TEST]
                DataPath=./modules/comments/zcom/test/
                ModDrv=zCom
                Versification=KJV
                """.trimIndent()
            )
        )
}

/** Declares what a module stores: one index entry per verse, one text per stored entry. */
private class FakeModule(private val v11n: Versification) {
    val entries = mutableMapOf<Int, VerseIndexEntry>()
    val texts = mutableMapOf<VerseIndexEntry, String>()
    private var nextOffset = 0

    /** One stored comment covering [from]..[to] — every covered verse points at the same entry. */
    fun comment(from: Verse, to: Verse = from, text: String) {
        val entry = VerseIndexEntry(v11n.getTestament(from.ordinal), 0, nextOffset, text.length)
        nextOffset += text.length
        for (ordinal in from.ordinal..to.ordinal) entries[ordinal] = entry
        texts[entry] = text
    }

    fun empty(from: Verse, to: Verse = from) {
        for (ordinal in from.ordinal..to.ordinal) {
            entries[ordinal] = VerseIndexEntry(v11n.getTestament(ordinal), 0, 0, 0)
        }
    }
}

private class FakeState : OpenFileState {
    override val bookMetaData: BookMetaData? = null
    override var lastAccess: Long = 0
    override fun releaseResources() = Unit
    override fun close() = Unit
}

private class FakeBackend(
    bmd: SwordBookMetaData,
    private val entries: Map<Int, VerseIndexEntry>,
    private val texts: Map<VerseIndexEntry, String>,
) : AbstractBackend<FakeState>(bmd) {

    override fun initState() = FakeState()

    override fun readIndexEntry(state: FakeState, key: Key): VerseIndexEntry? =
        entries[KeyUtil.getVerse(key).ordinal]

    override fun readRawContent(state: FakeState, key: Key): String {
        val entry = readIndexEntry(state, key) ?: return ""
        return texts[entry].orEmpty()
    }
}
