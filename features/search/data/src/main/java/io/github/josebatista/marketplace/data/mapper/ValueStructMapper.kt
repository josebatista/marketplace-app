package io.github.josebatista.marketplace.data.mapper

import io.github.josebatista.marketplace.data.dto.ValueStructDto
import io.github.josebatista.marketplace.domain.model.ValueStruct

internal fun ValueStructDto.toValueStruct() = ValueStruct(
    number = number,
    unit = unit,
)
