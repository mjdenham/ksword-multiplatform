package org.crosswire.common.util

enum class Locale(val languageCode: String) {
    ENGLISH("en"),
    SPANISH("es"),
    FRENCH("fr"),
    GERMAN("de"),
    PORTUGUESE("pt"),
    ITALIAN("it"),
    DUTCH("nl"),
    RUSSIAN("ru"),
    CHINESE_SIMPLIFIED("zh"),
    POLISH("pl"),
    KOREAN("ko"),
    JAPANESE("ja"),
    ARABIC("ar"),
    CZECH("cs"),
    FINNISH("fi"),
    FARSI("fa"),
    HINDI("hi"),
    CROATIAN("hr"),
    HUNGARIAN("hu"),
    INDONESIAN("id"),
    LITHUANIAN("lt"),
    LATVIAN("lv"),
    HEBREW("he"),
    NORWEGIAN("nb"),
    ROMANIAN("ro"),
    SLOVAK("sk"),
    SLOVENIAN("sl"),
    SWEDISH("sv"),
    TAMIL("ta"),
    TELUGU("te"),
    THAI("th"),
    TURKISH("tr"),
    UKRAINIAN("uk"),
    VIETNAMESE("vi"),
    CANTONESE("yue");

    companion object {
        var current: Locale = ENGLISH

        /**
         * Find a Locale enum by its language code.
         * @param languageCode ISO 639-1 language code
         * @return matching Locale or ENGLISH as fallback
         */
        fun findLocale(languageCode: String): Locale {
            return entries.find { it.languageCode == languageCode } ?: ENGLISH
        }

        /**
         * Set the default locale from a language code.
         * Automatically finds and sets the matching Locale enum.
         *
         * @param languageCode ISO 639-1 language code
         */
        fun setDefaultLocale(languageCode: String) {
            current = findLocale(languageCode)
        }
    }
}