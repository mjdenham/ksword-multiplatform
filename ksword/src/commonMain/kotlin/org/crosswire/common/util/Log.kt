package org.crosswire.common.util

object Log {
    fun e(msg: String, ex: Exception? = null) {
        if (ex != null) {
            println("Error: $msg $ex")
            ex.printStackTrace()
        } else {
            println("Error: $msg")
        }
    }

    fun d(msg: String) {
        println("Debug: $msg")
    }

    fun w(msg: String) {
        println("Warning: $msg")
    }
}