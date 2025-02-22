package io.github.josebatista.marketplace.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ValueStruct(
    @SerialName("number") val number: Double? = null,
    @SerialName("unit") val unit: String? = null,
)
