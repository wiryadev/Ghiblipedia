package com.wiryadev.ghiblipedia.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions
import com.wiryadev.ghiblipedia.ui.screens.about.aboutNavigationRoute
import com.wiryadev.ghiblipedia.ui.screens.about.navigateToAbout
import com.wiryadev.ghiblipedia.ui.screens.favorite.list.favoriteNavigationRoute
import com.wiryadev.ghiblipedia.ui.screens.favorite.list.navigateToFavorite
import com.wiryadev.ghiblipedia.ui.screens.films.list.homeNavigationRoute
import com.wiryadev.ghiblipedia.ui.screens.films.list.navigateToHome

@Composable
fun rememberAppState(
    navController: NavHostController
): GhibliAppState {
    return remember(navController) {
        GhibliAppState(navController)
    }
}

@Stable
class GhibliAppState(
    val navController: NavHostController,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val rootNavigationDestinations: List<RootNavigationDestination> = RootNavigationDestination.values().asList()
    private val rootNavigationDestinationRoute = rootNavigationDestinations.map { it.route }

    val currentTopLevelDestination: RootNavigationDestination?
        @Composable get() = when (currentDestination?.route) {
            homeNavigationRoute -> RootNavigationDestination.HOME
            favoriteNavigationRoute -> RootNavigationDestination.FAVORITE
            aboutNavigationRoute -> RootNavigationDestination.ABOUT
            else -> null
        }

    val shouldShowBottomBar: Boolean
        @Composable get() = currentDestination?.route in rootNavigationDestinationRoute

    fun navigateToRootNavigationItem(rootNavigationDestination: RootNavigationDestination) {
        val topLevelNavOptions = navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }

        when (rootNavigationDestination) {
            RootNavigationDestination.HOME -> navController.navigateToHome(topLevelNavOptions)
            RootNavigationDestination.FAVORITE -> navController.navigateToFavorite(topLevelNavOptions)
            RootNavigationDestination.ABOUT -> navController.navigateToAbout(topLevelNavOptions)
        }
    }

    fun onBackPress() = navController.popBackStack()
}