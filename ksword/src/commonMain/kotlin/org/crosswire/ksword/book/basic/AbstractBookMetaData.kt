/**
 * Distribution License:
 * KSword is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License, version 2.1 or later
 * as published by the Free Software Foundation. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * The License is available on the internet at:
 * http://www.gnu.org/copyleft/lgpl.html
 * or by writing to:
 * Free Software Foundation, Inc.
 * 59 Temple Place - Suite 330
 * Boston, MA 02111-1307, USA
 *
 * © CrossWire Bible Society, 2005 - 2016
 *
 */
package org.crosswire.ksword.book.basic

import okio.Path
import org.crosswire.ksword.book.BookMetaData
import org.crosswire.ksword.book.sword.SwordBookMetaData.Companion.KEY_DATA_PATH

/**
 * An implementation of the Property Change methods from BookMetaData.
 *
 * @see gnu.lgpl.License The GNU Lesser General Public License for details.
 *
 * @author Joe Walker
 */
/**
 * @author DM Smith
 * @see gnu.lgpl.License The GNU Lesser General Public License for details.<br></br>
 * The copyright to this program is held by its authors.
 */
abstract class AbstractBookMetaData : BookMetaData {

    override fun putProperty(key: String?, value: String?) {
        putProperty(key, value, false)
    }

    override val osisID: String
        get() = bookCategory.name + '.' + initials

    override lateinit var  library: Path

    // library/datapath = location of the datafiles e.g. mnt/sdcard/Android/data/packagename/files/modules/texts/ztext/bsb/
    val location: Path
        get() {
            val dataPath = getProperty(KEY_DATA_PATH) ?: throw IllegalStateException("Data path not set")
            return library.resolve(dataPath)
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AbstractBookMetaData) return false

        // Compares properties for structural equality
        return this.bookCategory == other.bookCategory && this.name == other.name && this.initials == other.initials
    }

    override fun hashCode(): Int {
        var result = bookCategory.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + initials.hashCode()
        return result
    }
}
