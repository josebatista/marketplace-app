package io.github.josebatista.marketplace

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.josebatista.marketplace.util.ConnectivityObserver
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel for the MainActivity that monitors the device's network connectivity.
 *
 * This ViewModel utilizes a [ConnectivityObserver] to observe network connectivity changes,
 * and exposes the connectivity status as a [StateFlow] of [Boolean] through the [isConnected] property.
 * The state flow is created using [stateIn] in the [viewModelScope] with a sharing strategy of
 * [SharingStarted.WhileSubscribed] and an initial value of `false`.
 *
 * @param connectivityObserver An instance of [ConnectivityObserver] that provides a [Flow] of connectivity status.
 */
internal class MainActivityViewModel(connectivityObserver: ConnectivityObserver) : ViewModel() {

    /**
     * A [StateFlow] that emits `true` when the device is connected to the network,
     * and `false` otherwise. This state is derived from the [connectivityObserver] and is
     * maintained within the [viewModelScope] with a stop timeout of 5000ms.
     */
    val isConnected = connectivityObserver
        .isConnected
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )
}
