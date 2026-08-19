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

import org.crosswire.ksword.passage.OsisParser
import org.crosswire.ksword.versification.Versification

internal class MappingSyntaxException(message: String) : Exception(message)

/** One line of a mapping file: left side in the source v11n, right side in KJVA. Right is null for flag lines. */
internal data class MappingEntry(val left: String, val right: String?)

/**
 * One side of a mapping entry after parsing. Ordinals belong to the versification the side was parsed against.
 */
internal sealed interface ParsedSide {
    /** Left-hand '?': the KJVA verses on the right have no counterpart in the source v11n. */
    object AbsentInLeft : ParsedSide

    /** Right-hand '?Name': the source verses map to content the KJVA lacks. Name includes the '?'. */
    data class Section(val name: String) : ParsedSide

    /** A verse or contiguous range. [part] is non-null only when cardinality is 1 (parts on range endpoints are dropped). */
    data class Verses(val startOrdinal: Int, val endOrdinal: Int, val part: String? = null) : ParsedSide {
        val cardinality: Int get() = endOrdinal - startOrdinal + 1
    }
}

/**
 * Parses the mapping-file DSL, a port of JSword's FileVersificationMapping line reading and
 * VersificationToKJVMapper.getRange. Deviation from JSword: the +N/-N offset syntax is rejected
 * (it appears in no shipped data file); MappingDataIntegrityTest fails loudly if upstream ever adds it.
 */
internal object MappingParser {

    const val ZEROS_UNMAPPED_FLAG = "!zerosUnmapped"

    /** Splits mapping-file text into entries: skips blank lines and '#' comments, splits at the FIRST '=' only. */
    fun parseMappingSource(text: String): List<MappingEntry> =
        text.lineSequence()
            .filter { it.isNotEmpty() && it[0] != '#' }
            .map { line ->
                val firstEqual = line.indexOf('=')
                if (firstEqual == -1) {
                    MappingEntry(line, null)
                } else {
                    MappingEntry(line.substring(0, firstEqual), line.substring(firstEqual + 1))
                }
            }
            .toList()

    /**
     * Parses one side of an entry against [v11n] (the source v11n for the left side, KJVA for the right).
     *
     * @throws MappingSyntaxException on empty text, offset syntax, or an unparseable/invalid reference
     */
    fun parseSide(v11n: Versification, text: String?, isSourceSide: Boolean): ParsedSide {
        if (text.isNullOrEmpty()) {
            throw MappingSyntaxException("Empty mapping side")
        }
        when (text[0]) {
            '?' -> return if (isSourceSide) ParsedSide.AbsentInLeft else ParsedSide.Section(text)
            '+', '-' -> throw MappingSyntaxException("Offset syntax is not supported: $text")
        }

        val range = OsisParser().parseOsisRef(v11n, text)
            ?: throw MappingSyntaxException("Unparseable reference: $text")
        val start = range.start
        val end = range.end
        if (!v11n.validate(start.book, start.chapter, start.verse, silent = true) ||
            !v11n.validate(end.book, end.chapter, end.verse, silent = true)
        ) {
            throw MappingSyntaxException("Reference not valid in ${v11n.name}: $text")
        }
        return if (start.ordinal == end.ordinal) {
            ParsedSide.Verses(start.ordinal, end.ordinal, start.subIdentifier?.takeIf { it.isNotEmpty() })
        } else {
            // Parts on range endpoints are dropped (JSword would ClassCastException on unmap here)
            ParsedSide.Verses(start.ordinal, end.ordinal, null)
        }
    }
}
