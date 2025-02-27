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
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import io.github.josebatista.marketplace.search.presentation.R
import io.github.josebatista.marketplace.search.presentation.components.ProductItem
import io.github.josebatista.marketplace.search.presentation.model.ProductUiItem
import io.github.josebatista.marketplace.search.presentation.model.toProductUiItem
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import java.util.UUID

/**
 * The debounce duration (in milliseconds) applied to the scroll position snapshot flow.
 */
private const val DEBOUNCE_TIME = 500L

/**
 * Displays a list of products in a paginated, lazy-loaded column.
 *
 * This composable observes the search results from [ListScreenViewModel] and displays them in a [LazyColumn].
 * It handles loading and error states by showing a loading indicator or an error message when needed.
 *
 * Additionally, the scroll position is tracked using a [LazyListState]. A [snapshotFlow] monitors the scroll
 * position, debounces changes using [DEBOUNCE_TIME], and dispatches [ListScreenEvent.OnScrollPositionChange]
 * events to update the ViewModel's state.
 *
 * @param modifier An optional [Modifier] to be applied to the [LazyColumn].
 * @param onItemClick Callback invoked when a product item is clicked. The corresponding [ProductUiItem]
 *                    is passed as a parameter.
 */
@OptIn(FlowPreview::class)
@Composable
internal fun ListScreen(
    modifier: Modifier = Modifier,
    onItemClick: (ProductUiItem) -> Unit
) {
    val viewModel: ListScreenViewModel = hiltViewModel()

    val lazyListState = rememberSaveable(saver = LazyListState.Saver) {
        LazyListState(
            firstVisibleItemIndex = viewModel.scrollPosition.value.index,
            firstVisibleItemScrollOffset = viewModel.scrollPosition.value.offset
        )
    }

    val lazyPagingItems = viewModel.searchResults.collectAsLazyPagingItems()

    LaunchedEffect(lazyListState) {
        snapshotFlow {
            lazyListState.firstVisibleItemIndex to lazyListState.firstVisibleItemScrollOffset
        }
            .debounce(DEBOUNCE_TIME)
            .collect { (index, offset) ->
                viewModel.onEvent(
                    ListScreenEvent.OnScrollPositionChange(index = index, offset = offset)
                )
            }
    }

    LazyColumn(
        state = lazyListState,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (lazyPagingItems.loadState.hasError) {
            item { Text(text = "Erro ao carregar os dados, tente novamente mais tarde.") }
        } else if (lazyPagingItems.loadState.refresh == LoadState.Loading) {
            item {
                Refresh()
            }
        }
        items(
            count = lazyPagingItems.itemCount,
            key = { index -> lazyPagingItems[index]?.id ?: UUID.randomUUID() }
        ) { index ->
            lazyPagingItems[index]?.let { product ->
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
                Loading()
            }
        }
    }
}

/**
 * Displays a text message indicating that the items are being refreshed.
 *
 * This composable is used as a placeholder at the top of the list when the search results
 * are being initially loaded.
 *
 * @param modifier An optional [Modifier] to be applied to the [Text] component.
 */
@Composable
private fun Refresh(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.features_search_presentation_waiting_for_items_message),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}


/**
 * Displays a circular loading indicator.
 *
 * This composable is used to indicate that more items are being loaded when paginating
 * through the search results.
 *
 * @param modifier An optional [Modifier] to be applied to the [CircularProgressIndicator].
 */
@Composable
private fun Loading(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}
