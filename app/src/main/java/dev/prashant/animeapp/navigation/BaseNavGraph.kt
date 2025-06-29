package dev.prashant.animeapp.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface BaseNavGraph {

    fun Build(
        modifier: Modifier = Modifier,
        navHostController: NavHostController,
        navGraphBuilder: NavGraphBuilder
    )
}