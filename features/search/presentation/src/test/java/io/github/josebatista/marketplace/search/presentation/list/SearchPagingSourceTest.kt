package io.github.josebatista.marketplace.search.presentation.list

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.common.truth.Truth.assertThat
import io.github.josebatista.marketplace.core.domain.Resource
import io.github.josebatista.marketplace.core.domain.UiText
import io.github.josebatista.marketplace.domain.model.ItemsSearchResponse
import io.github.josebatista.marketplace.domain.model.Paging
import io.github.josebatista.marketplace.domain.model.Result
import io.github.josebatista.marketplace.domain.usecase.SearchUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Test

internal class SearchPagingSourceTest {

    private val searchUseCase: SearchUseCase = mockk(relaxed = true)
    private val query = "testQuery"

    private val searchPagingSource = SearchPagingSource(searchUseCase, query)

    private fun <K : Any> createLoadParams(key: K?, loadSize: Int): PagingSource.LoadParams<K> {
        return PagingSource.LoadParams.Refresh(
            key = key,
            loadSize = loadSize,
            placeholdersEnabled = false
        )
    }

    @Test
    fun `load returns LoadResult Error when searchUseCase returns Resource Error`() = runTest {
        coEvery {
            searchUseCase(
                query = query,
                offset = 0
            )
        } returns Resource.Error(UiText.DynamicText("dummy error"))

        val params = createLoadParams<Int>(key = null, loadSize = 20)
        val result = searchPagingSource.load(params)

        assertThat(result).isInstanceOf(PagingSource.LoadResult.Error::class.java)
        val errorResult = result as PagingSource.LoadResult.Error
        assertThat(errorResult.throwable.message).isEqualTo("Error!")
    }

    @Test
    fun `load returns LoadResult Page when Resource Success with valid results and non-null nextKey`() =
        runTest {
            val dummyResult = mockk<Result>()
            val dummyResponse = mockk<ItemsSearchResponse>()
            every { dummyResponse.results } returns listOf(dummyResult)
            every { dummyResponse.paging } returns Paging(total = 100)
            coEvery { searchUseCase(query = query, offset = 0) } returns Resource.Success(
                dummyResponse
            )

            val params = createLoadParams<Int>(key = null, loadSize = 20)
            val result = searchPagingSource.load(params)

            assertTrue(result is PagingSource.LoadResult.Page)
            val page = result as PagingSource.LoadResult.Page
            assertThat(page.data).isEqualTo(listOf(dummyResult))
            assertThat(page.prevKey).isEqualTo(null)
            assertThat(page.nextKey).isEqualTo(1)
        }

    @Test
    fun `load returns LoadResult Page with null nextKey when offset + loadSize exceeds total`() =
        runTest {
            val dummyResult = mockk<Result>()
            val dummyResponse = mockk<ItemsSearchResponse>()
            every { dummyResponse.results } returns listOf(dummyResult)
            every { dummyResponse.paging } returns Paging(total = 20)
            coEvery { searchUseCase(query = query, offset = 5) } returns Resource.Success(
                dummyResponse
            )

            val params = createLoadParams(key = 5, loadSize = 20)
            val result = searchPagingSource.load(params)

            assertTrue(result is PagingSource.LoadResult.Page)
            val page = result as PagingSource.LoadResult.Page
            assertThat(page.data).isEqualTo(listOf(dummyResult))
            assertThat(page.prevKey).isEqualTo(4)
            assertThat(page.nextKey).isEqualTo(null)
        }

    @Test
    fun `load returns LoadResult Error when results are null`() = runTest {
        val dummyResponse = mockk<ItemsSearchResponse>()
        every { dummyResponse.results } returns null
        every { dummyResponse.paging } returns Paging(total = 100)
        coEvery { searchUseCase(query = query, offset = 0) } returns Resource.Success(dummyResponse)

        val params = createLoadParams<Int>(key = null, loadSize = 20)
        val result = searchPagingSource.load(params)

        assertThat(result).isInstanceOf(PagingSource.LoadResult.Error::class.java)
        val errorResult = result as PagingSource.LoadResult.Error
        assertThat(errorResult.throwable.message).isEqualTo("Error!!")
    }

    @Test
    fun `load returns LoadResult Error when an exception is thrown during load`() = runTest {
        coEvery {
            searchUseCase(
                query = query,
                offset = 0
            )
        } throws Exception("Unexpected exception")

        // Act
        val params = createLoadParams<Int>(key = null, loadSize = 20)
        val result = searchPagingSource.load(params)

        assertThat(result).isInstanceOf(PagingSource.LoadResult.Error::class.java)
        val errorResult = result as PagingSource.LoadResult.Error
        assertThat(errorResult.throwable.message).isEqualTo("Unexpected exception")
    }

    @Test
    fun `getRefreshKey returns null when anchorPosition is null`() {
        val pagingState = PagingState<Int, Result>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 20, prefetchDistance = 10),
            leadingPlaceholderCount = 0
        )

        val refreshKey = searchPagingSource.getRefreshKey(pagingState)

        assertThat(refreshKey).isNull()
    }

    @Test
    fun `getRefreshKey returns prevKey + 1 when closestPageToPosition has non-null prevKey`() {
        val dummyResult = mockk<Result>()
        val page = PagingSource.LoadResult.Page(
            data = listOf(dummyResult),
            prevKey = 2,
            nextKey = 5
        )
        val pagingState = mockk<PagingState<Int, Result>>()
        every { pagingState.anchorPosition } returns 10
        every { pagingState.closestPageToPosition(10) } returns page

        val refreshKey = searchPagingSource.getRefreshKey(pagingState)

        assertThat(refreshKey).isEqualTo(3)
    }

    @Test
    fun `getRefreshKey returns nextKey - 1 when closestPageToPosition has null prevKey but non-null nextKey`() {
        val dummyResult = mockk<Result>()
        val page = PagingSource.LoadResult.Page(
            data = listOf(dummyResult),
            prevKey = null,
            nextKey = 7
        )
        val pagingState = mockk<PagingState<Int, Result>>()
        every { pagingState.anchorPosition } returns 15
        every { pagingState.closestPageToPosition(15) } returns page

        val refreshKey = searchPagingSource.getRefreshKey(pagingState)

        assertThat(refreshKey).isEqualTo(6)
    }

    @Test
    fun `getRefreshKey returns null when closestPageToPosition returns null`() {
        val pagingState = mockk<PagingState<Int, Result>>()
        every { pagingState.anchorPosition } returns 10
        every { pagingState.closestPageToPosition(10) } returns null

        val refreshKey = searchPagingSource.getRefreshKey(pagingState)

        assertThat(refreshKey).isNull()
    }
}
