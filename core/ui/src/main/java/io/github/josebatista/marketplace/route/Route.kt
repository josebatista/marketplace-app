package io.github.josebatista.marketplace.route

import kotlinx.serialization.Serializable

public sealed interface Route {
    @Serializable
    public data object SearchRoute : Route
}
