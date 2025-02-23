package io.github.josebatista.marketplace.search.presentation

internal sealed interface SearchScreenEvent {
    data class OnQueryChange(val query: String) : SearchScreenEvent
    data class OnSearch(val query: String) : SearchScreenEvent
}
