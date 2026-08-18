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

import org.crosswire.ksword.versification.mapping.data.Catholic2Mapping
import org.crosswire.ksword.versification.mapping.data.CatholicMapping
import org.crosswire.ksword.versification.mapping.data.LeningradMapping
import org.crosswire.ksword.versification.mapping.data.LutherMapping
import org.crosswire.ksword.versification.mapping.data.MTMapping
import org.crosswire.ksword.versification.mapping.data.NrsvaMapping
import org.crosswire.ksword.versification.mapping.data.SegondMapping
import org.crosswire.ksword.versification.mapping.data.SynodalMapping
import org.crosswire.ksword.versification.mapping.data.SynodalProtMapping
import org.crosswire.ksword.versification.mapping.data.VulgMapping

internal object MappingData {

    /**
     * Corrections to provably-wrong upstream lines, applied after parsing so the embedded TEXT stays
     * byte-identical to upstream. KJVA Hos.1 has 11 verses; the upstream right-hand side is an obvious
     * typo for Hos.12 (JSword does not validate and silently maps Catholic Hos 12 onto KJV Hos 1-2).
     * See docs/MAPPING_DATA.md.
     */
    private val KNOWN_UPSTREAM_FIXES = mapOf(
        MappingEntry("Hos.12.2-Hos.12.15", "Hos.1.1-Hos.1.14") to
            MappingEntry("Hos.12.2-Hos.12.15", "Hos.12.1-Hos.12.14"),
    )

    /**
     * Upstream lines that are known to be invalid and have no unambiguous correction; the loader
     * records them in errors and skips them. Segond's own canon table says Mark 10 has 52 verses.
     */
    val KNOWN_BAD_LINES = mapOf(
        "Segond" to setOf("Mark.10.53=Mark.10.52!b"),
    )

    /** The parsed, corrected mapping entries for [v11nName], or null when it maps identically to KJVA. */
    fun entriesFor(v11nName: String): List<MappingEntry>? =
        sourceFor(v11nName)?.let { source ->
            MappingParser.parseMappingSource(source).map { KNOWN_UPSTREAM_FIXES[it] ?: it }
        }

    /**
     * The verbatim mapping source for [v11nName], or null when the v11n maps identically to the KJVA
     * (KJV, KJVA, Calvin, DarbyFr, LXX, Orthodox have no mapping file upstream).
     */
    fun sourceFor(v11nName: String): String? = when (v11nName) {
        "Catholic" -> CatholicMapping.TEXT
        "Catholic2" -> Catholic2Mapping.TEXT
        "German", "Luther" -> LutherMapping.TEXT   // byte-identical upstream
        "Leningrad" -> LeningradMapping.TEXT
        "MT" -> MTMapping.TEXT
        "NRSV", "NRSVA" -> NrsvaMapping.TEXT       // byte-identical upstream
        "Segond" -> SegondMapping.TEXT
        "Synodal" -> SynodalMapping.TEXT
        "SynodalProt" -> SynodalProtMapping.TEXT
        "Vulg" -> VulgMapping.TEXT
        else -> null
    }
}
