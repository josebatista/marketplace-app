package io.github.josebatista.marketplace.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PathFromRoot(
    @SerialName("id") val id: String? = null,
    @SerialName("name") val name: String? = null,
)
