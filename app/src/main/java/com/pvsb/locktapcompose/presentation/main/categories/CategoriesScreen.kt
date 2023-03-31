package com.pvsb.locktapcompose.presentation.main.categories

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pvsb.locktapcompose.presentation.main.categories.sessionOptions.SessionOptionsScreen

@ExperimentalFoundationApi
@Composable
fun CategoriesScreen(
    label: Int
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = CategoriesScreens.SessionOptions.route
    ) {
        composable(route = CategoriesScreens.SessionOptions.route) {
            SessionOptionsScreen(
                navController = navController,
                label = label
            )
        }
    }
}
