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
 * © CrossWire Bible Society, 2013 - 2016
 *
 */
package org.crosswire.ksword.passage

import org.crosswire.ksword.versification.Versification

/**
 * A VerseKey indicates that a Key has a Versification reference system.
 *
 * @param <T> The type of VerseKey that reversify returns.
 *
 * @author DM Smith
</T> */
interface VerseKey<out T> : Key {
    /**
     * Get the Versification that defines the Verses in this VerseKey.
     *
     * @return this VerseKey Versification.
     */
    fun getVersification(): Versification

    /**
     * Cast this VerseKey into another Versification. OSIS Sub Identifiers are ignored.
     *
     *
     *
     * Note: This is dangerous as it does not consider chapter boundaries
     * or whether the verses in this VerseKey are actually part of the
     * new versification. It should only be used when the start and end
     * verses are in both Versifications. You have been warned.
     *
     *
     * @param newVersification
     * @return this VerseKey Versification.
     */
    //    T reversify(Versification newVersification);
    /**
     * A VerseKey that does not have an OSIS sub identifier is a whole reference.
     *
     * @return whether this is a whole reference
     */
    fun isWhole(): Boolean

    /**
     * Convert this reference into one without a sub-identifier.
     * A Verse with an OSIS sub-identifier represents part of a reference.
     *
     * @return a whole reference
     */
    fun getWhole(): T
}
