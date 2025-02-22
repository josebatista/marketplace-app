package io.github.josebatista.marketplace.domain.model

import kotlinx.serialization.json.JsonElement

public data class Shipping(
    val storePickUp: Boolean? = null,
    val freeShipping: Boolean? = null,
    val logisticType: String? = null,
    val mode: String? = null,
    val tags: List<String>? = null,
    val benefits: JsonElement? = null,
    val promise: JsonElement? = null,
    val shippingScore: Int? = null,
)
