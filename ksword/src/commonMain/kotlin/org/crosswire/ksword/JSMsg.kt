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
 * © CrossWire Bible Society, 2007 - 2016
 *
 */
package org.crosswire.ksword

//import org.crosswire.common.util.MsgBase

/**
 * Compile safe Msg resource settings.
 *
 * @see gnu.lgpl.License The GNU Lesser General Public License for details.
 *
 * @author DM Smith
 */
object JSMsg /*: MsgBase() */ {
    /**
     * Get the internationalized text, but return key if key is unknown.
     * The text requires one or more parameters to be passed.
     *
     * @param key the formatted key to internationalize
     * @param params the parameters to use in creating the message
     * @return the formatted, internationalized text
     */
    fun gettext(key: String, vararg params: Any): String {
        return key //msg.lookup(key, params)
    }

//    private val msg: MsgBase = JSMsg()
}
