package io.github.josebatista.marketplace.search.presentation.search

/**
 * Represents the different events that can occur on the search screen.
 *
 * These events are used by the [SearchScreenViewModel] to handle user interactions such as
 * updating the search query and initiating a search.
 */

internal sealed interface SearchScreenEvent {
    /**
     * Event indicating that the search query has been updated.
     *
     * @property query the new search query entered by the user.
     */
    data class OnQueryChange(val query: String) : SearchScreenEvent

    /**
     * Event indicating that a search has been triggered.
     *
     * @property query the search query to be executed.
     */
    data class OnSearch(val query: String) : SearchScreenEvent
}
