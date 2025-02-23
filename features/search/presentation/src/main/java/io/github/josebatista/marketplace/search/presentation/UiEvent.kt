package io.github.josebatista.marketplace.search.presentation

import io.github.josebatista.marketplace.domain.UiText

internal sealed interface UiEvent {
    data class Search(val query: String) : UiEvent
    data class ShowError(val message: UiText) : UiEvent
}
