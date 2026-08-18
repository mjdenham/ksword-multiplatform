package org.crosswire.ksword.versification.mapping

import org.crosswire.ksword.passage.Verse
import org.crosswire.ksword.versification.Versification

/** Renders sorted ordinals as an OSIS ref, collapsing contiguous runs, matching RangedPassage.getOsisRef(). */
internal fun osisRef(v11n: Versification, ordinals: IntArray): String {
    if (ordinals.isEmpty()) return ""
    val sorted = ordinals.distinct().sorted().toIntArray()
    val runs = mutableListOf<String>()
    var start = sorted[0]
    var prev = sorted[0]
    fun emit(s: Int, e: Int) {
        val startId = Verse(v11n, s).getOsisID()
        runs.add(if (s == e) startId else "$startId-${Verse(v11n, e).getOsisID()}")
    }
    for (i in 1 until sorted.size) {
        if (sorted[i] != prev + 1) {
            emit(start, prev)
            start = sorted[i]
        }
        prev = sorted[i]
    }
    emit(start, prev)
    return runs.joinToString(" ")
}
