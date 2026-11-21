package org.crosswire.ksword.book.sword

import java.nio.charset.Charset
import kotlin.test.Test
import kotlin.test.assertEquals

/** Checks every Windows-1252 byte against the JVM's own table. */
class Windows1252OracleTest {

    // Windows-1252 leaves these five bytes undefined; ksword reads them as a space, as JSword does.
    private val undefined = setOf(0x81, 0x8D, 0x8F, 0x90, 0x9D)

    @Test
    fun everyHighByteMatchesTheJvm() {
        val jvm = Charset.forName("windows-1252")
        for (b in 0x80..0xFF) {
            val expected = if (b in undefined) " " else String(byteArrayOf(b.toByte()), jvm)
            assertEquals(expected, SwordUtil.decode(null, byteArrayOf(b.toByte()), "Latin-1"), "byte 0x${b.toString(16)}")
        }
    }
}
