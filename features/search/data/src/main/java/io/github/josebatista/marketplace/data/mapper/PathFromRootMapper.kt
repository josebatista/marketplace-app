package io.github.josebatista.marketplace.data.mapper

import io.github.josebatista.marketplace.data.dto.PathFromRootDto
import io.github.josebatista.marketplace.domain.model.PathFromRoot

internal fun PathFromRootDto.toPathFromRoot() = PathFromRoot(
    id = id,
    name = name,
)
