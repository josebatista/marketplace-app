package io.github.josebatista.marketplace.domain.model

import kotlinx.serialization.json.JsonElement

public data class AttributeCombination(
    val id: String? = null,
    val name: String? = null,
    val valueId: String? = null,
    val valueName: String? = null,
    val valueStruct: ValueStruct? = null,
    val values: JsonElement? = null,
)
