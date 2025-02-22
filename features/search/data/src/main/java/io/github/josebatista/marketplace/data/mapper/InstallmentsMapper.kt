package io.github.josebatista.marketplace.data.mapper

import io.github.josebatista.marketplace.data.dto.InstallmentsDto
import io.github.josebatista.marketplace.domain.model.Installments

internal fun InstallmentsDto.toInstallments() = Installments(
    quantity = quantity,
    amount = amount,
    rate = rate,
    currencyId = currencyId,
    metadata = metadata,
)
