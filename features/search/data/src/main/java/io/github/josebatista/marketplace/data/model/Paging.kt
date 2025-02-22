package io.github.josebatista.marketplace.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class Paging(
    @SerialName("total") val total: Int? = null,
    @SerialName("primary_results") val primaryResults: Int? = null,
    @SerialName("offset") val offset: Int? = null,
    @SerialName("limit") val limit: Int? = null,
)
