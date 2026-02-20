/**
 * Distribution License:
 * JSword is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License, version 2.1 or later
 * as published by the Free Software Foundation. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * The License is available on the internet at:
 * http://www.gnu.org/copyleft/lgpl.html
 * or by writing to:
 * Free Software Foundation, Inc.
 * 59 Temple Place - Suite 330
 * Boston, MA 02111-1307, USA
 *
 * © CrossWire Bible Society, 2005 - 2016
 *
 */
package org.crosswire.ksword.book.passage

import org.crosswire.ksword.JSMsg
import org.crosswire.ksword.passage.AccuracyType
import org.crosswire.ksword.passage.NoSuchVerseException
import org.crosswire.ksword.versification.Versification
import org.crosswire.ksword.versification.system.Versifications
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.fail

/**
 * JUnit Test
 *
 * @author DM Smith
 */
class AccuracyTypeTest {
    private var rs: Versification = Versifications.getVersification("KJV")

    @Test
    fun testFromTextValid() {
        try {
            AccuracyType.fromText(rs, "10", arrayOf<String>("10"), null, null)
        } catch (expected: NoSuchVerseException) {
            // This is allowed
        } catch (aioobe: IndexOutOfBoundsException) {
            fail("ArrayIndexOutOfBoundsException caught, expecting NoSuchVerseException")
        }
    }

    @Test
    fun testFromTextOnePartInvalidBook() {
        try {
            AccuracyType.fromText(rs, "10", arrayOf<String>("10"), null, null)
        } catch (expected: NoSuchVerseException) {
            // This is allowed
        } catch (aioobe: IndexOutOfBoundsException) {
            fail("ArrayIndexOutOfBoundsException caught, expecting NoSuchVerseException")
        }
    }

    @Test
    fun testFromTextTooManyParts() {
        val nsve = assertFailsWith<NoSuchVerseException> {
            AccuracyType.fromText(rs, "1:2:3:4", arrayOf<String>("1", "2", "3", "4"), null, null)
        }
        val correctException = NoSuchVerseException(
            JSMsg.gettext(
                "Too many parts to the Verse. (Parts are separated by any of {0})",
                "1:2:3:4, 1, 2, 3, 4"
            )
        )
        assertEquals(correctException.message, nsve.message, "Unexpected exception message")
    }

    @Test
    fun testFromTextThreePartsInvalidBook() {
        val nsve = assertFailsWith<NoSuchVerseException> {
            AccuracyType.fromText(rs, "-1:2:3", arrayOf<String>("-1", "2", "3"), null, null)
        }
        val correctException = NoSuchVerseException(
            JSMsg.gettext(
                "Too many parts to the Verse. (Parts are separated by any of {0})",
                "-1:2:3, -1, 2, 3"
            )
        )
        assertEquals(correctException.message, nsve.message, "Unexpected exception message")
    }
}