package org.crosswire.ksword.book.install.sword

import kotlinx.coroutines.test.runTest
import okio.FileSystem
import org.crosswire.ksword.book.Books
import org.crosswire.ksword.book.TestBooks
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
        TestBooks.reset()
    }

    @Test
    fun installDeletesTheDownloadedZip() = runTest {
        SwordInstallerFactory().crosswireInstaller.install("Hitchcock")

        assertNotNull(Books.getBook("Hitchcock"))
        assertFalse(FileSystem.SYSTEM.exists(FileSystem.SYSTEM_TEMPORARY_DIRECTORY / "Hitchcock.zip"))
    }
}
