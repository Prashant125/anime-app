package dev.prashant.anime.presentation.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import dev.prashant.anime.presentation.R
import dev.prashant.anime.presentation.details.components.PlanetInfo
import dev.prashant.anime.presentation.details.components.TransformationListItem

private const val ANIMATION_DURATION_ONE_SEC = 1000

@Composable
fun CharacterDetailScreen(
    modifier: Modifier = Modifier,
    id: Int,
    seeAllCharacters: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    val viewModel = hiltViewModel<DetailsViewModel>()
    LaunchedEffect(Unit) {
        viewModel.getDetails(id)
    }
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    CharacterDetailScreenContent(modifier, uiState.value, seeAllCharacters, onBackClick)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreenContent(
    modifier: Modifier = Modifier,
    uiState: DetailsuiState,
    seeAllCharcters: (Int) -> Unit,
    onBackClick: () -> Unit
) {

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "") },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack, contentDescription = null,
                        modifier = Modifier.clickable {
                            onBackClick()
                        })
                })
        }) { innerPadding ->
        AnimatedVisibility(
            visible = uiState.isLoading,
            enter = fadeIn(tween(ANIMATION_DURATION_ONE_SEC)),
            exit = fadeOut(tween(ANIMATION_DURATION_ONE_SEC))
        ) {
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        AnimatedVisibility(
            visible = uiState.error.isNotBlank(),
            enter = fadeIn(tween(ANIMATION_DURATION_ONE_SEC)),
            exit = fadeOut(tween(ANIMATION_DURATION_ONE_SEC))
        ) {
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = uiState.error)
            }
        }

        AnimatedVisibility(
            visible = uiState.data != null,
            enter = slideInVertically(tween(ANIMATION_DURATION_ONE_SEC)) { it } + fadeIn(
                tween(
                    ANIMATION_DURATION_ONE_SEC
                )
            ),
            exit = slideOutVertically(tween(ANIMATION_DURATION_ONE_SEC)) { it } + fadeOut(
                tween(
                    ANIMATION_DURATION_ONE_SEC
                )
            )
        ) {

            uiState.data?.let { data ->
                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {

                    item {
                        AsyncImage(
                            model = data.image,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                        )
                    }


                    item {
                        Text(
                            text = data.name,
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }

                    item {
                        Text(
                            text = data.description,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }

                    item {
                        Text(
                            text = stringResource(R.string.transformation),
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 16.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.headlineLarge
                        )
                    }

                    items(data.transformations) {
                        TransformationListItem(it)
                    }

                    item {
                        Text(
                            text = stringResource(R.string.planet_information),
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                            style = MaterialTheme.typography.headlineLarge
                        )
                    }

                    item {
                        PlanetInfo(data.originPlanet, seeAllCharcters)
                    }
                }
            }
        }
    }
}