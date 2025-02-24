package io.github.josebatista.marketplace.search.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.josebatista.marketplace.domain.usecase.SearchUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
internal class ListScreenViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
) : ViewModel() {
    private val _query = MutableStateFlow("")
    fun setQuery(query: String) {
        _query.value = query
    }

    val searchResults = _query
        .filter { it.isNotEmpty() }
        .flatMapLatest { query ->
            Pager(
                config = PagingConfig(pageSize = ITEMS_QTY, prefetchDistance = PREFETCH_DISTANCE),
                pagingSourceFactory = { SearchPagingSource(searchUseCase, query) }
            ).flow
        }
        .cachedIn(viewModelScope)

    private companion object {
        const val ITEMS_QTY = 50
        const val PREFETCH_DISTANCE = 30
    }
}
