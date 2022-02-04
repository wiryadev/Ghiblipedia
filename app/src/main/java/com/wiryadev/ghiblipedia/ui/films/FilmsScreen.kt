package com.wiryadev.ghiblipedia.ui.films

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.wiryadev.ghiblipedia.R
import com.wiryadev.ghiblipedia.model.Film
import com.wiryadev.ghiblipedia.utils.dummyFilm


@Composable
fun FilmsScreen(
    uiState: FilmsUiState,
    scaffoldState: ScaffoldState,
    onRefreshClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Log.d("UiState", "FilmsScreen: $uiState")
    Scaffold(scaffoldState = scaffoldState, modifier = modifier) { innerPadding ->
        val contentPadding = Modifier.padding(innerPadding)
        LoadingContent(
            empty = when (uiState) {
                is FilmsUiState.HasPosts -> false
                is FilmsUiState.NoPosts -> uiState.isLoading
            },
            emptyContent = { FilmsPlaceholder() }
        ) {
            when (uiState) {
                is FilmsUiState.HasPosts -> {
                    FilmList(
                        films = uiState.films,
                        isLoading = uiState.isLoading,
                        navigateToArticle = {}
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
    }
}

@Composable
private fun LoadingContent(
    empty: Boolean,
    emptyContent: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    if (empty) {
        emptyContent()
    } else {
        content()
    }
}

@Composable
fun FilmsPlaceholder() {
    LazyColumn {
        items(10) {
            FilmCard(film = dummyFilm, isLoading = true, navigateToDetail = {})
        }
    }
}

@Composable
fun FilmList(
    films: List<Film>,
    isLoading: Boolean,
    navigateToArticle: (String) -> Unit,
) {
    LazyColumn {
        items(
            items = films,
            key = { film -> film.id },
        ) { film ->
            FilmCard(
                film = film,
                isLoading = isLoading,
                navigateToDetail = navigateToArticle,
            )
        }
    }
}