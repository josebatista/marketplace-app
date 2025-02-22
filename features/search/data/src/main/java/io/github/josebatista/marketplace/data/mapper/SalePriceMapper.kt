package io.github.josebatista.marketplace.data.mapper

import io.github.josebatista.marketplace.data.dto.SalePriceDto
import io.github.josebatista.marketplace.domain.model.SalePrice

internal fun SalePriceDto.toSalePrice() = SalePrice(
    priceId = priceId,
    amount = amount,
    conditions = conditions?.toConditions(),
    currencyId = currencyId,
    exchangeRate = exchangeRate,
    paymentMethodPrices = paymentMethodPrices,
    paymentMethodType = paymentMethodType,
    regularAmount = regularAmount,
    type = type,
    metadata = metadata,
)
