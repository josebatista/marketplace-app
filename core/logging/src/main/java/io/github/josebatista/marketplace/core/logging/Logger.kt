package io.github.josebatista.marketplace.core.logging

/**
 * A simple logging interface.
 *
 * Implementations of this interface are responsible for logging messages,
 * which can be used for debugging and monitoring the application's behavior.
 */
public interface Logger {

    /**
     * Sends a log message.
     *
     * This function is used to output a log message, which might be printed to the console,
     * written to a file, or transmitted to a remote logging service.
     *
     * @param message The message to log.
     */
    public fun sendLog(message: String)
}
