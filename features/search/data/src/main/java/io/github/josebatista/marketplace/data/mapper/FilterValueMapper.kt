package io.github.josebatista.marketplace.data.mapper

import io.github.josebatista.marketplace.data.dto.FilterValueDto
import io.github.josebatista.marketplace.domain.model.FilterValue

internal fun FilterValueDto.toFilterValue() = FilterValue(
    id,
    name,
    pathFromRoot?.map { it.toPathFromRoot() },
)
