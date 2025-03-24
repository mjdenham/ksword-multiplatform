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
package org.crosswire.ksword.passage


/**
 * When something tries to use a verse that we don't understand.
 *
 * @see gnu.lgpl.License The GNU Lesser General Public License for details.
 *
 * @author Joe Walker
 */
class NoSuchVerseException : NoSuchKeyException {
    /**
     * Construct the Exception with a message
     *
     * @param msg
     * The resource id to read
     */
    constructor(msg: String?) : super(msg!!)

    /**
     * Construct the Exception with a message and a nested Exception
     *
     * @param msg
     * The resource id to read
     * @param ex
     * The nested Exception
     */
    constructor(msg: String?, ex: Throwable?) : super(msg!!, ex)

    companion object {
        /**
         * Serialization ID
         */
        private const val serialVersionUID = 3257572797638129463L
    }
}
