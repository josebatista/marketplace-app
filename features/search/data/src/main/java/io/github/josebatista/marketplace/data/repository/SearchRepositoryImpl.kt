package io.github.josebatista.marketplace.data.repository

import io.github.josebatista.marketplace.data.model.ItemsSearchResponse
import io.github.josebatista.marketplace.domain.repository.SearchRepository
import io.github.josebatista.marketplace.network.NetworkClient
import io.github.josebatista.marketplace.network.get
import javax.inject.Inject

internal class SearchRepositoryImpl @Inject constructor(
    private val networkClient: NetworkClient
) : SearchRepository {
    override suspend fun invoke(query: String): String {
        val response: ItemsSearchResponse = networkClient.get(
            url = "https://api.mercadolibre.com/sites/MLB/search",
            parameters = mapOf("q" to "Motorola", "offset" to "0"),
        )
        return response.toString()
    }
}
