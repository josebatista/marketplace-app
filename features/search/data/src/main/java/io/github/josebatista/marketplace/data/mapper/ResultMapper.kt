package io.github.josebatista.marketplace.data.mapper

import io.github.josebatista.marketplace.data.dto.ResultDto
import io.github.josebatista.marketplace.domain.model.Result

internal fun ResultDto.toResult() = Result(
    id,
    title,
    condition,
    thumbnailId,
    catalogProductId,
    listingTypeId,
    sanitizedTitle,
    permalink,
    buyingMode,
    siteId,
    categoryId,
    domainId,
    thumbnail,
    currencyId,
    orderBackend,
    price,
    originalPrice,
    salePrice?.toSalePrice(),
    availableQuantity,
    officialStoreId,
    officialStoreName,
    useThumbnailId,
    acceptsMercadopago,
    shipping?.toShipping(),
    stopTime,
    seller?.toSeller(),
    address?.toAddress(),
    attributes?.map { it.toAttribute() },
    installments?.toInstallments(),
    winnerItemId,
    catalogListing,
    discounts,
    promotionDecorations,
    promotions,
    inventoryId,
    installmentsMotors,
    variationFilters,
    variationsData?.map { it.key to it.value.toVariationData() }?.toMap(),
)
