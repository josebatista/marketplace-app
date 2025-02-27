package io.github.josebatista.marketplace.search.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import io.github.josebatista.marketplace.search.presentation.model.ProductUiItem

/**
 * Displays a product item in a horizontal layout.
 *
 * This composable renders a product item with an image on the left and product details on the right.
 * The image is loaded asynchronously using Coil's [rememberAsyncImagePainter] with the product's thumbnail URL.
 *
 * The layout is structured as a [Row] where:
 * - A [Box] occupies one-third of the width (using a weight of 1f) and displays the product image.
 * - A [Column] occupies two-thirds of the width (using a weight of 2f) and displays the product's brand (if available)
 *   in bold and the product title below it.
 *
 * @param product The [ProductUiItem] containing the details to be displayed.
 * @param modifier An optional [Modifier] for applying layout or styling adjustments.
 */
@Composable
internal fun ProductItem(
    product: ProductUiItem,
    modifier: Modifier = Modifier,
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(product.thumbnail)
            .build(),
    )
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(8.dp)
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
        }
        Column(
            modifier = Modifier
                .weight(2f)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = product.attributes["BRAND"].orEmpty(),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = product.title,
                fontSize = 12.sp
            )
        }
    }
}
