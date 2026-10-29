package org.crosswire.ksword.book.sword

import okio.FileSystem
import okio.Path.Companion.toPath
import okio.SYSTEM
import org.crosswire.ksword.book.BookException
import org.crosswire.ksword.book.sword.state.GenBookBackendState
import org.crosswire.ksword.book.sword.state.OpenFileStateManager
import org.crosswire.ksword.passage.Key
import org.crosswire.ksword.passage.KeyText
import org.crosswire.ksword.passage.NoSuchKeyException
import org.crosswire.ksword.passage.TreeKey

/**
 * Backend for RawGenBook modules: a tree of named nodes, each optionally holding a slice of uncompressed text.
 *
 * - `.idx`: 4-byte LE offsets into `.dat`; tree links below are offsets into this file.
 * - `.dat`: per node `parent LE32, nextSibling LE32, firstChild LE32, name\0, dataSize LE16, data`, where data is
 *   `(start LE32, size LE32)` into `.bdt` for nodes with text.
 * - `.bdt`: the text.
 *
 * The tree is small, so it is read once and kept in memory.
 */
internal class GenBookBackend(bookMetaData: SwordBookMetaData) : AbstractBackend<GenBookBackendState>(bookMetaData) {

    internal class Node(val key: TreeKey, val start: Int, val size: Int)

    private class Tree(val nodes: List<Node>) {
        val keys: List<TreeKey> = nodes.map { it.key }

        // SWORD resolves a path to the first of any same-named siblings, so later ones are reachable only by TreeKey.
        val byPath: Map<String, Node> = HashMap<String, Node>().also { map -> nodes.forEach { map.getOrPut(it.key.getOsisRef()) { it } } }
    }

    private val tree: Tree by lazy { readTree() }

    override fun initState(): GenBookBackendState = OpenFileStateManager.getGenBookBackendState(bmd)

    fun keys(): List<TreeKey> = loadedTree().keys

    /** Resolves a path such as "Part 1/Chapter 3"; a leading "/" and spaces around each name are ignored, as in SWORD. */
    fun find(path: String): TreeKey? = findNode(path)?.key

    override fun readRawContent(state: GenBookBackendState, key: Key): String {
        val node = requireNode(key)
        if (node.size == 0) return ""
        return SwordUtil.decode(key.getOsisRef(), SwordUtil.readFile(state.bdtFile, node.start, node.size), bmd.bookCharset)
    }

    override fun getRawText(key: Key): String {
        requireNode(key)
        return super.getRawText(key)
    }

    override fun readToOsis(key: Key): List<KeyText> {
        val node = requireNode(key)
        return listOf(KeyText(node.key, getRawText(node.key)))
    }

    override fun contains(key: Key): Boolean = (nodeFor(key)?.size ?: 0) > 0

    override fun findNextKey(key: Key): Key? = nextWithText(key) { from, size -> from + 1 until size }

    override fun findPreviousKey(key: Key): Key? = nextWithText(key) { from, _ -> from - 1 downTo 0 }

    private fun nextWithText(key: Key, range: (from: Int, size: Int) -> IntProgression): Key? {
        val nodes = loadedTree().nodes
        val from = nodeFor(key)?.key?.ordinal ?: return null
        return range(from, nodes.size).firstOrNull { nodes[it].size > 0 }?.let { nodes[it].key }
    }

    private fun requireNode(key: Key): Node = nodeFor(key) ?: throw NoSuchKeyException("No entry for '$key' in ${bmd.initials}")

    private fun nodeFor(key: Key): Node? {
        val nodes = loadedTree().nodes
        // By ordinal first, so a same-named sibling resolves to itself rather than to the first by path.
        if (key is TreeKey) nodes.getOrNull(key.ordinal)?.takeIf { it.key == key }?.let { return it }
        return findNode(key.getOsisRef())
    }

    private fun findNode(path: String): Node? {
        val byPath = loadedTree().byPath
        return byPath[path] ?: byPath[path.split('/').map { it.trim() }.filter { it.isNotEmpty() }.joinToString("/")]
    }

    private fun loadedTree(): Tree =
        try {
            tree
        } catch (e: BookException) {
            throw e
        } catch (e: Exception) {
            throw BookException("Unable to read the contents of ${bmd.initials}", e)
        }

    private fun readTree(): Tree {
        val base = SwordUtil.getExpandedDataPath(bmd).toString()
        val idx = FileSystem.SYSTEM.read((base + SwordConstants.EXTENSION_INDEX).toPath()) { readByteArray() }
        val dat = FileSystem.SYSTEM.read((base + SwordConstants.EXTENSION_DATA).toPath()) { readByteArray() }
        return Tree(parseTree(idx, dat, bmd.bookCharset))
    }

    companion object {
        // Real books nest a handful of levels; a corrupt chain would otherwise build ever longer paths.
        private const val MAX_DEPTH = 64

        /** The nodes below the root in reading order (depth first). Throws on malformed data rather than looping or recursing deeply. */
        internal fun parseTree(idx: ByteArray, dat: ByteArray, charset: String): List<Node> {
            val nodes = mutableListOf<Node>()
            val visited = hashSetOf(0)
            // Pending (idx offset, parent) pairs; a node's first child is pushed after its next sibling so it is read first.
            val pending = ArrayDeque<Pair<Int, TreeKey?>>()
            pending.addLast(SwordUtil.decodeLittleEndian32(dat, SwordUtil.decodeLittleEndian32(idx, 0) + 8) to null)

            while (pending.isNotEmpty()) {
                val (idxOffset, parent) = pending.removeLast()
                // Links are idx offsets, -1 for none; a corrupt module could loop, so each node is read once.
                if (idxOffset < 0 || idxOffset + 4 > idx.size || !visited.add(idxOffset)) continue

                var pos = SwordUtil.decodeLittleEndian32(idx, idxOffset)
                val next = SwordUtil.decodeLittleEndian32(dat, pos + 4)
                val firstChild = SwordUtil.decodeLittleEndian32(dat, pos + 8)
                pos += 12
                val nameEnd = SwordUtil.findByte(dat, pos, 0)
                if (nameEnd < 0) throw BookException("Truncated tree node at $idxOffset")
                val name = SwordUtil.decode(null, dat, pos, nameEnd - pos, charset).trim()
                pos = nameEnd + 1
                val hasText = SwordUtil.decodeLittleEndian16(dat, pos) >= 8
                pos += 2

                val key = TreeKey(name, parent, nodes.size)
                if (key.depth >= MAX_DEPTH) throw BookException("Tree deeper than $MAX_DEPTH at $idxOffset")
                parent?.childList?.add(key)
                nodes += Node(
                    key,
                    start = if (hasText) SwordUtil.decodeLittleEndian32(dat, pos) else 0,
                    size = if (hasText) SwordUtil.decodeLittleEndian32(dat, pos + 4) else 0,
                )
                pending.addLast(next to parent)
                pending.addLast(firstChild to key)
            }
            return nodes
        }
    }
}
