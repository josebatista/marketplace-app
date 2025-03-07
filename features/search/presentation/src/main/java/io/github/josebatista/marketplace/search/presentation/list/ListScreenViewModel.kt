package io.github.josebatista.marketplace.search.presentation.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.josebatista.marketplace.domain.usecase.SearchUseCase
import io.github.josebatista.marketplace.core.logging.Logger
import io.github.josebatista.marketplace.search.presentation.list.util.ListScrollPosition
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * ViewModel for the List Screen that handles fetching search results and maintaining the scroll position.
 *
 * This ViewModel retrieves the search query from the [SavedStateHandle] and uses it along with the
 * provided [searchUseCase] to generate a flow of paged search results via [searchResults]. The results
 * are fetched using a [Pager] configured with a fixed page size and prefetch distance, and the paging
 * flow is cached in the [viewModelScope].
 *
 * In addition, the ViewModel maintains the current scroll position using a [MutableStateFlow] of [ListScrollPosition],
 * which can be updated by processing [ListScreenEvent] events through the [onEvent] function.
 *
 * Upon initialization, a log is sent indicating the start of the product search for the specified query.
 *
 * @property searchUseCase The use case responsible for performing the search operation.
 * @property logger The logger used to log events and messages.
 * @property savedStateHandle Provides access to the saved state, including the search query.
 */
@HiltViewModel
internal class ListScreenViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
    logger: Logger,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    /**
     * The search query obtained from the [SavedStateHandle] using the key "query".
     */
    private val query: String = savedStateHandle.get<String>("query").orEmpty()

    /**
     * A flow of paged search results generated by invoking [SearchPagingSource] with the [query].
     *
     * The search results are retrieved using a [Pager] configured with a page size of [ITEMS_QTY] and
     * a prefetch distance of [PREFETCH_DISTANCE]. The resulting paging flow is cached in the [viewModelScope].
     */
    val searchResults = Pager(
        config = PagingConfig(pageSize = ITEMS_QTY, prefetchDistance = PREFETCH_DISTANCE),
        pagingSourceFactory = { SearchPagingSource(searchUseCase, query) }
    )
        .flow
        .cachedIn(viewModelScope)

    private val _scrollPosition = MutableStateFlow(ListScrollPosition())

    /**
     * Exposes the current scroll position as a read-only [StateFlow].
     */
    val scrollPosition = _scrollPosition.asStateFlow()

    init {
        logger.sendLog("Iniciando consulta de produtos para a query: $query")
    }

    /**
     * Processes events from the UI related to the list screen.
     *
     * Currently, this function handles scroll position changes by processing
     * [ListScreenEvent.OnScrollPositionChange] events.
     *
     * @param event The event to be processed.
     */
    fun onEvent(event: ListScreenEvent) {
        when (event) {
            is ListScreenEvent.OnScrollPositionChange -> updateScrollPosition(
                index = event.index,
                offset = event.offset
            )
        }
    }

    /**
     * Updates the scroll position state with the provided [index] and [offset].
     *
     * @param index The new index of the first visible item.
     * @param offset The new scroll offset of the first visible item.
     */
    private fun updateScrollPosition(index: Int, offset: Int) {
        _scrollPosition.value = ListScrollPosition(index, offset)
    }

    private companion object {
        /**
         * The number of items per page in the search results.
         */
        const val ITEMS_QTY = 50

        /**
         * The distance in items from the current position at which new pages are prefetched.
         */
        const val PREFETCH_DISTANCE = 50
    }
}
