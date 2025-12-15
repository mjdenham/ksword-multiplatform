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

import org.crosswire.ksword.book.Book
import org.crosswire.ksword.book.BookMetaData
import org.crosswire.ksword.passage.DefaultLeafKeyList
import org.crosswire.ksword.passage.Key
import org.crosswire.ksword.passage.KeyText
import org.crosswire.ksword.passage.NoSuchKeyException

/**
 * SwordDictionary is for dictionary/lexicon Sword books.
 * Provides dictionary-specific key handling and Strong's number normalization.
 *
 * @param bookMetaData the metadata for this dictionary
 * @param backend the backend for reading dictionary entries
 * @author Joe Walker (JSword original)
 */
class SwordDictionary(
    override var bookMetaData: BookMetaData,
    val backend: RawLDBackend
) : Book {

    override val name: String = bookMetaData.name
    override val initials: String = bookMetaData.initials

    // Delegate core operations to backend
    override fun getNextKey(key: Key): Key? = backend.findNextKey(key)
    override fun getPreviousKey(key: Key): Key? = backend.findPreviousKey(key)
    override fun contains(key: Key): Boolean = backend.contains(key)
    override fun readToOsis(key: Key): List<KeyText> = backend.readToOsis(key)
    override fun getRawText(key: Key): String = backend.getRawText(key)

    /**
     * Get a key for the given text.
     * Throws NoSuchKeyException if the key is not found.
     *
     * This method normalizes Strong's numbers (e.g., "123" -> "G123" or "H123")
     * based on the dictionary name.
     *
     * @param text the key name to look up
     * @return the Key if found
     * @throws NoSuchKeyException if the key is not found
     */
    fun getKey(text: String): Key {
        val normalizedKey = normalizeKey(text)

        if (backend.contains(DefaultLeafKeyList(normalizedKey))) {
            return DefaultLeafKeyList(normalizedKey)
        }

        throw NoSuchKeyException("Key not found: $text")
    }

    /**
     * Get a valid key for the given name.
     * Returns an empty key list if the key is not found (no exception thrown).
     *
     * This is the "safe" version of getKey() that doesn't throw.
     *
     * @param name the key name to look up
     * @return the Key if found, or an empty key if not found
     */
    fun getValidKey(name: String): Key {
        return try {
            getKey(name)
        } catch (e: NoSuchKeyException) {
            createEmptyKeyList()
        }
    }

    /**
     * Create an empty key list.
     *
     * @return an empty Key
     */
    fun createEmptyKeyList(): Key {
        return DefaultLeafKeyList("")
    }

    /**
     * Get the complete list of all dictionary entries.
     * Returns a list of keys containing all entry names.
     *
     * @return list of all dictionary entry keys
     */
    fun getGlobalKeyList(): List<Key> {
        return backend.getAllKeys().map { DefaultLeafKeyList(it) }
    }

    /**
     * Normalize dictionary keys, especially Strong's numbers.
     *
     * Examples:
     * - "123" -> "G123" or "00123" (depending on dictionary format)
     * - "G123" -> "G123" (already normalized)
     * - "g123" -> "G123" (case normalization)
     * - "H456" -> "H456" (Hebrew)
     * - "00001" -> "00001" (zero-padded, return as-is)
     * - "Word" -> "Word" (regular dictionary entry)
     *
     * @param key the key to normalize
     * @return the normalized key
     */
    private fun normalizeKey(key: String): String {
        // If it's already prefixed with G/H, just uppercase it
        if (key.matches(Regex("^[GHgh]\\d+$"))) {
            return key.uppercase()
        }

        // If it's a pure number without zero-padding (1-4 digits), try to add prefix or padding
        if (key.matches(Regex("^\\d{1,4}$"))) {
            // First try zero-padding to 5 digits (common in StrongsRealHebrew)
            val paddedKey = key.padStart(5, '0')
            if (backend.contains(DefaultLeafKeyList(paddedKey))) {
                return paddedKey
            }

            // Try to infer prefix from book metadata name
            val bookName = bookMetaData.name.lowercase()
            val prefix = when {
                bookName.contains("greek") || bookName.contains("robinson") || bookName.contains("thayer") -> "G"
                bookName.contains("hebrew") || bookName.contains("brown") || bookName.contains("bdb") -> "H"
                else -> return key // Can't determine, return as-is
            }
            return "$prefix$key"
        }

        // Already zero-padded (5+ digits) or regular dictionary entry - return as-is
        return key
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SwordDictionary) return false
        return this.bookMetaData == other.bookMetaData
    }

    override fun hashCode(): Int {
        return bookMetaData.hashCode()
    }
}
