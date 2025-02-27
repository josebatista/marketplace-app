package io.github.josebatista.marketplace.search.presentation.search

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import io.github.josebatista.marketplace.search.presentation.components.SearchBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Displays the search screen allowing the user to input a query and trigger a search.
 *
 * This composable integrates with a [SearchScreenViewModel] (injected via Hilt) to manage the
 * search state and handle events. The current search query is observed as state, and UI events
 * from the ViewModel are collected to perform actions such as navigating to the search results or
 * showing an error message via a Toast.
 *
 * The UI is composed of a [SearchBar] placed inside a [Box] that occupies the full available space
 * and applies a 16dp padding. When the user types in the search bar, the [SearchScreenEvent.OnQueryChange]
 * event is triggered. When a search action is executed (e.g. clicking the search icon), the
 * [SearchScreenEvent.OnSearch] event is dispatched.
 *
 * The composable also monitors the lifecycle of the current owner and uses a [LaunchedEffect]
 * to collect UI events from the ViewModel. In particular:
 * - When a [SearchUiEvent.Search] event is received, the [onSearchClick] callback is invoked with the query.
 * - When a [SearchUiEvent.ShowError] event is received, a Toast is displayed with the error message.
 *
 * @param modifier an optional [Modifier] to be applied to the root layout.
 * @param onSearchClick lambda function invoked when a search is triggered, receiving the query as a parameter.
 */
@Composable
public fun SearchScreen(
    modifier: Modifier = Modifier,
    onSearchClick: (String) -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val viewModel: SearchScreenViewModel = hiltViewModel()
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                viewModel.uiEvent.collect { event ->
                    when (event) {
                        is SearchUiEvent.Search -> onSearchClick(event.query)
                        is SearchUiEvent.ShowError -> {
                            Toast.makeText(
                                context,
                                event.message.asString(context),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        SearchBar(
            query = state.value.query,
            onQueryChange = { viewModel.onEvent(SearchScreenEvent.OnQueryChange(it)) },
            onSearch = { viewModel.onEvent(SearchScreenEvent.OnSearch(it)) }
        )
    }
}
