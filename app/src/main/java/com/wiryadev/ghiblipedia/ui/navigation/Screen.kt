package com.wiryadev.ghiblipedia.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object About : Screen("about")
    object DetailFilm : Screen("home/{filmId}") {
        fun createRoute(filmId: String) = "home/$filmId"
    }
}
