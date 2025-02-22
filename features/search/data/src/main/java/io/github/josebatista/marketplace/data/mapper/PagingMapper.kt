package io.github.josebatista.marketplace.data.mapper

import io.github.josebatista.marketplace.data.dto.PagingDto
import io.github.josebatista.marketplace.domain.model.Paging

internal fun PagingDto.toPaging() = Paging(
    total = total,
    primaryResults = primaryResults,
    offset = offset,
    limit = limit,
)
