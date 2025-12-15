/**
 * Distribution License:
 * KSword is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License, version 2.1 or later
 * as published by the Free Software Foundation. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * The License is available on the internet at:
 * http://www.gnu.org/copyleft/lgpl.html
 * or by writing to:
 * Free Software Foundation, Inc.
 * 59 Temple Place - Suite 330
 * Boston, MA 02111-1307, USA
 *
 * © CrossWire Bible Society, 2005 - 2016
 *
 */
package org.crosswire.ksword.book.sword.state

import okio.FileHandle
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.SYSTEM
import org.crosswire.ksword.book.BookMetaData
import org.crosswire.ksword.book.sword.SwordConstants
import org.crosswire.ksword.book.sword.SwordUtil

/**
 * State for RawLD/RawLD4 dictionary backends.
 * Manages the index and data file handles for on-demand reading.
 *
 * Unlike the previous implementation, this does NOT pre-load dictionary entries
 * into memory. Index entries are read on-demand during binary search, following
 * JSword's memory-efficient approach.
 *
 * @param bookMetaData metadata for the dictionary
 * @param dataSize 2 for RawLD, 4 for RawLD4 (size of the entry length field in bytes)
 * @author Joe Walker (JSword original)
 * @author DM Smith (JSword original)
 */
class RawLDBackendState(
    bookMetaData: BookMetaData,
    val dataSize: Int  // Made public for backend access
) : AbstractOpenFileState(bookMetaData) {

    private var idxFile: FileHandle? = null
    private var datFile: FileHandle? = null

    /**
     * Size of each index entry in bytes: offset (4) + size (2 or 4)
     */
    val entrySize = 4 + dataSize

    init {
        val path = SwordUtil.getExpandedDataPath(bookMetaData)

        // Dictionary files use .idx and .dat extensions
        // The DataPath from config already includes the full path to the files (without extension)
        val idxPath = (path.toString() + SwordConstants.EXTENSION_INDEX).toPath()
        val datPath = (path.toString() + SwordConstants.EXTENSION_DATA).toPath()

        if (FileSystem.SYSTEM.exists(idxPath) && FileSystem.SYSTEM.exists(datPath)) {
            idxFile = FileSystem.SYSTEM.openReadOnly(idxPath)
            datFile = FileSystem.SYSTEM.openReadOnly(datPath)
        } else {
            throw Exception("Dictionary files not found: $idxPath or $datPath")
        }
    }

    override fun releaseResources() {
        idxFile?.close()
        datFile?.close()
        idxFile = null
        datFile = null
    }

    /**
     * Get the index file handle.
     */
    fun getIdxFile(): FileHandle? = idxFile

    /**
     * Get the data file handle.
     */
    fun getDatFile(): FileHandle? = datFile
}
