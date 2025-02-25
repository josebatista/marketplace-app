package io.github.josebatista.marketplace.search.presentation.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.josebatista.marketplace.domain.usecase.SearchUseCase
import io.github.josebatista.marketplace.search.presentation.list.util.ListScrollPosition
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
internal class ListScreenViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val query: String = savedStateHandle.get<String>("query").orEmpty()

    val searchResults = Pager(
        config = PagingConfig(pageSize = ITEMS_QTY, prefetchDistance = PREFETCH_DISTANCE),
        pagingSourceFactory = { SearchPagingSource(searchUseCase, query) }
    )
        .flow
        .cachedIn(viewModelScope)

    private val _scrollPosition = MutableStateFlow(ListScrollPosition())
    val scrollPosition = _scrollPosition.asStateFlow()

    fun onEvent(event: ListScreenEvent) {
        when (event) {
            is ListScreenEvent.OnScrollPositionChange -> updateScrollPosition(
                index = event.index,
                offset = event.offset
            )
        }
    }

    private fun updateScrollPosition(index: Int, offset: Int) {
        _scrollPosition.value = ListScrollPosition(index, offset)
    }

    private companion object {
        const val ITEMS_QTY = 50
        const val PREFETCH_DISTANCE = 50
    }
}
