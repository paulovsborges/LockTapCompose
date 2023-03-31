package com.pvsb.locktapcompose.presentation.main.categories

sealed class CategoriesScreens(
    val route: String
){

    object SessionOptions: CategoriesScreens("main_categories_session_options")

}
