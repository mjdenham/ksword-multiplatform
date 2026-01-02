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
package org.crosswire.ksword.passage

import org.crosswire.ksword.versification.Versification

/**
 * A factory for creating Passage objects from string references.
 * This factory can parse scripture references from various sources including THML, OSIS,
 * and general text formats.
 *
 * Based on JSword's PassageKeyFactory but simplified for minimal functionality.
 *
 * Examples:
 * - "Joh 1:1" - single verse
 * - "Gen 1:1-5" - verse range
 * - "Ge 1:1; Pr 8:22-31" - multiple references
 * - "2" - verse only (requires context)
 *
 * @author Joe Walker (original JSword implementation)
 * @author DM Smith (original JSword implementation)
 * @author Martin Denham (KSword port)
 */
object PassageKeyFactory {

    /**
     * Create an empty passage for the given versification
     *
     * @param v11n The versification to use
     * @return An empty RangedPassage
     */
    fun createEmptyKeyList(v11n: Versification): Passage {
        return RangedPassage(v11n)
    }

    /**
     * Parse a reference string and return a Passage.
     * Throws NoSuchKeyException if the reference cannot be parsed.
     *
     * @param v11n The versification to use
     * @param ref The reference string to parse (e.g., "Joh 1:1", "Ge 1:1; Pr 8:22-31")
     * @param basis Optional context for parsing verse-only references
     * @return A Passage containing the parsed references
     * @throws NoSuchKeyException If the reference cannot be parsed
     */
    fun getKey(v11n: Versification, ref: String, basis: VerseRange? = null): Passage {
        return try {
            RangedPassage(v11n, ref, basis)
        } catch (e: NoSuchVerseException) {
            throw NoSuchKeyException("Cannot parse reference: $ref", e)
        }
    }

    /**
     * Parse a reference string and return a Passage.
     * Returns an empty passage if the reference cannot be parsed.
     *
     * @param v11n The versification to use
     * @param ref The reference string to parse
     * @param basis Optional context for parsing verse-only references
     * @return A Passage containing the parsed references, or an empty passage on error
     */
    fun getValidKey(v11n: Versification, ref: String, basis: VerseRange? = null): Passage {
        return try {
            getKey(v11n, ref, basis)
        } catch (e: NoSuchKeyException) {
            createEmptyKeyList(v11n)
        }
    }
}
