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
import androidx.navigation.toRoute
import dagger.hilt.android.AndroidEntryPoint
import io.github.josebatista.marketplace.presentation.route.Route
import io.github.josebatista.marketplace.presentation.theme.MarketplaceAppTheme
import io.github.josebatista.marketplace.search.presentation.AdaptiveListDetailPanel
import io.github.josebatista.marketplace.search.presentation.search.SearchScreen
import io.github.josebatista.marketplace.util.AndroidConnectivityObserver

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
}
