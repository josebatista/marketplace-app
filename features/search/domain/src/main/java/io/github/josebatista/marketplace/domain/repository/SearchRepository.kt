package io.github.josebatista.marketplace.domain.repository

public interface SearchRepository {
    public suspend operator fun invoke(query: String): String
}
