package org.crosswire.ksword.book

import kotlin.test.Test
import kotlin.test.assertEquals

class SourceTypeTest {

    @Test
    fun parsesConfValuesIgnoringCase() {
        assertEquals(SourceType.OSIS, SourceType.parse("OSIS"))
        assertEquals(SourceType.OSIS, SourceType.parse("osis"))
        assertEquals(SourceType.THML, SourceType.parse("ThML"))
        assertEquals(SourceType.THML, SourceType.parse("THML"))
        assertEquals(SourceType.TEI, SourceType.parse("TEI"))
        assertEquals(SourceType.PLAINTEXT, SourceType.parse("Plaintext"))
        assertEquals(SourceType.GBF, SourceType.parse("GBF"))
    }

    @Test
    fun readsMissingOrUnknownValueAsPlainText() {
        assertEquals(SourceType.PLAINTEXT, SourceType.parse(null))
        assertEquals(SourceType.PLAINTEXT, SourceType.parse(""))
        assertEquals(SourceType.PLAINTEXT, SourceType.parse("Latex"))
    }
}
