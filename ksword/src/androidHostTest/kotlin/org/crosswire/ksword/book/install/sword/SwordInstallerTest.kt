package org.crosswire.ksword.book.install.sword

import kotlinx.coroutines.test.runTest
import okio.FileSystem
import okio.Path.Companion.toPath
import org.crosswire.ksword.book.Books
import org.crosswire.ksword.book.sword.SwordBookPath
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertNotNull

class SwordInstallerTest {
    private val dir = FileSystem.SYSTEM_TEMPORARY_DIRECTORY / "SwordInstallerTest"

    @BeforeTest
    fun setUp() {
        SwordBookPath.swordBookPath = dir
        FileSystem.SYSTEM.createDirectories(dir)
        Books.refresh()
    }

    @AfterTest
    fun tearDown() {
        // refresh() never drops books, so unregister it or later test classes still see it.
        Books.getBook("Hitchcock")?.let { Books.removeBook(it) }
        FileSystem.SYSTEM.deleteRecursively(dir)
        SwordBookPath.swordBookPath = "../testFiles".toPath()
        Books.refresh()
    }

    @Test
    fun installDeletesTheDownloadedZip() = runTest {
        SwordInstallerFactory().crosswireInstaller.install("Hitchcock")

        assertNotNull(Books.getBook("Hitchcock"))
        assertFalse(FileSystem.SYSTEM.exists(FileSystem.SYSTEM_TEMPORARY_DIRECTORY / "Hitchcock.zip"))
    }
}
