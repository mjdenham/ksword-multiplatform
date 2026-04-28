package org.crosswire.ksword.book.sword

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DataEntryTest {

    @Test
    fun getBlockIndex_decodesEightBytesAfterCrlf() {
        // "Aaron\r\n" = 7 bytes, then blockNum=7 (LE32), blockEntry=3 (LE32)
        val data = "Aaron\r\n".encodeToByteArray() + le32(7) + le32(3)
        val entry = DataEntry("test", data, "UTF-8")

        val idx = entry.getBlockIndex()
        assertEquals(7, idx.offset)
        assertEquals(3, idx.size)
    }

    @Test
    fun getBlockIndex_decodesEightBytesAfterLf() {
        // "Aaron\n" + LE32(0) + LE32(0)
        val data = "Aaron\n".encodeToByteArray() + le32(0) + le32(0)
        val entry = DataEntry("test", data, "UTF-8")

        val idx = entry.getBlockIndex()
        assertEquals(0, idx.offset)
        assertEquals(0, idx.size)
    }

    @Test
    fun getBlockIndex_handlesLargeBlockNumbers() {
        val data = "Word\r\n".encodeToByteArray() + le32(123456) + le32(789)
        val entry = DataEntry("test", data, "UTF-8")

        val idx = entry.getBlockIndex()
        assertEquals(123456, idx.offset)
        assertEquals(789, idx.size)
    }

    @Test
    fun getBlockIndex_returnsZeroOnEmptyData() {
        val entry = DataEntry("test", ByteArray(0), "UTF-8")

        val idx = entry.getBlockIndex()
        assertEquals(0, idx.offset)
        assertEquals(0, idx.size)
    }

    @Test
    fun getBlockIndex_returnsZeroWhenNoTerminator() {
        val data = "NoNewline".encodeToByteArray()
        val entry = DataEntry("test", data, "UTF-8")

        val idx = entry.getBlockIndex()
        assertEquals(0, idx.offset)
        assertEquals(0, idx.size)
    }

    @Test
    fun getBlockIndex_returnsZeroWhenInsufficientBytesAfterTerminator() {
        // Only 4 bytes after LF, not 8
        val data = "Aaron\n".encodeToByteArray() + le32(7)
        val entry = DataEntry("test", data, "UTF-8")

        val idx = entry.getBlockIndex()
        assertEquals(0, idx.offset)
        assertEquals(0, idx.size)
    }

    @Test
    fun getKey_stillWorksWithBlockReferenceTrailing() {
        // Regression: the new 8-byte trailer must not confuse getKey().
        val data = "Aaron\r\n".encodeToByteArray() + le32(7) + le32(3)
        val entry = DataEntry("test", data, "UTF-8")

        assertEquals("Aaron", entry.getKey())
    }

    // --- bodyOnly mode (used by ZLDBackend for resolved zLD entries) ---

    @Test
    fun bodyOnly_getRawText_returnsWholeDataEvenWithEmbeddedNewlines() {
        // Regression: previously findKeyTerminator would treat the first \n
        // as a key terminator and silently drop everything before it.
        val data = "<entry n=\"Aaron\">\n  <orth>AARON</orth>\n  <def>...</def>\n</entry>".encodeToByteArray()
        val entry = DataEntry("Aaron", data, "UTF-8", bodyOnly = true)

        val text = entry.getRawText(null)
        assertTrue(text.startsWith("<entry n=\"Aaron\">"), "Should not drop content before first \\n: was '$text'")
        assertTrue(text.contains("<orth>AARON</orth>"))
    }

    @Test
    fun bodyOnly_isLinkEntry_detectsBareLinkPayload() {
        // ZLD link entries have no key+\n prefix in the resolved body — just "@LINK target".
        val data = "@LINK Aaron".encodeToByteArray()
        val entry = DataEntry("aliasOfAaron", data, "UTF-8", bodyOnly = true)

        assertTrue(entry.isLinkEntry(), "Bare @LINK payload should be detected")
        assertEquals("Aaron", entry.getLinkTarget())
    }

    @Test
    fun bodyOnly_isLinkEntry_falseForNonLinkBody() {
        val data = "Some content\nwith a newline".encodeToByteArray()
        val entry = DataEntry("test", data, "UTF-8", bodyOnly = true)

        assertFalse(entry.isLinkEntry())
        assertEquals("", entry.getLinkTarget())
    }

    @Test
    fun rawLD_isLinkEntry_stillDetectsKeyPrefixedLink() {
        // RawLD link entry: "key\r\n@LINK target"
        val data = "alias\r\n@LINK Aaron".encodeToByteArray()
        val entry = DataEntry("alias", data, "UTF-8")  // bodyOnly defaults to false

        assertTrue(entry.isLinkEntry())
        assertEquals("Aaron", entry.getLinkTarget())
    }

    @Test
    fun rawLD_getRawText_stillStripsKeyPrefix() {
        // Regression: default (non-bodyOnly) path must keep RawLD's existing behaviour.
        val data = "Aaron\r\nAaron, son of Amram".encodeToByteArray()
        val entry = DataEntry("Aaron", data, "UTF-8")

        assertEquals("Aaron, son of Amram", entry.getRawText(null))
    }

    private fun le32(value: Int): ByteArray {
        val out = ByteArray(4)
        SwordUtil.encodeLittleEndian32(value, out, 0)
        return out
    }
}
