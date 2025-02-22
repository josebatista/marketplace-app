package io.github.josebatista.marketplace.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
internal data class InstallmentsDto(
    @SerialName("quantity") val quantity: Int? = null,
    @SerialName("amount") val amount: Double? = null,
    @SerialName("rate") val rate: Double? = null,
    @SerialName("currency_id") val currencyId: String? = null,
    @SerialName("metadata") val metadata: JsonObject? = null,
)
