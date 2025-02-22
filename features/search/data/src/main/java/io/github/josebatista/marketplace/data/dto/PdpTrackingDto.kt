package io.github.josebatista.marketplace.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PdpTrackingDto(
    @SerialName("group") val group: Boolean? = null,
    @SerialName("product_info") val productInfo: List<ProductInfoDto>? = null,
)
