package io.github.josebatista.marketplace.search.presentation.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import io.github.josebatista.marketplace.search.presentation.R
import io.github.josebatista.marketplace.search.presentation.components.ProductItem
import io.github.josebatista.marketplace.search.presentation.model.ProductUiItem

@Composable
internal fun DetailScreen(
    selectedItem: ProductUiItem?,
    modifier: Modifier = Modifier
) {
    selectedItem?.let {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            ProductItem(product = it)
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            Text(
                text = stringResource(
                    R.string.features_search_presentation_original_price,
                    it.originalPrice.formatted
                ),
                textDecoration = TextDecoration.LineThrough,
                fontSize = MaterialTheme.typography.bodySmall.fontSize
            )
            Text(
                text = stringResource(
                    R.string.features_search_presentation_price,
                    it.price.formatted
                ),
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                fontStyle = FontStyle.Normal
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            it.attributes.forEach { (key, value) ->
                Text(
                    text = "$key: $value",
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize
                )
            }
        }
    }
}
