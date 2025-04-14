package org.crosswire.ksword.book.install.sword

class SwordInstallerFactory {
    companion object {
        val CROSSWIRE_INSTALLER_URLS = InstallerUrls("CrossWire", "www.crosswire.org", "/ftpmirror/pub/sword/packages/rawzip", "/ftpmirror/pub/sword/raw")
    }

    val crosswireInstaller = HttpsSwordInstaller(CROSSWIRE_INSTALLER_URLS)
}