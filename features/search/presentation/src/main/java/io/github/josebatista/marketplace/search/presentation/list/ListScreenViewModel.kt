package io.github.josebatista.marketplace.search.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.josebatista.marketplace.domain.usecase.SearchUseCase
import javax.inject.Inject

@HiltViewModel
internal class ListScreenViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
) : ViewModel() {

    fun search(query: String) = Pager(
        config = PagingConfig(pageSize = ITEMS_QTY, enablePlaceholders = false),
        pagingSourceFactory = { SearchPagingSource(searchUseCase, query) }
    ).flow.cachedIn(viewModelScope)

    private companion object {
        const val ITEMS_QTY = 50
    }
}
