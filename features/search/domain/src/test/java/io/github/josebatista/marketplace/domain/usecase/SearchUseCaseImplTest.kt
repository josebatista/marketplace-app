package io.github.josebatista.marketplace.domain.usecase

import com.google.common.truth.Truth.assertThat
import io.github.josebatista.marketplace.core.domain.Resource
import io.github.josebatista.marketplace.domain.model.ItemsSearchResponse
import io.github.josebatista.marketplace.domain.repository.SearchRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

internal class SearchUseCaseImplTest {

    @RelaxedMockK
    private lateinit var searchRepository: SearchRepository
    private lateinit var searchUseCase: SearchUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        searchUseCase = SearchUseCaseImpl(searchRepository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `invoke should return Success with correct data`() = runTest {
        val result = Resource.Success(data = ItemsSearchResponse(siteId = "MLB"))

        coEvery { searchRepository.invoke(any(), any()) } answers { result }

        val response = searchUseCase.invoke("query", 0)

        coEvery {
            searchRepository.invoke(any(), any())
        }
        assertThat(response).isInstanceOf(Resource.Success::class.java)
        assertThat((response as Resource.Success).data).isEqualTo(result.data)
    }
}
