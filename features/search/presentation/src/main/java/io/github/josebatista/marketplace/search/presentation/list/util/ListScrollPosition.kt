package io.github.josebatista.marketplace.search.presentation.list.util

/**
 * Represents the current scroll position of a list.
 *
 * This data class encapsulates the index of the first visible item in a list
 * and its corresponding scroll offset (in pixels). It is used to restore or track
 * the scroll state in UI components such as [LazyColumn].
 *
 * @property index The index of the first visible item. Defaults to 0.
 * @property offset The vertical offset (in pixels) of the first visible item. Defaults to 0.
 */
internal data class ListScrollPosition(
    val index: Int = 0,
    val offset: Int = 0
)
