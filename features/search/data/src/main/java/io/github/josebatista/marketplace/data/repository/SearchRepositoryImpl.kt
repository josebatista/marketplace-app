package io.github.josebatista.marketplace.data.repository

import io.github.josebatista.marketplace.data.dto.ItemsSearchResponseDto
import io.github.josebatista.marketplace.data.mapper.toItemsSearchResponse
import io.github.josebatista.marketplace.data.network.NetworkClient
import io.github.josebatista.marketplace.data.network.NetworkClientResponse
import io.github.josebatista.marketplace.data.network.constructUrl
import io.github.josebatista.marketplace.data.network.get
import io.github.josebatista.marketplace.domain.Resource
import io.github.josebatista.marketplace.domain.UiText
import io.github.josebatista.marketplace.domain.model.ItemsSearchResponse
import io.github.josebatista.marketplace.domain.repository.SearchRepository
import io.github.josebatista.marketplace.logging.Logger
import javax.inject.Inject

internal class SearchRepositoryImpl @Inject constructor(
    private val networkClient: NetworkClient,
    private val logger: Logger
) : SearchRepository {
    override suspend fun invoke(query: String, offset: Int): Resource<ItemsSearchResponse, UiText> =
        runCatching {
            val url = constructUrl("/sites/MLB/search")
            logger.sendLog("Iniciando consulta para: $url")
            val response: NetworkClientResponse<ItemsSearchResponseDto> =
                networkClient.get(
                    url = url,
                    parameters = mapOf("q" to query, "offset" to offset.toString()),
                )
            when (response) {
                is NetworkClientResponse.NetworkClientError -> {
                    logger.sendLog("Erro ao buscar produtos: ${response.message}")
                    Resource.Error(
                        response.message
                    )
                }

                is NetworkClientResponse.NetworkClientResponseSuccess -> {
                    logger.sendLog("Produtos buscados com sucesso")
                    Resource.Success(
                        data = response.data.toItemsSearchResponse()
                    )
                }
            }
        }.getOrElse {
            logger.sendLog("Erro ao buscar produtos: ${it.message}")
            Resource.Error(UiText.DynamicText(it.message.toString()))
        }
}
