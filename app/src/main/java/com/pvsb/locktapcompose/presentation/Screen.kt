package com.pvsb.locktapcompose.presentation

sealed class Screen(val route: String) {

    object SplashScree : Screen("splash_screen")
    object OnBoarding : Screen("on_boarding")
    object MainScreen : Screen("main_screen")

    sealed class PasswordScreen(passwordRoute: String) : Screen(passwordRoute) {
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