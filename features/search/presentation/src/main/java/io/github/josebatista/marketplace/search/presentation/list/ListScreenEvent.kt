package io.github.josebatista.marketplace.search.presentation.list

internal sealed interface ListScreenEvent {
    data class OnQueryChange(val query: String) : ListScreenEvent
    data class OnScrollPositionChange(val index: Int, val offset: Int) : ListScreenEvent
}
