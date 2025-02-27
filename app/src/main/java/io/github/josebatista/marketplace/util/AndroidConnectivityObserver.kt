package io.github.josebatista.marketplace.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.core.content.getSystemService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/**
 * An implementation of [ConnectivityObserver] that monitors network connectivity changes using
 * Android's [ConnectivityManager].
 *
 * This class registers a default network callback and emits connectivity status updates as
 * a [Flow] of [Boolean] values.
 * The flow emits `true` when the device is connected to a network with validated capabilities, and
 * `false` when the network
 * is lost or unavailable.
 *
 * Connectivity changes are observed via a [callbackFlow] that creates
 * a [ConnectivityManager.NetworkCallback]. When the flow
 * collection is cancelled, the network callback is automatically unregistered using [awaitClose].
 *
 * @param context The [Context] used to retrieve the [ConnectivityManager] system service.
 */
internal class AndroidConnectivityObserver(context: Context) : ConnectivityObserver {

    private val connectivityManager = context.getSystemService<ConnectivityManager>()!!

    override val isConnected: Flow<Boolean>
        get() = callbackFlow {
            val callback = object : ConnectivityManager.NetworkCallback() {
                override fun onCapabilitiesChanged(
                    network: Network,
                    networkCapabilities: NetworkCapabilities
                ) {
                    super.onCapabilitiesChanged(network, networkCapabilities)
                    val connected = networkCapabilities.hasCapability(
                        NetworkCapabilities.NET_CAPABILITY_VALIDATED
                    )
                    trySend(connected)
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    trySend(false)
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    trySend(false)
                }

                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    trySend(true)
                }
            }
            connectivityManager.registerDefaultNetworkCallback(callback)
            awaitClose { connectivityManager.unregisterNetworkCallback(callback) }
        }
}
