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
 * Holds offset and size information for a dictionary entry.
 * Read from the .idx file during binary search.
 *
 * @param offset Byte offset in .dat file where entry begins
 * @param size Size of entry in bytes
 * @see org.crosswire.jsword.book.sword.DataIndex
 * @author DM Smith (JSword original)
 */
data class DataIndex(
    val offset: Int,
    val size: Int
)
