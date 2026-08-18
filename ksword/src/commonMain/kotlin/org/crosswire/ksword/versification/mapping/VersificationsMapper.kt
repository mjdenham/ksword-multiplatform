/**
 * Distribution License:
 * KSword is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License, version 2.1 or later
 * as published by the Free Software Foundation. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 */
package org.crosswire.ksword.versification.mapping

import kotlinx.atomicfu.locks.SynchronizedObject
import kotlinx.atomicfu.locks.synchronized
import org.crosswire.ksword.passage.Verse
import org.crosswire.ksword.versification.Versification
import org.crosswire.ksword.versification.system.SystemKJVA
import org.crosswire.ksword.versification.system.Versifications

/**
 * Maps verses between any two versifications by pivoting through the KJVA,
 * a port of JSword's VersificationsMapper. Unlike JSword's, the mapper cache is thread-safe.
 */
internal object VersificationsMapper {

    private val lock = SynchronizedObject()

    /** Mapper per v11n name; null is cached for v11ns that map identically to the KJVA. */
    private val mappers = mutableMapOf<String, VersificationToKJVAMapper?>()

    private val kjva: Versification get() = Versifications.getVersification(SystemKJVA.V11N_NAME)

    /** Parse and cache the mapping table for [v11n]. Idempotent and cheap when already loaded. */
    fun ensureLoaded(v11n: Versification) {
        mapperFor(v11n)
    }

    private fun mapperFor(v11n: Versification): VersificationToKJVAMapper? = synchronized(lock) {
        if (v11n.name in mappers) return@synchronized mappers[v11n.name]
        val mapper = MappingData.entriesFor(v11n.name)?.let { VersificationToKJVAMapper(v11n, it) }
        mappers[v11n.name] = mapper
        mapper
    }

    /**
     * Maps [verse] to [target], returning the counterpart ordinals in [target], sorted ascending.
     * Empty means the verse genuinely has no counterpart. Port of jsword mapVerse() :118-175.
     */
    fun mapVerse(verse: Verse, target: Versification): IntArray {
        if (verse.getVersification() == target) return intArrayOf(verse.ordinal)   // :119-121

        // source -> KJVA; qualified refs carry parts across the pivot            // :128-146
        val kjvaRefs: List<QualifiedRef> = mapperFor(verse.getVersification())
            ?.map(verse.ordinal, verse.subIdentifier)
            ?: verse.reversify(kjva)?.let { listOf(QualifiedRef.Single(it.ordinal)) }.orEmpty()

        if (target == kjva) {                                                     // :148-151
            return kjvaRefs.flatMap { wholeKjvaOrdinals(it) }.distinct().sorted().toIntArray()
        }

        val targetMapper = mapperFor(target)
            ?: return kjvaRefs.flatMap { guessOrdinals(it, target) }.distinct().sorted().toIntArray()  // :156-160

        return kjvaRefs.flatMap { targetMapper.unmap(it).asList() }.distinct().sorted().toIntArray()   // :166-174
    }

    private fun wholeKjvaOrdinals(ref: QualifiedRef): List<Int> = when (ref) {
        is QualifiedRef.Single -> listOf(ref.ordinal)
        is QualifiedRef.Range -> (ref.startOrdinal..ref.endOrdinal).toList()
        is QualifiedRef.Section -> emptyList()
    }

    /** Wild guess for targets with no mapping table: reversify the KJVA verses. Port of :186-198. */
    private fun guessOrdinals(ref: QualifiedRef, target: Versification): List<Int> =
        wholeKjvaOrdinals(ref).mapNotNull { ordinal ->
            Verse(kjva, ordinal).reversify(target)?.ordinal
        }
}
