package io.github.josebatista.marketplace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dagger.hilt.android.AndroidEntryPoint
import io.github.josebatista.marketplace.presentation.route.Route
import io.github.josebatista.marketplace.presentation.theme.MarketplaceAppTheme
import io.github.josebatista.marketplace.search.presentation.AdaptiveListDetailPanel
import io.github.josebatista.marketplace.search.presentation.search.SearchScreen

@AndroidEntryPoint
public class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MarketplaceAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = Route.SearchRoute,
                    ) {
                        composable<Route.SearchRoute> {
                            SearchScreen { query ->
                                navController.navigate(
                                    Route.ListScreen(
                                        query = query
                                    )
                                )
                            }
                        }
                        composable<Route.ListScreen> { backStackEntry ->
                            val route = backStackEntry.toRoute<Route.ListScreen>()
                            AdaptiveListDetailPanel(query = route.query)
                        }
                    }
                }
            }
        }
    }
}
