package io.github.josebatista.marketplace

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.josebatista.marketplace.util.ConnectivityObserver
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

internal class MainActivityViewModel(connectivityObserver: ConnectivityObserver) : ViewModel() {
    val isConnected = connectivityObserver
        .isConnected
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )
}
