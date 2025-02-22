package io.github.josebatista.marketplace.data.repository

import io.github.josebatista.marketplace.data.dto.ItemsSearchResponseDto
import io.github.josebatista.marketplace.data.mapper.toItemsSearchResponse
import io.github.josebatista.marketplace.domain.Resource
import io.github.josebatista.marketplace.domain.UiText
import io.github.josebatista.marketplace.domain.model.ItemsSearchResponse
import io.github.josebatista.marketplace.domain.repository.SearchRepository
import io.github.josebatista.marketplace.network.NetworkClient
import io.github.josebatista.marketplace.network.get
import javax.inject.Inject

internal class SearchRepositoryImpl @Inject constructor(
    private val networkClient: NetworkClient
) : SearchRepository {
    override suspend fun invoke(query: String): Resource<ItemsSearchResponse, UiText> {
        val response: ItemsSearchResponseDto = networkClient.get(
            url = "https://api.mercadolibre.com/sites/MLB/search",
            parameters = mapOf("q" to "Motorola", "offset" to "0"),
        )
        return Resource.Success(response.toItemsSearchResponse())
    }
}
