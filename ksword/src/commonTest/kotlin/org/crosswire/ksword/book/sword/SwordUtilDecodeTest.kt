package org.crosswire.ksword.book.sword

import kotlin.test.Test
import kotlin.test.assertEquals

/** SWORD reads a module with no Encoding, or Encoding=Latin-1, as Windows-1252. */
class SwordUtilDecodeTest {

    private fun bytes(vararg values: Int) = ByteArray(values.size) { values[it].toByte() }

    /** Smith's Bible Dictionary, AARON: the apostrophe in "Moses' departure" is the single byte 0x92. */
    @Test
    fun readsWindows1252Punctuation() {
        val moses = bytes(0x4D, 0x6F, 0x73, 0x65, 0x73, 0x92)
        assertEquals("Moses" + Char(0x2019), SwordUtil.decode("AARON", moses, "Latin-1"))
    }

    /** Rieger writes German letters as single bytes: 0xFC is u-umlaut, 0xDF is sharp s. */
    @Test
    fun readsLatin1Letters() {
        assertEquals("f" + Char(0xFC) + "r", SwordUtil.decode(null, bytes(0x66, 0xFC, 0x72), "Latin-1"))
        assertEquals("da" + Char(0xDF), SwordUtil.decode(null, bytes(0x64, 0x61, 0xDF), "Latin-1"))
    }

    /** ACV has no Encoding either, yet its few special characters are stored as UTF-8. */
    @Test
    fun keepsUtf8TextInAModuleWithNoEncoding() {
        val dash = bytes(0x61, 0xE2, 0x80, 0x94, 0x62)
        assertEquals("a" + Char(0x2014) + "b", SwordUtil.decode(null, dash, "Latin-1"))
    }

    @Test
    fun readsUtf8ModulesAsBefore() {
        val eAcute = bytes(0x63, 0x61, 0x66, 0xC3, 0xA9)
        assertEquals("caf" + Char(0xE9), SwordUtil.decode(null, eAcute, "UTF-8"))
    }
}
