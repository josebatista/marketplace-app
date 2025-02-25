package io.github.josebatista.marketplace.search.presentation.model

import android.icu.text.NumberFormat
import android.os.Parcelable
import io.github.josebatista.marketplace.domain.model.Result
import kotlinx.parcelize.Parcelize
import java.util.Locale

private const val HTTP = "http://"
private const val HTTPS = "https://"

@Parcelize
internal data class ProductUiItem(
    val id: String,
    val thumbnail: String,
    val title: String,
    val price: DisplayableNumber,
    val originalPrice: DisplayableNumber,
    val attributes: Map<String, String>,
) : Parcelable

internal fun Result.toProductUiItem() = ProductUiItem(
    id = id.orEmpty(),
    thumbnail = thumbnail?.trim()?.replace(HTTP, HTTPS).orEmpty(),
    title = title.orEmpty(),
    price = price?.toDisplayableNumber() ?: 0.0.toDisplayableNumber(),
    originalPrice = originalPrice?.toDisplayableNumber() ?: 0.0.toDisplayableNumber(),
    attributes = attributes?.associate { it.name.orEmpty() to it.valueName.orEmpty() } ?: emptyMap()
)

@Parcelize
internal data class DisplayableNumber(
    val value: Double,
    val formatted: String
) : Parcelable

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
