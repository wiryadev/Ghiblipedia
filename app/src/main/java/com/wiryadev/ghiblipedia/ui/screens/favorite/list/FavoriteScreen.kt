package com.wiryadev.ghiblipedia.ui.screens.favorite.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val favoriteNavigationRoute = "favorite"

fun NavController.navigateToFavorite(
    navOptions: NavOptions? = null
) {
    this.navigate(favoriteNavigationRoute, navOptions)
}

fun NavGraphBuilder.favoriteScreen(
    navigateToDetail: (String) -> Unit,
) {
    composable(route = favoriteNavigationRoute) {
        FavoriteRoute(
            navigateToDetail = navigateToDetail,
        )
    }
}

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun FavoriteRoute(
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    FavoriteScreen()
}

@Composable
fun FavoriteScreen() {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Text(text = "Favorite Screen")
    }
}
