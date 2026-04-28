package org.crosswire.ksword.book.sword.state

import okio.FileHandle
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.SYSTEM
import org.crosswire.ksword.book.BookMetaData
import org.crosswire.ksword.book.sword.SwordUtil

/**
 * State for the zLD (compressed lexicon dictionary) backend.
 *
 * Extends RawLDBackendState (which manages .idx and .dat) and additionally opens
 * the .zdx (compressed-block index) and .zdt (compressed text) files. Caches the
 * most recently decompressed block so consecutive lookups within the same block
 * skip decompression.
 *
 * @author Joe Walker (JSword original)
 * @author DM Smith (JSword original)
 */
class ZLDBackendState(
    bookMetaData: BookMetaData
) : RawLDBackendState(bookMetaData, dataSize = 4) {

    private var zdxFile: FileHandle? = null
    private var zdtFile: FileHandle? = null

    var lastBlockNum: Long = -1L
    var lastUncompressed: ByteArray = ByteArray(0)

    init {
        // super() has already opened .idx and .dat — if zdx/zdt setup fails
        // we must release them to avoid leaking the file handles.
        try {
            val path = SwordUtil.getExpandedDataPath(bookMetaData)
            val zdxPath = (path.toString() + EXTENSION_Z_INDEX).toPath()
            val zdtPath = (path.toString() + EXTENSION_Z_DATA).toPath()

            if (FileSystem.SYSTEM.exists(zdxPath) && FileSystem.SYSTEM.exists(zdtPath)) {
                zdxFile = FileSystem.SYSTEM.openReadOnly(zdxPath)
                zdtFile = FileSystem.SYSTEM.openReadOnly(zdtPath)
            } else {
                throw Exception("zLD compressed files not found: $zdxPath or $zdtPath")
            }
        } catch (t: Throwable) {
            releaseResources()
            throw t
        }
    }

    fun getZdxFile(): FileHandle? = zdxFile
    fun getZdtFile(): FileHandle? = zdtFile

    override fun releaseResources() {
        super.releaseResources()
        zdxFile?.close()
        zdtFile?.close()
        zdxFile = null
        zdtFile = null
        lastBlockNum = -1L
        lastUncompressed = ByteArray(0)
    }

    companion object {
        private const val EXTENSION_Z_INDEX = ".zdx"
        private const val EXTENSION_Z_DATA = ".zdt"
    }
}
