package org.crosswire.ksword.book.install.sword

import kotlinx.coroutines.test.runTest
import okio.FileSystem
import okio.Path.Companion.toPath
import org.crosswire.ksword.book.sword.SwordBookPath
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class IbtInstallerTest {

    private val factory = SwordInstallerFactory()

    private val dir = FileSystem.SYSTEM_TEMPORARY_DIRECTORY.resolve("IbtInstallerTest".toPath())

    @BeforeTest
    fun setUp() {
        SwordBookPath.swordBookPath = dir
        FileSystem.SYSTEM.createDirectories(SwordBookPath.swordBookPath)
    }

    @AfterTest
    fun tearDown() {
        FileSystem.SYSTEM.deleteRecursively(SwordBookPath.swordBookPath)
    }

    @Test
    fun ibtCatalogContainsSupportedRussianCommentaryAndCisBibles() = runTest {
        val catalog = factory.ibtInstaller.getBooks()
        assertTrue(catalog.isNotEmpty())
        // NGSB (zCom) is the supported Russian commentary; CHV/OSS are CIS-language Bibles (zText).
        // Lopukhin/BARC are RawCom4 and intentionally excluded by isSupported until that driver lands.
        listOf("NGSB", "CHV", "OSS").forEach { initials ->
            assertTrue(catalog.any { it.initials == initials }, "IBT catalog missing $initials")
        }
    }
}
