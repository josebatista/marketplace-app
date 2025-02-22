package io.github.josebatista.marketplace.data.mapper

import io.github.josebatista.marketplace.data.dto.ItemsSearchResponseDto
import io.github.josebatista.marketplace.domain.model.ItemsSearchResponse

internal fun ItemsSearchResponseDto.toItemsSearchResponse() = ItemsSearchResponse(
    siteId,
    countryDefaultTimeZone,
    query,
    paging?.toPaging(),
    results?.map { it.toResult() },
    sort?.toSort(),
    availableSorts?.map { it.toSortOption() },
    filters?.map { it.toFilter() },
    availableFilters?.map { it.toAvailableFilter() },
    pdpTracking?.toPdpTracking(),
    userContext,
    rankingIntrospection,
)
