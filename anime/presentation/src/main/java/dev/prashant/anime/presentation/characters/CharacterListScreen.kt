package dev.prashant.anime.presentation.characters

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.prashant.anime.presentation.characters.components.CharacterListItem

@Composable
fun CharacterListScreen(
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit
) {
    val viewModel = hiltViewModel<CharacterViewModel>()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getCharcters()
    }
    CharacterListScreenContent(
        modifier = modifier.fillMaxSize(),
        uiState = uiState.value,
        onClick = onClick
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterListScreenContent(
    modifier: Modifier = Modifier,
    uiState: CharacterUiState,
    onClick: (Int) -> Unit
) {

    Scaffold(
        modifier = modifier .fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text("Anime Characters") })
        }
    ) { innerPadding ->

        AnimatedVisibility(
            visible = uiState.isLoading,
            enter = fadeIn(tween(durationMillis = 1000)),
            exit = fadeOut(tween(durationMillis = 1000))
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        AnimatedVisibility(
            visible = uiState.error.isNotBlank(),
            enter = fadeIn(tween(durationMillis = 1000)),
            exit = fadeOut(tween(durationMillis = 1000))
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(text = uiState.error)
            }
        }

        AnimatedVisibility(
            visible = !uiState.data.isNullOrEmpty(),
            enter = slideInVertically(tween(1000)) { it } + fadeIn(tween(1000)),
            exit = slideOutVertically(tween(1000)) { it } + fadeOut(tween(1000))
        ) {
            uiState.data?.let { data ->

                LazyVerticalGrid(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    columns = GridCells.Fixed(2)
                ) {
                    items(data, key = { it.id }) {
                        CharacterListItem(
                            modifier = Modifier.fillMaxWidth(),
                            character = it,
                            onClick = onClick
                        )

                    }
                }

            }
        }
    }
}