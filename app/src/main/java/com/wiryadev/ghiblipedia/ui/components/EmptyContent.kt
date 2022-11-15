package com.wiryadev.ghiblipedia.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.wiryadev.ghiblipedia.R

@Composable
fun EmptyContent(
    message: String,
    @DrawableRes illustration: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 24.dp),
    ) {
        AsyncImage(
            model = illustration,
            contentDescription = stringResource(R.string.no_data_illustration),
            modifier = Modifier.size(192.dp)
        )
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.h6,
        )
    }
}