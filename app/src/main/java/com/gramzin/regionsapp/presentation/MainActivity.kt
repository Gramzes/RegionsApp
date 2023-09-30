package com.gramzin.regionsapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.gramzin.regionsapp.navigation.AppNavGraph
import com.gramzin.regionsapp.navigation.Screen
import com.gramzin.regionsapp.presentation.screens.details_screen.DetailsScreen
import com.gramzin.regionsapp.presentation.screens.details_screen.DetailsViewModel
import com.gramzin.regionsapp.presentation.screens.main_screen.MainScreen
import com.gramzin.regionsapp.presentation.screens.main_screen.MainViewModel
import com.gramzin.regionsapp.presentation.theme.RegionsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegionsAppTheme {
                val navController = rememberNavController()
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background)
                ) {
                    AppNavGraph(
                        navHostController = navController,
                        mainScreen = {
                            val viewModel = hiltViewModel<MainViewModel>()
                            MainScreen(
                                viewModel = viewModel
                            ) {
                                navController.navigate(
                                    route = Screen.RegionDetails.getRouteWithParam(it)
                                )
                            }
                        },
                        regionDetails = {
                            val viewModel = hiltViewModel<DetailsViewModel>()
                            DetailsScreen(
                                viewModel = viewModel
                            ) {
                                navController.popBackStack()
                            }
                        }
                    )
                }
            }
        }
    }
}
