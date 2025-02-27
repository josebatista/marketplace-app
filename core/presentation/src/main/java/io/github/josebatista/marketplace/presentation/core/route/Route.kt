package io.github.josebatista.marketplace.presentation.core.route

import kotlinx.serialization.Serializable

/**
 * Represents the navigation routes within the application.
 *
 * This sealed interface defines the different routes available in the app. Each route is modeled as a distinct type,
 * enabling type-safe navigation throughout the application.
 */
public sealed interface Route {

    /**
     * The route for the search screen.
     *
     * This route displays the initial search interface where users can enter a query.
     */
    @Serializable
    public data object SearchRoute : Route

    /**
     * The route for the list screen.
     *
     * This route displays a list of search results based on the provided query.
     *
     * @property query The search query string used to display the corresponding list of results.
     */
    @Serializable
    public data class ListScreen(val query: String) : Route
}
