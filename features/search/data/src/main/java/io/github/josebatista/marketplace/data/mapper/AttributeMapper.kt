package io.github.josebatista.marketplace.data.mapper

import io.github.josebatista.marketplace.data.dto.AttributeDto
import io.github.josebatista.marketplace.domain.model.Attribute

internal fun AttributeDto.toAttribute() = Attribute(
    id = id,
    name = name,
    valueId = valueId,
    valueName = valueName,
    attributeGroupId = attributeGroupId,
    attributeGroupName = attributeGroupName,
    valueStruct = valueStruct?.toValueStruct(),
    values = values?.map { it.toAttributeValue() },
    source = source,
    valueType = valueType,
)
