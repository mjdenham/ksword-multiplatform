package org.crosswire.ksword.book.sword

import kotlinx.coroutines.test.runTest
import okio.FileSystem
import okio.Path.Companion.toPath
import org.crosswire.ksword.book.Books
import org.crosswire.ksword.book.install.sword.HttpsSwordInstaller
import org.crosswire.ksword.book.install.sword.SwordInstallerFactory
import org.junit.After
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SwordBookDriverTest {

    private lateinit var swordBookDriver: SwordBookDriver

    private lateinit var httpsSwordInstaller: HttpsSwordInstaller

    private val dir = FileSystem.SYSTEM_TEMPORARY_DIRECTORY.resolve("SwordBookDriverTest".toPath())

    @Before
    fun setUp() {
        SwordBookPath.swordBookPath = dir
        FileSystem.SYSTEM.createDirectories(SwordBookPath.swordBookPath)

        httpsSwordInstaller = SwordInstallerFactory().crosswireInstaller

        swordBookDriver = SwordBookDriver()
        Books.refresh()
    }

    @After
    fun tearDown() {
        FileSystem.SYSTEM.deleteRecursively(SwordBookPath.swordBookPath)
    }

    @Test
    fun canRemoveBook() = runTest {
        val initials = "AB"
        httpsSwordInstaller.install(initials)
        val book = Books.getBook(initials)
        assertTrue(book?.initials == initials)

        swordBookDriver.delete(book!!)
        assertNull(Books.getBook(initials))
    }
}