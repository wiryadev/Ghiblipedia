package com.wiryadev.ghiblipedia.ui.screens.films.list

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
import com.wiryadev.ghiblipedia.ui.components.LoadingContent
import com.wiryadev.ghiblipedia.ui.components.SearchAppBar
import org.koin.androidx.compose.koinViewModel

const val homeNavigationRoute = "home"

fun NavController.navigateToHome(
    navOptions: NavOptions? = null
) {
    this.navigate(homeNavigationRoute, navOptions)
}

fun NavGraphBuilder.homeScreen(
    navigateToDetail: (String) -> Unit,
) {
    composable(route = homeNavigationRoute) {
        FilmsRoute(
            navigateToDetail = navigateToDetail,
        )
    }
}

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun FilmsRoute(
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FilmsViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    FilmsScreen(
        uiState = uiState,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        navigateToDetail = navigateToDetail,
        modifier = modifier,
    )
}

@Composable
fun FilmsScreen(
    uiState: FilmsUiState,
    onSearchQueryChanged: (String) -> Unit,
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val lazyListState = rememberLazyListState()

    Scaffold(
        topBar = {
            SearchAppBar(
                value = uiState.query,
                onValueChange = onSearchQueryChanged,
                hint = stringResource(R.string.search),
            )
        }
    ) { padding ->
        LoadingContent(
            empty = when (uiState) {
                is FilmsUiState.HasData -> false
                is FilmsUiState.NoData -> uiState.isLoading
            },
            content = {
                when (uiState) {
                    is FilmsUiState.HasData -> {
                        FilmList(
                            films = uiState.films,
                            navigateToDetail = navigateToDetail,
                            state = lazyListState,
                            modifier = modifier.padding(padding)
                        )
                    }

                    is FilmsUiState.NoData -> {
                        EmptyContent(
                            message = stringResource(R.string.empty_search_result),
                            illustration = R.drawable.ic_search_empty,
                        )
                    }
                }
            }
        )
    }
}
