package org.crosswire.ksword.book.install.sword

import kotlinx.coroutines.test.runTest
import okio.FileSystem
import okio.Path.Companion.toPath
import org.crosswire.ksword.book.sword.SwordBookPath
import org.junit.After
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class HttpsSwordInstallerTest {

    private lateinit var httpsSwordInstaller: HttpsSwordInstaller

    private val dir = FileSystem.SYSTEM_TEMPORARY_DIRECTORY.resolve("HttpsSwordInstallerTest".toPath())

    @Before
    fun setUp() {
        httpsSwordInstaller = SwordInstallerFactory().crosswireInstaller
        SwordBookPath.swordBookPath = dir
        FileSystem.SYSTEM.createDirectories(SwordBookPath.swordBookPath)
    }

    @After
    fun tearDown() {
        FileSystem.SYSTEM.deleteRecursively(SwordBookPath.swordBookPath)
    }


    @Test
    fun loadBookList() = runTest {
        httpsSwordInstaller.loadBookList()
        val books = httpsSwordInstaller.getBooks()
        assertTrue(books.isNotEmpty())
        val bsb = books.find { it.bookMetaData.initials == "BSB" }
        assertTrue(bsb != null)
        books.forEach {
            println(it.bookMetaData.initials + " " + it.bookMetaData.name)
        }
    }
}