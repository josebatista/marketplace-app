package io.github.josebatista.marketplace.data.mapper

import io.github.josebatista.marketplace.data.dto.VariationDataDto
import io.github.josebatista.marketplace.domain.model.VariationData

internal fun VariationDataDto.toVariationData() = VariationData(
    thumbnail = thumbnail,
    ratio = ratio,
    name = name,
    picturesQty = picturesQty,
    price = price,
    userProductId = userProductId,
    attributes = attributes,
    attributeCombinations = attributeCombinations?.map { it.toAttributeCombination() },
)
