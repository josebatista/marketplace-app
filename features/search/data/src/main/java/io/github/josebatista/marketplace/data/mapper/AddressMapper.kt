package io.github.josebatista.marketplace.data.mapper

import io.github.josebatista.marketplace.data.dto.AddressDto
import io.github.josebatista.marketplace.domain.model.Address

internal fun AddressDto.toAddress() = Address(
    stateId = stateId,
    stateName = stateName,
    cityId = cityId,
    cityName = cityName,
)
