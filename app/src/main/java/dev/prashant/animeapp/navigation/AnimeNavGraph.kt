package dev.prashant.animeapp.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import dev.prashant.anime.presentation.characters.CharacterListScreen
import dev.prashant.anime.presentation.details.CharacterDetailScreen
import kotlinx.serialization.Serializable

object AnimeNavGraph : BaseNavGraph {

    sealed interface Dest {

        @Serializable
        data object Root : Dest

        @Serializable
        data object CharacterList : Dest

        @Serializable
        data class CharacterDetails(val id: Int) : Dest

    }

    override fun Build(
        modifier: Modifier,
        navHostController: NavHostController,
        navGraphBuilder: NavGraphBuilder
    ) {
        navGraphBuilder.navigation<Dest.Root>(startDestination = Dest.CharacterList) {

            composable<Dest.CharacterList> {
                CharacterListScreen(modifier = modifier.fillMaxSize(), onClick = {
                    navHostController.navigate(Dest.CharacterDetails(it))
                })
            }

            composable<Dest.CharacterDetails> {
                val id = requireNotNull(it.toRoute<Dest.CharacterDetails>().id)
                CharacterDetailScreen(
                    modifier = Modifier,
                    id = id,
                    seeAllCharacters = {
                        navHostController.navigate(PlanetNavGraph.Dest.PlanetWithCharacters(id))
                    },
                    onBackClick = {
                        navHostController.popBackStack()
                    }
                )
            }
        }
    }
}