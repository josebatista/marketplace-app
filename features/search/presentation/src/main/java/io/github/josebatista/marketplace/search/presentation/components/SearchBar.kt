package io.github.josebatista.marketplace.search.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import io.github.josebatista.marketplace.search.presentation.R

/**
 * Displays a search bar for user input and search actions.
 *
 * This composable renders an [OutlinedTextField] that allows the user to enter a search query.
 * It features a trailing search icon which, when clicked, triggers the [onSearch] callback with the current query.
 * Additionally, the keyboard is configured with the "Search" IME action, so pressing the action
 * will also invoke the [onSearch] callback.
 *
 * The label of the text field is provided by a string resource defined in
 * [R.string.features_search_presentation_search_text].
 *
 * @param modifier An optional [Modifier] to be applied to the search bar.
 * @param query The current search query string.
 * @param onQueryChange Callback invoked when the search query changes.
 * @param onSearch Callback invoked when a search is triggered, either by clicking the icon or via the keyboard action.
 */
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
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onSearch(query) })
    )
}
