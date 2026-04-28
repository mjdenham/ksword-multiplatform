package org.crosswire.ksword.book.sword

import org.crosswire.ksword.book.sword.state.OpenFileStateManager
import org.crosswire.ksword.book.sword.state.RawLDBackendState
import org.crosswire.ksword.book.sword.state.ZLDBackendState

/**
 * Backend for reading zLD (compressed lexicon dictionary) modules.
 *
 * Layout per module:
 * - `.idx` / `.dat`: dictionary key index. Each .dat slice is `[key text][\r?\n][4-byte blockNum LE][4-byte blockEntry LE]`
 *   so the standard RawLD binary search by key still works.
 * - `.zdx`: compressed-block index, 8 bytes per entry: `(blockStart LE32, blockSize LE32)` into `.zdt`.
 * - `.zdt`: concatenated compressed blocks. Each block, once decompressed, has:
 *     - 4 bytes: entryCount (unused)
 *     - then `entryCount` rows of `(entryStart LE32, entrySize LE32)` (8 bytes each)
 *     - then the entry payloads
 *
 * Currently only ZIP compression is supported (sufficient for ISBE and most
 * common zLD modules). LZSS / BZIP2 / GZIP / XZ throw an explicit error.
 *
 * @author Joe Walker (JSword original)
 * @author DM Smith (JSword original)
 */
class ZLDBackend(
    bookMetaData: SwordBookMetaData
) : RawLDBackend(bookMetaData, ZLD_DATA_SIZE) {

    override fun initState(): ZLDBackendState =
        OpenFileStateManager.getZLDBackendState(bmd)

    override fun getEntry(state: RawLDBackendState, entry: DataEntry): DataEntry {
        val zState = state as ZLDBackendState
        val empty = { DataEntry(entry.name, ByteArray(0), entry.charset, bodyOnly = true) }

        val blockIdx = entry.getBlockIndex()
        val blockNum = blockIdx.offset.toLong()
        val blockEntry = blockIdx.size

        if (blockNum != zState.lastBlockNum) {
            val zdx = zState.getZdxFile() ?: return empty()
            val zdt = zState.getZdtFile() ?: return empty()

            val zdxBuf = SwordUtil.readFile(zdx, (blockNum * ZDX_ENTRY_SIZE).toInt(), ZDX_ENTRY_SIZE)
            if (zdxBuf.size < ZDX_ENTRY_SIZE) return empty()

            val blockStart = SwordUtil.decodeLittleEndian32(zdxBuf, 0)
            val blockSize = SwordUtil.decodeLittleEndian32(zdxBuf, 4)

            // jsword applies Sapphire cipher here. Skip when no key is configured (ISBE has none).
            // TODO: implement Sapphire decipher and apply to the compressed block when KEY_CIPHER_KEY is set.
            val ctype = bmd.getProperty(SwordBookMetaData.KEY_COMPRESS_TYPE) ?: "ZIP"
            zState.lastUncompressed = when (ctype.uppercase()) {
                "ZIP" -> SwordUtil.readAndInflateFileUnknownSize(zdt, blockStart, blockSize)
                else -> throw UnsupportedOperationException(
                    "zLD compression '$ctype' not yet supported (ksword currently supports ZIP)"
                )
            }
            zState.lastBlockNum = blockNum
        }

        val u = zState.lastUncompressed
        if (u.size < BLOCK_HEADER_SIZE) return empty()

        // Validate against the per-block entry count stored in the first 4 bytes —
        // matches jsword and prevents reading entry-payload bytes as if they were a table row.
        val entryCount = SwordUtil.decodeLittleEndian32(u, 0)
        if (blockEntry < 0 || blockEntry >= entryCount) return empty()

        val tableOffset = BLOCK_HEADER_SIZE + blockEntry * BLOCK_ENTRY_SIZE
        if (tableOffset + BLOCK_ENTRY_SIZE > u.size) return empty()

        val entryStart = SwordUtil.decodeLittleEndian32(u, tableOffset)
        var entrySize = SwordUtil.decodeLittleEndian32(u, tableOffset + 4)

        // jsword: strip a single trailing 0x00 if it falls exactly at end-of-slice.
        val nul = SwordUtil.findByte(u, entryStart, 0)
        if (nul in entryStart until entryStart + entrySize &&
            (nul - entryStart + 1) == entrySize
        ) {
            entrySize -= 1
        }

        if (entryStart < 0 || entrySize < 0 || entryStart + entrySize > u.size) return empty()

        val bytes = ByteArray(entrySize)
        u.copyInto(bytes, 0, entryStart, entryStart + entrySize)
        return DataEntry(entry.name, bytes, entry.charset, bodyOnly = true)
    }

    companion object {
        private const val ZLD_DATA_SIZE = 4
        private const val ZDX_ENTRY_SIZE = 8

        /** Size in bytes of the entry-count header at the start of each decompressed block. */
        private const val BLOCK_HEADER_SIZE = 4

        /** Size in bytes of one (entryStart, entrySize) row in the in-block table. */
        private const val BLOCK_ENTRY_SIZE = 8
    }
}
