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
package org.crosswire.ksword.book.sword.state

import okio.FileHandle
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.SYSTEM
import org.crosswire.ksword.book.BookMetaData
import org.crosswire.ksword.book.sword.SwordConstants
import org.crosswire.ksword.book.sword.SwordUtil

/**
 * State for RawLD/RawLD4 dictionary backends.
 * Manages the index and data file handles and provides an in-memory index
 * for fast dictionary lookups.
 *
 * @param bookMetaData metadata for the dictionary
 * @param dataSize 2 for RawLD, 4 for RawLD4 (size of the entry length field in bytes)
 * @author Joe Walker (JSword original)
 */
class RawLDBackendState(
    bookMetaData: BookMetaData,
    private val dataSize: Int
) : AbstractOpenFileState(bookMetaData) {

    /**
     * Index entry data class
     */
    data class IndexEntry(
        val keyName: String,
        val offset: Int,
        val size: Int
    )

    private var idxFile: FileHandle? = null
    private var datFile: FileHandle? = null

    /**
     * In-memory index of all dictionary entries.
     * Loaded once and cached for fast lookups.
     */
    private var entryIndex: List<IndexEntry>? = null

    /**
     * Case-insensitive sorted list of entry names for binary search.
     */
    private var sortedKeys: List<String>? = null

    init {
        val path = SwordUtil.getExpandedDataPath(bookMetaData)

        // Dictionary files use .idx and .dat extensions
        // The DataPath from config already includes the full path to the files (without extension)
        val idxPath = (path.toString() + SwordConstants.EXTENSION_INDEX).toPath()
        val datPath = (path.toString() + SwordConstants.EXTENSION_DATA).toPath()

        if (FileSystem.SYSTEM.exists(idxPath) && FileSystem.SYSTEM.exists(datPath)) {
            idxFile = FileSystem.SYSTEM.openReadOnly(idxPath)
            datFile = FileSystem.SYSTEM.openReadOnly(datPath)

            // Load the index into memory
            loadIndex()
        } else {
            throw Exception("Dictionary files not found: $idxPath or $datPath")
        }
    }

    /**
     * Load the complete index into memory for fast lookups.
     * This reads all index entries and extracts the key names from the data file.
     */
    private fun loadIndex() {
        val idxHandle = idxFile ?: return
        val datHandle = datFile ?: return

        val fileSize = idxHandle.size()
        val entrySize = 4 + dataSize // offset (4) + size (2 or 4)
        val entryCount = (fileSize / entrySize).toInt()

        val entries = mutableListOf<IndexEntry>()
        val keys = mutableListOf<String>()

        for (i in 0 until entryCount) {
            val position = i * entrySize.toLong()
            val indexData = ByteArray(entrySize)
            idxHandle.read(position, indexData, 0, entrySize)

            val offset = SwordUtil.decodeLittleEndian32(indexData, 0)
            val size = if (dataSize == 2) {
                SwordUtil.decodeLittleEndian16(indexData, 4)
            } else {
                SwordUtil.decodeLittleEndian32(indexData, 4)
            }

            // Read the entry name from the data file
            // Entry format: "KeyName\n<definition text>" (or with \r\n or \0)
            if (size > 0 && offset >= 0 && offset < datHandle.size()) {
                try {
                    // Read a reasonable chunk to find the key name (limited to first 256 bytes)
                    val readSize = minOf(size, 256)
                    val entryData = ByteArray(readSize)
                    datHandle.read(offset.toLong(), entryData, 0, readSize)

                    // Find the key terminator (CRLF, LF, or null) to extract the key name
                    // First try to find CRLF sequence
                    var keyEndIndex = -1
                    for (j in 0 until readSize - 1) {
                        if (entryData[j] == 0x0D.toByte() && entryData[j + 1] == 0x0A.toByte()) {
                            keyEndIndex = j
                            break
                        }
                    }

                    // Fall back to just LF if CRLF not found
                    if (keyEndIndex < 0) {
                        val lfIndex = entryData.indexOfFirst { it == 0x0A.toByte() }
                        if (lfIndex >= 0) {
                            keyEndIndex = lfIndex
                        }
                    }

                    // Fall back to null terminator if neither found
                    if (keyEndIndex < 0) {
                        val nullIndex = entryData.indexOfFirst { it == 0.toByte() }
                        if (nullIndex >= 0) {
                            keyEndIndex = nullIndex
                        }
                    }

                    if (keyEndIndex > 0) {
                        val keyName = entryData.decodeToString(0, keyEndIndex)
                        entries.add(IndexEntry(keyName, offset, size))
                        keys.add(keyName)
                    }
                } catch (e: Exception) {
                    // Skip malformed entries - silently ignore
                }
            }
        }

        this.entryIndex = entries
        // Sort keys case-insensitively for binary search
        this.sortedKeys = keys.sortedWith(String.CASE_INSENSITIVE_ORDER)
    }

    override fun releaseResources() {
        idxFile?.close()
        datFile?.close()
        idxFile = null
        datFile = null
        entryIndex = null
        sortedKeys = null
    }

    /**
     * Get the index file handle.
     */
    fun getIdxFile(): FileHandle? = idxFile

    /**
     * Get the data file handle.
     */
    fun getDatFile(): FileHandle? = datFile

    /**
     * Get all index entries.
     */
    fun getEntryIndex(): List<IndexEntry> = entryIndex ?: emptyList()

    /**
     * Get sorted key names (case-insensitive).
     */
    fun getSortedKeys(): List<String> = sortedKeys ?: emptyList()

    /**
     * Find an entry by name (case-insensitive).
     * Returns null if not found.
     *
     * @param keyName the key name to search for
     * @return the IndexEntry if found, null otherwise
     */
    fun findEntry(keyName: String): IndexEntry? {
        val index = entryIndex ?: return null

        // Case-insensitive search
        return index.firstOrNull { it.keyName.equals(keyName, ignoreCase = true) }
    }
}
