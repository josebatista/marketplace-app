package io.github.josebatista.marketplace.domain.usecase

import io.github.josebatista.marketplace.core.domain.Resource
import io.github.josebatista.marketplace.core.domain.UiText
import io.github.josebatista.marketplace.domain.model.ItemsSearchResponse
import io.github.josebatista.marketplace.domain.repository.SearchRepository
import javax.inject.Inject

/**
 * Implementation of the [SearchUseCase] that delegates the search operation to a [SearchRepository].
 *
 * This class is responsible for invoking a search by forwarding the [query] and [offset]
 * parameters to the repository and returning its result. The result is a [Resource] encapsulating
 * either a successful [ItemsSearchResponse] or an error represented by a [UiText].
 *
 * @property repository The [SearchRepository] used to perform the search operation.
 */
internal class SearchUseCaseImpl @Inject constructor(
    private val repository: SearchRepository
) : SearchUseCase {

    /**
     * Invokes the search operation using the provided [query] and [offset].
     *
     * Delegates the call to the [repository] and returns its result.
     *
     * @param query The search query string.
     * @param offset The offset used for paginating search results.
     * @return A [Resource] containing either the [ItemsSearchResponse] on success or a [UiText] error.
     */
    override suspend fun invoke(query: String, offset: Int): Resource<ItemsSearchResponse, UiText> =
        repository.invoke(query = query, offset = offset)
}
