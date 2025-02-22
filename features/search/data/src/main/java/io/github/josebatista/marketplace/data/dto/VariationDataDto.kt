package io.github.josebatista.marketplace.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
internal data class VariationDataDto(
    @SerialName("thumbnail") val thumbnail: String? = null,
    @SerialName("ratio") val ratio: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("pictures_qty") val picturesQty: Int? = null,
    @SerialName("price") val price: Double? = null,
    @SerialName("user_product_id") val userProductId: String? = null,
    @SerialName("attributes") val attributes: List<JsonElement>? = null,
    @SerialName("attribute_combinations") val attributeCombinations: List<AttributeCombinationDto>? = null,
)
