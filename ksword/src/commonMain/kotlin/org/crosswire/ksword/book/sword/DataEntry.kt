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

/**
 * Represents a dictionary entry read from the .dat file.
 * Contains the raw entry data and provides methods to extract key and definition.
 *
 * Entry format: "KeyName\r\n<definition>" or "KeyName\n<definition>" or "KeyName\0<definition>"
 *
 * @param name Context name (for debugging/error messages)
 * @param data Raw entry bytes from .dat file
 * @param charset Character encoding (e.g., "UTF-8", "CP1252")
 * @see org.crosswire.jsword.book.sword.DataEntry
 * @author Joe Walker (JSword original)
 */
class DataEntry(
    val name: String,
    private val data: ByteArray,
    val charset: String,
    /**
     * True when [data] is body content with no leading "key\r?\n" prefix.
     * Set by ZLDBackend after resolving an entry from a compressed `.zdt` block,
     * where the .dat-side key terminator does not exist in the body bytes.
     */
    private val bodyOnly: Boolean = false
) {
    /**
     * Extract the key name from the entry.
     * Handles two formats:
     * 1. Simple: "00001\r\n" or "00001\\r\n" - backslash at end is removed (JSword approach)
     * 2. Formatted: "00001\ extra text\r\n" - backslash separates key from extra metadata
     *
     * @return The key name, or empty string if malformed
     */
    fun getKey(): String {
        // Some entries are empty
        if (data.isEmpty()) {
            return ""
        }

        val keyEndIndex = findKeyTerminator()
        if (keyEndIndex < 0) {
            // No terminator found, return entire data as key
            return SwordUtil.decode(name, data, 0, data.size.coerceAtMost(50), charset)
        }

        // Decode only the bytes before the terminator
        return SwordUtil.decode(name, data, 0, keyEndIndex, charset)
    }

    /**
     * Get the raw text (definition) without the key prefix.
     * Applies cipher decryption if cipherKey is provided.
     *
     * @param cipherKey Optional cipher key for encrypted modules (null for unencrypted)
     * @return The definition text
     */
    fun getRawText(cipherKey: ByteArray?): String {
        val text = if (bodyOnly) {
            // ZLD body-only: data is the entire payload; no key prefix to skip.
            // Match jsword's DataEntry.getRawText (decode whole, trim).
            if (data.isEmpty()) return ""
            SwordUtil.decode(name, data, 0, data.size, charset).trim()
        } else {
            val keyEndIndex = findKeyTerminator()
            if (keyEndIndex < 0) return ""
            val definitionStart = skipTerminator(keyEndIndex)
            if (definitionStart >= data.size) return ""
            SwordUtil.decode(name, data, definitionStart, data.size - definitionStart, charset)
        }

        return if (cipherKey != null) decipher(text, cipherKey) else text
    }

    /**
     * For zLD entries, the .dat slice holds `[key text][\r?\n][4-byte blockNum LE][4-byte blockEntry LE]`.
     * The returned DataIndex packs (blockNum, blockEntry) into (offset, size) — same convention as jsword.
     */
    fun getBlockIndex(): DataIndex {
        val nlIdx = data.indexOfFirst { it == 0x0A.toByte() }
        if (nlIdx < 0 || nlIdx + 1 + 8 > data.size) return DataIndex(0, 0)
        val start = nlIdx + 1
        val blockNum = SwordUtil.decodeLittleEndian32(data, start)
        val blockEntry = SwordUtil.decodeLittleEndian32(data, start + 4)
        return DataIndex(blockNum, blockEntry)
    }

    /**
     * Check if this entry is a link to another entry. Link format: `@LINK <target>`.
     *
     * Matches jsword's byte-level check: looks at data[keyEnd+1..keyEnd+5] where
     * keyEnd is the position of the first \n, or -1 if none. With keyEnd=-1 the
     * check naturally falls back to data[0..4] — exactly what's needed for a zLD
     * body-only entry whose payload is the bare string "@LINK target".
     */
    fun isLinkEntry(): Boolean {
        val keyEnd = SwordUtil.findByte(data, SEP_NL)
        val start = keyEnd + 1
        return start + 5 <= data.size &&
            data[start] == AT_BYTE &&
            data[start + 1] == L_BYTE &&
            data[start + 2] == I_BYTE &&
            data[start + 3] == N_BYTE &&
            data[start + 4] == K_BYTE
    }

    /**
     * Get the target key for a link entry.
     *
     * @return Target key name, or empty string if not a link
     */
    fun getLinkTarget(): String {
        if (!isLinkEntry()) return ""
        val keyEnd = SwordUtil.findByte(data, SEP_NL)
        val linkStart = keyEnd + 1 + 5  // skip "[\n]@LINK"
        if (linkStart >= data.size) return ""

        // linkEnd: next \n after linkStart, or end of data.
        val nextNl = SwordUtil.findByte(data, linkStart, SEP_NL)
        val linkEnd = if (nextNl == -1) data.size - 1 else nextNl
        val len = linkEnd - linkStart + 1
        return SwordUtil.decode(name, data, linkStart, len, charset).trim()
    }

    /**
     * Find the index where the key portion ends.
     * Handles two cases:
     * 1. If backslash has content after it: "00001\ extra\r\n" → returns index of backslash
     * 2. If backslash at end or no backslash: "00001\\r\n" → returns index adjusted to skip trailing \ and \r
     *
     * This combines JSword's approach (remove trailing \) with formatted key support.
     *
     * @return Index where key ends, or -1 if not found
     */
    private fun findKeyTerminator(): Int {
        // Find newline position (CRLF or LF)
        var newlineIndex = -1
        for (i in 0 until data.size - 1) {
            if (data[i] == 0x0D.toByte() && data[i + 1] == 0x0A.toByte()) {
                newlineIndex = i
                break
            }
        }
        if (newlineIndex < 0) {
            newlineIndex = data.indexOfFirst { it == 0x0A.toByte() }
        }
        if (newlineIndex < 0) {
            // No newline, try null terminator
            newlineIndex = data.indexOfFirst { it == 0.toByte() }
        }
        if (newlineIndex < 0) return -1

        // Find backslash position
        val backslashIndex = data.indexOfFirst { it == '\\'.code.toByte() }

        // If there's a backslash before the newline
        if (backslashIndex >= 0 && backslashIndex < newlineIndex) {
            // Check if there's meaningful content between backslash and newline
            // (more than just whitespace and \r)
            val hasContentAfterBackslash = backslashIndex < newlineIndex - 1 &&
                    data.slice(backslashIndex + 1 until newlineIndex).any {
                        it != 0x0D.toByte() && it != 0x20.toByte()
                    }

            if (hasContentAfterBackslash) {
                // Case 2: Formatted key like "00001\ extra text\r\n"
                // Use backslash as terminator
                return backslashIndex
            }
        }

        // Case 1: Simple key like "00001\r\n" or "00001\\r\n"
        // Use newline as terminator, but remove trailing \r and \
        var end = newlineIndex

        // Remove trailing \r if present
        if (end > 0 && data[end - 1] == 0x0D.toByte()) {
            end--
        }

        // Remove trailing \ if present (JSword: "plain text dictionaries all get \ added to the ends")
        if (end > 0 && data[end - 1] == '\\'.code.toByte()) {
            end--
        }

        return end
    }

    /**
     * Calculate the start position of the definition after the key terminator.
     *
     * @param keyEndIndex Index of the last byte before the terminator
     * @return Index where definition starts
     */
    private fun skipTerminator(keyEndIndex: Int): Int {
        // CRLF: skip 2 bytes
        if (keyEndIndex < data.size - 1 &&
            data[keyEndIndex] == 0x0D.toByte() &&
            data[keyEndIndex + 1] == 0x0A.toByte()) {
            return keyEndIndex + 2
        }
        // LF or null: skip 1 byte
        return keyEndIndex + 1
    }

    /**
     * Decipher encrypted text using XOR cipher (same as JSword).
     *
     * @param text Encrypted text
     * @param cipherKey Cipher key bytes
     * @return Decrypted text
     */
    private fun decipher(text: String, cipherKey: ByteArray): String {
        val bytes = text.encodeToByteArray()
        for (i in bytes.indices) {
            bytes[i] = (bytes[i].toInt() xor cipherKey[i % cipherKey.size].toInt()).toByte()
        }
        return bytes.decodeToString()
    }

    companion object {
        private const val SEP_NL: Byte = 0x0A
        private const val AT_BYTE: Byte = 0x40  // '@'
        private const val L_BYTE: Byte = 0x4C   // 'L'
        private const val I_BYTE: Byte = 0x49   // 'I'
        private const val N_BYTE: Byte = 0x4E   // 'N'
        private const val K_BYTE: Byte = 0x4B   // 'K'
    }
}
