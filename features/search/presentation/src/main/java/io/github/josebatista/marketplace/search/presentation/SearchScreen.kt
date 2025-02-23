package io.github.josebatista.marketplace.search.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.josebatista.marketplace.search.presentation.components.SearchBar

@Composable
public fun SearchScreen(
    modifier: Modifier = Modifier,
    onSearchClick: (String) -> Unit,
) {
    val context = LocalContext.current
    val viewModel: SearchScreenViewModel = viewModel()
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Search -> onSearchClick(event.query)
                is UiEvent.ShowError -> {
                    Toast.makeText(
                        context,
                        event.message.asString(context),
                        Toast.LENGTH_SHORT
                    ).show()
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

@Preview(showBackground = true)
@Composable
private fun SearchScreenPreview() {
    SearchScreen(onSearchClick = {})
}
