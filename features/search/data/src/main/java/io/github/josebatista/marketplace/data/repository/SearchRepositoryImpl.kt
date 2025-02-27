package io.github.josebatista.marketplace.data.repository

import io.github.josebatista.marketplace.core.data.network.NetworkClient
import io.github.josebatista.marketplace.core.data.network.NetworkClientResponse
import io.github.josebatista.marketplace.core.data.network.constructUrl
import io.github.josebatista.marketplace.core.data.network.get
import io.github.josebatista.marketplace.core.domain.Resource
import io.github.josebatista.marketplace.core.domain.UiText
import io.github.josebatista.marketplace.core.logging.Logger
import io.github.josebatista.marketplace.data.dto.ItemsSearchResponseDto
import io.github.josebatista.marketplace.data.mapper.toItemsSearchResponse
import io.github.josebatista.marketplace.domain.model.ItemsSearchResponse
import io.github.josebatista.marketplace.domain.repository.SearchRepository
import javax.inject.Inject

/**
 * Implementation of [SearchRepository] that retrieves search results from a network source.
 *
 * This repository uses a [NetworkClient] to perform an HTTP GET request to retrieve product search results
 * for a given query and offset. The URL is constructed using [constructUrl] with the endpoint "/sites/MLB/search".
 * It logs key events using the provided [Logger] and maps the network response, which is a [ItemsSearchResponseDto],
 * to the domain model [ItemsSearchResponse] using the extension function [toItemsSearchResponse].
 *
 * In case the network response indicates an error (i.e. [NetworkClientResponse.NetworkClientError]),
 * it logs the error and returns a [Resource.Error] containing the error message.
 * If the network call is successful (i.e. [NetworkClientResponse.NetworkClientResponseSuccess]),
 * it logs the success and returns a [Resource.Success] with the mapped data.
 *
 * If an exception occurs during the network operation, it catches the exception, logs the error, and
 * returns a [Resource.Error] with a dynamic error message.
 *
 * @property networkClient The network client used to perform HTTP GET requests.
 * @property logger The logger used to log events and errors.
 */
internal class SearchRepositoryImpl @Inject constructor(
    private val networkClient: NetworkClient,
    private val logger: Logger
) : SearchRepository {

    /**
     * Executes a search operation by sending a network request for products.
     *
     * The function constructs the URL using [constructUrl], logs the initiation of the search,
     * and performs an HTTP GET request via [networkClient] with the provided [query] and [offset].
     *
     * Depending on the response:
     * - If the response is an error ([NetworkClientResponse.NetworkClientError]), it logs the error message
     *   and returns a [Resource.Error] with the error details.
     * - If the response is successful ([NetworkClientResponse.NetworkClientResponseSuccess]), it logs the success,
     *   converts the DTO to the domain model ([ItemsSearchResponse]) using [toItemsSearchResponse], and returns
     *   a [Resource.Success] containing the data.
     *
     * In case of any exception during the network operation, the exception is caught, logged, and a
     * [Resource.Error] is returned with a dynamic error message.
     *
     * @param query The search query string.
     * @param offset The offset used for pagination.
     * @return A [Resource] containing either the [ItemsSearchResponse] on success or a [UiText] error.
     */
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
