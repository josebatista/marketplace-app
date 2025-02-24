package io.github.josebatista.marketplace.search.presentation.list

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.github.josebatista.marketplace.domain.Resource
import io.github.josebatista.marketplace.domain.model.ItemsSearchResponse
import io.github.josebatista.marketplace.domain.model.Result
import io.github.josebatista.marketplace.domain.usecase.SearchUseCase

internal class SearchPagingSource(
    private val searchUseCase: SearchUseCase,
    private val query: String
) : PagingSource<Int, Result>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val offset = params.key ?: INITIAL_OFFSET
        return runCatching {
            val response: ItemsSearchResponse = when (
                val result = searchUseCase(
                    query = query,
                    offset = offset
                )
            ) {
                is Resource.Error -> return LoadResult.Invalid()
                is Resource.Success -> result.data
            }
            val items = response.results
            val nextKey =
                if (offset + params.loadSize >= (response.paging?.total ?: INITIAL_OFFSET)) {
                    null
                } else {
                    offset + 1
                }
            items?.let {
                LoadResult.Page(
                    data = it,
                    prevKey = if (offset == INITIAL_OFFSET) null else offset - 1,
                    nextKey = nextKey
                )
            } ?: LoadResult.Invalid()
        }.getOrElse {
            LoadResult.Error(it)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }

    private companion object {
        const val INITIAL_OFFSET = 0
    }
}
