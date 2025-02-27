package io.github.josebatista.marketplace.data.repository

import android.content.Context
import com.google.common.truth.Truth.assertThat
import io.github.josebatista.marketplace.data.dto.ItemsSearchResponseDto
import io.github.josebatista.marketplace.data.mapper.toItemsSearchResponse
import io.github.josebatista.marketplace.core.data.network.NetworkClient
import io.github.josebatista.marketplace.core.data.network.NetworkClientResponse
import io.github.josebatista.marketplace.core.domain.Resource
import io.github.josebatista.marketplace.core.domain.UiText
import io.github.josebatista.marketplace.domain.model.ItemsSearchResponse
import io.github.josebatista.marketplace.core.logging.Logger
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Test

internal class SearchRepositoryImplTest {

    private val context: Context = mockk(relaxed = true)
    private val networkClient: NetworkClient = mockk(relaxed = true)
    private val logger: Logger = mockk(relaxed = true)
    private val repository = SearchRepositoryImpl(networkClient, logger)
    private val query = "testQuery"
    private val offset = 10

    @Test
    fun `invoke returns Resource Error when networkClient returns NetworkClientError`() = runTest {
        val errorMessage = "Network error"

        coEvery {
            networkClient.get<NetworkClientResponse<ItemsSearchResponseDto>>(
                any(),
                any(),
                any(),
                any()
            )
        } answers {
            NetworkClientResponse.NetworkClientError(500, UiText.DynamicText(errorMessage))
        }

        val result = repository.invoke(query, offset)

        assertThat(result).isInstanceOf(Resource.Error::class.java)
        val errorResult = result as Resource.Error
        assertThat(errorResult.message.asString(context)).isEqualTo(errorMessage)

        verify { logger.sendLog(match { it.contains("Iniciando consulta para:") }) }
        verify { logger.sendLog("Erro ao buscar produtos: ${result.message}") }
    }

    @Test
    fun `invoke returns Resource Success when networkClient returns NetworkClientResponseSuccess`() =
        runTest {
            val dummyDto: ItemsSearchResponseDto = mockk(relaxed = true, relaxUnitFun = true)
            val dummyResponse: ItemsSearchResponse = mockk(relaxed = true, relaxUnitFun = true)
            mockkStatic("io.github.josebatista.marketplace.data.mapper.ItemsSearchResponseMapperKt")
            every {
                dummyDto.toItemsSearchResponse()
            } answers {
                dummyResponse
            }

            coEvery {
                networkClient.get<ItemsSearchResponseDto>(
                    any(),
                    any(),
                    any(),
                    any()
                )
            } answers {
                NetworkClientResponse.NetworkClientResponseSuccess(200, dummyDto)
            }

            val result = repository.invoke(query, offset)

            assertThat(result).isInstanceOf(Resource.Success::class.java)
            val successResult = result as Resource.Success
            assertThat(successResult.data).isEqualTo(dummyResponse)

            verify { logger.sendLog(match { it.contains("Iniciando consulta para:") }) }
            verify { logger.sendLog("Produtos buscados com sucesso") }
        }

    @Test
    fun `invoke returns Resource Error when exception is thrown`() = runTest {
        val exceptionMessage = "Unexpected exception"
        coEvery {
            networkClient.get<ItemsSearchResponseDto>(
                any(),
                any(),
                any(),
                any()
            )
        } throws Exception(exceptionMessage)

        val result = repository.invoke(query, offset)

        assertThat(result).isInstanceOf(Resource.Error::class.java)
        val errorResult = result as Resource.Error
        assertThat(errorResult.message).isInstanceOf(UiText.DynamicText::class.java)
        val dynamicText = errorResult.message as UiText.DynamicText
        assertThat(dynamicText.asString(context)).isEqualTo(exceptionMessage)

        verify { logger.sendLog(match { it.contains("Iniciando consulta para:") }) }
        verify { logger.sendLog("Erro ao buscar produtos: $exceptionMessage") }
    }
}
