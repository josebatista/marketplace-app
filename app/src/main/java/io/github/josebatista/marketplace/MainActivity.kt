package io.github.josebatista.marketplace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import io.github.josebatista.marketplace.presentation.core.route.Route
import io.github.josebatista.marketplace.presentation.core.theme.MarketplaceAppTheme
import io.github.josebatista.marketplace.search.presentation.AdaptiveListDetailPanel
import io.github.josebatista.marketplace.search.presentation.search.SearchScreen
import io.github.josebatista.marketplace.util.AndroidConnectivityObserver

/**
 * Main entry point of the Marketplace application.
 *
 * This [ComponentActivity] is annotated with [AndroidEntryPoint] to enable dependency injection via Hilt.
 * It sets up edge-to-edge display and applies the [MarketplaceAppTheme] to the app's content.
 *
 * The activity instantiates a [MainActivityViewModel] to monitor network connectivity via an
 * [AndroidConnectivityObserver]. The connectivity status is observed as a state, and if the device
 * is not connected to the internet, an error banner is displayed at the top of the screen.
 *
 * Navigation within the app is managed by a [NavHost] using Jetpack Compose Navigation. The app
 * defines two routes:
 * - [Route.SearchRoute]: Displays the [SearchScreen] where users can enter a search query.
 * - [Route.ListScreen]: Displays the [AdaptiveListDetailPanel] with the search results and product details.
 *
 * When a search is performed from the [SearchScreen], the query is passed to the navigation controller,
 * which then navigates to the list screen displaying the relevant search results.
 */
@AndroidEntryPoint
public class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MarketplaceAppTheme {
                val viewModel = viewModel<MainActivityViewModel> {
                    MainActivityViewModel(
                        connectivityObserver = AndroidConnectivityObserver(
                            context = applicationContext
                        )
                    )
                }

                val isConnected by viewModel.isConnected.collectAsStateWithLifecycle()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .statusBarsPadding()
                    ) {
                        if (!isConnected) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.Red)
                                    .padding(vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Sem conexão com a internet",
                                    color = Color.White
                                )
                            }
                        }
                        val navController = rememberNavController()
                        NavHost(
                            modifier = Modifier.fillMaxSize(),
                            navController = navController,
                            startDestination = Route.SearchRoute,
                        ) {
                            composable<Route.SearchRoute> {
                                SearchScreen { query ->
                                    navController.navigate(Route.ListScreen(query = query))
                                }
                            }
                            composable<Route.ListScreen> {
                                AdaptiveListDetailPanel()
                            }
                        }
                    }
                }
            }
        }
    }
}
