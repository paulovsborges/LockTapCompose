package com.pvsb.locktapcompose.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pvsb.locktapcompose.presentation.mainScreen.MainContent
import com.pvsb.locktapcompose.presentation.splash.SplashContent


@Composable
fun Navigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.SplashScree.route) {
        composable(Screen.SplashScree.route) {
            SplashContent(navController = navController)
        }

        composable(Screen.MainScreen.route) {
            MainContent()
        }
    }
}
