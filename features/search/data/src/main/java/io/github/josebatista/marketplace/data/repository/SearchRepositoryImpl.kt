package io.github.josebatista.marketplace.data.repository

import io.github.josebatista.marketplace.data.dto.ItemsSearchResponseDto
import io.github.josebatista.marketplace.data.mapper.toItemsSearchResponse
import io.github.josebatista.marketplace.domain.Resource
import io.github.josebatista.marketplace.domain.UiText
import io.github.josebatista.marketplace.domain.model.ItemsSearchResponse
import io.github.josebatista.marketplace.domain.repository.SearchRepository
import io.github.josebatista.marketplace.network.NetworkClient
import io.github.josebatista.marketplace.network.NetworkClientResponse
import io.github.josebatista.marketplace.network.get
import javax.inject.Inject

internal class SearchRepositoryImpl @Inject constructor(
    private val networkClient: NetworkClient
) : SearchRepository {
    override suspend fun invoke(query: String, offset: Int): Resource<ItemsSearchResponse, UiText> =
        runCatching {
            val response: NetworkClientResponse<ItemsSearchResponseDto> = networkClient.get(
                url = "https://api.mercadolibre.com/sites/MLB/search",
                parameters = mapOf("q" to query, "offset" to offset.toString()),
            )
            return when (response) {
                is NetworkClientResponse.NetworkClientError -> Resource.Error(response.message)
                is NetworkClientResponse.NetworkClientResponseSuccess -> Resource.Success(
                    data = response.data.toItemsSearchResponse()
                )
            }
        }.getOrElse {
            Resource.Error(UiText.DynamicText(it.message.toString()))
        }
}
