package io.github.josebatista.marketplace.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

@Serializable
internal data class ItemsSearchResponseDto(
    @SerialName("site_id") val siteId: String? = null,
    @SerialName("country_default_time_zone") val countryDefaultTimeZone: String? = null,
    @SerialName("query") val query: String? = null,
    @SerialName("paging") val paging: PagingDto? = null,
    @SerialName("results") val results: List<ResultDto>? = null,
    @SerialName("sort") val sort: SortDto? = null,
    @SerialName("available_sorts") val availableSorts: List<SortOptionDto>? = null,
    @SerialName("filters") val filters: List<FilterDto>? = null,
    @SerialName("available_filters") val availableFilters: List<AvailableFilterDto>? = null,
    @SerialName("pdp_tracking") val pdpTracking: PdpTrackingDto? = null,
    @SerialName("user_context") val userContext: JsonElement? = null,
    @SerialName("ranking_introspection") val rankingIntrospection: JsonObject? = null,
)
