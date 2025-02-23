package io.github.josebatista.marketplace.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.josebatista.marketplace.domain.UiText
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SearchScreenViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(SearchScreenState())
    val state = _state.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onEvent(event: SearchScreenEvent) {
        when (event) {
            is SearchScreenEvent.OnSearch -> search(event.query)
            is SearchScreenEvent.OnQueryChange -> onQueryChange(event.query)
        }
    }

    private fun search(query: String) {
        viewModelScope.launch {
            if (query.length < MIN_QUERY_LENGTH) {
                _uiEvent.emit(
                    UiEvent.ShowError(
                        UiText.StringResource(
                            R.string.features_search_presentation_min_length_search,
                            MIN_QUERY_LENGTH
                        )
                    )
                )
            } else {
                _uiEvent.emit(UiEvent.Search(query))
            }
        }
    }

    private fun onQueryChange(query: String) {
        _state.update { it.copy(query = query) }
    }

    private companion object {
        const val MIN_QUERY_LENGTH = 3
    }
}
