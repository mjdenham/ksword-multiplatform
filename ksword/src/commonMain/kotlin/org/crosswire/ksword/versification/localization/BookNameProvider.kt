package org.crosswire.ksword.versification.localization

import org.crosswire.common.util.Locale
import org.crosswire.ksword.versification.BibleBook

/**
 * Provider for localized Bible book names.
 */
class BookNameProvider(private val locale: Locale) {

    private val localization by lazy {
        LocalizedBookNames.forLanguage(locale.languageCode)
    }

    /**
     * Type of book name to retrieve.
     */
    enum class NameType {
        FULL,
        SHORT,
        ALTERNATE
    }

    /**
     * Get a localized name for a Bible book.
     *
     * @param book the Bible book
     * @param type the type of name to retrieve (full, short, or alternate)
     * @return the localized name, or the book's OSIS name if not found
     */
    fun getName(book: BibleBook, type: NameType): String {
        return when (type) {
            NameType.FULL -> localization.getFullName(book) ?: book.osis
            NameType.SHORT -> localization.getShortName(book) ?: book.osis
            NameType.ALTERNATE -> localization.getAlternateName(book) ?: ""
        }
    }
}