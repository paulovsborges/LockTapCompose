package com.pvsb.presentation.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.pvsb.presentation.R

sealed class MainScreens(
    val route: String,
    @StringRes val label: Int,
    @DrawableRes val icon: Int
) {

    object Categories : MainScreens(
        "main_screen_categories",
        R.string.main_screen_bottom_nav_label_categories,
        R.drawable.ic_bottom_nav_categories
    )

    object Passwords : MainScreens(
        "main_screen_passwords",
        R.string.main_screen_bottom_nav_label_passwords,
        R.drawable.ic_bottom_nav_passwords
    )

    object Memos : MainScreens(
        "main_screen_memos",
        R.string.main_screen_bottom_nav_label_memos,
        R.drawable.ic_bottom_nav_memos
    )

    object Settings : MainScreens(
        "main_screen_settings",
        R.string.main_screen_bottom_nav_label_settings,
        R.drawable.ic_bottom_nav_settings
    )
}
