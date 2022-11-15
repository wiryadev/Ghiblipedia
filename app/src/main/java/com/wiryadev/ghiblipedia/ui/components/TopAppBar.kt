package com.wiryadev.ghiblipedia.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun GhibliTopAppBar(
    @StringRes titleTextId:Int,
    modifier: Modifier = Modifier,
    elevation: Dp = 0.dp,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(text = stringResource(id = titleTextId))
        },
        actions = actions,
        backgroundColor = MaterialTheme.colors.surface,
        elevation = elevation,
    )
}