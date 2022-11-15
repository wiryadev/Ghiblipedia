package com.wiryadev.ghiblipedia.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.wiryadev.ghiblipedia.ui.screens.about.aboutScreen
import com.wiryadev.ghiblipedia.ui.screens.favorite.list.favoriteScreen
import com.wiryadev.ghiblipedia.ui.screens.films.detail.filmDetailScreen
import com.wiryadev.ghiblipedia.ui.screens.films.detail.navigateToFilmDetail
//import com.wiryadev.ghiblipedia.ui.screens.films.list.homeGraphRoutePattern
import com.wiryadev.ghiblipedia.ui.screens.films.list.homeNavigationRoute
import com.wiryadev.ghiblipedia.ui.screens.films.list.homeScreen

@Composable
fun GhibliNavHost(
    navController: NavHostController,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = homeNavigationRoute,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        homeScreen(
            navigateToDetail = { filmId ->
                navController.navigateToFilmDetail(filmId)
            },
        )
        favoriteScreen(
            navigateToDetail = { filmId ->
                navController.navigateToFilmDetail(filmId)
            }
        )
        aboutScreen()

        filmDetailScreen(onBackClick)
    }
}