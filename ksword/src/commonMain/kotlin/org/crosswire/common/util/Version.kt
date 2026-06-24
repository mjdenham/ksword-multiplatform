package org.crosswire.common.util

/**
 * An immutable dotted version of 1 to 4 numeric parts (major.minor.micro.nano), comparable
 * part-by-part. Ported from jsword's org.crosswire.common.util.Version.
 *
 * The separator in the pattern is the regex `.` (any char), matching jsword: this is what lets
 * SWORD module versions like "1.5-150704" parse (the '-' is consumed as a separator, giving
 * parts 1, 5, 150704) rather than being rejected.
 */
class Version(val original: String) : Comparable<Version> {

    private val parts = intArrayOf(-1, -1, -1, -1)

    init {
        val match = VERSION_PATTERN.matchEntire(original)
            ?: throw IllegalArgumentException("invalid: $original")
        for (i in 1..4) {
            val group = match.groups[i] ?: break
            parts[i - 1] = group.value.toInt()
        }
    }

    override fun compareTo(other: Version): Int {
        for (i in parts.indices) {
            val result = parts[i] - other.parts[i]
            if (result != 0) return result
        }
        return 0
    }

    override fun equals(other: Any?): Boolean =
        other is Version && parts.contentEquals(other.parts)

    override fun hashCode(): Int = parts.contentHashCode()

    override fun toString(): String = original

    companion object {
        val VERSION_PATTERN = Regex("^(\\d+)(?:.(\\d+))?(?:.(\\d+))?(?:.(\\d+))?$")
    }
}
