/**
 * Distribution License:
 * KSword is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License, version 2.1 or later
 * as published by the Free Software Foundation. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * The License is available on the internet at:
 * http://www.gnu.org/copyleft/lgpl.html
 * or by writing to:
 * Free Software Foundation, Inc.
 * 59 Temple Place - Suite 330
 * Boston, MA 02111-1307, USA
 *
 * © CrossWire Bible Society, 2005 - 2016
 *
 */
package org.crosswire.ksword.book.sword

import okio.use
import org.crosswire.ksword.book.sword.state.OpenFileStateManager
import org.crosswire.ksword.book.sword.state.RawLDBackendState
import org.crosswire.ksword.passage.DefaultLeafKeyList
import org.crosswire.ksword.passage.Key

/**
 * Backend for reading RawLD (2-byte) and RawLD4 (4-byte) dictionary modules.
 *
 * These are uncompressed dictionaries with a simple index/data file structure:
 * - Index file (.idx): Contains (offset, size) pairs for each entry
 * - Data file (.dat): Contains "KeyName\0definition" entries
 *
 * @param bookMetaData metadata for the dictionary
 * @param dataSize 2 for RawLD, 4 for RawLD4 (size of the entry length field in bytes)
 * @author Joe Walker (JSword original)
 */
class RawLDBackend(
    bookMetaData: SwordBookMetaData,
    private val dataSize: Int
) : AbstractBackend<RawLDBackendState>(bookMetaData) {

    override fun initState(): RawLDBackendState {
        return OpenFileStateManager.getRawLDBackendState(bmd, dataSize)
    }

    override fun readRawContent(state: RawLDBackendState, key: Key): String {
        val keyName = key.getName()
        val entry = state.findEntry(keyName) ?: return "" // Entry not found

        val datFile = state.getDatFile() ?: return ""

        // Read the entry data from the data file
        val entryData = SwordUtil.readFile(datFile, entry.offset, entry.size)
        if (entryData.isEmpty()) return ""

        // Entry format: "KeyName\r\n<definition text>" (or with \n or \0)
        // Skip past the key name and terminator to get the definition
        // First try to find CRLF sequence
        var definitionStart = -1
        for (i in 0 until entryData.size - 1) {
            if (entryData[i] == 0x0D.toByte() && entryData[i + 1] == 0x0A.toByte()) {
                definitionStart = i + 2 // Skip past both CR and LF
                break
            }
        }

        // Fall back to just LF if CRLF not found
        if (definitionStart < 0) {
            val lfIndex = entryData.indexOfFirst { it == 0x0A.toByte() }
            if (lfIndex >= 0 && lfIndex < entryData.size - 1) {
                definitionStart = lfIndex + 1
            }
        }

        // Fall back to null terminator if neither found
        if (definitionStart < 0) {
            val nullIndex = entryData.indexOfFirst { it == 0.toByte() }
            if (nullIndex >= 0 && nullIndex < entryData.size - 1) {
                definitionStart = nullIndex + 1
            }
        }

        if (definitionStart < 0 || definitionStart >= entryData.size) {
            return "" // Malformed entry
        }

        val definitionLength = entryData.size - definitionStart

        // Get charset from metadata, default to UTF-8
        val charset = bmd.getProperty("Encoding") ?: "UTF-8"

        return SwordUtil.decode(keyName, entryData, definitionStart, definitionLength, charset)
    }

    override fun contains(key: Key): Boolean {
        initState().use { state ->
            return state.findEntry(key.getName()) != null
        }
    }

    /**
     * Override next key navigation for dictionary-specific behavior.
     * Returns the next entry in alphabetical order.
     */
    override fun findNextKey(key: Key): Key? {
        initState().use { state ->
            val sortedKeys = state.getSortedKeys()
            if (sortedKeys.isEmpty()) return null

            val currentKey = key.getName()
            val currentIndex = sortedKeys.indexOfFirst { it.equals(currentKey, ignoreCase = true) }

            return if (currentIndex >= 0 && currentIndex < sortedKeys.size - 1) {
                DefaultLeafKeyList(sortedKeys[currentIndex + 1])
            } else {
                null
            }
        }
    }

    /**
     * Override previous key navigation for dictionary-specific behavior.
     * Returns the previous entry in alphabetical order.
     */
    override fun findPreviousKey(key: Key): Key? {
        initState().use { state ->
            val sortedKeys = state.getSortedKeys()
            if (sortedKeys.isEmpty()) return null

            val currentKey = key.getName()
            val currentIndex = sortedKeys.indexOfFirst { it.equals(currentKey, ignoreCase = true) }

            return if (currentIndex > 0) {
                DefaultLeafKeyList(sortedKeys[currentIndex - 1])
            } else {
                null
            }
        }
    }

    /**
     * Get all entry keys from the dictionary.
     * Used by SwordDictionary.getGlobalKeyList().
     *
     * @return list of all entry key names
     */
    fun getAllKeys(): List<String> {
        initState().use { state ->
            return state.getSortedKeys()
        }
    }
}
