package com.wiryadev.ghiblipedia.ui.screens.favorite.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.wiryadev.ghiblipedia.R
import com.wiryadev.ghiblipedia.ui.screens.films.list.FilmList
import com.wiryadev.ghiblipedia.ui.screens.films.list.FilmsPlaceholder
import com.wiryadev.ghiblipedia.ui.screens.films.list.LoadingContent
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
    LoadingContent(
        empty = when (uiState) {
            is FavoriteUiState.HasData -> false
            is FavoriteUiState.NoData -> uiState.isLoading
        },
        emptyContent = { FilmsPlaceholder() },
        content = {
            when (uiState) {
                is FavoriteUiState.HasData -> {
                    FilmList(
                        films = uiState.films,
                        isLoading = uiState.isLoading,
                        navigateToDetail = navigateToDetail,
                        state = lazyListState,
                    )
                }

                is FavoriteUiState.NoData -> {
                    Box(modifier = modifier.fillMaxSize()) {
                        Text(
                            stringResource(id = R.string.retry),
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    )
}
