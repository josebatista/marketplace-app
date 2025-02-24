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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import io.github.josebatista.marketplace.domain.model.Attribute
import io.github.josebatista.marketplace.domain.model.Result
import io.github.josebatista.marketplace.presentation.theme.MarketplaceAppTheme

@Composable
internal fun ProductItem(
    modifier: Modifier = Modifier,
    product: Result,
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(product.thumbnail?.trim()?.replace("http://", "https://"))
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
                text = product.attributes?.first { it.id?.lowercase() == "brand" }?.valueName.orEmpty(),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = product.title.orEmpty(),
                fontSize = 12.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductItemPreview() {
    io.github.josebatista.marketplace.presentation.theme.MarketplaceAppTheme {
        ProductItem(
            product = Result(
                title = "Product title adfasdfasdfasdfasdfads",
                attributes = listOf(Attribute(id = "brand", valueName = "XPTO"))
            )
        )
    }
}
