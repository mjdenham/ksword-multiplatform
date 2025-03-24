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
package org.crosswire.ksword.book.sword.state

import okio.Closeable
import org.crosswire.ksword.book.BookMetaData

/**
 * Marker interface for objects holding open files that should be freed up upon finishing
 */
interface OpenFileState : Closeable {
    val bookMetaData: BookMetaData?

    fun releaseResources()

    /**
     * The time at which this instance was last accessed
     */
    var lastAccess: Long
}
