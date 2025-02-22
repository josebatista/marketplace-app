package io.github.josebatista.marketplace.data.mapper

import io.github.josebatista.marketplace.data.dto.AttributeValueDto
import io.github.josebatista.marketplace.domain.model.AttributeValue

internal fun AttributeValueDto.toAttributeValue() = AttributeValue(
    id = id,
    name = name,
    struct = struct?.toValueStruct(),
    source = source,
)
