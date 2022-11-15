package com.wiryadev.ghiblipedia.ui.screens.films.list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.wiryadev.ghiblipedia.R
import com.wiryadev.ghiblipedia.model.Film
import com.wiryadev.ghiblipedia.ui.components.BottomNavigationHeight
import com.wiryadev.ghiblipedia.ui.components.EmptyContent
import com.wiryadev.ghiblipedia.ui.components.FilmCard
import com.wiryadev.ghiblipedia.ui.components.LoadingContent
import com.wiryadev.ghiblipedia.ui.components.SearchAppBar
import com.wiryadev.ghiblipedia.utils.dummyFilm
import org.koin.androidx.compose.getViewModel

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
    viewModel: FilmsViewModel = getViewModel(),
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
            emptyContent = { FilmsPlaceholder() },
            content = {
                when (uiState) {
                    is FilmsUiState.HasData -> {
                        FilmList(
                            films = uiState.films,
                            isLoading = uiState.isLoading,
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

@Composable
fun FilmsPlaceholder() {
    LazyColumn(
        contentPadding = PaddingValues(
            top = 0.dp,
            bottom = BottomNavigationHeight,
            start = 16.dp,
            end = 16.dp,
        ),
    ) {
        items(10) {
            FilmCard(
                filmId = dummyFilm.id,
                title = dummyFilm.title,
                imageUrl = dummyFilm.imageUrl,
                releaseDate = dummyFilm.releaseDate,
                duration = dummyFilm.duration,
                isLoading = true,
                navigateToDetail = {},
                modifier = Modifier.padding(vertical = 8.dp),
            )
        }
    }
}

@Composable
fun FilmList(
    films: List<Film>,
    isLoading: Boolean,
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
) {
    LazyColumn(
        contentPadding = PaddingValues(
            top = 0.dp,
            bottom = BottomNavigationHeight
                    + WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding(),
            start = 16.dp,
            end = 16.dp,
        ),
        state = state,
        modifier = modifier,
    ) {
        items(
            items = films,
            key = { film -> film.id },
        ) { film ->
            FilmCard(
                filmId = film.id,
                title = film.title,
                imageUrl = film.imageUrl,
                releaseDate = film.releaseDate,
                duration = film.duration,
                isLoading = isLoading,
                navigateToDetail = navigateToDetail,
                modifier = Modifier.padding(vertical = 8.dp),
            )
        }
    }
}