package io.github.josebatista.marketplace.domain.model

public data class Attribute(
    val id: String? = null,
    val name: String? = null,
    val valueId: String? = null,
    val valueName: String? = null,
    val attributeGroupId: String? = null,
    val attributeGroupName: String? = null,
    val valueStruct: ValueStruct? = null,
    val values: List<AttributeValue>? = null,
    val source: Long? = null,
    val valueType: String? = null,
)
