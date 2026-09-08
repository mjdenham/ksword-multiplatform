package org.crosswire.ksword.book

/** The markup a module's text is written in, from its `SourceType` conf entry. */
enum class SourceType {
    OSIS,
    THML,
    TEI,
    PLAINTEXT,
    GBF;

    companion object {
        /** SWORD reads a missing or unrecognised `SourceType` as plain text. */
        fun parse(value: String?): SourceType =
            entries.firstOrNull { it.name.equals(value, ignoreCase = true) } ?: PLAINTEXT
    }
}
