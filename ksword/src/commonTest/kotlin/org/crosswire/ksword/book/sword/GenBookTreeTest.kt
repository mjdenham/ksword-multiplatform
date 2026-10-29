package org.crosswire.ksword.book.sword

import okio.Buffer
import okio.FileSystem
import okio.SYSTEM
import okio.use
import org.crosswire.ksword.book.BookException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GenBookTreeTest {

    private class Spec(val name: String, val next: Int = -1, val firstChild: Int = -1, val text: Pair<Int, Int>? = null)

    /** Encodes [specs] as `.idx`/`.dat`; spec i is at idx offset 4 * i and spec 0 is the root. Links are spec indexes. */
    private fun encode(specs: List<Spec>): Pair<ByteArray, ByteArray> {
        val idx = Buffer()
        val dat = Buffer()
        for (spec in specs) {
            idx.writeIntLe(dat.size.toInt())
            dat.writeIntLe(-1)
            dat.writeIntLe(if (spec.next < 0) -1 else spec.next * 4)
            dat.writeIntLe(if (spec.firstChild < 0) -1 else spec.firstChild * 4)
            dat.writeUtf8(spec.name).writeByte(0)
            val text = spec.text
            if (text == null) {
                dat.writeShortLe(0)
            } else {
                dat.writeShortLe(8).writeIntLe(text.first).writeIntLe(text.second)
            }
        }
        return idx.readByteArray() to dat.readByteArray()
    }

    private fun parse(specs: List<Spec>) = encode(specs).let { (idx, dat) -> GenBookBackend.parseTree(idx, dat, "UTF-8") }

    @Test
    fun nodesAreInReadingOrderWithPathsAndDepth() {
        val nodes = parse(
            listOf(
                Spec("", firstChild = 1),
                Spec("Part 1", next = 3, firstChild = 2),
                Spec(" Chapter 1 ", text = 0 to 10),
                Spec("Part 2", text = 10 to 5),
            )
        )
        assertEquals(listOf("Part 1", "Part 1/Chapter 1", "Part 2"), nodes.map { it.key.toString() })
        assertEquals(listOf(0, 1, 0), nodes.map { it.key.depth })
        assertEquals(listOf(0, 10, 5), nodes.map { it.size })
        assertEquals(listOf(nodes[1].key), nodes[0].key.children)
    }

    @Test
    fun siblingLoopIsReadOnce() {
        val nodes = parse(listOf(Spec("", firstChild = 1), Spec("A", next = 2), Spec("B", next = 1)))
        assertEquals(listOf("A", "B"), nodes.map { it.key.toString() })
    }

    @Test
    fun deepChainThrows() {
        val specs = List(1000) { Spec("n$it", firstChild = it + 1) } + Spec("leaf")
        assertFailsWith<BookException> { parse(specs) }
    }

    @Test
    fun truncatedNameThrows() {
        val (idx, dat) = encode(listOf(Spec("", firstChild = 1), Spec("Name")))
        assertFailsWith<BookException> { GenBookBackend.parseTree(idx, dat.copyOf(dat.size - 3), "UTF-8") }
    }

    @Test
    fun readFileTrimsOversizedRead() {
        val path = FileSystem.SYSTEM_TEMPORARY_DIRECTORY / "GenBookTreeTest.bin"
        FileSystem.SYSTEM.write(path) { write(ByteArray(10) { it.toByte() }) }
        try {
            FileSystem.SYSTEM.openReadOnly(path).use { file ->
                assertEquals(listOf<Byte>(5, 6, 7, 8, 9), SwordUtil.readFile(file, 5, Int.MAX_VALUE).toList())
            }
        } finally {
            FileSystem.SYSTEM.delete(path)
        }
    }
}
