package org.crosswire.ksword.book.sword

import okio.FileSystem
import okio.Path
import okio.SYSTEM
import org.crosswire.ksword.book.sword.SwordConstants.DIR_CATALOG
import org.crosswire.ksword.book.sword.SwordConstants.DIR_CONF

object SwordBookPath {
    lateinit var swordBookPath: Path

    val confDir: Path
        get() = swordBookPath.resolve(DIR_CONF)

    val catalogDir: Path
        get() = swordBookPath.resolve(DIR_CATALOG)

    /**
     * Get a list of books in a given location.
     *
     * @param bookDir
     * the directory in which to look
     * @return the list of books in that location
     */
    fun getBookList(bookDir: Path): List<Path> {
        return FileSystem.SYSTEM.list(bookDir).filter { it.name.endsWith(".conf") }
    }

}