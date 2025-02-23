package io.github.josebatista.marketplace.search.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.github.josebatista.marketplace.search.presentation.R

@Composable
internal fun SearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = query,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                modifier = Modifier.clickable { onSearch(query) }
            )
        },
        onValueChange = { onQueryChange(it) },
        label = { Text(text = stringResource(R.string.features_search_presentation_search_text)) },
    )
}
