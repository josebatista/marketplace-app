package io.github.josebatista.marketplace.domain.model

public data class AvailableFilter(
    val id: String? = null,
    val name: String? = null,
    val type: String? = null,
    val values: List<AvailableFilterValue>? = null,
)
