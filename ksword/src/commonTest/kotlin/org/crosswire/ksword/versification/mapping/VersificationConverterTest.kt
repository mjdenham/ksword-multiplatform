package org.crosswire.ksword.versification.mapping

import org.crosswire.ksword.passage.Verse
import org.crosswire.ksword.passage.VerseRange
import org.crosswire.ksword.versification.BibleBook
import org.crosswire.ksword.versification.VersificationConverter
import org.crosswire.ksword.versification.system.Versifications
import kotlin.math.abs
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * Tests of the public facade, ported from JSword's and And Bible's VersificationConverterTest,
 * plus a strided all-versifications sweep as a validity oracle.
 */
class VersificationConverterTest {

    private val kjv = Versifications.getVersification("KJV")
    private val synodal = Versifications.getVersification("Synodal")

    @Test
    fun isConvertibleMatrix() {
        val segond = Versifications.getVersification("Segond")
        val john316 = Verse(segond, BibleBook.JOHN, 3, 16)

        for (target in listOf("KJV", "NRSV", "German", "Luther", "KJVA", "Synodal", "SynodalProt", "Vulg")) {
            assertNotNull(
                VersificationConverter.convertOrNull(john316, Versifications.getVersification(target)),
                "John 3:16 should be convertible from Segond to $target"
            )
        }
        // Hebrew-only versifications have no NT
        for (target in listOf("MT", "Leningrad")) {
            assertNull(
                VersificationConverter.convertOrNull(john316, Versifications.getVersification(target)),
                "John 3:16 should not be convertible to $target"
            )
        }
    }

    @Test
    fun convertAllExposesSplitVerses() {
        // Synodal Ps.49.1 = KJVA Ps.50.0-Ps.50.1 (title merged into verse 1)
        val verse = Verse(synodal, BibleBook.PS, 49, 1)
        val all = VersificationConverter.convertAll(verse, kjv)
        assertEquals(listOf("Ps.50.0", "Ps.50.1"), all.map { it.getOsisID() })
        // convertOrNull collapses to the lowest ordinal
        assertEquals("Ps.50.0", VersificationConverter.convertOrNull(verse, kjv)?.getOsisID())
        // unmappable -> empty
        val mt = Versifications.getVersification("MT")
        assertEquals(emptyList(), VersificationConverter.convertAll(Verse(kjv, BibleBook.JOHN, 3, 16), mt))
    }

    @Test
    fun kjvPsalm51ConvertsToSynodalPsalm50() {
        val verse = Verse(kjv, BibleBook.PS, 51, 1)
        val converted = VersificationConverter.convertOrNull(verse, synodal)
        assertEquals("Ps.50.3", converted?.getOsisID())
    }

    @Test
    fun rangeConversionMapsEndpoints() {
        // And Bible's ConvertibleVerseRangeTest expectation: KJV Ps 14:2-4 == SynodalProt Ps 13:2-4
        val synodalProt = Versifications.getVersification("SynodalProt")
        val range = VerseRange(kjv, Verse(kjv, BibleBook.PS, 14, 2), Verse(kjv, BibleBook.PS, 14, 4))
        val converted = VersificationConverter.convertOrNull(range, synodalProt)
        assertEquals("Ps.13.2-Ps.13.4", converted?.getOsisRef())
    }

    @Test
    fun unmappableRangeIsNull() {
        val mt = Versifications.getVersification("MT")
        val range = VerseRange(kjv, Verse(kjv, BibleBook.JOHN, 3, 16), Verse(kjv, BibleBook.JOHN, 3, 17))
        assertNull(VersificationConverter.convertOrNull(range, mt))
    }

    /** Strided sweep: every 7th ordinal of every v11n, plus all of Psalms; results must validate in the target. */
    @Test
    fun strictResultsAlwaysValidateInTarget() {
        val targets = listOf(kjv, synodal, Versifications.getVersification("Catholic"))
        for (name in Versifications.iterator().asSequence().filterNotNull()) {
            val source = Versifications.getVersification(name)
            var checked = 0
            var ordinal = 0
            val max = source.maximumOrdinal()
            while (ordinal <= max) {
                val verse = source.decodeOrdinal(ordinal)
                for (target in targets) {
                    val converted = VersificationConverter.convertOrNull(verse, target) ?: continue
                    assertTrue(
                        target.validate(converted.book, converted.chapter, converted.verse, silent = true),
                        "$name ordinal $ordinal (${verse.getOsisID()}) -> ${target.name} " +
                            "gave invalid ${converted.getOsisID()}"
                    )
                    checked++
                }
                ordinal += if (verse.book == BibleBook.PS) 1 else 7
            }
            assertTrue(checked > 0, "$name: nothing was checked")
        }
    }

    /** Round-trip stability: source -> KJVA -> source must include the original verse. */
    @Test
    fun roundTripThroughKjvaIsStable() {
        val kjva = Versifications.getVersification("KJVA")
        for (name in listOf("Synodal", "Catholic", "Catholic2", "Segond", "Vulg", "Luther")) {
            val source = Versifications.getVersification(name)
            // sample across the interesting books: Psalms plus a spread of the whole ordinal space
            val samples = buildList {
                val psStart = source.getOrdinal(Verse(source, BibleBook.PS, 1, 1))
                val psEnd = source.getOrdinal(Verse(source, BibleBook.PS, source.getLastChapter(BibleBook.PS), 1))
                for (o in psStart..psEnd step 37) add(o)
                for (o in 0..source.maximumOrdinal() step 501) add(o)
            }
            for (ordinal in samples) {
                val verse = source.decodeOrdinal(ordinal)
                val kjvaOrdinals = VersificationsMapper.mapVerse(verse, kjva)
                if (kjvaOrdinals.isEmpty()) continue
                val back = kjvaOrdinals.flatMap { o ->
                    VersificationsMapper.mapVerse(Verse(kjva, o), source).asList()
                }
                if (verse.verse == 0) {
                    // Intros land on the counterpart chapter; titled and splice chapters may shift by one.
                    if (verse.chapter < 1 || back.isEmpty()) continue
                    val landed = source.decodeOrdinal(back.min())
                    assertTrue(
                        landed.book == verse.book && abs(landed.chapter - verse.chapter) <= 1,
                        "$name ${verse.getOsisID()} intro round-trip left its chapter: went to " +
                            osisRef(kjva, kjvaOrdinals) + ", came back to " + osisRef(source, back.toIntArray())
                    )
                    continue
                }
                assertTrue(
                    verse.ordinal in back,
                    "$name ${verse.getOsisID()} round-trip lost itself: went to " +
                        osisRef(kjva, kjvaOrdinals) + ", came back to " + osisRef(source, back.toIntArray())
                )
            }
        }
    }

    @Test
    fun crossedEndpointsAfterConversionAreRejected() {
        // Synodal relocates Rom 16:25-27 into Rom 14, so converting this KJV range
        // endpoint-by-endpoint would produce a swapped, ~two-chapter range.
        val kjv = Versifications.getVersification("KJV")
        val synodal = Versifications.getVersification("Synodal")
        val range = VerseRange(kjv, Verse(kjv, BibleBook.ROM, 16, 24), Verse(kjv, BibleBook.ROM, 16, 27))
        assertNull(VersificationConverter.convertOrNull(range, synodal))
    }
}
