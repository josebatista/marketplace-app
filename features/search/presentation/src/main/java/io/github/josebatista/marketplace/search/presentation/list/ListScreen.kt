package io.github.josebatista.marketplace.search.presentation.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import io.github.josebatista.marketplace.search.presentation.components.ProductItem
import io.github.josebatista.marketplace.search.presentation.model.ProductUiItem
import io.github.josebatista.marketplace.search.presentation.model.toProductUiItem
import java.util.UUID

@Composable
internal fun ListScreen(
    modifier: Modifier = Modifier,
    query: String,
    onItemClick: (ProductUiItem) -> Unit
) {
    val viewModel: ListScreenViewModel = hiltViewModel()

    LaunchedEffect(query) {
        viewModel.setQuery(query)
    }

    val listState = rememberSaveable(saver = LazyListState.Saver) {
        LazyListState()
    }

    val lazyPagingItems = viewModel.searchResults.collectAsLazyPagingItems()

    LazyColumn(
        state = listState,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (lazyPagingItems.loadState.refresh == LoadState.Loading) {
            item {
                Text(
                    text = "Waiting for items to load from the backend",
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
        }
        items(
            count = lazyPagingItems.itemCount,
            key = { index -> lazyPagingItems[index]?.id ?: UUID.randomUUID() }
        ) { index ->
            val item = lazyPagingItems[index]
            item?.let { product ->
                val productUiItem = product.toProductUiItem()
                ProductItem(
                    modifier = Modifier.clickable {
                        onItemClick(productUiItem)
                    },
                    product = productUiItem
                )
            }
        }
        if (lazyPagingItems.loadState.append == LoadState.Loading) {
            item {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
        }
    }
}
