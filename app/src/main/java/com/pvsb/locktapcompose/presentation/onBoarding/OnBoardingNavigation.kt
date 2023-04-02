package com.pvsb.locktapcompose.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pvsb.locktapcompose.presentation.onBoarding.OnBoardingScreen
import com.pvsb.locktapcompose.presentation.onBoarding.OnBoardingScreens
import com.pvsb.locktapcompose.presentation.onBoarding.createPassword.PasswordScreen
import com.pvsb.locktapcompose.presentation.onBoarding.createPassword.PasswordScreenType
import com.pvsb.locktapcompose.presentation.onBoarding.splash.SplashScreen

@Composable
fun OnBoardingNavigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = OnBoardingScreens.SplashScree.route) {
        composable(OnBoardingScreens.SplashScree.route) {
            SplashScreen(navController = navController)
        }

        composable(OnBoardingScreens.OnBoarding.route) {
            OnBoardingScreen(navController = navController)
        }

        composable(route = OnBoardingScreens.PasswordScreen.Create.route) {
            PasswordScreen(
                navController = navController,
                screenType = PasswordScreenType.CreatePassword
            )
        }

        composable(route = OnBoardingScreens.PasswordScreen.Repeat.route + "/{createdPassword}") {

            val createdPassword = it.arguments?.getString("createdPassword") ?: return@composable

            PasswordScreen(
                navController = navController,
                screenType = PasswordScreenType.RepeatPassword(createdPassword)
            )
        }

        composable(route = OnBoardingScreens.PasswordScreen.Enter.route) {
            PasswordScreen(
                navController = navController, screenType = PasswordScreenType.EnterPassword
            )
        }
    }
}
