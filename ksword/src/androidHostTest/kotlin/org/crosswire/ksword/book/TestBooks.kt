package org.crosswire.ksword.book

import kotlinx.coroutines.runBlocking
import okio.FileSystem
import okio.Path.Companion.toPath
import org.crosswire.ksword.book.sword.SwordBookPath

/** Undoes a test that installed modules into its own folder. */
object TestBooks {
    private val testFiles = "../testFiles".toPath()

    fun reset() {
        // Books.refresh() never drops books, so installed ones would stay registered for later test classes.
        runBlocking { Books.getBooks() }.forEach { Books.removeBook(it) }
        if (SwordBookPath.swordBookPath != testFiles) {
            FileSystem.SYSTEM.deleteRecursively(SwordBookPath.swordBookPath)
        }
        SwordBookPath.swordBookPath = testFiles
        Books.refresh()
    }
}
