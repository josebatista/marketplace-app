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
import dagger.hilt.android.AndroidEntryPoint
import io.github.josebatista.marketplace.route.Route
import io.github.josebatista.marketplace.search.presentation.SearchScreen
import io.github.josebatista.marketplace.ui.theme.MarketplaceAppTheme

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
                                println("[NAVIGATE TO $query]")
//                            navController.navigate(it)
                            }
                        }
                    }
                }
            }
        }
    }
}
