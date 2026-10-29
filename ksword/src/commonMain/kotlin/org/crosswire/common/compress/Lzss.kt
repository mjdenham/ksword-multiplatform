package org.crosswire.common.compress

import okio.Buffer

/**
 * Decompressor for SWORD's LZSS block compression, equivalent to SWORD's LZSSCompress::Decode.
 */
internal object Lzss {
    private const val RING_SIZE = 4096
    private const val RING_WRAP = RING_SIZE - 1
    private const val MAX_STORE_LENGTH = 18
    private const val THRESHOLD = 3

    fun uncompress(input: ByteArray): ByteArray {
        val out = Buffer()
        val ring = ByteArray(RING_SIZE)
        var r = RING_SIZE - MAX_STORE_LENGTH
        ring.fill(' '.code.toByte(), 0, r)

        fun emit(b: Byte) {
            out.writeByte(b.toInt())
            ring[r] = b
            r = (r + 1) and RING_WRAP
        }

        var i = 0
        decode@ while (i < input.size) {
            val flags = input[i++].toInt() and 0xFF
            for (bit in 0 until 8) {
                if (((flags shr bit) and 1) != 0) {
                    if (i >= input.size) break@decode
                    emit(input[i++])
                } else {
                    if (i + 2 > input.size) break@decode
                    val lo = input[i++].toInt() and 0xFF
                    val hi = input[i++].toInt() and 0xFF
                    val pos = lo or ((hi and 0xF0) shl 4)
                    repeat((hi and 0x0F) + THRESHOLD) { k -> emit(ring[(pos + k) and RING_WRAP]) }
                }
            }
        }
        return out.readByteArray()
    }
}
