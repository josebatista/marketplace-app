package io.github.josebatista.marketplace.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

@Serializable
internal data class ItemsSearchResponse(
    @SerialName("site_id") val siteId: String? = null,
    @SerialName("country_default_time_zone") val countryDefaultTimeZone: String? = null,
    @SerialName("query") val query: String? = null,
    @SerialName("paging") val paging: Paging? = null,
    @SerialName("results") val results: List<Result>? = null,
    @SerialName("sort") val sort: Sort? = null,
    @SerialName("available_sorts") val availableSorts: List<SortOption>? = null,
    @SerialName("filters") val filters: List<Filter>? = null,
    @SerialName("available_filters") val availableFilters: List<AvailableFilter>? = null,
    @SerialName("pdp_tracking") val pdpTracking: PdpTracking? = null,
    @SerialName("user_context") val userContext: JsonElement? = null,
    @SerialName("ranking_introspection") val rankingIntrospection: JsonObject? = null,
)
