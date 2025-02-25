package io.github.josebatista.marketplace.data.network

import io.github.josebatista.marketplace.data.BuildConfig

public fun constructUrl(endpoint: String): String {
    return when {
        endpoint.contains(BuildConfig.BASE_URL) -> endpoint
        endpoint.startsWith("/") -> BuildConfig.BASE_URL + endpoint.drop(1)
        else -> BuildConfig.BASE_URL + endpoint
    }
}
