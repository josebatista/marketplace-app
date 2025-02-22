package io.github.josebatista.marketplace.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ProductInfo(
    @SerialName("id") val id: String? = null,
    @SerialName("score") val score: Int? = null,
    @SerialName("status") val status: String? = null,
)
