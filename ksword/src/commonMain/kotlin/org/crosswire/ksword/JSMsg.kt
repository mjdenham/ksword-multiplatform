package org.crosswire.ksword

import org.crosswire.common.util.Locale
import org.crosswire.ksword.versification.localization.LocalizedStrings

object JSMsg {
    /**
     * Get the internationalized text, but return key if key is unknown.
     * The text requires one or more parameters to be passed.
     *
     * @param key the formatted key to internationalize
     * @param params the parameters to use in creating the message
     * @return the formatted, internationalized text
     */
    fun gettext(key: String, vararg params: Any): String {
        val localization = LocalizedStrings.forLanguage(Locale.current.languageCode)
        val localizedString = localization.getString(key)

        // If we have a localized string, format it with params if any
        return if (localizedString != null && params.isNotEmpty()) {
            String.format(localizedString, *params)
        } else {
            localizedString ?: key // Return key if no translation found
        }
    }
}
