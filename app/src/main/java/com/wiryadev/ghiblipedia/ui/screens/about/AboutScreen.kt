package com.wiryadev.ghiblipedia.ui.screens.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.wiryadev.ghiblipedia.R
import com.wiryadev.ghiblipedia.ui.theme.GhiblipediaTheme

const val aboutNavigationRoute = "about"

fun NavController.navigateToAbout(
    navOptions: NavOptions? = null
) {
    this.navigate(aboutNavigationRoute, navOptions)
}

fun NavGraphBuilder.aboutScreen() {
    composable(route = aboutNavigationRoute) {
        AboutScreen()
    }
}

@Composable
fun AboutScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.about_page))
                },
                backgroundColor = MaterialTheme.colors.surface,
                elevation = 0.dp,
            )
        },
    ) { padding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(
                        id = R.drawable.abrar
                    ),
                    contentDescription = stringResource(id = R.string.profile),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(
                            width = 180.dp,
                            height = 180.dp,
                        ),
                )
                Text(
                    text = "Abrar Wiryawan",
                    style = MaterialTheme.typography.h4,
                )
                Text(
                    text = "abrarwiryawan@gmail.com",
                    style = MaterialTheme.typography.subtitle2,
                )
            }
        }
    }
}

@Preview
@Composable
fun ProfilePreview() {
    GhiblipediaTheme {
        AboutScreen()
    }
}