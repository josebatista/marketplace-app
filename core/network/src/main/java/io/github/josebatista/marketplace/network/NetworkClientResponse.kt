package io.github.josebatista.marketplace.network

import io.github.josebatista.marketplace.domain.UiText

public sealed interface NetworkClientResponse<T> {
    public data class NetworkClientResponseSuccess<T>(
        public val code: Int, public val data: T
    ) : NetworkClientResponse<T>

    public data class NetworkClientError<T>(
        public val code: Int, public val message: UiText
    ) : NetworkClientResponse<T>
}
