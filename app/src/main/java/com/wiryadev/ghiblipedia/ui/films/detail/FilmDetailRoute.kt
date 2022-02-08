package com.wiryadev.ghiblipedia.ui.films.detail

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun FilmDetailRoute(
    viewModel: FilmDetailViewModel,
    onBackPressed: () -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
) {
    val uiState by viewModel.uiState.collectAsState()

    FilmDetailScreen(
        uiState = uiState,
        onBackPressed = onBackPressed,
        scaffoldState = scaffoldState,
    )
}