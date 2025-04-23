/**
 * Distribution License:
 * JSword is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License, version 2.1 or later
 * as published by the Free Software Foundation. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 */
package org.crosswire.ksword.book.sword

import okio.Path
import org.crosswire.common.util.Log
import org.crosswire.ksword.book.Book
import org.martin.ktar.isDirectory

/**
 * This represents all of the Sword Books (aka modules).
 */
class SwordBookDriver { //} : AbstractBookDriver() {
    val driverName: String = "Sword"

    val books: List<Book>
        get() {
            return getBooks(SwordBookPath.confDir).toList()
        }

    private fun getBooks(confDir: Path): List<Book> {
        if (!confDir.isDirectory()) {
            Log.w("mods.d directory at $confDir does not exist")
            return emptyList()
        }

        val bookConfPaths: List<Path> = SwordBookPath.getBookList(confDir)
        return bookConfPaths.map { bookConfPath ->
            try {
                  val sbmd = SwordBookMetaData.createFromFile(bookConfPath, SwordBookPath.swordBookPath)

                sbmd.driver = this
                createBook(sbmd)
            } catch (e: Exception) {
                Log.e("Couldn't create SwordBookMetaData: $bookConfPath", e)
                null
            }
        }.filterNotNull()
    }

    /**
     * Create a Book appropriate for the BookMetaData
     */
    private fun createBook(sbmd: SwordBookMetaData): Book {
        val modtype: BookType = sbmd.bookType
        return modtype.createBook(sbmd)
    }
}