package io.github.josebatista.marketplace.search.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.josebatista.marketplace.core.domain.UiText
import io.github.josebatista.marketplace.core.logging.Logger
import io.github.josebatista.marketplace.search.presentation.R
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for managing the state and events of the search screen.
 *
 * This ViewModel maintains the UI state using a [MutableStateFlow] of [SearchScreenState] and exposes
 * UI events through a [Channel] of type [SearchUiEvent] as a flow. It processes events received from the UI,
 * represented by [SearchScreenEvent], to either update the search query or initiate a search operation.
 *
 * When a search is initiated via [SearchScreenEvent.OnSearch], the ViewModel checks if the query length meets
 * the minimum requirement defined by [MIN_QUERY_LENGTH]:
 * - If the query is too short, it logs an error message and emits a [SearchUiEvent.ShowError] event.
 * - If the query is valid, it logs a success message and emits a [SearchUiEvent.Search] event.
 *
 * The search query is updated in the state when a [SearchScreenEvent.OnQueryChange] event is received.
 *
 * @property logger Logger instance used to log search-related events.
 */
@HiltViewModel
internal class SearchScreenViewModel @Inject constructor(
    private val logger: Logger
) : ViewModel() {

    private val _state = MutableStateFlow(SearchScreenState())

    /**
     * Exposes the current search screen state as a read-only state flow.
     */
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<SearchUiEvent>()

    /**
     * Exposes the UI events as a flow for the UI layer to observe.
     */

    val uiEvent = _uiEvent.receiveAsFlow()

    /**
     * Processes the given [event] from the search screen.
     *
     * Depending on the event type:
     * - [SearchScreenEvent.OnQueryChange]: Updates the search query in the state.
     * - [SearchScreenEvent.OnSearch]: Initiates the search operation.
     *
     * @param event The [SearchScreenEvent] to process.
     */
    fun onEvent(event: SearchScreenEvent) {
        when (event) {
            is SearchScreenEvent.OnSearch -> search(event.query)
            is SearchScreenEvent.OnQueryChange -> onQueryChange(event.query)
        }
    }

    /**
     * Executes a search operation based on the provided [query].
     *
     * If the query length is less than [MIN_QUERY_LENGTH], it logs a message indicating that the query is too short
     * and emits a [SearchUiEvent.ShowError] event with a resource message specifying the minimum length requirement.
     * Otherwise, it logs that the query is valid and emits a [SearchUiEvent.Search] event.
     *
     * This operation is launched in the [viewModelScope].
     *
     * @param query The search query string.
     */
    private fun search(query: String) {
        viewModelScope.launch {
            if (query.length < MIN_QUERY_LENGTH) {
                logger.sendLog("Search query [$query] is too short")
                _uiEvent.send(
                    SearchUiEvent.ShowError(
                        UiText.StringResource(
                            R.string.features_search_presentation_min_length_search,
                            MIN_QUERY_LENGTH
                        )
                    )
                )
            } else {
                logger.sendLog("Search query [$query] is valid")
                _uiEvent.send(SearchUiEvent.Search(query))
            }
        }
    }

    /**
     * Updates the search query in the current state.
     *
     * This method updates the [SearchScreenState] with the new query value.
     *
     * @param query The new search query.
     */
    private fun onQueryChange(query: String) {
        _state.update { it.copy(query = query) }
    }

    private companion object {
        /**
         * The minimum length required for a search query.
         */
        const val MIN_QUERY_LENGTH = 3
    }
}
