package io.github.josebatista.marketplace.core.data.network

import io.github.josebatista.marketplace.core.domain.UiText

/**
 * Represents the response from a network client.
 *
 * This sealed interface encapsulates two possible outcomes of a network request:
 * a successful response or an error response.
 *
 * @param T The type of the data returned on a successful response.
 */
public sealed interface NetworkClientResponse<T> {

    /**
     * Represents a successful network response.
     *
     * @param T The type of the data returned.
     * @property code The HTTP status code of the response.
     * @property data The data returned by the network request.
     */
    public data class NetworkClientResponseSuccess<T>(
        public val code: Int,
        public val data: T
    ) : NetworkClientResponse<T>

    /**
     * Represents an error that occurred during a network request.
     *
     * @param T The type parameter, which can be used for consistency even though no data is returned.
     * @property code The HTTP status code associated with the error.
     * @property message The error message encapsulated as a [UiText], which can be converted to a [String].
     */
    public data class NetworkClientError<T>(
        public val code: Int,
        public val message: UiText
    ) : NetworkClientResponse<T>
}
