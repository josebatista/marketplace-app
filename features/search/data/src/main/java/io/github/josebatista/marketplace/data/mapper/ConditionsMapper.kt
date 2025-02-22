package io.github.josebatista.marketplace.data.mapper

import io.github.josebatista.marketplace.data.dto.ConditionsDto
import io.github.josebatista.marketplace.domain.model.Conditions

internal fun ConditionsDto.toConditions() = Conditions(
    eligible = eligible,
    contextRestrictions = contextRestrictions,
    startTime = startTime,
    endTime = endTime,
)
