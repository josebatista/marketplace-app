package io.github.josebatista.marketplace.search.presentation.search

/**
 * Represents the state of the search screen.
 *
 * This data class holds the current search query entered by the user. It is used by the
 * [SearchScreenViewModel] to maintain and update the UI state.
 *
 * @property query the current search query. Defaults to an empty string.
 */
internal data class SearchScreenState(
    val query: String = "",
)
