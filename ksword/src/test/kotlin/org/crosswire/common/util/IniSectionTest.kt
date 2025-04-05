package org.crosswire.common.util

import kotlinx.coroutines.test.runTest
import okio.FileSystem
import okio.Path.Companion.toPath
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class IniSectionTest {

    private val TestConfigFile = "../testFiles/mods.d/bsb.conf".toPath()

    private lateinit var iniSection: IniSection

    @Before
    fun setup() {
        iniSection = IniSection()
    }

    @Test
    fun canReadConfFile() = runTest {
        assertTrue(FileSystem.SYSTEM.exists(TestConfigFile))
        iniSection.load(TestConfigFile)

        assertEquals("BSB", iniSection.name)
    }
}