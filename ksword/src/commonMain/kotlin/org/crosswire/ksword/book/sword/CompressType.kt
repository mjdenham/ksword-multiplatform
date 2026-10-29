package org.crosswire.ksword.book.sword

internal enum class CompressType {
    ZIP,
    LZSS;

    companion object {
        fun fromString(name: String?): CompressType? = entries.find { it.name.equals(name, ignoreCase = true) }
    }
}
