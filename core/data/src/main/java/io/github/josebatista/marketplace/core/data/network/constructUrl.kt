package io.github.josebatista.marketplace.core.data.network

import io.github.josebatista.marketplace.core.data.BuildConfig

/**
 * Constructs a full URL by combining the application's base URL with the given endpoint.
 *
 * The function checks the provided [endpoint] and:
 * - Returns the [endpoint] unchanged if it already contains the base URL.
 * - If the [endpoint] starts with a forward slash ("/"), it removes the slash
 * and appends the remainder to [BuildConfig.BASE_URL].
 * - Otherwise, it appends the [endpoint] directly to [BuildConfig.BASE_URL].
 *
 * @param endpoint The endpoint to be appended to the base URL.
 * @return A fully qualified URL formed by concatenating [BuildConfig.BASE_URL] and the adjusted [endpoint].
 */
public fun constructUrl(endpoint: String): String {
    return when {
        endpoint.contains(BuildConfig.BASE_URL) -> endpoint
        endpoint.startsWith("/") -> BuildConfig.BASE_URL + endpoint.drop(1)
        else -> BuildConfig.BASE_URL + endpoint
    }
}
