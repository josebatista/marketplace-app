package io.github.josebatista.marketplace.presentation.route

import kotlinx.serialization.Serializable

public sealed interface Route {
    @Serializable
    public data object SearchRoute : Route

    @Serializable
    public data class ListScreen(val query: String) : Route
}
