package io.github.josebatista.marketplace.data.mapper

import io.github.josebatista.marketplace.data.dto.SellerDto
import io.github.josebatista.marketplace.domain.model.Seller

internal fun SellerDto.toSeller() = Seller(
    id = id,
    nickname = nickname,
)
