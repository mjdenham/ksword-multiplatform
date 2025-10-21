package org.crosswire.ksword.book.install.sword

class SwordInstallerFactory {
    companion object {
        val CROSSWIRE_INSTALLER_URLS = InstallerUrls("CrossWire", "www.crosswire.org", "/ftpmirror/pub/sword/packages/rawzip", "/ftpmirror/pub/sword/raw")
        val EBIBLE_INSTALLER_URLS = InstallerUrls("eBible", "ebible.org", "/sword/zip", "/sword")
        val LOCKMAN_INSTALLER_URLS = InstallerUrls("Lockman", "www.crosswire.org", "/ftpmirror/pub/sword/lockmanpackages", "/ftpmirror/pub/sword/lockmanraw")
    }

    val crosswireInstaller = HttpsSwordInstaller(CROSSWIRE_INSTALLER_URLS)
    val ebibleInstaller = HttpsSwordInstaller(EBIBLE_INSTALLER_URLS)
    val lockmanInstaller = HttpsSwordInstaller(LOCKMAN_INSTALLER_URLS)

    suspend fun findInstaller(initials: String): HttpsSwordInstaller {
        listOf(crosswireInstaller, lockmanInstaller, ebibleInstaller).forEach { installer ->
            if (installer.getBooks().find { it.initials == initials } != null) {
                return installer
            }
        }
        throw Exception("No installer found for initials: $initials")
    }
}