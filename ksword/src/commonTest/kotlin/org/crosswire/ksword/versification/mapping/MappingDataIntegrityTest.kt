package org.crosswire.ksword.versification.mapping

import org.crosswire.ksword.versification.system.Versifications
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame
import kotlin.test.assertTrue

/**
 * Port of JSword's FileVersificationMappingTest plus guards that fail loudly if an upstream
 * data re-sync ever introduces syntax this port deliberately dropped.
 */
class MappingDataIntegrityTest {

    private fun allV11nNames(): List<String> =
        Versifications.iterator().asSequence().filterNotNull().toList()

    private fun allSources(): Map<String, String> =
        allV11nNames().mapNotNull { name -> MappingData.sourceFor(name)?.let { name to it } }.toMap()

    private fun mappingLines(text: String): List<String> =
        text.lineSequence().filter { it.isNotEmpty() && it[0] != '#' }.toList()

    @Test
    fun everyVersificationLoadsWithoutErrors() {
        var loaded = 0
        for (name in allV11nNames()) {
            val entries = MappingData.entriesFor(name) ?: continue
            val mapper = VersificationToKJVAMapper(Versifications.getVersification(name), entries)
            val knownBad = MappingData.KNOWN_BAD_LINES[name].orEmpty()
            val unexpected = mapper.errors.filterNot { error -> knownBad.any { error.startsWith("[$it]") } }
            assertTrue(unexpected.isEmpty(), "Failed to load $name: $unexpected")
            assertEquals(knownBad.size, mapper.errors.size, "$name: known-bad line count drifted: ${mapper.errors}")
            loaded++
        }
        assertEquals(12, loaded, "expected 12 v11ns with mapping data (10 files + 2 aliases)")
    }

    @Test
    fun knownUpstreamFixIsStillNeeded() {
        // If upstream corrects the Hosea typo, the fix in MappingData no longer matches anything
        // and should be deleted; this test surfaces that on a data re-sync.
        for (name in listOf("Catholic", "Catholic2")) {
            val raw = MappingParser.parseMappingSource(MappingData.sourceFor(name)!!)
            assertTrue(
                raw.contains(MappingEntry("Hos.12.2-Hos.12.15", "Hos.1.1-Hos.1.14")),
                "$name no longer contains the Hosea typo - remove KNOWN_UPSTREAM_FIXES"
            )
            val fixed = MappingData.entriesFor(name)!!
            assertTrue(fixed.contains(MappingEntry("Hos.12.2-Hos.12.15", "Hos.12.1-Hos.12.14")))
        }
    }

    @Test
    fun noOffsetSyntax() {
        for ((name, source) in allSources()) {
            for (line in mappingLines(source)) {
                val right = line.substringAfter('=', "")
                assertTrue(
                    right.isEmpty() || (right[0] != '+' && (right[0] != '-' || right.length == 1)),
                    "$name uses the unsupported offset syntax: $line"
                )
            }
        }
    }

    @Test
    fun noLeftHandAbsentMarkers() {
        for ((name, source) in allSources()) {
            for (line in mappingLines(source)) {
                assertTrue(line[0] != '?', "$name uses a left-hand '?' marker (untested upstream): $line")
            }
        }
    }

    @Test
    fun partNamesAreKnown() {
        val known = setOf("a", "b", "pv", "v", "intro", "verse", "zerosUnmapped")
        val partRegex = Regex("!([A-Za-z]+)")
        for ((name, source) in allSources()) {
            for (line in mappingLines(source)) {
                for (match in partRegex.findAll(line)) {
                    assertTrue(
                        match.groupValues[1] in known,
                        "$name introduces an unreviewed part name '${match.value}': $line"
                    )
                }
            }
        }
    }

    @Test
    fun sectionNamesAreUniquePerFile() {
        // A second bare '=?' in one file would silently share a section bucket with the first.
        for ((name, source) in allSources()) {
            val sections = mappingLines(source)
                .map { it.substringAfter('=', "") }
                .filter { it.startsWith("?") }
            assertEquals(
                sections.distinct().size, sections.size,
                "$name has colliding section names: $sections"
            )
        }
    }

    @Test
    fun aliasedSourcesAreShared() {
        assertSame(MappingData.sourceFor("German"), MappingData.sourceFor("Luther"))
        assertSame(MappingData.sourceFor("NRSV"), MappingData.sourceFor("NRSVA"))
    }

    @Test
    fun identityVersificationsHaveNoSource() {
        for (name in MappingData.IDENTITY_V11NS) {
            assertEquals(null, MappingData.sourceFor(name), "$name should map identically to KJVA")
        }
    }

    @Test
    fun everyKnownVersificationIsMappedOrDeclaredIdentity() {
        // A new v11n must be given mapping data or added to IDENTITY_V11NS explicitly;
        // falling through to null would silently degrade it to same-number guessing.
        for (name in allV11nNames()) {
            assertTrue(
                (MappingData.sourceFor(name) != null) != (name in MappingData.IDENTITY_V11NS),
                "$name must have mapping data or be declared identity (exactly one of the two)"
            )
        }
    }
}
