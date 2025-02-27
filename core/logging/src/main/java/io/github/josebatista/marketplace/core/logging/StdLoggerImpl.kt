package io.github.josebatista.marketplace.core.logging

import javax.inject.Inject
import javax.inject.Singleton

/**
 * A simple logger implementation that outputs log messages to the standard output.
 *
 * This implementation of [Logger] prints log messages to the console prefixed with "[LOGGER]:".
 * It is annotated with [Singleton] to ensure a single instance is used throughout the application.
 *
 * Example usage:
 * ```
 * val logger: Logger = StdLoggerImpl()
 * logger.sendLog("Test message")
 * // Output: [LOGGER]: Test message
 * ```
 */
@Singleton
internal class StdLoggerImpl @Inject constructor() : Logger {
    override fun sendLog(message: String) {
        println("[LOGGER]: $message")
    }
}
