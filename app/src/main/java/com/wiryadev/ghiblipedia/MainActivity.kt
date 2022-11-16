package com.wiryadev.ghiblipedia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.wiryadev.ghiblipedia.ui.navigation.GhibliAppNavigation
import com.wiryadev.ghiblipedia.ui.navigation.rememberAppState
import com.wiryadev.ghiblipedia.ui.theme.GhiblipediaTheme

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
            val darkTheme = isSystemInDarkTheme()
            
            DisposableEffect(key1 = systemUiController) {
                systemUiController.setSystemBarsColor(
                    color = Color.Transparent,
                    darkIcons = !darkTheme,
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