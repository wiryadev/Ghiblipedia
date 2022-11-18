package com.wiryadev.ghiblipedia.ui.screens.about

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import coil.compose.AsyncImage
import com.wiryadev.ghiblipedia.R
import com.wiryadev.ghiblipedia.ui.components.GhibliTopAppBar
import com.wiryadev.ghiblipedia.ui.theme.GhiblipediaTheme

const val aboutNavigationRoute = "about"

fun NavController.navigateToAbout(
    navOptions: NavOptions? = null,
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
            GhibliTopAppBar(title = stringResource(id = R.string.about_page))
        }
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
                AsyncImage(
                    model = R.drawable.abrar,
                    contentDescription = stringResource(id = R.string.profile),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(
                            width = 192.dp,
                            height = 192.dp,
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