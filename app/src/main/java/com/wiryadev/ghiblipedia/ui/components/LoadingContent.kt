package com.wiryadev.ghiblipedia.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


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