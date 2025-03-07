package io.github.josebatista.marketplace.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AttributeDto(
    @SerialName("id") val id: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("value_id") val valueId: String? = null,
    @SerialName("value_name") val valueName: String? = null,
    @SerialName("attribute_group_id") val attributeGroupId: String? = null,
    @SerialName("attribute_group_name") val attributeGroupName: String? = null,
    @SerialName("value_struct") val valueStruct: ValueStructDto? = null,
    @SerialName("values") val values: List<AttributeValueDto>? = null,
    @SerialName("source") val source: Long? = null,
    @SerialName("value_type") val valueType: String? = null,
)
