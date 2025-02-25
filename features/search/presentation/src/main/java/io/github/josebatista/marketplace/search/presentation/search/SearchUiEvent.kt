package io.github.josebatista.marketplace.search.presentation.search

import io.github.josebatista.marketplace.domain.UiText

internal sealed interface SearchUiEvent {
    data class Search(val query: String) : SearchUiEvent
    data class ShowError(val message: UiText) : SearchUiEvent
}
