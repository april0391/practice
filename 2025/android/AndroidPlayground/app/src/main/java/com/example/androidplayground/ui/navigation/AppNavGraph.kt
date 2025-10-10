package com.example.androidplayground.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidplayground.ui.home.HomeDestination
import com.example.androidplayground.ui.home.HomeScreen
import com.example.androidplayground.ui.itementry.ItemEntryDestination
import com.example.androidplayground.ui.itementry.ItemEntryScreen

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(HomeDestination.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate("item_entry") }
            )
        }

        composable(ItemEntryDestination.route) {
            ItemEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}