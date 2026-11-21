package org.crosswire.ksword.book.sword

/** Windows-1252, the single-byte encoding SWORD calls Latin-1. */
internal object Windows1252 {

    /** Control characters other than tab and line breaks, and the five bytes the encoding leaves unused, are read as a space, as JSword does. */
    fun decode(data: ByteArray, start: Int, end: Int): String = buildString(end - start) {
        for (i in start until end) {
            val byte = data[i].toInt() and 0xFF
            append(
                when {
                    byte in 0x80..0x9F -> HIGH_CONTROLS[byte - 0x80].toChar()
                    byte < 0x20 && byte != TAB && byte != LINE_FEED && byte != CARRIAGE_RETURN -> ' '
                    else -> byte.toChar()
                }
            )
        }
    }

    private const val TAB = 0x09
    private const val LINE_FEED = 0x0A
    private const val CARRIAGE_RETURN = 0x0D

    // The encoding matches Unicode everywhere but 0x80..0x9F. Copied from Python's cp1252 codec, the five unused bytes as a space.
    private val HIGH_CONTROLS = intArrayOf(
        0x20AC, 0x0020, 0x201A, 0x0192, 0x201E, 0x2026, 0x2020, 0x2021, 0x02C6, 0x2030, 0x0160, 0x2039, 0x0152, 0x0020, 0x017D, 0x0020,
        0x0020, 0x2018, 0x2019, 0x201C, 0x201D, 0x2022, 0x2013, 0x2014, 0x02DC, 0x2122, 0x0161, 0x203A, 0x0153, 0x0020, 0x017E, 0x0178,
    )
}
