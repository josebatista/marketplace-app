package io.github.josebatista.marketplace.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
internal data class AttributeCombinationDto(
    @SerialName("id") val id: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("value_id") val valueId: String? = null,
    @SerialName("value_name") val valueName: String? = null,
    @SerialName("value_struct") val valueStruct: ValueStructDto? = null,
    @SerialName("values") val values: JsonElement? = null,
)
