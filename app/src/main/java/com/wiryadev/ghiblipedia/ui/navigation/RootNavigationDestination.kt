package com.wiryadev.ghiblipedia.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.wiryadev.ghiblipedia.R
import com.wiryadev.ghiblipedia.ui.screens.about.aboutNavigationRoute
import com.wiryadev.ghiblipedia.ui.screens.favorite.list.favoriteNavigationRoute
import com.wiryadev.ghiblipedia.ui.screens.films.list.homeNavigationRoute

enum class RootNavigationDestination(
    @StringRes val titleTextId: Int,
    val icon: ImageVector,
    val selectedIcon: ImageVector,
    val route: String,
) {
    HOME(
        titleTextId = R.string.home,
        icon = Icons.Rounded.Home,
        selectedIcon = Icons.Filled.Home,
        route = homeNavigationRoute,
    ),
    FAVORITE(
        titleTextId = R.string.favorite,
        icon = Icons.Rounded.FavoriteBorder,
        selectedIcon = Icons.Filled.Favorite,
        route = favoriteNavigationRoute,
    ),
    ABOUT(
        titleTextId = R.string.about_page,
        icon = Icons.Rounded.Person,
        selectedIcon = Icons.Filled.AccountCircle,
        route = aboutNavigationRoute,
    ),
}
