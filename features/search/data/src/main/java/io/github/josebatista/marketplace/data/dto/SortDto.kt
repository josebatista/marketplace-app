package io.github.josebatista.marketplace.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SortDto(
    @SerialName("id") val id: String? = null,
    @SerialName("name") val name: String? = null,
)
