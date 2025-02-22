package io.github.josebatista.marketplace.domain.model

import kotlinx.serialization.json.JsonElement

public data class VariationData(
    val thumbnail: String? = null,
    val ratio: String? = null,
    val name: String? = null,
    val picturesQty: Int? = null,
    val price: Double? = null,
    val userProductId: String? = null,
    val attributes: List<JsonElement>? = null,
    val attributeCombinations: List<AttributeCombination>? = null,
)
