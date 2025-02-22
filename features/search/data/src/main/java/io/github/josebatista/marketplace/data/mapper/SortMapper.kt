package io.github.josebatista.marketplace.data.mapper

import io.github.josebatista.marketplace.data.dto.SortDto
import io.github.josebatista.marketplace.domain.model.Sort

internal fun SortDto.toSort() = Sort(
    id = id,
    name = name,
)
