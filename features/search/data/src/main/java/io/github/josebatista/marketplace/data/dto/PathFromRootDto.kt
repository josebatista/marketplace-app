package io.github.josebatista.marketplace.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PathFromRootDto(
    @SerialName("id") val id: String? = null,
    @SerialName("name") val name: String? = null,
)
