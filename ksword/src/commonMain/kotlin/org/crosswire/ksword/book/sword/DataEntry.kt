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
     * Finds the terminator (CRLF, LF, or null) and decodes bytes before it.
     *
     * @return The key name, or empty string if malformed
     */
    fun getKey(): String {
        val keyEndIndex = findKeyTerminator()
        if (keyEndIndex < 0) return ""
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
     * Find the index of the key terminator (CRLF, LF, or null).
     * Tries CRLF first (most common), then LF, then null terminator.
     *
     * @return Index of the last byte of the terminator, or -1 if not found
     */
    private fun findKeyTerminator(): Int {
        // Try CRLF first (\r\n)
        for (i in 0 until data.size - 1) {
            if (data[i] == 0x0D.toByte() && data[i + 1] == 0x0A.toByte()) {
                return i
            }
        }

        // Try LF (\n)
        val lfIndex = data.indexOfFirst { it == 0x0A.toByte() }
        if (lfIndex >= 0) return lfIndex

        // Try null terminator (\0)
        val nullIndex = data.indexOfFirst { it == 0.toByte() }
        if (nullIndex >= 0) return nullIndex

        return -1
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
