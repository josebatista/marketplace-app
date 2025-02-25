package io.github.josebatista.marketplace.search.presentation

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import io.github.josebatista.marketplace.search.presentation.detail.DetailScreen
import io.github.josebatista.marketplace.search.presentation.list.ListScreen
import io.github.josebatista.marketplace.search.presentation.model.ProductUiItem

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
public fun AdaptiveListDetailPanel(
    query: String,
    modifier: Modifier = Modifier
) {
    var selectedItem by rememberSaveable { mutableStateOf<ProductUiItem?>(null) }

    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    NavigableListDetailPaneScaffold(
        navigator = navigator,
        listPane = {
            AnimatedPane {
                ListScreen(
                    query = query,
                    onItemClick = {
                        selectedItem = it
                        navigator.navigateTo(pane = ListDetailPaneScaffoldRole.Detail)
                    }
                )
            }
        },
        detailPane = {
            AnimatedPane {
                DetailScreen(selectedItem = selectedItem)
            }
        },
        modifier = modifier
    )
}
