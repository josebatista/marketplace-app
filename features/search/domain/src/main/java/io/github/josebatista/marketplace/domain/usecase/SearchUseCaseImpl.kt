package io.github.josebatista.marketplace.domain.usecase

import io.github.josebatista.marketplace.domain.Resource
import io.github.josebatista.marketplace.domain.UiText
import io.github.josebatista.marketplace.domain.model.ItemsSearchResponse
import io.github.josebatista.marketplace.domain.repository.SearchRepository
import javax.inject.Inject

internal class SearchUseCaseImpl @Inject constructor(
    private val repository: SearchRepository
) : SearchUseCase {
    override suspend fun invoke(query: String, offset: Int): Resource<ItemsSearchResponse, UiText> =
        repository.invoke(query = query, offset = offset)
}
