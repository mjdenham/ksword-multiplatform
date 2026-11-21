package org.crosswire.ksword.book.sword

import kotlin.test.Test
import kotlin.test.assertEquals

class Windows1252Test {

    private fun decode(vararg bytes: Int) = Windows1252.decode(ByteArray(bytes.size) { bytes[it].toByte() }, 0, bytes.size)

    @Test
    fun readsOnlyTheRangeAsked() {
        val bytes = byteArrayOf(0x61, 0x62, 0x63, 0x64)
        assertEquals("bc", Windows1252.decode(bytes, 1, 3))
    }

    @Test
    fun keepsTabsAndLineBreaksButBlanksOtherControlCharacters() {
        assertEquals("a\tb\nc\rd", decode(0x61, 0x09, 0x62, 0x0A, 0x63, 0x0D, 0x64))
        assertEquals("a b", decode(0x61, 0x01, 0x62))
    }

    @Test
    fun blanksTheFiveUnusedBytes() {
        assertEquals("     ", decode(0x81, 0x8D, 0x8F, 0x90, 0x9D))
    }
}
