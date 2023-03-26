package com.pvsb.locktapcompose.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.pvsb.locktapcompose.presentation.createPassword.PasswordScreen
import com.pvsb.locktapcompose.presentation.createPassword.PasswordScreenType
import com.pvsb.locktapcompose.presentation.mainScreen.MainContent
import com.pvsb.locktapcompose.presentation.onBoarding.OnBoardingScreen
import com.pvsb.locktapcompose.presentation.splash.SplashScreen

@Composable
fun Navigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.SplashScree.route) {
        composable(Screen.SplashScree.route) {
            SplashScreen(navController = navController)
        }

        composable(Screen.MainScreen.route) {
            MainContent(navController = navController)
        }

        composable(Screen.OnBoarding.route) {
            OnBoardingScreen(navController = navController)
        }

        composable(route = Screen.PasswordScreen.Create.route) {
            PasswordScreen(
                navController = navController,
                screenType = PasswordScreenType.CreatePassword
            )
        }

        composable(route = Screen.PasswordScreen.Repeat.route + "/{createdPassword}") {

            val createdPassword = it.arguments?.getString("createdPassword") ?: return@composable

            PasswordScreen(
                navController = navController,
                screenType = PasswordScreenType.RepeatPassword(createdPassword)
            )
        }

        composable(route = Screen.PasswordScreen.Enter.route) {
            PasswordScreen(
                navController = navController, screenType = PasswordScreenType.EnterPassword
            )
        }
    }
}
