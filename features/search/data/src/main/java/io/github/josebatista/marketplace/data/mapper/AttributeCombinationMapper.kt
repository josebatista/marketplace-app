package io.github.josebatista.marketplace.data.mapper

import io.github.josebatista.marketplace.data.dto.AttributeCombinationDto
import io.github.josebatista.marketplace.domain.model.AttributeCombination

internal fun AttributeCombinationDto.toAttributeCombination() = AttributeCombination(
    id = id,
    name = name,
    valueId = valueId,
    valueName = valueName,
)
