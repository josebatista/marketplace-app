package io.github.josebatista.marketplace.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PdpTracking(
    @SerialName("group") val group: Boolean? = null,
    @SerialName("product_info") val productInfo: List<ProductInfo>? = null,
)
