package org.crosswire.common.util

object Log {
    fun e(msg: String, ex: Exception? = null) {
        println("Error: $msg")
        ex?.printStackTrace()
    }

    fun d(msg: String) {
        println("Debug: $msg")
    }

    fun w(msg: String, ex: Exception? = null) {
        println("Warning: $msg")
        ex?.printStackTrace()
    }
}