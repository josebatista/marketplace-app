package io.github.josebatista.marketplace.data.repository

import io.github.josebatista.marketplace.domain.repository.SearchRepository
import javax.inject.Inject

internal class SearchRepositoryImpl @Inject constructor() : SearchRepository {
    override suspend fun invoke(query: String): String {
        TODO("Not yet implemented")
    }
}
