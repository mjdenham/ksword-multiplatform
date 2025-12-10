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

/**
 * A simple Key implementation that holds a single string key name.
 * Used for dictionary entries where the key is just a name (not a verse reference).
 * This is an immutable leaf node that cannot have children.
 *
 * @param name The name/key of this dictionary entry
 * @author Joe Walker (JSword original)
 */
class DefaultLeafKeyList(
    private val name: String
) : Key {

    // Key identification
    override fun getName(): String = name

    override fun getName(base: Key?): String = name

    override fun getRootName(): String = name

    override fun getOsisRef(): String = name

    override fun getOsisID(): String = name

    // Leaf node - cannot have children
    override fun canHaveChildren(): Boolean = false

    override fun getChildCount(): Int = 0

    override fun getCardinality(): Int = 1

    override fun isEmpty(): Boolean = false

    // Single item container
    override fun contains(key: Key): Boolean = this == key

    override fun get(index: Int): Key? = if (index == 0) this else null

    override fun indexOf(that: Key): Int = if (this == that) 0 else -1

    // Mutations throw - this is immutable
    override fun addAll(key: Key) {
        throw UnsupportedOperationException("Cannot add to leaf key")
    }

    override fun removeAll(key: Key) {
        throw UnsupportedOperationException("Cannot remove from leaf key")
    }

    override fun retainAll(key: Key) {
        throw UnsupportedOperationException("Cannot retain in leaf key")
    }

    override fun clear() {
        throw UnsupportedOperationException("Cannot clear leaf key")
    }

    // Cloning and iteration
    override fun clone(): Key = DefaultLeafKeyList(name)

    override fun iterator(): Iterator<Key> = listOf<Key>(this).iterator()

    // Comparison
    override fun compareTo(other: Key): Int = name.compareTo(other.getName())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DefaultLeafKeyList) return false
        return name == other.name
    }

    override fun hashCode(): Int = name.hashCode()

    override fun toString(): String = name
}
