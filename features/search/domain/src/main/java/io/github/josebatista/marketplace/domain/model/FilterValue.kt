package io.github.josebatista.marketplace.domain.model

public data class FilterValue(
    val id: String? = null,
    val name: String? = null,
    val pathFromRoot: List<PathFromRoot>? = null,
)
