package io.github.josebatista.marketplace.search.presentation.list

internal sealed interface ListScreenEvent {
    data class OnScrollPositionChange(val index: Int, val offset: Int) : ListScreenEvent
}
