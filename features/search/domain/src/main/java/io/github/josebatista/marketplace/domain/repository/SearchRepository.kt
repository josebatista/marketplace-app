package io.github.josebatista.marketplace.domain.repository

import io.github.josebatista.marketplace.domain.Resource
import io.github.josebatista.marketplace.domain.UiText
import io.github.josebatista.marketplace.domain.model.ItemsSearchResponse

public interface SearchRepository {
    public suspend operator fun invoke(query: String): Resource<ItemsSearchResponse, UiText>
}
