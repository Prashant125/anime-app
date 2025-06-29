package dev.prashant.animeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.prashant.animeapp.navigation.AnimeNavGraph
import dev.prashant.animeapp.navigation.PlanetNavGraph
import dev.prashant.animeapp.ui.theme.AnimeAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnimeAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navHostController = rememberNavController()
                    NavHost(
                        navController = navHostController,
                        startDestination = AnimeNavGraph.Dest.Root
                    ) {
                        listOf(
                            AnimeNavGraph,
                            PlanetNavGraph
                        ).forEach {
                            it.Build(
                                modifier = Modifier.padding(innerPadding).fillMaxSize(),
                                navHostController = navHostController,
                                navGraphBuilder = this
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AnimeAppTheme {
        Greeting("Android")
    }
}