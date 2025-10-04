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

        /**
         * Find a Locale enum by its language code.
         * @param languageCode ISO 639-1 language code (e.g., "en", "es")
         * @return matching Locale or ENGLISH as fallback
         */
        fun findLocale(languageCode: String): Locale {
            return entries.find { it.languageCode == languageCode } ?: ENGLISH
        }

        /**
         * Set the default locale from a language code.
         * Automatically finds and sets the matching Locale enum.
         *
         * @param languageCode ISO 639-1 language code (e.g., "en", "es")
         */
        fun setDefaultLocale(languageCode: String) {
            current = findLocale(languageCode)
        }
    }
}