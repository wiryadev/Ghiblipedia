package com.wiryadev.ghiblipedia.ui.screens.favorite.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.wiryadev.ghiblipedia.R
import com.wiryadev.ghiblipedia.ui.components.EmptyContent
import com.wiryadev.ghiblipedia.ui.components.FilmList
import com.wiryadev.ghiblipedia.ui.components.GhibliTopAppBar
import com.wiryadev.ghiblipedia.ui.components.LoadingContent
import org.koin.androidx.compose.getViewModel

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
    viewModel: FavoriteViewModel = getViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    FavoriteScreen(
        uiState = uiState,
        navigateToDetail = navigateToDetail,
        modifier = modifier,
    )
}

@Composable
fun FavoriteScreen(
    uiState: FavoriteUiState,
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val lazyListState = rememberLazyListState()
    Scaffold(
        modifier = modifier,
        topBar = {
            GhibliTopAppBar(title = stringResource(id = R.string.favorite))
        }
    ) { padding ->
        LoadingContent(
            empty = when (uiState) {
                is FavoriteUiState.HasData -> false
                is FavoriteUiState.NoData -> uiState.isLoading
            },
            content = {
                when (uiState) {
                    is FavoriteUiState.HasData -> {
                        FilmList(
                            films = uiState.films,
                            navigateToDetail = navigateToDetail,
                            state = lazyListState,
                            modifier = Modifier.padding(padding)
                        )
                    }

                    is FavoriteUiState.NoData -> {
                        EmptyContent(
                            message = stringResource(R.string.empty_favorite),
                            illustration = R.drawable.ic_no_data,
                            modifier = Modifier.fillMaxSize(),
                        )
                    }
                }
            }
        )
    }
}
