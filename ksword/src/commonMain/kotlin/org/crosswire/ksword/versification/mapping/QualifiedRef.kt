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

/**
 * A reference on the KJVA side of a versification mapping (port of JSword's QualifiedKey).
 *
 * Every ordinal inside a QualifiedRef is a KJVA ordinal. Equality is part-sensitive:
 * Single(o, "a") != Single(o, null), which is what lets a verse's halves be distinct map keys.
 */
internal sealed interface QualifiedRef {
    /** A single KJVA verse, optionally qualified by an opaque part marker ("a", "b", "pv", "intro", ...). */
    data class Single(val ordinal: Int, val part: String? = null) : QualifiedRef

    /** A contiguous KJVA verse range, cardinality >= 2. Parts on endpoints are dropped. */
    data class Range(val startOrdinal: Int, val endOrdinal: Int) : QualifiedRef

    /** A named section absent from the KJVA, e.g. "?BelThenKingSaid". The '?' is part of the name. */
    data class Section(val name: String) : QualifiedRef
}
