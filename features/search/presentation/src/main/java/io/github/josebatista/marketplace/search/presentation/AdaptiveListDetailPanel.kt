package io.github.josebatista.marketplace.search.presentation

import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.josebatista.marketplace.search.presentation.detail.DetailScreen
import io.github.josebatista.marketplace.search.presentation.list.ListScreen
import io.github.josebatista.marketplace.search.presentation.model.ProductUiItem

/**
 * Displays an adaptive two-pane layout that combines a list pane and a detail pane.
 *
 * This composable uses Material3 Adaptive components to create a responsive layout,
 * where the [ListScreen] is shown in the list pane and, upon item selection, the corresponding
 * details are displayed in the detail pane via [DetailScreen]. Navigation between panes is handled
 * by a [NavigableListDetailPaneScaffold] and its associated navigator.
 *
 * In the list pane, an [AnimatedPane] wraps the [ListScreen]. When an item is clicked,
 * the navigator navigates to the detail pane, passing the selected item's content.
 *
 * In the detail pane, if there is a current destination with content, it is cast to [ProductUiItem]
 * and displayed using [DetailScreen]. If no item is selected, a fallback text "Sem item selecionado" is shown.
 *
 * @param modifier an optional [Modifier] to be applied to the root layout of the panel.
 */
@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
public fun AdaptiveListDetailPanel(
    modifier: Modifier = Modifier
) {
    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()

    NavigableListDetailPaneScaffold(
        navigator = navigator,
        listPane = {
            AnimatedPane {
                ListScreen(
                    onItemClick = {
                        navigator.navigateTo(
                            pane = ListDetailPaneScaffoldRole.Detail,
                            content = it
                        )
                    }
                )
            }
        },
        detailPane = {
            AnimatedPane {
                navigator.currentDestination?.content?.let { selectedItem ->
                    DetailScreen(selectedItem = selectedItem as ProductUiItem)
                } ?: Text("Sem item selecionado")
            }
        },
        modifier = modifier
    )
}
