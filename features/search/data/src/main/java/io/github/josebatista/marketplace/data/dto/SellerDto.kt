package io.github.josebatista.marketplace.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SellerDto(
    @SerialName("id") val id: Long? = null,
    @SerialName("nickname") val nickname: String? = null,
)
