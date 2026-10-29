package org.crosswire.ksword.passage

/** A node in a general book's tree: [getName] is its own name, [toString] its full path, e.g. "Part 1/Chapter 3". */
class TreeKey internal constructor(
    private val name: String,
    val parent: TreeKey?,
    internal val ordinal: Int,
) : Key {

    /** Nesting level: 0 for a top-level node. */
    val depth: Int = if (parent == null) 0 else parent.depth + 1

    private val path: String = if (parent == null) name else "$parent/$name"

    internal val childList = mutableListOf<TreeKey>()

    val children: List<TreeKey> get() = childList

    override fun getName(): String = name

    override fun getName(base: Key?): String = name

    override fun getRootName(): String = generateSequence(this) { it.parent }.last().name

    override fun getOsisRef(): String = path

    override fun getOsisID(): String = path

    override fun canHaveChildren(): Boolean = true

    override fun getChildCount(): Int = childList.size

    override fun getCardinality(): Int = 1

    override fun isEmpty(): Boolean = false

    override fun contains(key: Key): Boolean = this == key

    override fun get(index: Int): Key? = if (index == 0) this else null

    override fun indexOf(that: Key): Int = if (this == that) 0 else -1

    override fun addAll(key: Key) = throw UnsupportedOperationException("TreeKey is read-only")

    override fun removeAll(key: Key) = throw UnsupportedOperationException("TreeKey is read-only")

    override fun retainAll(key: Key) = throw UnsupportedOperationException("TreeKey is read-only")

    override fun clear() = throw UnsupportedOperationException("TreeKey is read-only")

    override fun clone(): Key = this

    override fun iterator(): Iterator<Key> = listOf<Key>(this).iterator()

    override fun compareTo(other: Key): Int =
        if (other is TreeKey) ordinal.compareTo(other.ordinal) else path.compareTo(other.getOsisRef())

    // By path, as in SWORD; same-named siblings are therefore equal although they compare by ordinal.
    override fun equals(other: Any?): Boolean = other is TreeKey && path == other.path

    override fun hashCode(): Int = path.hashCode()

    override fun toString(): String = path
}
