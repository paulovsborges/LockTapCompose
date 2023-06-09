package com.pvsb.presentation.onBoarding.onBoarding

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pvsb.presentation.onBoarding.createPassword.PasswordScreenContainer
import com.pvsb.presentation.onBoarding.createPassword.PasswordScreenType
import com.pvsb.presentation.onBoarding.splash.SplashScreen

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
            PasswordScreenContainer(
                navController = navController,
                screenType = PasswordScreenType.CreatePassword
            )
        }

        composable(route = OnBoardingScreens.PasswordScreen.Repeat.route + "/{createdPassword}") {

            val createdPassword = it.arguments?.getString("createdPassword") ?: return@composable

            PasswordScreenContainer(
                navController = navController,
                screenType = PasswordScreenType.RepeatPassword(createdPassword)
            )
        }

        composable(route = OnBoardingScreens.PasswordScreen.Enter.route) {
            PasswordScreenContainer(
                navController = navController, screenType = PasswordScreenType.EnterPassword
            )
        }
    }
}
