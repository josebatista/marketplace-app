package io.github.josebatista.marketplace.data.mapper

import io.github.josebatista.marketplace.data.dto.AvailableFilterDto
import io.github.josebatista.marketplace.domain.model.AvailableFilter

internal fun AvailableFilterDto.toAvailableFilter() = AvailableFilter(
    id = id,
    name = name,
    type = type,
    values = values?.map { it.toAvailableFilterValue() },
)
