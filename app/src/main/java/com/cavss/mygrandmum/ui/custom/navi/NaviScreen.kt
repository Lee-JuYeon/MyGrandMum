package com.cavss.mygrandmum.ui.custom.navi

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NaviScreen(navController : NavHostController, screens: Map<Screen, @Composable () -> Unit>) {

    NavHost(navController = navController, startDestination = screens.keys.first().type) {
        screens.forEach { (screen, view) ->
            composable(screen.type) { view() }
        }
//        composable(Screen.CallBookView.type) { CallBookView(navController) }
//        composable(Screen.MapView.type) { MapView(navController) }
//        composable(Screen.CardGameView.type) { CardGameView(navController) }
    }
}
