package com.example.jetcodeapp.navigation

sealed class Page(val route: String) {
    object Home : Page("home")
    object Favorite : Page("favorite")
    object About : Page("about")
    object DetailCode : Page("home/{codeId}") {
        fun createRoute(codeId: String) = "home/$codeId"
    }
}