package io.github.josebatista.marketplace.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class FilterValueDto(
    @SerialName("id") val id: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("path_from_root") val pathFromRoot: List<PathFromRootDto>? = null,
)
