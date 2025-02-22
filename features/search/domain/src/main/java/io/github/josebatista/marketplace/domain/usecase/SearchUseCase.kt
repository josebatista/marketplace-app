package io.github.josebatista.marketplace.domain.usecase

public interface SearchUseCase {
    public suspend operator fun invoke(query: String): String
}
