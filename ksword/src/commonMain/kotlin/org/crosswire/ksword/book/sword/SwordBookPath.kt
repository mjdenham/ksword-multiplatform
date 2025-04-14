package org.crosswire.ksword.book.sword

import okio.Path
import org.crosswire.ksword.book.sword.SwordConstants.DIR_CATALOG
import org.crosswire.ksword.book.sword.SwordConstants.DIR_CONF

object SwordBookPath {
    lateinit var swordBookPath: Path

    val confDir: Path
        get() = swordBookPath.resolve(DIR_CONF)

    val catalogDir: Path
        get() = swordBookPath.resolve(DIR_CATALOG)
}