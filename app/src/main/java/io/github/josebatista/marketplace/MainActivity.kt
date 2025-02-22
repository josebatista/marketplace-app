package io.github.josebatista.marketplace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.github.josebatista.marketplace.ui.theme.MarketplaceAppTheme

@AndroidEntryPoint
public class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MarketplaceAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}

@Composable
private fun Greeting(
    name: String,
    modifier: Modifier = Modifier,
) {
    val viewModel: MainActivityViewModel = hiltViewModel()
    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = "Hello $name!", modifier = modifier
        )
        Button(
            onClick = viewModel::onEvent
        ) {
            Text(text = "Search")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GreetingPreview() {
    MarketplaceAppTheme {
        Greeting("Android")
    }
}
