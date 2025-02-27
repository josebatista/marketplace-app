package io.github.josebatista.marketplace.domain.usecase

import io.github.josebatista.marketplace.core.domain.Resource
import io.github.josebatista.marketplace.core.domain.UiText
import io.github.josebatista.marketplace.domain.model.ItemsSearchResponse

/**
 * Defines the contract for performing a search operation.
 *
 * This use case encapsulates the logic for searching based on a given query and offset.
 * The result is represented as a [Resource] containing either an [ItemsSearchResponse] on success,
 * or a [UiText] error on failure.
 *
 * Implementations of this interface are typically responsible for delegating the search operation
 * to a repository or other data source.
 */
public interface SearchUseCase {

    /**
     * Executes a search based on the provided query and offset.
     *
     * This is a suspend operator function, allowing instances of [SearchUseCase] to be invoked directly.
     *
     * @param query The search query string.
     * @param offset The offset to be used for pagination.
     * @return A [Resource] wrapping an [ItemsSearchResponse] on success or a [UiText] error on failure.
     */
    public suspend operator fun invoke(
        query: String,
        offset: Int,
    ): Resource<ItemsSearchResponse, UiText>
}
