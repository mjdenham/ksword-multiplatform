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

import okio.IOException
import okio.use
import org.crosswire.ksword.book.sword.state.OpenFileStateManager
import org.crosswire.ksword.book.sword.state.RawLDBackendState
import org.crosswire.ksword.passage.DefaultLeafKeyList
import org.crosswire.ksword.passage.Key

/**
 * Backend for reading RawLD (2-byte) and RawLD4 (4-byte) dictionary modules.
 *
 * This implementation uses JSword's memory-efficient approach:
 * - On-demand disk reading with binary search
 * - No pre-loading of dictionary entries into memory
 * - Strong's number normalization support (G1 ↔ G0001)
 * - Link following (@LINK entries)
 * - Cipher/encryption support
 *
 * @param bookMetaData metadata for the dictionary
 * @param dataSize 2 for RawLD, 4 for RawLD4 (size of the entry length field in bytes)
 * @author Joe Walker (JSword original)
 * @author DM Smith (JSword original)
 */
class RawLDBackend(
    bookMetaData: SwordBookMetaData,
    private val dataSize: Int
) : AbstractBackend<RawLDBackendState>(bookMetaData) {

    override fun initState(): RawLDBackendState {
        return OpenFileStateManager.getRawLDBackendState(bmd, dataSize)
    }

    override fun readRawContent(state: RawLDBackendState, key: Key): String {
        return doReadRawContent(state, key.getName())
    }

    /**
     * Read raw content for a key, following links if necessary.
     *
     * @param state Backend state with file handles
     * @param keyName Key name to look up
     * @return Raw text content
     * @throws IOException if key not found or read error
     */
    private fun doReadRawContent(state: RawLDBackendState, keyName: String): String {
        if (keyName.isEmpty()) return ""
        val pos = search(state, keyName)
        if (pos >= 0) {
            val dataIndex = getIndex(state, pos.toLong())
            val entry = getEntry(state, keyName, dataIndex)

            // Follow links (@LINK entries)
            if (entry.isLinkEntry()) {
                return doReadRawContent(state, entry.getLinkTarget())
            }

            // Apply cipher if configured
            val cipherKey = getCipherKey()
            return entry.getRawText(cipherKey)
        }

        throw IOException("Key not found: $keyName")
    }

    /**
     * Get cipher key for encrypted modules.
     *
     * @return Cipher key bytes, or null if no cipher configured
     */
    private fun getCipherKey(): ByteArray? {
        val cipherKeyString = bmd.getProperty(SwordBookMetaData.KEY_CIPHER_KEY) ?: return null
        return try {
            cipherKeyString.encodeToByteArray()
        } catch (e: Exception) {
            cipherKeyString.encodeToByteArray()
        }
    }

    /**
     * Get cardinality (total entry count) by dividing index file size by entry size.
     * Matches JSword's approach of calculating on-demand rather than caching.
     *
     * @return Number of entries in the dictionary
     */
    fun getCardinality(): Int {
        initState().use { state ->
            val idxFile = state.getIdxFile() ?: return 0
            return (idxFile.size() / state.entrySize).toInt()
        }
    }

    /**
     * Read index entry (offset, size) at given position.
     *
     * @param state Backend state with file handles
     * @param entry Entry number (0-indexed)
     * @return DataIndex with offset and size
     * @throws IOException if read error
     */
    private fun getIndex(state: RawLDBackendState, entry: Long): DataIndex {
        val idxFile = state.getIdxFile() ?: throw IOException("Index file not available")

        val buffer = ByteArray(state.entrySize)
        val position = entry * state.entrySize
        idxFile.read(position, buffer, 0, state.entrySize)

        val offset = SwordUtil.decodeLittleEndian32(buffer, 0)
        val size = when (state.dataSize) {
            2 -> SwordUtil.decodeLittleEndian16(buffer, 4)
            4 -> SwordUtil.decodeLittleEndian32(buffer, 4)
            else -> error("Invalid dataSize: ${state.dataSize}")
        }

        return DataIndex(offset, size)
    }

    /**
     * Read entry data from .dat file using index information.
     *
     * @param state Backend state with file handles
     * @param name Context name (for debugging/error messages)
     * @param dataIndex Offset and size information
     * @return DataEntry with raw entry data
     * @throws IOException if read error
     */
    private fun getEntry(state: RawLDBackendState, name: String, dataIndex: DataIndex): DataEntry {
        val datFile = state.getDatFile() ?: throw IOException("Data file not available")
        val data = SwordUtil.readFile(datFile, dataIndex.offset, dataIndex.size)
        val charset = bmd.getProperty("Encoding") ?: "UTF-8"
        return DataEntry(name, data, charset)
    }

    /**
     * Binary search for a key in the dictionary.
     * Returns the index if found, or -(insertionPoint + 1) if not found.
     *
     * Special handling (matching JSword):
     * - Index 0 may be an introductory entry (checked separately)
     * - Skips bogus entries where size == 0
     * - Normalizes keys for comparison (case-insensitive by default)
     * - Applies Strong's number normalization if applicable
     *
     * @param state Backend state with file handles
     * @param key Key name to search for
     * @return Index if found (>= 0), or -(insertionPoint + 1) if not found
     * @throws IOException if read error
     */
    private fun search(state: RawLDBackendState, key: String): Int {
        val total = getCardinality()
        var low = 0
        var high = total
        var match = -1
        var suppliedKey: String? = null

        // Binary search from index 1 to end
        // Index 0 is checked separately (may be introductory entry)
        while (high - low > 1) {
            var mid = (low + high) ushr 1  // Use >>> to keep mid in range

            // Get entry at mid position
            var dataIndex = getIndex(state, mid.toLong())

            // Skip bogus entries (size == 0)
            while (dataIndex.size == 0) {
                mid += if (high - mid > mid - low) 1 else -1
                if (mid < low || mid > high) break
                dataIndex = getIndex(state, mid.toLong())
            }

            val entry = getEntry(state, key, dataIndex)
            val entryKey = normalizeForSearch(entry.getKey())

            // Normalize supplied key based on first entry examined
            if (suppliedKey == null) {
                suppliedKey = normalizeForSearch(external2internal(key, entryKey))
            }

            val cmp = entryKey.compareTo(suppliedKey)
            when {
                cmp < 0 -> low = mid
                cmp > 0 -> high = mid
                else -> {
                    match = mid
                    break
                }
            }
        }

        // Found exact match
        if (match >= 0) return match

        // Check introductory entry at index 0
        val dataIndex = getIndex(state, 0)
        val entry = getEntry(state, key, dataIndex)
        val entryKey = normalizeForSearch(entry.getKey())
        if (suppliedKey == null) {
            suppliedKey = normalizeForSearch(external2internal(key, entryKey))
        }
        if (entryKey == suppliedKey) return 0

        // Not found - return negative insertion point
        return -(high + 1)
    }

    /**
     * Convert external key to internal format.
     * Handles Strong's number padding (G1 -> G0001 or G00001).
     *
     * @param externalKey User-provided key
     * @param pattern First non-introduction key (for detecting format)
     * @return Normalized internal key
     */
    private fun external2internal(externalKey: String, pattern: String): String {
        if (externalKey.isEmpty()) return externalKey

        // Check if this is a Strong's dictionary
        // In SWORD modules, these have Feature=GreekDef or Feature=HebrewDef
        val feature = bmd.getProperty(SwordBookMetaData.KEY_FEATURE) ?: ""
        if (!feature.contains("GreekDef") && !feature.contains("HebrewDef")) {
            return externalKey
        }

        // Validate Strong's number format: G1234 or H5678
        STRONGS_PATTERN.matchEntire(externalKey) ?: return externalKey

        // Check if padding is enabled
        if (bmd.getProperty(SwordBookMetaData.KEY_STRONGS_PADDING) != "true") {
            // Unpad: G0001 -> G1
            return unpadStrongsNumber(externalKey)
        }

        // Pad to 4 or 5 digits based on pattern
        return padStrongsNumber(externalKey, pattern)
    }

    /**
     * Pad Strong's number to match dictionary format.
     * Examples: G1 -> G0001 or G00001 (depending on pattern)
     *
     * @param key Strong's number to pad
     * @param pattern First key in dictionary (determines padding length)
     * @return Padded Strong's number
     */
    private fun padStrongsNumber(key: String, pattern: String): String {
        val matcher = STRONGS_PATTERN.matchEntire(key) ?: return key

        val type = matcher.groupValues[1][0]  // G or H
        var number = matcher.groupValues[2]    // The digits
        val suffix = matcher.groupValues[3]    // Optional letter suffix (e.g., "a")

        // Remove trailing ! if present (NASB format)
        val hasBang = number.endsWith("!")
        if (hasBang) number = number.dropLast(1)

        val strongsNumber = number.toIntOrNull() ?: return key

        // Determine padding length from pattern
        val patternMatcher = STRONGS_PATTERN.matchEntire(pattern)
        val padLength = if (patternMatcher != null) {
            patternMatcher.groupValues[2].length
        } else {
            5  // Default to 5 digits
        }

        val formatted = when (padLength) {
            4 -> strongsNumber.toString().padStart(4, '0')
            5 -> strongsNumber.toString().padStart(5, '0')
            else -> strongsNumber.toString()
        }

        // Build result: type + padded number + suffix
        return buildString {
            append(type)
            append(formatted)
            if (suffix.isNotEmpty()) append(suffix)
        }
    }

    /**
     * Remove zero-padding from Strong's number.
     * Examples: G0001 -> G1, H00123 -> H123
     *
     * @param key Padded Strong's number
     * @return Unpadded Strong's number
     */
    private fun unpadStrongsNumber(key: String): String {
        val matcher = STRONGS_PATTERN.matchEntire(key) ?: return key

        val type = matcher.groupValues[1][0]
        val number = matcher.groupValues[2]
        val suffix = matcher.groupValues[3]

        // Remove leading zeros
        val strongsNumber = number.toIntOrNull() ?: return key

        return buildString {
            append(type)
            append(strongsNumber)
            if (suffix.isNotEmpty()) append(suffix)
        }
    }

    /**
     * Normalize key for case-insensitive search.
     * Respects CaseSensitiveKeys configuration.
     *
     * @param key Key to normalize
     * @return Normalized key (uppercase unless case-sensitive enabled)
     */
    private fun normalizeForSearch(key: String): String {
        // Check if case-sensitive keys are enabled
        if (bmd.getProperty(SwordBookMetaData.KEY_CASE_SENSITIVE_KEYS) == "true") {
            return key
        }
        return key.uppercase()
    }

    override fun contains(key: Key): Boolean {
        initState().use { state ->
            return try {
                search(state, key.getName()) >= 0
            } catch (e: IOException) {
                false
            }
        }
    }

    /**
     * Get key at specific index position.
     *
     * @param index Entry index (0-based)
     * @return Key at that position
     * @throws ArrayIndexOutOfBoundsException if index out of range
     */
    fun get(index: Int): Key {
        initState().use { state ->
            if (index >= 0 && index < getCardinality()) {
                val dataIndex = getIndex(state, index.toLong())
                val entry = getEntry(state, bmd.initials, dataIndex)
                return DefaultLeafKeyList(entry.getKey())
            }
            throw IndexOutOfBoundsException("Index: $index, Size: ${getCardinality()}")
        }
    }

    /**
     * Find index of a key using binary search.
     *
     * @param key Key to find
     * @return Index if found (>= 0), or -(insertionPoint + 1) if not found
     */
    fun indexOf(key: Key): Int {
        initState().use { state ->
            return try {
                search(state, key.getName())
            } catch (e: IOException) {
                -getCardinality() - 1
            }
        }
    }

    /**
     * Find next key in dictionary order.
     *
     * @param key Current key
     * @return Next key, or null if at end
     */
    override fun findNextKey(key: Key): Key? {
        initState().use { state ->
            val currentIndex = try {
                search(state, key.getName())
            } catch (e: IOException) {
                return null
            }

            if (currentIndex < 0) return null

            val nextIndex = currentIndex + 1
            if (nextIndex >= getCardinality()) return null

            val dataIndex = getIndex(state, nextIndex.toLong())
            val entry = getEntry(state, "next", dataIndex)
            return DefaultLeafKeyList(entry.getKey())
        }
    }

    /**
     * Find previous key in dictionary order.
     *
     * @param key Current key
     * @return Previous key, or null if at beginning
     */
    override fun findPreviousKey(key: Key): Key? {
        initState().use { state ->
            val currentIndex = try {
                search(state, key.getName())
            } catch (e: IOException) {
                return null
            }

            if (currentIndex <= 0) return null

            val prevIndex = currentIndex - 1
            val dataIndex = getIndex(state, prevIndex.toLong())
            val entry = getEntry(state, "previous", dataIndex)
            return DefaultLeafKeyList(entry.getKey())
        }
    }

    /**
     * Get all dictionary keys (used by SwordDictionary).
     * This reads through the entire index - avoid calling frequently.
     *
     * @return List of all entry key names, sorted case-insensitively
     */
    fun getAllKeys(): List<String> {
        initState().use { state ->
            val total = getCardinality()
            val keys = mutableListOf<String>()

            for (i in 0 until total) {
                try {
                    val dataIndex = getIndex(state, i.toLong())
                    val entry = getEntry(state, "scan", dataIndex)
                    keys.add(entry.getKey())
                } catch (e: Exception) {
                    // Skip malformed entries
                }
            }

            return keys.sortedWith(String.CASE_INSENSITIVE_ORDER)
        }
    }

    companion object {
        /**
         * Strong's number pattern: G or H, followed by digits, optional letter suffix.
         * Examples: G1, H5678, G1234a, H5678!a (NASB format)
         */
        private val STRONGS_PATTERN = Regex("^([GH])(\\d+)((!)?([a-z])?)$")
    }
}
