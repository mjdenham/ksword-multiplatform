package org.crosswire.common.util

import kotlinx.coroutines.test.runTest
import okio.Buffer
import okio.FileSystem
import okio.Path.Companion.toPath
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class IniSectionTest {

    private val TestConfigFile = "../testFiles/mods.d/bsb.conf".toPath()

    private lateinit var iniSection: IniSection

    @BeforeTest
    fun setup() {
        iniSection = IniSection()
    }

    @Test
    fun canReadConfFile() = runTest {
        assertTrue(FileSystem.SYSTEM.exists(TestConfigFile))
        iniSection.load(TestConfigFile)

        assertEquals("BSBTEST", iniSection.name)
    }

    private fun load(conf: String) = iniSection.load(Buffer().writeUtf8(conf))

    /** nhy2006eb's name has an equals sign in it; the whole line used to be dropped. */
    @Test
    fun aValueMayContainEqualsSigns() {
        load("[nhy2006eb]\nDescription=Itlajtol totajtzin Dios = El Nuevo Testamento\nTextSource=https://ibt.org.ru/ru/media?id=KGZ/cyrl\n")

        assertEquals("Itlajtol totajtzin Dios = El Nuevo Testamento", iniSection["Description"])
        assertEquals("https://ibt.org.ru/ru/media?id=KGZ/cyrl", iniSection["TextSource"])
    }

    @Test
    fun aValueMayBeEmpty() {
        load("[T]\nCipherKey=\n")

        assertEquals("", iniSection["CipherKey"])
    }

    @Test
    fun aLineWithNoEqualsSignIsSkipped() {
        load("[T]\njust some words\nLang=en\n")

        assertEquals(setOf("Lang"), iniSection.keys)
    }
}
