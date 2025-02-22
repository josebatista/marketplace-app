package io.github.josebatista.marketplace.domain.model

public data class Paging(
    val total: Int? = null,
    val primaryResults: Int? = null,
    val offset: Int? = null,
    val limit: Int? = null,
)
