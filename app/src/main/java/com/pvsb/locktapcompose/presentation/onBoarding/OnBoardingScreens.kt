package com.pvsb.locktapcompose.presentation.onBoarding

sealed class OnBoardingScreens(val route: String) {

    object SplashScree : OnBoardingScreens("splash_screen")
    object OnBoarding : OnBoardingScreens("on_boarding")

    sealed class PasswordScreen(passwordRoute: String) : OnBoardingScreens(passwordRoute) {
        object Create : PasswordScreen("password_create")
        object Repeat : PasswordScreen("password_repeat")
        object Enter : PasswordScreen("password_enter")
    }

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}