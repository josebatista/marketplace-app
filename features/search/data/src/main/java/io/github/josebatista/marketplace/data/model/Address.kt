package io.github.josebatista.marketplace.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class Address(
    @SerialName("state_id") val stateId: String? = null,
    @SerialName("state_name") val stateName: String? = null,
    @SerialName("city_id") val cityId: String? = null,
    @SerialName("city_name") val cityName: String? = null,
)
