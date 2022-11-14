package com.wiryadev.ghiblipedia.ui.films.list

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun FilmsRoute(
    viewModel: FilmsViewModel,
    navigateToDetail: (String) -> Unit,
    navigateToAbout: () -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
) {
    val uiState by viewModel.uiState.collectAsState()

    FilmsScreen(
        uiState = uiState,
        scaffoldState = scaffoldState,
        onRefreshClicked = viewModel::refreshPage,
        navigateToDetail = navigateToDetail,
        navigateToAbout = navigateToAbout,
    )
}