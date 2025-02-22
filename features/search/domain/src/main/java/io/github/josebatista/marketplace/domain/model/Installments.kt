package io.github.josebatista.marketplace.domain.model

import kotlinx.serialization.json.JsonObject

public data class Installments(
    val quantity: Int? = null,
    val amount: Double? = null,
    val rate: Double? = null,
    val currencyId: String? = null,
    val metadata: JsonObject? = null,
)
