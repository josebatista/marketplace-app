package io.github.josebatista.marketplace.data.mapper

import io.github.josebatista.marketplace.data.dto.ShippingDto
import io.github.josebatista.marketplace.domain.model.Shipping

internal fun ShippingDto.toShipping() = Shipping(
    storePickUp = storePickUp,
    freeShipping = freeShipping,
    logisticType = logisticType,
    mode = mode,
    tags = tags,
    benefits = benefits,
    promise = promise,
    shippingScore = shippingScore,
)
