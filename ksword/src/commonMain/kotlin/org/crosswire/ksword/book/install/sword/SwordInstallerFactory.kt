package org.crosswire.ksword.book.install.sword

class SwordInstallerFactory {
    companion object {
        val CROSSWIRE_INSTALLER_URLS = InstallerUrls("CrossWire", "www.crosswire.org", "/ftpmirror/pub/sword/packages/rawzip", "/ftpmirror/pub/sword/raw")
        val EBIBLE_INSTALLER_URLS = InstallerUrls("eBible", "ebible.org", "/sword/zip", "/sword")
        val LOCKMAN_INSTALLER_URLS = InstallerUrls("Lockman", "www.crosswire.org", "/ftpmirror/pub/sword/lockmanpackages", "/ftpmirror/pub/sword/lockmanraw")
        val AND_BIBLE_INSTALLER_URLS = InstallerUrls("AndBible", "andbible.github.io", "/data/andbible/zip", "/data/andbible")
        // Institute for Bible Translation — HTTPS mirror (ibtrussia.org), same /ftpmirror pattern as CrossWire.
        val IBT_INSTALLER_URLS = InstallerUrls("IBT", "ibtrussia.org", "/ftpmirror/pub/modsword/rawzip", "/ftpmirror/pub/modsword/raw")
    }

    val crosswireInstaller = HttpsSwordInstaller(CROSSWIRE_INSTALLER_URLS)
    val ebibleInstaller = HttpsSwordInstaller(EBIBLE_INSTALLER_URLS)
    val lockmanInstaller = HttpsSwordInstaller(LOCKMAN_INSTALLER_URLS)
    val andBibleInstaller = HttpsSwordInstaller(AND_BIBLE_INSTALLER_URLS)
    val ibtInstaller = HttpsSwordInstaller(IBT_INSTALLER_URLS)

    suspend fun findInstaller(initials: String): HttpsSwordInstaller {
        // IBT before eBible: eBible carries many duplicate copies, so a native IBT module wins.
        // Skip a source whose catalog can't be reached, otherwise one source being down (e.g.
        // CrossWire, listed first) would abort the whole search and modules in later sources
        // (IBT, eBible) could never be installed.
        listOf(crosswireInstaller, andBibleInstaller, lockmanInstaller, ibtInstaller, ebibleInstaller).forEach { installer ->
            val books = runCatching { installer.getBooks() }.getOrDefault(emptyList())
            if (books.find { it.initials == initials } != null) {
                return installer
            }
        }
        throw Exception("No installer found for initials: $initials")
    }
}