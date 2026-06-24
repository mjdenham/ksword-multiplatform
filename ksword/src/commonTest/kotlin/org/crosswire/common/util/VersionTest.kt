package org.crosswire.common.util

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class VersionTest {

    @Test
    fun comparesByPartInOrder() {
        assertTrue(Version("2.0") > Version("1.7.1"))
        assertTrue(Version("1.7.1") > Version("1.7"))
        assertTrue(Version("1.7") < Version("1.7.1"))
        assertEquals(0, Version("2.0").compareTo(Version("2.0")))
    }

    // SWORD module versions encode a date in a non-dotted suffix; the lenient separator must let
    // these parse and order monotonically (this is the StrongsRealGreek case).
    @Test
    fun parsesNonDottedSeparators() {
        assertTrue(Version("1.5-160101") > Version("1.5-150704"))
        assertEquals(Version("1.5-150704"), Version("1.5-150704"))
    }

    @Test
    fun bundledModuleVersionsAllParse() {
        for (v in listOf("2.0", "1.4", "1.090107", "1.5-150704", "1.7.1")) {
            // must not throw
            Version(v)
        }
    }

    @Test
    fun equalsIgnoresOriginalStringFormatting() {
        assertEquals(Version("2.0"), Version("2.0"))
        assertTrue(Version("2.0") != Version("2.0.0"))
    }

    @Test
    fun rejectsNonNumericLeadingVersion() {
        assertFailsWith<IllegalArgumentException> { Version("abc") }
        assertFailsWith<IllegalArgumentException> { Version("") }
    }
}
