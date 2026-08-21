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

import org.crosswire.ksword.passage.Verse
import org.crosswire.ksword.versification.Versification
import org.crosswire.ksword.versification.system.SystemKJVA
import org.crosswire.ksword.versification.system.Versifications
import kotlin.math.abs

/**
 * Maps a single versification to and from the KJVA, a port of JSword's VersificationToKJVMapper.
 *
 * Ordinal-space discipline: [forward]'s keys are ordinals in [sourceV11n]; every ordinal inside a
 * [QualifiedRef] is a KJVA ordinal; [reverse]'s values are ordinals in [sourceV11n]. The two spaces
 * are never compared; crossing between them always goes through (book, chapter, verse).
 *
 * Deviations from JSword (each is deliberate, see docs/MAPPING_DATA.md):
 * - parts on range endpoints are dropped (fixes a live ClassCastException in JSword's unmap)
 * - +N/-N offset syntax is rejected (dead code upstream: appears in no shipped data file)
 * - bad entries are recorded in [errors] instead of aborting construction
 */
internal class VersificationToKJVAMapper(
    val sourceV11n: Versification,
    entries: List<MappingEntry>
) {
    private val kjva: Versification = Versifications.getVersification(SystemKJVA.V11N_NAME)

    /** source ordinal -> KJVA refs, in file order (the order is load-bearing for part pairs). */
    private val forward: Map<Int, List<QualifiedRef>>

    /** KJVA ref -> source ordinals, sorted ascending. Part-qualified keys are double-indexed part-stripped too. */
    private val reverse: Map<QualifiedRef, IntArray>

    /** KJVA ordinals explicitly absent from the source v11n (left-hand '?'). Empty for all shipped data. */
    private val absentKjva: Set<Int>

    val errors: List<String>

    val forwardSize: Int get() = forward.size
    val reverseSize: Int get() = reverse.size

    init {
        val fwd = mutableMapOf<Int, MutableList<QualifiedRef>>()
        val rev = mutableMapOf<QualifiedRef, MutableSet<Int>>()
        val absent = mutableSetOf<Int>()
        val errs = mutableListOf<String>()

        fun addForward(sourceOrdinal: Int, ref: QualifiedRef) {                        // jsword :367-373
            fwd.getOrPut(sourceOrdinal) { mutableListOf() }.add(ref)
        }

        fun addReverse(ref: QualifiedRef, sourceOrdinal: Int) {                        // jsword :315-325
            rev.getOrPut(ref) { mutableSetOf() }.add(sourceOrdinal)
            // If we have a part, also index the whole verse so part-agnostic lookups still work
            if (ref is QualifiedRef.Single && ref.part != null) {
                rev.getOrPut(QualifiedRef.Single(ref.ordinal)) { mutableSetOf() }.add(sourceOrdinal)
            }
        }

        fun cardinalityError(left: ParsedSide.Verses, right: ParsedSide.Verses): Nothing =  // jsword :301-307
            throw MappingSyntaxException(
                "cardinality mismatch: left has ${left.cardinality} verses, right has ${right.cardinality}"
            )

        fun ParsedSide.Verses.toQualifiedRef(): QualifiedRef =
            if (cardinality == 1) QualifiedRef.Single(startOrdinal, part)
            else QualifiedRef.Range(startOrdinal, endOrdinal)

        // jsword add1ToManyMappings + addReverse1ToManyMappings :334-359
        fun add1ToMany(sourceOrdinal: Int, right: ParsedSide) {
            when (right) {
                is ParsedSide.Section -> {
                    val ref = QualifiedRef.Section(right.name)
                    addForward(sourceOrdinal, ref)
                    addReverse(ref, sourceOrdinal)
                }
                is ParsedSide.Verses -> {
                    addForward(sourceOrdinal, right.toQualifiedRef())
                    if (right.cardinality == 1) {                                       // jsword :347-349
                        addReverse(QualifiedRef.Single(right.startOrdinal, right.part), sourceOrdinal)
                    } else {                                                            // jsword :354-357
                        for (o in right.startOrdinal..right.endOrdinal) {
                            addReverse(QualifiedRef.Single(o), sourceOrdinal)
                        }
                    }
                }
                ParsedSide.AbsentInLeft -> throw MappingSyntaxException("'?' is not valid on the right-hand side")
            }
        }

        // jsword addManyToMany :212-293
        fun addManyToMany(left: ParsedSide.Verses, right: ParsedSide) {
            if (right is ParsedSide.Verses && right.cardinality != 1) {                 // :217
                val diff = abs(left.cardinality - right.cardinality)                    // :219
                if (diff > 1) cardinalityError(left, right)                             // :221-223
                val skipVerse0 = diff == 1                                              // :224

                var li = left.startOrdinal
                var ri = right.startOrdinal
                while (li <= left.endOrdinal) {                                         // :227
                    var leftOrd = li++                                                  // :228
                    if (ri > right.endOrdinal) cardinalityError(left, right)            // :231-233
                    var rightOrd = ri++                                                 // :235
                    var ref: QualifiedRef = QualifiedRef.Single(rightOrd)               // :236

                    // When cardinalities differ by one the extra verse is the chapter-title verse 0:
                    // map it and its neighbour to the same partner on the other side.
                    if (skipVerse0 && sourceV11n.decodeOrdinal(leftOrd).verse == 0) {   // :245
                        addForward(leftOrd, ref); addReverse(ref, leftOrd)              // :247-248
                        if (li > left.endOrdinal) cardinalityError(left, right)         // :250-252
                        leftOrd = li++                                                  // :254
                    }
                    if (skipVerse0 && kjva.decodeOrdinal(rightOrd).verse == 0) {        // :262
                        addForward(leftOrd, ref); addReverse(ref, leftOrd)              // :264-265
                        if (ri > right.endOrdinal) cardinalityError(left, right)        // :267-269
                        rightOrd = ri++                                                 // :271
                        ref = QualifiedRef.Single(rightOrd)                             // :272
                    }
                    addForward(leftOrd, ref); addReverse(ref, leftOrd)                  // :276-277
                }
                if (ri <= right.endOrdinal) cardinalityError(left, right)               // :282-284
            } else {                                                                    // :285-291 many -> 1 (or Section)
                val ref: QualifiedRef = when (right) {
                    is ParsedSide.Verses -> QualifiedRef.Single(right.startOrdinal, right.part)
                    is ParsedSide.Section -> QualifiedRef.Section(right.name)
                    ParsedSide.AbsentInLeft -> throw MappingSyntaxException("'?' is not valid on the right-hand side")
                }
                for (o in left.startOrdinal..left.endOrdinal) {
                    addForward(o, ref); addReverse(ref, o)
                }
            }
        }

        // jsword processMappings + processEntry :150-186 and addMappings :195-203
        for (entry in entries) {
            if (entry.left == MappingParser.ZEROS_UNMAPPED_FLAG) continue               // :174-176
            try {
                if (entry.right == null) throw MappingSyntaxException("Line has no '='")
                val left = MappingParser.parseSide(sourceV11n, entry.left, isSourceSide = true)
                val right = MappingParser.parseSide(kjva, entry.right, isSourceSide = false)
                when {
                    left is ParsedSide.AbsentInLeft -> {                                // :196-197
                        if (right is ParsedSide.Verses) absent.addAll(right.startOrdinal..right.endOrdinal)
                    }
                    left is ParsedSide.Verses && left.cardinality == 1 -> add1ToMany(left.startOrdinal, right)
                    left is ParsedSide.Verses -> addManyToMany(left, right)
                    else -> throw MappingSyntaxException("Left-hand side must be a verse or range")
                }
            } catch (ex: MappingSyntaxException) {                                      // :155-159, recorded not thrown
                errs.add("[${entry.left}=${entry.right}] ${ex.message}")
            }
        }

        forward = fwd
        reverse = rev.mapValues { (_, ordinals) -> ordinals.toIntArray().apply { sort() } }
        absentKjva = absent
        errors = errs
    }

    /**
     * Maps a whole source-v11n ordinal to its KJVA refs, a port of jsword map() :532-552.
     * Three-way outcome: explicit mapping / implicit identity (if valid in KJVA) / empty.
     * Unlisted chapter intros resolve via verse 1 to the counterpart chapter (docs/MAPPING_DATA.md).
     */
    fun map(sourceOrdinal: Int, part: String? = null): List<QualifiedRef> {
        forward[sourceOrdinal]?.let { if (it.isNotEmpty()) return it }                  // :535-539
        val v = sourceV11n.decodeOrdinal(sourceOrdinal)
        if (v.verse == 0 && v.chapter >= 1) {
            val verse1Start = map(sourceOrdinal + 1).minOfOrNull { ref ->
                when (ref) {
                    is QualifiedRef.Single -> ref.ordinal
                    is QualifiedRef.Range -> ref.startOrdinal
                    is QualifiedRef.Section -> Int.MAX_VALUE
                }
            }
            if (verse1Start == null || verse1Start == Int.MAX_VALUE) return emptyList()
            val kjvaVerse1 = kjva.decodeOrdinal(verse1Start)
            return listOf(QualifiedRef.Single(Verse(kjva, kjvaVerse1.book, kjvaVerse1.chapter, 0).ordinal, part))
        }
        if (kjva.validate(v.book, v.chapter, v.verse, silent = true)) {                 // :540-545
            return listOf(QualifiedRef.Single(Verse(kjva, v.book, v.chapter, v.verse).ordinal, part))
        }
        return emptyList()                                                              // :548
    }

    /**
     * Maps a KJVA ref back to source-v11n ordinals (ascending), a port of jsword unmap() :560-587.
     * Lookup order: part-sensitive, part-stripped, absent-check, implicit identity, empty.
     */
    fun unmap(ref: QualifiedRef): IntArray {
        reverse[ref]?.let { return it }                                                 // :562
        if (ref is QualifiedRef.Single && ref.part != null) {
            reverse[QualifiedRef.Single(ref.ordinal)]?.let { return it }                // :564-567
        }
        // Single only: a Range starting at verse 0 is a split verse and keeps the expansion below.
        if (ref is QualifiedRef.Single && ref.ordinal !in absentKjva) {
            val v = kjva.decodeOrdinal(ref.ordinal)
            if (v.verse == 0 && v.chapter >= 1) {
                val verse1Source = unmap(QualifiedRef.Single(ref.ordinal + 1))
                if (verse1Source.isEmpty()) return EMPTY
                val sv = sourceV11n.decodeOrdinal(verse1Source[0])   // sorted ascending: lowest
                return intArrayOf(Verse(sourceV11n, sv.book, sv.chapter, 0).ordinal)
            }
        }
        val kjvaOrdinals: IntArray = when (ref) {
            is QualifiedRef.Section -> return EMPTY                                     // :571 (section has no verse key)
            is QualifiedRef.Single -> intArrayOf(ref.ordinal)
            is QualifiedRef.Range -> IntArray(ref.endOrdinal - ref.startOrdinal + 1) { ref.startOrdinal + it }
        }
        if (kjvaOrdinals.all { it in absentKjva }) return EMPTY                         // :572-575
        val out = IntArray(kjvaOrdinals.size)
        for ((i, o) in kjvaOrdinals.withIndex()) {
            val v = kjva.decodeOrdinal(o)
            if (!sourceV11n.validate(v.book, v.chapter, v.verse, silent = true)) return EMPTY  // :576-584
            out[i] = Verse(sourceV11n, v.book, v.chapter, v.verse).ordinal
        }
        out.sort()                                                                      // ascending, matches Passage order
        return out
    }

    private companion object {
        val EMPTY = IntArray(0)
    }
}
