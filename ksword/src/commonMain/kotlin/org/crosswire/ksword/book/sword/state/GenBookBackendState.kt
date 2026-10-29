package org.crosswire.ksword.book.sword.state

import okio.FileHandle
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.SYSTEM
import org.crosswire.ksword.book.BookMetaData
import org.crosswire.ksword.book.sword.SwordUtil

internal class GenBookBackendState(bookMetaData: BookMetaData) : AbstractOpenFileState(bookMetaData) {

    val bdtFile: FileHandle = FileSystem.SYSTEM.openReadOnly((SwordUtil.getExpandedDataPath(bookMetaData).toString() + EXTENSION_BOOK_DATA).toPath())

    override fun releaseResources() {
        bdtFile.close()
    }

    companion object {
        private const val EXTENSION_BOOK_DATA = ".bdt"
    }
}
