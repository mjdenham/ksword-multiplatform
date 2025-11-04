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
 * © CrossWire Bible Society, 2012 - 2016
 *
 */
package org.crosswire.ksword.versification.system

import org.crosswire.ksword.versification.Versification

/**
 * The Versifications class manages the creation of Versifications as needed.
 * It delays the construction of the Versification until getVersification(String name) is called.
 *
 * @author DM Smith
 */
object Versifications {
    val defaultVersification: Versification
        /**
         * Get the default Versification.
         */
        get() = getVersification(DEFAULT_V11N)

    /**
     * Get the Versification by its name. If name is null then return the default Versification.
     *
     * @param name the name of the Versification
     * @return the Versification or throw exception if it is not known.
     */
    fun getVersification(name: String = DEFAULT_V11N): Versification {
        var actual = name

        // This class delays the building of a Versification to when it is
        // actually needed.
        var rs = fluffed[actual]
        if (rs == null) {
            rs = fluff(actual)
            if (rs != null) {
                fluffed[actual] = rs
            }
        }

        return rs
    }

    /**
     * Determine whether the named Versification is known.
     *
     * @param name the name of the Versification
     * @return true when the Versification is available for use
     */
    fun isDefined(name: String?): Boolean {
        return name == null || known.contains(name)
    }

    private fun fluff(name: String): Versification {
        // Keep KJV at the top as it is the most common
        return when (name) {
            SystemKJV.V11N_NAME -> SystemKJV()
        //then in alphabetical order, to ease the developer checking we have them all
            SystemCalvin.V11N_NAME -> SystemCalvin()
            SystemCatholic.V11N_NAME -> SystemCatholic()
            SystemCatholic2.V11N_NAME -> SystemCatholic2()
            SystemDarbyFR.V11N_NAME -> SystemDarbyFR()
            SystemGerman.V11N_NAME -> SystemGerman()
            SystemKJVA.V11N_NAME -> SystemKJVA()
            SystemLeningrad.V11N_NAME -> SystemLeningrad()
            SystemLuther.V11N_NAME -> SystemLuther()
            SystemLXX.V11N_NAME -> SystemLXX()
            SystemMT.V11N_NAME -> SystemMT()
            SystemNRSV.V11N_NAME -> SystemNRSV()
            SystemNRSVA.V11N_NAME -> SystemNRSVA()
            SystemOrthodox.V11N_NAME -> SystemOrthodox()
            SystemSegond.V11N_NAME -> SystemSegond()
            SystemSynodal.V11N_NAME -> SystemSynodal()
            SystemSynodalProt.V11N_NAME -> SystemSynodalProt()
            SystemVulg.V11N_NAME -> SystemVulg()
            else -> throw RuntimeException("Unknown Versification: $name")
        }
    }

    /**
     * Add a Versification that is not predefined by KSword.
     *
     * @param rs the Versification to register
     */
    fun register(rs: Versification) {
        fluffed[rs.name] = rs
        known.add(rs.name)
    }

    /**
     * Get an iterator over all known versifications.
     *
     * @return an iterator of versification names.
     */
    fun iterator(): Iterator<String?> {
        return known.iterator()
    }

    /**
     * @return number of versifications
     */
    fun size(): Int {
        return known.size
    }

    /**
     * The set of v11n names.
     */
    private val known: MutableSet<String?> = mutableSetOf()

    /**
     * The map of instantiated Versifications, given by their names.
     */
    private val fluffed: MutableMap<String?, Versification>

    /**
     * This class is a singleton, enforced by a private constructor.
     */
    init {
        known.add(SystemCalvin.V11N_NAME)
        known.add(SystemCatholic.V11N_NAME)
        known.add(SystemCatholic2.V11N_NAME)
        known.add(SystemDarbyFR.V11N_NAME)
        known.add(SystemGerman.V11N_NAME)
        known.add(SystemKJV.V11N_NAME)
        known.add(SystemKJVA.V11N_NAME)
        known.add(SystemLeningrad.V11N_NAME)
        known.add(SystemLuther.V11N_NAME)
        known.add(SystemLXX.V11N_NAME)
        known.add(SystemMT.V11N_NAME)
        known.add(SystemNRSV.V11N_NAME)
        known.add(SystemNRSVA.V11N_NAME)
        known.add(SystemOrthodox.V11N_NAME)
        known.add(SystemSegond.V11N_NAME)
        known.add(SystemSynodal.V11N_NAME)
        known.add(SystemSynodalProt.V11N_NAME)
        known.add(SystemVulg.V11N_NAME)
        fluffed = mutableMapOf()
    }

    /**
     * The default Versification for KSword is the KJV.
     * This is subject to change at any time.
     */
    const val DEFAULT_V11N: String = SystemKJV.V11N_NAME
}
