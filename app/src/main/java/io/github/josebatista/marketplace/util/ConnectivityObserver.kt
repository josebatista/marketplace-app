package io.github.josebatista.marketplace.util

import kotlinx.coroutines.flow.Flow

/**
 * Defines a contract for observing network connectivity changes.
 *
 * Implementations of this interface provide a [Flow] that emits the current connectivity status:
 * `true` when the device is connected to a network, and `false` when it is not.
 */
internal interface ConnectivityObserver {

    /**
     * A [Flow] that continuously emits the network connectivity status.
     *
     * Observers can collect this flow to react to connectivity changes in real-time.
     */
    val isConnected: Flow<Boolean>
}
