package io.github.josebatista.marketplace.data.mapper

import io.github.josebatista.marketplace.data.dto.SortOptionDto
import io.github.josebatista.marketplace.domain.model.SortOption

internal fun SortOptionDto.toSortOption() = SortOption(
    id = id,
    name = name,
)
