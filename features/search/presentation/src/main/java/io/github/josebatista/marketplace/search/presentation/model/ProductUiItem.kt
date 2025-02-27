package io.github.josebatista.marketplace.search.presentation.model

import android.icu.text.NumberFormat
import android.os.Parcelable
import io.github.josebatista.marketplace.domain.model.Result
import kotlinx.parcelize.Parcelize
import java.util.Locale

private const val HTTP = "http://"
private const val HTTPS = "https://"

/**
 * Represents a product in the UI layer with all necessary details for display.
 *
 * @property id Unique identifier of the product.
 * @property thumbnail URL of the product's thumbnail image. The URL is processed to use a secure protocol (HTTPS).
 * @property title Title or name of the product.
 * @property price The current price of the product represented as a [DisplayableNumber].
 * @property originalPrice The original price of the product represented as a [DisplayableNumber].
 * @property attributes Additional attributes of the product as a key-value map.
 */
@Parcelize
internal data class ProductUiItem(
    val id: String,
    val thumbnail: String,
    val title: String,
    val price: DisplayableNumber,
    val originalPrice: DisplayableNumber,
    val attributes: Map<String, String>,
) : Parcelable

/**
 * Extension function that converts a domain model [Result] into a [ProductUiItem] for UI consumption.
 *
 * It ensures that null values are replaced by default values (empty strings or zero prices) and formats the thumbnail
 * URL to use HTTPS. Additionally, it converts numeric price values to a displayable format
 * using [Double.toDisplayableNumber].
 *
 * @return A [ProductUiItem] instance with the mapped data from the [Result].
 */
internal fun Result.toProductUiItem() = ProductUiItem(
    id = id.orEmpty(),
    thumbnail = thumbnail?.trim()?.replace(HTTP, HTTPS).orEmpty(),
    title = title.orEmpty(),
    price = price?.toDisplayableNumber() ?: 0.0.toDisplayableNumber(),
    originalPrice = originalPrice?.toDisplayableNumber() ?: 0.0.toDisplayableNumber(),
    attributes = attributes?.associate { it.name.orEmpty() to it.valueName.orEmpty() } ?: emptyMap()
)

/**
 * Represents a numeric value along with its formatted string representation, suitable for display in the UI.
 *
 * @property value The raw numeric value.
 * @property formatted The formatted string representation of [value], respecting locale-specific formatting.
 */
@Parcelize
internal data class DisplayableNumber(
    val value: Double,
    val formatted: String
) : Parcelable

/**
 * Extension function to convert a [Double] into a [DisplayableNumber].
 *
 * The function formats the double value to a string with exactly two decimal places, using the default locale.
 *
 * @return A [DisplayableNumber] containing the original value and its formatted representation.
 */
internal fun Double.toDisplayableNumber(): DisplayableNumber {
    val formatter = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }
    return DisplayableNumber(
        value = this,
        formatted = formatter.format(this)
    )
}
