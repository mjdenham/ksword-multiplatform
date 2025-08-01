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
 * © CrossWire Bible Society, 2013 - 2016
 *
 */
package org.crosswire.ksword.book.sword.state

import okio.FileHandle
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.SYSTEM
import org.crosswire.ksword.book.BookMetaData
import org.crosswire.ksword.book.sword.BlockType
import org.crosswire.ksword.book.sword.SwordConstants
import org.crosswire.ksword.book.sword.SwordUtil
import org.crosswire.ksword.versification.Testament

/**
 * Stores the random access files required for processing the passage request.
 *
 * The caller is required to close to correctly free resources and avoid File
 * pointer leaks.
 */
class ZVerseBackendState(bookMetaData: BookMetaData, blockType: BlockType) :
    AbstractOpenFileState(bookMetaData) {
    override fun releaseResources() {
        println("Releasing ZVerseBackendState files")
        otIdxFile?.close()
        otCompFile?.close()
        otTextFile?.close()
        ntIdxFile?.close()
        ntCompFile?.close()
        ntTextFile?.close()
        otIdxFile = null
        otCompFile = null
        otTextFile = null
        ntIdxFile = null
        ntCompFile = null
        ntTextFile = null
    }

    /**
     * Get the index file for the given testament.
     *
     * @param testament the testament for the index
     * @return the index for the testament
     */
    fun getIdxFile(testament: Testament): FileHandle? {
        return if (testament == Testament.NEW) ntIdxFile else otIdxFile
    }

    /**
     * Get the compression file for the given testament.
     *
     * @param testament the testament for the index
     * @return the index for the testament
     */
    fun getCompFile(testament: Testament): FileHandle? {
        return if (testament == Testament.NEW) ntCompFile else otCompFile
    }

    /**
     * Get the text file for the given testament.
     *
     * @param testament the testament for the index
     * @return the index for the testament
     */
    fun getTextFile(testament: Testament): FileHandle? {
        return if (testament == Testament.NEW) ntTextFile else otTextFile
    }

    /**
     * The index random access files
     */
    private var otIdxFile: FileHandle? = null
    private var ntIdxFile: FileHandle? = null

    /**
     * The compressed random access files
     */
    private var otCompFile: FileHandle? = null
    private var ntCompFile: FileHandle? = null

    /**
     * The data random access files
     */
    private var otTextFile: FileHandle? = null
    private var ntTextFile: FileHandle? = null

    var lastLoadedBlock: LastLoadedBlock? = null

    init {
        println("Opening ZVerseBackendState files")
        val path = SwordUtil.getExpandedDataPath(bookMetaData)

        val otAllButLast = path.resolve(SwordConstants.FILE_OT + '.' + blockType.indicator + SUFFIX_PART1).toString()
        val otIndexFile = (otAllButLast + SUFFIX_INDEX).toPath()
        if (FileSystem.SYSTEM.exists(otIndexFile)) {
            otIdxFile = FileSystem.SYSTEM.openReadOnly(otIndexFile)
            otTextFile = FileSystem.SYSTEM.openReadOnly((otAllButLast + SUFFIX_TEXT).toPath())
            otCompFile = FileSystem.SYSTEM.openReadOnly((otAllButLast + SUFFIX_COMP).toPath())
        }

        val ntAllButLast = path.resolve(SwordConstants.FILE_NT + '.' + blockType.indicator + SUFFIX_PART1).toString()
        val ntIndexFile = (ntAllButLast + SUFFIX_INDEX).toPath()
        if (FileSystem.SYSTEM.exists(ntIndexFile)) {
            ntIdxFile = FileSystem.SYSTEM.openReadOnly((ntAllButLast + SUFFIX_INDEX).toPath())
            ntTextFile = FileSystem.SYSTEM.openReadOnly((ntAllButLast + SUFFIX_TEXT).toPath())
            ntCompFile = FileSystem.SYSTEM.openReadOnly((ntAllButLast + SUFFIX_COMP).toPath())
        }
    }

    companion object {
        private const val SUFFIX_PART1 = "z"

        private const val SUFFIX_INDEX = "v"
        private const val SUFFIX_COMP = "s"
        private const val SUFFIX_TEXT = "z"
    }
}
