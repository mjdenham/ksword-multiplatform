package org.crosswire.common.util

enum class Locale(val languageCode: String) {
    ENGLISH("en"),
    FRENCH("fr"),
    GERMAN("de"),
    ITALIAN("it"),
    PORTUGUESE("pt"),
    SPANISH("es");

    companion object {
        var current: Locale = ENGLISH

        fun findLocale(languageCode: String): Locale {
            return entries.find { it.languageCode == languageCode } ?: ENGLISH
        }
    }
}