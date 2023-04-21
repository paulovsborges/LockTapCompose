@file:OptIn(ExperimentalFoundationApi::class)

package com.pvsb.presentation.mainBottomNav

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pvsb.presentation.R
import com.pvsb.presentation.categories.CategoriesScreen
import com.pvsb.presentation.memos.MemosScreen
import com.pvsb.presentation.passwords.PasswordsScreen
import com.pvsb.presentation.settings.SettingsScreen
import com.pvsb.presentation.ui.theme.AppColors.secondary

@Composable
fun MainNavigation() {

    val screens = listOf(
        MainScreens.Categories,
        MainScreens.Passwords,
        MainScreens.Memos,
        MainScreens.Settings,
    )

    val navController = rememberNavController()

    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
        BottomNavigation(backgroundColor = secondary) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            screens.forEach { screen ->
                val isItemSelected = currentDestination?.hierarchy?.any {
                    it.route == screen.route
                } == true

                BottomNavigationItem(icon = {
                    Icon(painter = painterResource(id = screen.icon), contentDescription = "")
                }, label = {
                    Text(
                        text = stringResource(id = screen.label),
                        fontFamily = FontFamily(Font(R.font.sf_pro_display_regular)),
                        fontSize = 10.sp
                    )
                }, selected = isItemSelected, onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                })
            }
        }
    }) {
        SetupNavHost(navController, it)
    }
}

@Composable
fun SetupNavHost(
    navController: NavHostController,
    padding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = MainScreens.Categories.route,
        modifier = Modifier.padding(padding)
    ) {
        composable(MainScreens.Categories.route) {
            CategoriesScreen(MainScreens.Categories.label)
        }
        composable(MainScreens.Passwords.route) {
            PasswordsScreen()
        }
        composable(MainScreens.Memos.route) {
            MemosScreen()
        }
        composable(MainScreens.Settings.route) {
            SettingsScreen()
        }
    }
}
