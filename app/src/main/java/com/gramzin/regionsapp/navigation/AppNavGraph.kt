package com.gramzin.regionsapp.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    mainScreen: @Composable () -> Unit,
    regionDetails: @Composable () -> Unit
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = Screen.Main.route
    ) {
        composable(
            route = Screen.Main.route,
            enterTransition = {
                slideInHorizontally() + fadeIn()
            },
            exitTransition = {
                slideOutHorizontally() + fadeOut()
            }
        ){
            mainScreen()
        }
        composable(
            route = Screen.RegionDetails.route,
            enterTransition = {
                slideInHorizontally(initialOffsetX = { it/2 }) + fadeIn()
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { it }) + fadeOut()
            }
        ){
            regionDetails()
        }
    }
}