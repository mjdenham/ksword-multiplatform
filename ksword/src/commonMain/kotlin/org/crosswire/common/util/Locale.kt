package org.crosswire.common.util

enum class Locale(val languageCode: String) {
    AFRIKAANS("af"),
    ALBANIAN("sq"),
    ARABIC("ar"),
    ARMENIAN("hy"),
    AZERBAIJANI("az"),
    AZERBAIJANI_SOUTH("azb"),
    BASQUE("eu"),
    BEAVER("bea"),
    BELARUSIAN("be"),
    BRETON("br"),
    BULGARIAN("bg"),
    BURMESE("my"),
    CALO("rmq"),
    CANTONESE("yue"),
    CEBUANO("ceb"),
    CHEROKEE("chr"),
    CHINESE_LITERARY("lzh"),
    CHINESE_SIMPLIFIED("zh"),
    CHINESE_SIMPLIFIED_HANS("zh-hans"),
    CHINESE_TRADITIONAL_HANT("zh-hant"),
    CHURCH_SLAVONIC("cu"),
    COPTIC("cop"),
    COPTIC_SAHIDIC("cop-sa"),
    CROATIAN("hr"),
    CZECH("cs"),
    DANISH("da"),
    DARI("prs"),
    DUTCH("nl"),
    ENGLISH("en"),
    ENGLISH_MIDDLE("enm"),
    ESPERANTO("eo"),
    ESTONIAN("et"),
    FARSI("fa"),
    FINNISH("fi"),
    FRENCH("fr"),
    GAELIC_MANX("gv"),
    GAELIC_SCOTTISH("gd"),
    GEEZ("gez"),
    GERMAN("de"),
    GOTHIC("got"),
    GREEK("el"),
    GREEK_ANCIENT("grc"),
    HAITIAN_CREOLE("ht"),
    HEBREW("he"),
    HEBREW_BIBLICAL("hbo"),
    HINDI("hi"),
    HUNGARIAN("hu"),
    INDONESIAN("id"),
    IRISH("ga"),
    ITALIAN("it"),
    JAPANESE("ja"),
    KAPINGAMARANGI("kpl"),
    KEKCHI("kek"),
    KHMER("km"),
    KLINGON("tlh"),
    KOREAN("ko"),
    KAREN_SGAW("ksw"),
    LATIN("la"),
    LATVIAN("lv"),
    LINGALA("ln"),
    LITHUANIAN("lt"),
    MALAGASY("mg"),
    MALAYALAM("mlf"),
    MAORI("mi"),
    MONGOLIAN("mn"),
    NDEBELE("nd"),
    NORWEGIAN("nb"),
    NORWEGIAN_NYNORSK("nn"),
    POHNPEIAN("pon"),
    POLISH("pl"),
    PORTUGUESE("pt"),
    POTAWATOMI("pot"),
    ROMANIAN("ro"),
    RUSSIAN("ru"),
    SAMA("sml"),
    SHONA("sn"),
    SLOVAK("sk"),
    SLOVENIAN("sl"),
    SOMALI("so"),
    SPANISH("es"),
    SWAHILI("sw"),
    SWEDISH("sv"),
    SYRIAC("syr"),
    TAGALOG("tl"),
    TAMIL("ta"),
    TAUSUG("tsg"),
    TELUGU("te"),
    THAI("th"),
    TOK_PISIN("tpi"),
    TURKISH("tr"),
    UKRAINIAN("uk"),
    UMA("ppk"),
    URDU("ur"),
    VIETNAMESE("vi"),
    WELSH("cy"),
    WEST_FLEMISH("vls"),
    UNKNOWN("??");

    val displayName: String
        get() = name.split('_').joinToString(" ") { it.lowercase().replaceFirstChar(Char::titlecase) }

    companion object {
        var current: Locale = ENGLISH

        /**
         * Find a Locale enum by its language code.
         * @param languageCode ISO 639-1 language code
         * @return matching Locale or ENGLISH as fallback
         */
        fun findLocale(languageCode: String): Locale {
            return entries.find { it.languageCode == languageCode } ?: UNKNOWN
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