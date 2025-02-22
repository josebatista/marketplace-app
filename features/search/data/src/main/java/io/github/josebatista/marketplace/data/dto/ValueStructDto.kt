package io.github.josebatista.marketplace.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ValueStructDto(
    @SerialName("number") val number: Double? = null,
    @SerialName("unit") val unit: String? = null,
)
