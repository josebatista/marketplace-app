package io.github.josebatista.marketplace.search.presentation.list

/**
 * Represents events that can occur on the List Screen.
 *
 * These events are handled by the [ListScreenViewModel] to update UI state, such as the current scroll position.
 */

internal sealed interface ListScreenEvent {
    /**
     * Event triggered when the scroll position of the list changes.
     *
     * @property index the new index of the first visible item.
     * @property offset the new scroll offset (in pixels) of the first visible item.
     */
    data class OnScrollPositionChange(val index: Int, val offset: Int) : ListScreenEvent
}
