package com.wiryadev.ghiblipedia.ui.films

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun FilmsRoute(
    viewModel: FilmsViewModel,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
) {
    val uiState by viewModel.uiState.collectAsState()

    FilmsScreen(uiState = uiState, scaffoldState = scaffoldState, onRefreshClicked = { viewModel.refreshPage() })
}