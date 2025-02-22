package io.github.josebatista.marketplace.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class Seller(
    @SerialName("id") val id: Int? = null,
    @SerialName("nickname") val nickname: String? = null,
)
