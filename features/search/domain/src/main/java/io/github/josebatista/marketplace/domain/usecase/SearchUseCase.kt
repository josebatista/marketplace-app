package io.github.josebatista.marketplace.domain.usecase

import io.github.josebatista.marketplace.domain.Resource
import io.github.josebatista.marketplace.domain.UiText
import io.github.josebatista.marketplace.domain.model.ItemsSearchResponse

public interface SearchUseCase {
    public suspend operator fun invoke(query: String): Resource<ItemsSearchResponse, UiText>
}
