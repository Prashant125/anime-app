package dev.prashant.planet.presentation.planetInfo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import dev.prashant.planet.presentation.R

private const val ANIMATION_DURATION_ONE_SEC = 1000

@Composable
fun PlanetInfoCharactersScreen(
    modifier: Modifier = Modifier,
    id: Int,
    onClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    val viewModel = hiltViewModel<PlanetInfoViewModel>()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getAllCharacters(id)
    }

    PlanetInfoCharactersScreenContent(
        modifier = modifier,
        uiState = uiState.value,
        onClick = onClick,
        onBackClick
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanetInfoCharactersScreenContent(
    modifier: Modifier = Modifier,
    uiState: PlanetInfoUiState,
    onClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = {
                if (uiState.data != null) {
                    Text(text = uiState.data.name)
                }
            }, navigationIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowBack, contentDescription = null,
                    modifier = Modifier.clickable {
                        onBackClick()
                    })
            }
            )
        }
    ) { innerPadding ->

        AnimatedVisibility(
            visible = uiState.isLoading,
            enter = fadeIn(tween(durationMillis = ANIMATION_DURATION_ONE_SEC)),
            exit = fadeOut(tween(durationMillis = ANIMATION_DURATION_ONE_SEC))
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
            enter = fadeIn(tween(durationMillis = ANIMATION_DURATION_ONE_SEC)),
            exit = fadeOut(tween(durationMillis = ANIMATION_DURATION_ONE_SEC))
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
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)

                        )
                    }

                    item {
                        Text(
                            text = data.description,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                                .fillMaxWidth()
                        )
                    }

                    item {
                        Text(
                            text = stringResource(R.string.characters),
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                                .fillMaxWidth()
                        )
                    }

                    items(data.characters) {
                        Row(
                            modifier = Modifier
                                .clickable { onClick(it.id) }
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = it.image,
                                contentDescription = null,
                                contentScale = ContentScale.FillHeight,
                                modifier = Modifier.size(80.dp)

                            )
                            Spacer(Modifier.width(8.dp))
                            Text(text = it.name, style = MaterialTheme.typography.bodyLarge)
                        }
                    }

                }

            }
        }
    }
}