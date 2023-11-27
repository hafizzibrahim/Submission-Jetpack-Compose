package com.example.jetcodeapp

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.jetcodeapp.navigation.NavigationItem
import com.example.jetcodeapp.navigation.Page
import com.example.jetcodeapp.ui.page.about.AboutPage
import com.example.jetcodeapp.ui.page.detail.DetailPage
import com.example.jetcodeapp.ui.page.favorite.FavoritePage
import com.example.jetcodeapp.ui.page.home.HomePage

@Composable
fun JetCodeApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (currentRoute != Page.DetailCode.route) {
                BottomBar(navController = navController)
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "main",
            modifier = Modifier.padding(innerPadding).statusBarsPadding()
        ) {
            navigation(
                startDestination = Page.Home.route,
                route = "main"
            ){
                composable(route = Page.Home.route) {
                    HomePage(navigateToDetail = { codeId ->
                        navController.navigate(Page.DetailCode.createRoute(codeId))
                    })
                }
                composable(route = Page.Favorite.route) {
                    FavoritePage(
                        navigateBack = { navController.navigateUp() }
                    ) { codeId ->
                        navController.navigate(Page.DetailCode.createRoute(codeId))
                    }
                }
                composable(
                    route = Page.About.route
                ) {
                    AboutPage(
                        navigateBack = { navController.navigateUp() }
                    )
                }
            }
            composable(
                route = Page.DetailCode.route,
                arguments = listOf(navArgument("codeId") { type = NavType.StringType })
            ) {
                val id = it.arguments?.getString("codeId") ?: ""
                DetailPage(
                    codeId = id,
                    navigateBack = { navController.navigateUp() }
                )
            }

        }
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                page = Page.Home
            ),
            NavigationItem(
                title = stringResource(R.string.menu_favorite),
                icon = Icons.Default.Favorite,
                page = Page.Favorite
            ),
            NavigationItem(
                title = stringResource(R.string.menu_about),
                icon = Icons.Default.Person,
                page = Page.About
            ),
        )
        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                selected = currentRoute == item.page.route,
                onClick = {
                    navController.navigate(item.page.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}