package com.wiryadev.ghiblipedia.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.wiryadev.ghiblipedia.ui.components.BottomNavItem
import com.wiryadev.ghiblipedia.ui.components.GhibliTopAppBar

@Composable
fun GhibliAppNavigation(
    appState: GhibliAppState,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            val destination = appState.currentTopLevelDestination
            if (destination != null) {
                GhibliTopAppBar(
                    titleTextId = destination.titleTextId,
                )
            }
        },
        bottomBar = {
            if (appState.shouldShowBottomBar) {
                BottomNavBar(
                    destinations = appState.rootNavigationDestinations,
                    onNavigateToDestination = appState::navigateToRootNavigationItem,
                    currentDestination = appState.currentDestination,
                )
            }
        }
    ) { padding ->
        GhibliNavHost(
            navController = appState.navController,
            onBackClick = appState::onBackPress,
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
private fun BottomNavBar(
    destinations: List<RootNavigationDestination>,
    onNavigateToDestination: (RootNavigationDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    BottomNavigation(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.background,
    ) {
        destinations.forEach { destination ->
            BottomNavItem(
                title = stringResource(id = destination.titleTextId),
                icon = destination.icon,
                selectedIcon = destination.selectedIcon,
                isSelected = currentDestination.isTopLevelDestinationInHierarchy(destination),
                onClick = { onNavigateToDestination(destination) },
            )
        }
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: RootNavigationDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false