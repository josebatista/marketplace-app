package io.github.josebatista.marketplace.data.mapper

import io.github.josebatista.marketplace.data.dto.PdpTrackingDto
import io.github.josebatista.marketplace.domain.model.PdpTracking

internal fun PdpTrackingDto.toPdpTracking() = PdpTracking(
    group,
    productInfo?.map { it.toProductInfo() },
)
