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
 * © CrossWire Bible Society, 2008 - 2016
 *
 */
package org.crosswire.common.util

/**
 * An `ItemIterator` is an `Iterator` that iterates a
 * single item.
 *
 * @param <T> The type of the single element that this iterator will return.
 * @see gnu.lgpl.License The GNU Lesser General Public License for details.
 *
 * @author DM Smith
</T> */
class ItemIterator<T>(private val item: T) : MutableIterator<T> {
    /* (non-Javadoc)
     * @see java.util.Iterator#hasNext()
     */
    override fun hasNext(): Boolean {
        return !done
    }

    /* (non-Javadoc)
     * @see java.util.Iterator#next()
     */
    override fun next(): T {
        if (done) {
            throw NoSuchElementException()
        }

        done = true
        return item
    }

    /* (non-Javadoc)
     * @see java.util.Iterator#remove()
     */
    override fun remove() {
        throw UnsupportedOperationException()
    }

    private var done = false
}
