package com.cavss.mygrandmum.ui.custom.navi

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlin.coroutines.coroutineContext

@Composable
fun NaviBar(
    navController: NavHostController,
    state: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    bottomNavList : List<Screen>
) {

    NavigationBar(
        modifier = modifier,
        containerColor = Color.Black,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        bottomNavList.forEach { screen ->
            NavigationBarItem(
                label = {
                    Text(stringResource(screen.title))
                },
                icon = {

                },
                selected = currentRoute == screen.type,
                onClick = {
                    navController.navigate(screen.type) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    unselectedTextColor = Color.Gray, selectedTextColor = Color.White
                ),
            )
        }
    }

}