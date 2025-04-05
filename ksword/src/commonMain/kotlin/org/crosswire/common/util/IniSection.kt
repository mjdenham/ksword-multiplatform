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
 * © CrossWire Bible Society, 2015 - 2016
 */
package org.crosswire.common.util

import okio.BufferedSource
import okio.FileSystem
import okio.Path
import okio.SYSTEM
import okio.buffer
import okio.use
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * A utility class for a section of an INI style configuration file.
 * Keys and values are maintained in insertion order. A key may have more than one value.
 */
class IniSection {

    /**
     * Start over.
     */
    fun clear() {
        section.clear()
        warnings.setLength(0)
        warnings.trimToSize()
        report = ""
    }

    /**
     * Get the number of keys in this section.
     *
     * @return the count
     */
    fun size(): Int {
        return section.size
    }

    /**
     * Get the first value for the key.
     *
     * @param key the key
     * @return the value at the specified index or null
     */
    operator fun get(key: String?): String? {
        val values: List<String>? = section[key]
        return values?.get(0)
    }

    fun get(key: String?, defaultValue: String?): String? {
        val values: List<String>? = section[key]
        return values?.get(0) ?: defaultValue
    }

    /**
     * Load the INI from a file using the given encoding.
     *
     * @param file the file to load
     * @param encoding the encoding of the file
     * @throws IOException
     */
    fun load(file: Path) {
        configFilePath = file
        FileSystem.SYSTEM.source(configFilePath).use { fileSource ->
            fileSource.buffer().use { bufferedFileSource ->
                load(bufferedFileSource)
            }
        }
    }

    /**
     * Load the conf from a buffer. This is used to load conf entries from the
     * mods.d.tar.gz file.
     *
     * @param buffer the buffer to load
     */
    fun load(buffer: BufferedSource) {
        doLoad(buffer)
    }

    private fun doLoad(buffer: BufferedSource) {
        while (true) {
            val line = buffer.readUtf8Line() ?: break
                if (line.isEmpty() || line.first() == '#')
                    continue

                if (isSectionLine(line)) {
                    // The conf file contains a leading line of the form [KJV]
                    // This is the acronym by which Sword refers to it.
                    name = line.substring(1, line.length - 1)
                    continue
                }

            val split = line.split("=")
            if (split.size != 2) {
                warnings.appendLine("Skipping: Expected to see '=' in: $line")
                continue
            }
            val key = split[0].trim()
            val value = split[1].trim()
            add(key, value)
        }
    }

    /**
     * Replace the value(s) for the key with a new value.
     *
     * @param key the key for the section
     * @param value the value for the key
     * @return whether the replace happened
     */
    fun replace(key: String?, value: String?): Boolean {
        if (!allowed(key, value)) {
            return false
        }

        val values: MutableCollection<String> = getOrCreateValues(key)
        values.clear()
        return values.add(value)
    }

    /**
     * Add a value for the key. Duplicate values are not allowed.
     *
     * @param key the key for the section
     * @param value the value for the key
     * @return whether the value was added or is already present.
     */
    private fun add(key: String?, value: String): Boolean {
        if (!allowed(key, value)) {
            return false
        }

        val values: MutableList<String> = getOrCreateValues(key)
        if (values.contains(value)) {
            warnings.append("Duplicate value: ").append(key).append(" = ").append(value).append('\n')
            return true
        }
        return values.add(value)
    }

    private fun getOrCreateValues(key: String) = section.getOrPut(key, { mutableListOf() })

    private fun isSectionLine(line: String) = line.isNotEmpty() && line.first() == '[' && line.last() == ']';

    @OptIn(ExperimentalContracts::class)
    private fun allowed(key: String?, value: String?): Boolean {
        contract {
            returns(true) implies (key != null && value != null)
        }

        if (key.isNullOrEmpty() || value == null) {
            if (key == null) {
                warnings.appendLine("Null keys not allowed: = $value")
            } else if (key.isEmpty()) {
                warnings.appendLine("Empty keys not allowed: = $value")
            }
            if (value == null) {
                warnings.appendLine("Null values are not allowed: $key =")
            }
            return false
        }
        return true
    }

    lateinit var name: String

    /**
     * A map of values by key names.
     */
    private val section: MutableMap<String, MutableList<String>> = mutableMapOf()

    private lateinit var configFilePath: Path

    private var charset: String? = null

    private var warnings: StringBuilder = StringBuilder()

    private var report: String? = null

    companion object {
        /**
         * Is there more following this line
         *
         * @param line the trimmed string to check
         * @return whether this line continues
         */
        private fun more(line: String): Boolean {
            val length = line.length
            return length > 0 && line[length - 1] == '\\'
        }

        /**
         * Buffer size is based on file size but keep it with within reasonable limits
         */
        private const val MAX_BUFF_SIZE = 2 * 1024
    }
}
