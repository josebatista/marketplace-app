package io.github.josebatista.marketplace.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
internal data class Result(
    @SerialName("id") val id: String? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("condition") val condition: String? = null,
    @SerialName("thumbnail_id") val thumbnailId: String? = null,
    @SerialName("catalog_product_id") val catalogProductId: String? = null,
    @SerialName("listing_type_id") val listingTypeId: String? = null,
    @SerialName("sanitized_title") val sanitizedTitle: String? = null,
    @SerialName("permalink") val permalink: String? = null,
    @SerialName("buying_mode") val buyingMode: String? = null,
    @SerialName("site_id") val siteId: String? = null,
    @SerialName("category_id") val categoryId: String? = null,
    @SerialName("domain_id") val domainId: String? = null,
    @SerialName("thumbnail") val thumbnail: String? = null,
    @SerialName("currency_id") val currencyId: String? = null,
    @SerialName("order_backend") val orderBackend: Int? = null,
    @SerialName("price") val price: Double? = null,
    @SerialName("original_price") val originalPrice: Double? = null,
    @SerialName("sale_price") val salePrice: SalePrice? = null,
    @SerialName("available_quantity") val availableQuantity: Int? = null,
    @SerialName("official_store_id") val officialStoreId: Int? = null,
    @SerialName("official_store_name") val officialStoreName: String? = null,
    @SerialName("use_thumbnail_id") val useThumbnailId: Boolean? = null,
    @SerialName("accepts_mercadopago") val acceptsMercadopago: Boolean? = null,
    @SerialName("shipping") val shipping: Shipping? = null,
    @SerialName("stop_time") val stopTime: String? = null,
    @SerialName("seller") val seller: Seller? = null,
    @SerialName("address") val address: Address? = null,
    @SerialName("attributes") val attributes: List<Attribute>? = null,
    @SerialName("installments") val installments: Installments? = null,
    @SerialName("winner_item_id") val winnerItemId: String? = null,
    @SerialName("catalog_listing") val catalogListing: Boolean? = null,
    @SerialName("discounts") val discounts: JsonElement? = null,
    @SerialName("promotion_decorations") val promotionDecorations: JsonElement? = null,
    @SerialName("promotions") val promotions: JsonElement? = null,
    @SerialName("inventory_id") val inventoryId: String? = null,
    @SerialName("installments_motors") val installmentsMotors: JsonElement? = null,
    @SerialName("variation_filters") val variationFilters: List<String>? = null,
    @SerialName("variations_data") val variationsData: Map<String, VariationData>? = null,
)
