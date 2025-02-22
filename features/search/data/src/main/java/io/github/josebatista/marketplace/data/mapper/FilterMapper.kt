package io.github.josebatista.marketplace.data.mapper

import io.github.josebatista.marketplace.data.dto.FilterDto
import io.github.josebatista.marketplace.domain.model.Filter

internal fun FilterDto.toFilter() = Filter(
    id = id,
    name = name,
    type = type,
    values = values?.map { it.toFilterValue() },
)
