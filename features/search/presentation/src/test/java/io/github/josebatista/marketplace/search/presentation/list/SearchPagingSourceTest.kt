package io.github.josebatista.marketplace.search.presentation.list

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.common.truth.Truth.assertThat
import io.github.josebatista.marketplace.domain.Resource
import io.github.josebatista.marketplace.domain.UiText
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
    fun `load retorna LoadResult Error quando searchUseCase retorna Resource Error`() = runTest {
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
    fun `load retorna LoadResult Page quando Resource Success com resultados válidos e nextKey não nulo`() =
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
    fun `load retorna LoadResult Page com nextKey nulo quando offset + loadSize ultrapassa total`() =
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
    fun `load retorna LoadResult Error quando results é nulo`() = runTest {
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
    fun `load retorna LoadResult Error quando exceção é lançada durante o load`() = runTest {
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
    fun `getRefreshKey retorna null quando anchorPosition é nulo`() {
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
    fun `getRefreshKey retorna prevKey + 1 quando closestPageToPosition tem prevKey não nulo`() {
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
    fun `getRefreshKey retorna nextKey - 1 quando closestPageToPosition tem prevKey nulo mas nextKey não nulo`() {
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
    fun `getRefreshKey retorna null quando closestPageToPosition retorna null`() {
        val pagingState = mockk<PagingState<Int, Result>>()
        every { pagingState.anchorPosition } returns 10
        every { pagingState.closestPageToPosition(10) } returns null

        val refreshKey = searchPagingSource.getRefreshKey(pagingState)

        assertThat(refreshKey).isNull()
    }
}
