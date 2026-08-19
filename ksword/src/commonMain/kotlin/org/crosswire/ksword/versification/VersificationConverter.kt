/**
 * Distribution License:
 * KSword is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License, version 2.1 or later
 * as published by the Free Software Foundation. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 */
package org.crosswire.ksword.versification

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.crosswire.ksword.passage.Verse
import org.crosswire.ksword.passage.VerseRange
import org.crosswire.ksword.versification.mapping.VersificationsMapper

/**
 * Converts verses between versifications using CrossWire's mapping tables, pivoting through the KJVA.
 *
 * This is a real conversion — e.g. KJV Ps.51.1 converts to Synodal Ps.50.3 — unlike
 * [Verse.reversify], which merely reinterprets the same book/chapter/verse numbers.
 *
 * Verse 0 (a chapter introduction, or the Psalm title in the KJV) is an ordinary, fully
 * convertible verse; verse-0-ness is not preserved where the mapping data says otherwise
 * (KJVA Ps.50.0, the Psalm title, converts to Synodal Ps.49.1).
 *
 * Thread-safe. First use of a versification parses its mapping table (a few ms);
 * call [preload] from a background coroutine to avoid that cost on first navigation.
 */
object VersificationConverter {

    /**
     * Strict conversion: null when [verse] genuinely has no counterpart in [target]
     * (e.g. an NT verse converted to a Hebrew-only versification).
     * When a verse maps to several (a split verse), the first is returned.
     */
    fun convertOrNull(verse: Verse, target: Versification): Verse? {
        val ordinals = VersificationsMapper.mapVerse(verse, target)
        return if (ordinals.isEmpty()) null else Verse(target, ordinals[0])
    }

    /**
     * All counterparts of [verse] in [target], ascending by ordinal; empty when unmappable.
     * Usually 0, 1 or 2 entries — a split verse (e.g. a Psalm-title merge) yields several,
     * letting callers choose a landing policy such as "first non-zero verse".
     */
    fun convertAll(verse: Verse, target: Versification): List<Verse> =
        VersificationsMapper.mapVerse(verse, target).map { Verse(target, it) }

    /**
     * Strict, endpoint-only range conversion: null if either endpoint is unmappable.
     * Interior verses are not converted individually, so a range crossing a splice point
     * may be approximate — an accepted inaccuracy for range-level uses such as bookmarks.
     */
    fun convertOrNull(range: VerseRange, target: Versification): VerseRange? {
        val start = convertOrNull(range.start, target) ?: return null
        val end = convertOrNull(range.end, target) ?: return null
        return VerseRange(target, start, end)
    }

    /** Parse and cache the mapping table for [v11n] off the caller's thread. Idempotent, cheap when loaded. */
    suspend fun preload(v11n: Versification) = withContext(Dispatchers.Default) {
        VersificationsMapper.ensureLoaded(v11n)
    }
}
