package org.crosswire.ksword.book.install.sword

import kotlinx.coroutines.test.runTest
import okio.FileSystem
import okio.Path.Companion.toPath
import org.crosswire.ksword.book.Books
import org.crosswire.ksword.book.sword.SwordBookPath
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class HttpsSwordInstallerTest {

    private lateinit var httpsSwordInstaller: HttpsSwordInstaller

    private val dir = FileSystem.SYSTEM_TEMPORARY_DIRECTORY.resolve("HttpsSwordInstallerTest".toPath())

    @BeforeTest
    fun setUp() {
        httpsSwordInstaller = SwordInstallerFactory().crosswireInstaller
        SwordBookPath.swordBookPath = dir
        FileSystem.SYSTEM.createDirectories(SwordBookPath.swordBookPath)
    }

    @AfterTest
    fun tearDown() {
        FileSystem.SYSTEM.deleteRecursively(SwordBookPath.swordBookPath)
    }

    @Test
    fun loadBookList() = runTest {
        httpsSwordInstaller.loadBookList()
        val catalog = httpsSwordInstaller.getBooks()
        assertTrue(catalog.isNotEmpty())
        val abbott = catalog.find { it.initials == "Abbott" }
        assertTrue(abbott != null)
    }

    @Test
    fun installBook() = runTest {
        val bookInitials = "Abbott"
        httpsSwordInstaller.install(bookInitials)
        assertTrue(Books.getBook(bookInitials)?.initials == bookInitials)
    }
}
