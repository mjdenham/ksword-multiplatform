package org.crosswire.common.util

import kotlinx.coroutines.test.runTest
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
}
