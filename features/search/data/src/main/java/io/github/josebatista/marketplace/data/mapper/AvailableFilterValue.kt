package io.github.josebatista.marketplace.data.mapper

import io.github.josebatista.marketplace.data.dto.AvailableFilterValueDto
import io.github.josebatista.marketplace.domain.model.AvailableFilterValue

internal fun AvailableFilterValueDto.toAvailableFilterValue() = AvailableFilterValue(
    id = id,
    name = name,
    results = results,
)
