package org.crosswire.ksword.versification

import org.crosswire.ksword.versification.system.Versifications
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Pins every versification's table (book order, chapter counts, last-verse values) with a digest,
 * verified against JSword's runtime output at jsword commit 901dbac7. Any change to any table digit
 * turns exactly one named row red. Table edits must be re-verified against JSword before repinning —
 * see docs/MAPPING_DATA.md "Bugs found and fixed during the port" for why (five tables once drifted
 * silently for months).
 */
class VersificationTablesDigestTest {

    private data class Digest(val v11n: String, val bookCount: Int, val maximumOrdinal: Int, val fnv1a: Long)

    @Test
    fun tablesAreUnchanged() {
        val expected = listOf(
            Digest("Calvin", 69, 32427, -400428030571764810L),
            Digest("Catholic", 76, 36905, 7504843608308014138L),
            Digest("Catholic2", 76, 37019, -9157912237687293824L),
            Digest("DarbyFr", 69, 32364, 4184004121490805887L),
            Digest("German", 69, 32429, 3416447307808918520L),
            Digest("KJV", 69, 32359, -8187285884755934910L),
            Digest("KJVA", 83, 38272, 817896867602786006L),
            Digest("LXX", 87, 41192, -6245121646561931558L),
            Digest("Leningrad", 41, 24182, 2836961890934445961L),
            Digest("Luther", 79, 37184, -2603995784968093654L),
            Digest("MT", 41, 24182, 886331384597883657L),
            Digest("NRSV", 69, 32361, -9008347611036790958L),
            Digest("NRSVA", 86, 39273, -7713740445205025661L),
            Digest("Orthodox", 83, 38918, 1469217621081152488L),
            Digest("Segond", 69, 32427, -8559004761106774872L),
            Digest("Synodal", 81, 38541, -7517739090575719289L),
            Digest("SynodalProt", 69, 32420, 4030485718284391749L),
            Digest("Vulg", 81, 38697, -4756810592991422087L),
        )
        val actual = Versifications.iterator().asSequence().filterNotNull().sorted().map { name ->
            val v = Versifications.getVersification(name)
            var h = -3750763034362895579L // FNV-1a 64-bit offset basis
            fun mix(x: Int) {
                h = h xor x.toLong()
                h *= 1099511628211L
            }
            for (b in v.bookIterator) {
                mix(b.ordinal)
                val lastChap = v.getLastChapter(b)
                mix(lastChap)
                for (c in 1..lastChap) mix(v.getLastVerse(b, c))
            }
            Digest(name, v.bookCount, v.maximumOrdinal(), h)
        }.toList()
        assertEquals(expected, actual)
    }
}
