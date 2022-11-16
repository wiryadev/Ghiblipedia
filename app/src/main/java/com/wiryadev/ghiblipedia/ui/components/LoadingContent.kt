package com.wiryadev.ghiblipedia.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun LoadingContent(
    empty: Boolean,
    content: @Composable (Modifier) -> Unit,
    modifier: Modifier = Modifier,
    emptyContent: @Composable (Modifier) -> Unit = { LoadingIndicator(modifier) },
) {
    if (empty) {
        emptyContent(modifier)
    } else {
        content(modifier)
    }
}

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth()
        )
    }
}