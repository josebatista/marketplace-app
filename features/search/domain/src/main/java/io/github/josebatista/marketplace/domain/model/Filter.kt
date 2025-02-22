package io.github.josebatista.marketplace.domain.model

public data class Filter(
    val id: String? = null,
    val name: String? = null,
    val type: String? = null,
    val values: List<FilterValue>? = null,
)
