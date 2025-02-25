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
