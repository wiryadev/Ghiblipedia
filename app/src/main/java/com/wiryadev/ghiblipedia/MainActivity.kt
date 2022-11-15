package com.wiryadev.ghiblipedia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.wiryadev.ghiblipedia.ui.navigation.GhibliAppNavigation
import com.wiryadev.ghiblipedia.ui.navigation.rememberAppState
import com.wiryadev.ghiblipedia.ui.screens.about.AboutScreen
import com.wiryadev.ghiblipedia.ui.screens.films.detail.FilmDetailRoute
import com.wiryadev.ghiblipedia.ui.screens.films.detail.FilmDetailViewModel
import com.wiryadev.ghiblipedia.ui.screens.films.list.FilmsRoute
import com.wiryadev.ghiblipedia.ui.screens.films.list.FilmsViewModel
import com.wiryadev.ghiblipedia.ui.theme.GhiblipediaTheme
import org.koin.androidx.compose.getViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        // Turn off the decor fitting system windows, which allows us to handle insets,
        // including IME animations
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val navController = rememberNavController()
            val appState = rememberAppState(navController = navController)
            
            val systemUiController = rememberSystemUiController()
            
            DisposableEffect(key1 = systemUiController) {
                systemUiController.setSystemBarsColor(
                    color = Color.Transparent,
                    darkIcons = true,
                )

                onDispose {  }
            }

            GhiblipediaTheme {
                // A surface container using the 'background' color from the theme
                GhibliAppNavigation(
                    appState = appState
                )
            }
        }
    }
}