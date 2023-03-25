package com.pvsb.locktapcompose.presentation

sealed class Screen(val route: String) {

    object SplashScree : Screen("splash_screen")
    object OnBoarding : Screen("on_boarding")
    object MainScreen : Screen("main_screen")
    object CreatePassword : Screen("create_password")
    object DetailScreen : Screen("detail_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}