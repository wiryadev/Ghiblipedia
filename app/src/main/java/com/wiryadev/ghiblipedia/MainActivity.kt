package com.wiryadev.ghiblipedia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.wiryadev.ghiblipedia.ui.films.FilmsRoute
import com.wiryadev.ghiblipedia.ui.films.FilmsViewModel
import com.wiryadev.ghiblipedia.ui.films.detail.FilmDetailRoute
import com.wiryadev.ghiblipedia.ui.films.detail.FilmDetailViewModel
import com.wiryadev.ghiblipedia.ui.theme.GhiblipediaTheme
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalAnimationApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberAnimatedNavController()
            GhiblipediaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AppNavHost(navController = navController)
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavHost(
    navController: NavHostController
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = "films",
    ) {
        composable(route = "films") {
            val viewModel = getViewModel<FilmsViewModel>()
            FilmsRoute(
                viewModel = viewModel,
                navigateToDetail = { filmId ->
                    navController.navigate("films/$filmId")
                }
            )
        }
        composable(
            route = "films/{id}",
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
            }),
        ) { backStackEntry ->
            val filmId = backStackEntry.arguments?.getString("id")
            val viewModel = getViewModel<FilmDetailViewModel>()
            filmId?.let {
                viewModel.showSelectedFilm(it)
            }

            FilmDetailRoute(
                viewModel = viewModel,
                onBackPressed = { navController.navigateUp() }
            )
        }
    }
}