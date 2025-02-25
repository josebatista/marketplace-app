package io.github.josebatista.logging

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class StdLoggerImpl @Inject constructor() : Logger {
    override fun sendLog(message: String) {
        println("[LOGGER]: $message")
    }
}
