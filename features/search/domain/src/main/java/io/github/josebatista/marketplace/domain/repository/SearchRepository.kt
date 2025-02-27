package io.github.josebatista.marketplace.domain.repository

import io.github.josebatista.marketplace.core.domain.Resource
import io.github.josebatista.marketplace.core.domain.UiText
import io.github.josebatista.marketplace.domain.model.ItemsSearchResponse

/**
 * Defines the contract for a search repository.
 *
 * This repository interface provides a mechanism to perform search operations based on a given query and offset.
 * Implementations of this interface are responsible for retrieving search results
 * from a data source (e.g., network or database) and returning them wrapped
 * in a [Resource] which encapsulates either a successful [ItemsSearchResponse] or a [UiText] error.
 *
 */
public interface SearchRepository {

    /**
     * Executes a search operation based on the provided [query] and [offset].
     *
     * This suspend operator function allows the repository to be invoked directly as a function.
     *
     * @param query The search query string.
     * @param offset The offset used for pagination of search results.
     * @return A [Resource] containing an [ItemsSearchResponse] on success, or a [UiText] error on failure.
     */
    public suspend operator fun invoke(
        query: String,
        offset: Int,
    ): Resource<ItemsSearchResponse, UiText>
}
