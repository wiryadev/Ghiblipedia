package com.wiryadev.ghiblipedia.ui.screens.films.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wiryadev.ghiblipedia.R
import com.wiryadev.ghiblipedia.model.Film
import com.wiryadev.ghiblipedia.utils.dummyFilm

@Composable
fun FilmsScreen(
    uiState: FilmsUiState,
    scaffoldState: ScaffoldState,
    onRefreshClicked: () -> Unit,
    navigateToDetail: (String) -> Unit,
    navigateToAbout: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val lazyListState = rememberLazyListState()

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = modifier,
        topBar = {
            FilmsAppBar(
                elevation = if (lazyListState.isScrolled) 4.dp else 0.dp,
                navigateToAbout = navigateToAbout,
            )
        }
    ) { paddingValues ->
        LoadingContent(
            empty = when (uiState) {
                is FilmsUiState.HasPosts -> false
                is FilmsUiState.NoPosts -> uiState.isLoading
            },
            emptyContent = { FilmsPlaceholder() },
            modifier = Modifier.padding(paddingValues),
            content = {
                when (uiState) {
                    is FilmsUiState.HasPosts -> {
                        FilmList(
                            films = uiState.films,
                            isLoading = uiState.isLoading,
                            navigateToDetail = navigateToDetail,
                            state = lazyListState,
                        )
                    }
                    is FilmsUiState.NoPosts -> {
                        Box(modifier = modifier.fillMaxSize()) {
                            TextButton(
                                onClick = onRefreshClicked,
                                modifier.fillMaxSize()
                            ) {
                                Text(
                                    stringResource(id = R.string.retry),
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun LoadingContent(
    empty: Boolean,
    emptyContent: @Composable (Modifier) -> Unit,
    content: @Composable (Modifier) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (empty) {
        emptyContent(modifier)
    } else {
        content(modifier)
    }
}

@Composable
fun FilmsAppBar(
    elevation: Dp,
    navigateToAbout: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(text = stringResource(id = R.string.app_name))
        },
        actions = {
            IconButton(onClick = navigateToAbout) {
                Icon(
                    imageVector = Icons.Rounded.Info,
                    contentDescription = stringResource(id = R.string.about_page),
                    tint = MaterialTheme.colors.onSurface,
                )
            }
        },
        backgroundColor = MaterialTheme.colors.surface,
        elevation = elevation,
    )
}

@Composable
private fun FilmsPlaceholder() {
    LazyColumn(
        contentPadding = PaddingValues(
            top = 0.dp,
            bottom = 16.dp,
            start = 16.dp,
            end = 16.dp,
        ),
    ) {
        items(10) {
            FilmCard(
                film = dummyFilm,
                isLoading = true,
                navigateToDetail = {},
                modifier = Modifier.padding(vertical = 8.dp),
            )
        }
    }
}

@Composable
private fun FilmList(
    films: List<Film>,
    isLoading: Boolean,
    navigateToDetail: (String) -> Unit,
    state: LazyListState = rememberLazyListState(),
) {
    LazyColumn(
        contentPadding = PaddingValues(
            top = 0.dp,
            bottom = 16.dp,
            start = 16.dp,
            end = 16.dp,
        ),
        state = state,
    ) {
        items(
            items = films,
            key = { film -> film.id },
        ) { film ->
            FilmCard(
                film = film,
                isLoading = isLoading,
                navigateToDetail = navigateToDetail,
                modifier = Modifier.padding(vertical = 8.dp),
            )
        }
    }
}

val LazyListState.isScrolled: Boolean
    get() = firstVisibleItemIndex > 0 || firstVisibleItemScrollOffset > 0