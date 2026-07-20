package org.crosswire.ksword.book.install.sword

import org.crosswire.ksword.book.BookException

class SwordInstallerFactory {
    companion object {
        private const val DAY_MS = 24L * 60 * 60 * 1000
        private const val DEFAULT_CATALOG_MAX_AGE_MS = 10 * DAY_MS

        val CROSSWIRE_INSTALLER_URLS = InstallerUrls("CrossWire", "www.crosswire.org", "/ftpmirror/pub/sword/packages/rawzip", "/ftpmirror/pub/sword/raw", DEFAULT_CATALOG_MAX_AGE_MS)
        val EBIBLE_INSTALLER_URLS = InstallerUrls("eBible", "ebible.org", "/sword/zip", "/sword", DEFAULT_CATALOG_MAX_AGE_MS)
        val LOCKMAN_INSTALLER_URLS = InstallerUrls("Lockman", "www.crosswire.org", "/ftpmirror/pub/sword/lockmanpackages", "/ftpmirror/pub/sword/lockmanraw", DEFAULT_CATALOG_MAX_AGE_MS)
        val AND_BIBLE_INSTALLER_URLS = InstallerUrls("AndBible", "andbible.github.io", "/data/andbible/zip", "/data/andbible", DEFAULT_CATALOG_MAX_AGE_MS)
        // Institute for Bible Translation — HTTPS mirror (ibtrussia.org), same /ftpmirror pattern as CrossWire.
        val IBT_INSTALLER_URLS = InstallerUrls("IBT", "ibtrussia.org", "/ftpmirror/pub/modsword/rawzip", "/ftpmirror/pub/modsword/raw", DEFAULT_CATALOG_MAX_AGE_MS)
        // Tap Bible's own modules (generated commentaries), GitHub Pages project site.
        val TAP_BIBLE_INSTALLER_URLS = InstallerUrls("TapBible", "mjdenham.github.io", "/tap-bible-modules/zip", "/tap-bible-modules", 2 * DAY_MS)
    }

    val crosswireInstaller = HttpsSwordInstaller(CROSSWIRE_INSTALLER_URLS)
    val ebibleInstaller = HttpsSwordInstaller(EBIBLE_INSTALLER_URLS)
    val lockmanInstaller = HttpsSwordInstaller(LOCKMAN_INSTALLER_URLS)
    val andBibleInstaller = HttpsSwordInstaller(AND_BIBLE_INSTALLER_URLS)
    val ibtInstaller = HttpsSwordInstaller(IBT_INSTALLER_URLS)
    val tapBibleInstaller = HttpsSwordInstaller(TAP_BIBLE_INSTALLER_URLS)

    suspend fun findInstaller(initials: String): HttpsSwordInstaller {
        // IBT before eBible: eBible carries many duplicate copies, so a native IBT module wins.
        // Skip a source whose catalog can't be reached, otherwise one source being down (e.g.
        // CrossWire, listed first) would abort the whole search and modules in later sources
        // (IBT, eBible) could never be installed.
        // TapBible first: our own modules win any (future) initials collision with other repos.
        listOf(tapBibleInstaller, crosswireInstaller, andBibleInstaller, lockmanInstaller, ibtInstaller, ebibleInstaller).forEach { installer ->
            val books = runCatching { installer.getBooks() }.getOrDefault(emptyList())
            if (books.find { it.initials == initials } != null) {
                return installer
            }
        }
        throw BookException("No installer found for initials: $initials")
    }
}