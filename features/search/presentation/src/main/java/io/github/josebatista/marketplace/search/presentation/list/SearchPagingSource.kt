package io.github.josebatista.marketplace.search.presentation.list

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.github.josebatista.marketplace.core.domain.Resource
import io.github.josebatista.marketplace.domain.model.ItemsSearchResponse
import io.github.josebatista.marketplace.domain.model.Result
import io.github.josebatista.marketplace.domain.usecase.SearchUseCase

/**
 * A PagingSource that loads search results based on a query.
 *
 * This PagingSource fetches search results by invoking the provided [searchUseCase] with the
 * specified [query] and an offset. It processes the response to generate pages of data,
 * calculates the previous and next keys for pagination, and handles error scenarios.
 *
 * @property searchUseCase The use case responsible for performing the search operation.
 * @property query The search query string used to fetch results.
 */
internal class SearchPagingSource(
    private val searchUseCase: SearchUseCase,
    private val query: String
) : PagingSource<Int, Result>() {

    /**
     * Loads a page of search results based on the given [params].
     *
     * The [params.key] represents the offset for the search; if it is null, [INITIAL_OFFSET] is used.
     * The method calls [searchUseCase] with the current [query] and calculated offset.
     *
     * If [searchUseCase] returns a [Resource.Error], a [LoadResult.Error] is immediately returned.
     * Otherwise, for a successful response, the following occurs:
     * - The response's [results] are extracted.
     * - The [nextKey] is calculated: if the sum of the current offset and [params.loadSize]
     *   is greater than or equal to the total number of items (from [response.paging]), [nextKey]
     *   is set to null; otherwise, it is set to offset + 1.
     * - If the items are non-null, a [LoadResult.Page] is returned with the data, previous key,
     *   and next key. If items are null, a [LoadResult.Error] is returned.
     *
     * If any exception is thrown during the process, it is caught and wrapped in a [LoadResult.Error].
     *
     * @param params The parameters required to load a page, including the key and load size.
     * @return A [LoadResult] containing a page of search results or an error.
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val offset = params.key ?: INITIAL_OFFSET
        return runCatching {
            val response: ItemsSearchResponse = when (
                val result = searchUseCase(
                    query = query,
                    offset = offset
                )
            ) {
                is Resource.Error -> return LoadResult.Error(Exception("Error!"))
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
            } ?: LoadResult.Error(Exception("Error!!"))
        }.getOrElse {
            LoadResult.Error(it)
        }
    }

    /**
     * Returns the key for the initial load when refreshing the data.
     *
     * The refresh key is determined based on the anchor position in the current [PagingState].
     * If the closest page to the anchor has a previous key, its value plus one is returned;
     * otherwise, if it has a next key, its value minus one is returned. If no such page exists,
     * null is returned.
     *
     * @param state The current [PagingState] containing information about loaded pages.
     * @return The key to use for refreshing, or null if it cannot be determined.
     */
    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }

    private companion object {
        /**
         * The initial offset value for loading search results.
         */
        const val INITIAL_OFFSET = 0
    }
}
