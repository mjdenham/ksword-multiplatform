package org.crosswire.common.util

/**
 * Minimal logging facade for ksword. By default it prints to stdout; a host app can redirect
 * output (e.g. to Logcat or os_log) by setting [writer], and can filter with [minLevel].
 */
object Log {
    enum class Level { VERBOSE, DEBUG, INFO, WARN, ERROR }

    /** Receives every emitted record. Replace to route ksword logs into the host app's logger. */
    var writer: (level: Level, message: String, error: Throwable?) -> Unit = { level, message, error ->
        println("${level.name.first()}: $message")
        error?.printStackTrace()
    }

    /** Records below this level are dropped. */
    var minLevel: Level = Level.DEBUG

    fun v(msg: String) = log(Level.VERBOSE, msg, null)

    fun d(msg: String) = log(Level.DEBUG, msg, null)

    fun i(msg: String) = log(Level.INFO, msg, null)

    fun w(msg: String, error: Throwable? = null) = log(Level.WARN, msg, error)

    fun e(msg: String, error: Throwable? = null) = log(Level.ERROR, msg, error)

    private fun log(level: Level, msg: String, error: Throwable?) {
        if (level.ordinal >= minLevel.ordinal) writer(level, msg, error)
    }
}
