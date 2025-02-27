package io.github.josebatista.marketplace.search.presentation.search

import io.github.josebatista.marketplace.core.domain.UiText

/**
 * Represents the UI events emitted by the SearchScreenViewModel.
 *
 * These events are used by the UI layer to react to actions performed in the search screen.
 * For example, initiating a search operation or displaying an error message.
 */
internal sealed interface SearchUiEvent {

    /**
     * Event indicating that a search should be performed.
     *
     * @property query the search query string to be executed.
     */
    data class Search(val query: String) : SearchUiEvent

    /**
     * Event indicating that an error occurred and an error message should be displayed.
     *
     * @property message the error message encapsulated as a [UiText] instance.
     */
    data class ShowError(val message: UiText) : SearchUiEvent
}
