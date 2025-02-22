package io.github.josebatista.marketplace.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ConditionsDto(
    @SerialName("eligible") val eligible: Boolean? = null,
    @SerialName("context_restrictions") val contextRestrictions: List<String>? = null,
    @SerialName("start_time") val startTime: String? = null,
    @SerialName("end_time") val endTime: String? = null,
)
