package io.github.josebatista.marketplace.domain.model

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

public data class ItemsSearchResponse(
    val siteId: String? = null,
    val countryDefaultTimeZone: String? = null,
    val query: String? = null,
    val paging: Paging? = null,
    val results: List<Result>? = null,
    val sort: Sort? = null,
    val availableSorts: List<SortOption>? = null,
    val filters: List<Filter>? = null,
    val availableFilters: List<AvailableFilter>? = null,
    val pdpTracking: PdpTracking? = null,
    val userContext: JsonElement? = null,
    val rankingIntrospection: JsonObject? = null,
)
