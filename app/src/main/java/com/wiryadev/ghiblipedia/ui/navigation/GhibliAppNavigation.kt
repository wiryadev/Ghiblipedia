package com.wiryadev.ghiblipedia.ui.navigation

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.wiryadev.ghiblipedia.ui.components.BottomNavItem
import com.wiryadev.ghiblipedia.ui.components.GhibliBottomNavBar
import com.wiryadev.ghiblipedia.ui.components.GhibliTopAppBar

@OptIn(ExperimentalLayoutApi::class)
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
                    title = stringResource(id = destination.titleTextId),
                    backgroundColor = MaterialTheme.colors.surface.copy(
                        alpha = 0.9f
                    )
                )
            }
        },
        bottomBar = {
            if (appState.shouldShowBottomBar) {
                BottomNavigation(
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
            modifier = Modifier
                .consumedWindowInsets(padding),
        )
    }
}

@Composable
private fun BottomNavigation(
    destinations: List<RootNavigationDestination>,
    onNavigateToDestination: (RootNavigationDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    GhibliBottomNavBar(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.9f),
        contentPadding = WindowInsets.navigationBars.asPaddingValues(),
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