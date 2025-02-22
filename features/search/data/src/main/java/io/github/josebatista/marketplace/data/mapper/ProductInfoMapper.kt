package io.github.josebatista.marketplace.data.mapper

import io.github.josebatista.marketplace.data.dto.ProductInfoDto
import io.github.josebatista.marketplace.domain.model.ProductInfo

internal fun ProductInfoDto.toProductInfo() = ProductInfo(
    id = id,
    score = score,
    status = status,
)
