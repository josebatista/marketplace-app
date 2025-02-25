package io.github.josebatista.marketplace.util

import kotlinx.coroutines.flow.Flow

internal interface ConnectivityObserver {
    val isConnected: Flow<Boolean>
}
