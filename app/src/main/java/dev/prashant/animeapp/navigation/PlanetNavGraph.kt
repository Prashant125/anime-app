package dev.prashant.animeapp.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import dev.prashant.planet.presentation.planetInfo.PlanetInfoCharactersScreen
import kotlinx.serialization.Serializable

object PlanetNavGraph : BaseNavGraph {

    sealed interface Dest {

        @Serializable
        data class PlanetWithCharacters(val id: Int) : Dest
    }

    override fun Build(
        modifier: Modifier,
        navHostController: NavHostController,
        navGraphBuilder: NavGraphBuilder
    ) {

        navGraphBuilder.composable<Dest.PlanetWithCharacters> {
            val id = requireNotNull(it.toRoute<Dest.PlanetWithCharacters>()).id
            PlanetInfoCharactersScreen(
                modifier = Modifier,
                id = id,
                onClick = {
                    navHostController.navigate(AnimeNavGraph.Dest.CharacterDetails(it))
                },
                onBackClick = {
                    navHostController.popBackStack()
                }
            )
        }
    }
}