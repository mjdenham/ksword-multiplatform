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
    private val charset: String
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
        val keyEndIndex = findKeyTerminator()
        if (keyEndIndex < 0) return ""

        // Skip past terminator to get definition start
        val definitionStart = skipTerminator(keyEndIndex)
        if (definitionStart >= data.size) return ""

        val definitionLength = data.size - definitionStart
        val text = SwordUtil.decode(name, data, definitionStart, definitionLength, charset)

        // Apply cipher if needed (for encrypted modules)
        return if (cipherKey != null) {
            decipher(text, cipherKey)
        } else {
            text
        }
    }

    /**
     * Check if this entry is a link to another entry.
     * Link format: "@LINK <target_key>"
     *
     * @return true if this is a link entry
     */
    fun isLinkEntry(): Boolean {
        val text = getRawText(null)
        return text.startsWith("@LINK ")
    }

    /**
     * Get the target key for a link entry.
     *
     * @return Target key name, or empty string if not a link
     */
    fun getLinkTarget(): String {
        val text = getRawText(null)
        return if (isLinkEntry()) {
            text.substring(6).trim()
        } else {
            ""
        }
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
}
