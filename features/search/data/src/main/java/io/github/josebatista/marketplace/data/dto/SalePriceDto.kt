package io.github.josebatista.marketplace.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

@Serializable
internal data class SalePriceDto(
    @SerialName("price_id") val priceId: String? = null,
    @SerialName("amount") val amount: Double? = null,
    @SerialName("conditions") val conditions: ConditionsDto? = null,
    @SerialName("currency_id") val currencyId: String? = null,
    @SerialName("exchange_rate") val exchangeRate: JsonElement? = null,
    @SerialName("payment_method_prices") val paymentMethodPrices: List<JsonElement>? = null,
    @SerialName("payment_method_type") val paymentMethodType: String? = null,
    @SerialName("regular_amount") val regularAmount: Double? = null,
    @SerialName("type") val type: String? = null,
    @SerialName("metadata") val metadata: JsonObject? = null,
)
