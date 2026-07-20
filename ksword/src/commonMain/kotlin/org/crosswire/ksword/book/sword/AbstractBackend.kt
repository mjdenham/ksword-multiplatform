package org.crosswire.ksword.book.sword

import okio.Path
import okio.use
import org.crosswire.common.util.Log
import org.crosswire.ksword.book.BookException
import org.crosswire.ksword.book.BookMetaData
import org.crosswire.ksword.book.KeyType
import org.crosswire.ksword.book.sword.state.OpenFileState
import org.crosswire.ksword.passage.Key
import org.crosswire.ksword.passage.KeyText
import org.crosswire.ksword.passage.KeyUtil.getPassage
import org.crosswire.ksword.passage.KeyUtil.getVerse
import org.crosswire.ksword.passage.RestrictionType
import org.crosswire.ksword.passage.Verse
import org.crosswire.ksword.passage.VerseRange

internal abstract class AbstractBackend<T: OpenFileState>(val bmd: SwordBookMetaData) : StatefulFileBackedBackend<T>, Backend<T> {

    override fun contains(key: Key): Boolean {
        val text = getRawText(key)
        return text.isNotBlank()
    }

    override fun findNextKey(key: Key): Key? {
        val verse = (key as? Verse) ?: return null
        val v11n = verse.getVersification()
        return findKeyWithDifferentContent(verse) { v -> v11n.add(v, 1) }
    }

    override fun findPreviousKey(key: Key): Key? {
        val verse = (key as? Verse) ?: return null
        val v11n = verse.getVersification()
        return findKeyWithDifferentContent(verse) { v -> v11n.subtract(v, 1) }
    }

    private fun findKeyWithDifferentContent(
        verse: Verse,
        adjacentVerse: (Verse) -> Verse?
    ): Key? {
        val startContent = getRawText(verse)
        val v11n = verse.getVersification()
        var currentVerse = adjacentVerse(verse) ?: return null
        var versesChecked = 0

        while (versesChecked < 40 && currentVerse.book == verse.book) {
            if (currentVerse.ordinal == 0 || currentVerse.ordinal == v11n.maximumOrdinal()) return null

            getRawText(currentVerse).let { currentContent ->
                if (currentContent.isNotBlank() && currentContent != startContent && currentVerse.verse >= 0) {
                    return currentVerse
                }
            }

            currentVerse = adjacentVerse(currentVerse) ?: break
            versesChecked++
        }

        return null
    }

    override fun getRawText(key: Key): String {
        try {
            initState().use { openFileState ->
                return readRawContent(openFileState, key)
            }
        } catch (e: Exception) {
            throw BookException("Unable to obtain raw content from backend for key='$key'", e)
        }
    }

    override fun readToOsis(key: Key): List<KeyText> {

        initState().use { openFileState ->
            val content = when (this.bmd.keyType) {
                KeyType.LIST -> readNormalOsis(key, openFileState)
//                TREE -> readNormalOsisSingleKey(key, processor, content, openFileState)
                KeyType.VERSE -> readPassageOsis(key, openFileState)
                else -> throw BookException("Book has unsupported type of key")
            }
            return content
        }
    }

    private fun readNormalOsis(
        key: Key,
//        processor: RawTextToXmlProcessor,
//        content: List<org.jdom2.Content>,
        openFileState: T
    ): List<KeyText> {
        val contentList = mutableListOf<KeyText>()
        // simply lookup the key and process the relevant information
        val iterator: Iterator<Key> = key.iterator()

        while (iterator.hasNext()) {
            val next = iterator.next()
            var rawText: String?
            try {
                rawText = readRawContent(openFileState, next)
                contentList.add(KeyText(next, rawText))
//                processor.postVerse(next, content, rawText)
            } catch (e: Exception) {
                Log.w("Failed to read key '$next' from ${bmd.initials}", e)
            }
        }
        return contentList
    }


    /**
     * Reads a passage as OSIS
     *
     * @param key           the given key
     * @param openFileState the open file state, from which we read things
     */
    private fun readPassageOsis(
        key: Key,
        openFileState: T
    ): List<KeyText> {
        val contentList = mutableListOf<KeyText>()
        var currentVerse: Verse? = null
        val rit = when (key) {
            is VerseRange -> key.iterator()
            else -> getPassage(key).rangeIterator(RestrictionType.CHAPTER)
        }
        while (rit.hasNext()) {
            val range = rit.next()
//            processor.preRange(range, content)

            // FIXME(CJB): can this now be optimized since we can calculate
            // the buffer size of what to read?
            // now iterate through all verses in range
            for (verseInRange in range) {
                currentVerse = getVerse(verseInRange)
                try {
                    val rawText = readRawContent(openFileState, currentVerse)
                    contentList.add(KeyText(currentVerse, rawText))
//                    processor.postVerse(verseInRange, content, rawText)
                } catch (e: Exception) {
                    // Some versifications have more verses than the module contains, so a missing
                    // verse here is expected — log at debug rather than failing the whole passage.
                    Log.d("No content for $currentVerse in ${bmd.initials}: ${e.message}")
                }
            }
        }

        return contentList
    }

    fun getExpandedDataPath(): Path /* URI */ {
//        val loc: java.net.URI = NetUtil.lengthenURI(
//            bmd.getLibrary(),
//            bmd.getProperty(ConfigEntryType.DATA_PATH) as String
//        )
//            ?: throw BookException(Msg.MISSING_FILE)
//
//        return loc
        val dataPath = bmd.getProperty(SwordBookMetaData.KEY_DATA_PATH) ?: throw BookException("Missing data path")
        return SwordBookPath.swordBookPath.resolve(dataPath)
    }

    override fun getBookMetaData(): BookMetaData = bmd
}
