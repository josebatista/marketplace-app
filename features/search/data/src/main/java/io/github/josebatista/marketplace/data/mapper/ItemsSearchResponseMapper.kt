package io.github.josebatista.marketplace.data.mapper

import io.github.josebatista.marketplace.data.dto.ItemsSearchResponseDto
import io.github.josebatista.marketplace.domain.model.ItemsSearchResponse

internal fun ItemsSearchResponseDto.toItemsSearchResponse() = ItemsSearchResponse(
    siteId = siteId,
    countryDefaultTimeZone = countryDefaultTimeZone,
    query = query,
    paging = paging?.toPaging(),
    results = results?.map { it.toResult() },
    sort = sort?.toSort(),
    availableSorts = availableSorts?.map { it.toSortOption() },
    filters = filters?.map { it.toFilter() },
    availableFilters = availableFilters?.map { it.toAvailableFilter() },
    pdpTracking = pdpTracking?.toPdpTracking(),
    userContext = userContext,
    rankingIntrospection = rankingIntrospection,
)
