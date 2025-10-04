package org.crosswire.ksword.javautil

import org.crosswire.common.util.Locale
import org.crosswire.ksword.versification.BibleBook
import org.crosswire.ksword.versification.localization.LocalizedBookNames

/**
 * ResourceBundle for loading localized Bible book names.
 * This implementation uses the LocalizedBookNames system for efficient memory usage.
 */
class ResourceBundle(private val locale: Locale) {

    private val localization by lazy {
        LocalizedBookNames.forLanguage(locale.languageCode)
    }

    /**
     * Get a localized string for the given key.
     * Keys should be in format: "{OSIS}.Full", "{OSIS}.Short", or "{OSIS}.Alt"
     *
     * @param key the resource key (e.g., "Gen.Full", "Matt.Short")
     * @return the localized string, or the key itself if not found
     */
    fun getString(key: String): String {
        // Parse the key to extract book OSIS and type
        val parts = key.split(".")
        if (parts.size < 2) {
            return key
        }

        val osisName = parts[0]
        val type = parts[1]

        val book = BibleBook.fromExactOSISOrNull(osisName) ?: return key

        return when (type) {
            "Full" -> localization.getFullName(book) ?: key
            "Short" -> localization.getShortName(book) ?: key
            "Alt" -> localization.getAlternateName(book) ?: ""
            else -> key
        }
    }
}