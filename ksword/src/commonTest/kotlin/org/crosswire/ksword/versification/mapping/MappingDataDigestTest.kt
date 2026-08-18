package org.crosswire.ksword.versification.mapping

import org.crosswire.ksword.versification.system.Versifications
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Pins the embedded mapping data and the loaded table sizes per versification.
 * Any accidental data edit, or a loader behaviour change, turns exactly one row red.
 * After a deliberate upstream re-sync, regenerate these values.
 */
class MappingDataDigestTest {

    private data class Digest(
        val v11n: String,
        val length: Int,
        val fnv1a: Long,
        val forwardSize: Int,
        val reverseSize: Int
    )

    private fun fnv1a(s: String): Long {
        var h = -3750763034362895579L // FNV-1a 64-bit offset basis
        for (c in s) {
            h = h xor c.code.toLong()
            h *= 1099511628211L
        }
        return h
    }

    @Test
    fun dataAndTablesAreUnchanged() {
        val expected = listOf(
            Digest("Catholic", 9980, 6790599995388556195L, 2444, 2473),
            Digest("Catholic2", 10385, -1108039820802645614L, 2552, 2579),
            Digest("German", 5033, -7393732654007257349L, 2055, 2070),
            Digest("Leningrad", 6447, 9207817413286452362L, 2112, 2186),
            Digest("Luther", 5033, -7393732654007257349L, 2055, 2070),
            Digest("MT", 6479, 7575181622502293756L, 2112, 2186),
            Digest("NRSV", 843, 5212828029585001806L, 4, 6),
            Digest("NRSVA", 843, 5212828029585001806L, 4, 6),
            Digest("Segond", 7586, -5173313149673904340L, 1485, 1550),
            Digest("Synodal", 8734, 4812940951778240886L, 2834, 2831),
            Digest("SynodalProt", 8554, -9096488358592117167L, 2767, 2830),
            Digest("Vulg", 7868, -5275747690668707727L, 2514, 2575),
        )
        val actual = Versifications.iterator().asSequence().filterNotNull().sorted().mapNotNull { name ->
            val source = MappingData.sourceFor(name) ?: return@mapNotNull null
            val mapper = VersificationToKJVAMapper(
                Versifications.getVersification(name),
                MappingData.entriesFor(name)!!
            )
            Digest(name, source.length, fnv1a(source), mapper.forwardSize, mapper.reverseSize)
        }.toList()
        assertEquals(expected, actual)
    }
}
