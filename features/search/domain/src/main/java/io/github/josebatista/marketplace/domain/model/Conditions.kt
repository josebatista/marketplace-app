package io.github.josebatista.marketplace.domain.model

public data class Conditions(
    val eligible: Boolean? = null,
    val contextRestrictions: List<String>? = null,
    val startTime: String? = null,
    val endTime: String? = null,
)
