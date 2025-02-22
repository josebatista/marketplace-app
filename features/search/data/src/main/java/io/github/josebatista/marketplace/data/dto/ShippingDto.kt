package io.github.josebatista.marketplace.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
internal data class ShippingDto(
    @SerialName("store_pick_up") val storePickUp: Boolean? = null,
    @SerialName("free_shipping") val freeShipping: Boolean? = null,
    @SerialName("logistic_type") val logisticType: String? = null,
    @SerialName("mode") val mode: String? = null,
    @SerialName("tags") val tags: List<String>? = null,
    @SerialName("benefits") val benefits: JsonElement? = null,
    @SerialName("promise") val promise: JsonElement? = null,
    @SerialName("shipping_score") val shippingScore: Int? = null,
)
