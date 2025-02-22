package io.github.josebatista.marketplace.domain.model

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

public data class SalePrice(
    val priceId: String? = null,
    val amount: Double? = null,
    val conditions: Conditions? = null,
    val currencyId: String? = null,
    val exchangeRate: JsonElement? = null,
    val paymentMethodPrices: List<JsonElement>? = null,
    val paymentMethodType: String? = null,
    val regularAmount: Double? = null,
    val type: String? = null,
    val metadata: JsonObject? = null,
)
