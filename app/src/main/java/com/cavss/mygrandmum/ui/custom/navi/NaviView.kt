package com.cavss.mygrandmum.ui.custom.navi

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NaviView(
    barAnimation : Boolean = false,
    navController : NavHostController,
    screens : Map<Screen, @Composable () -> Unit>
) {
    // A surface container using the 'background' color from the theme
    val bottomBarHeight = 56.dp
    val bottomBarOffsetHeightPx = remember { mutableStateOf(0f) }

    var buttonsVisible = remember { mutableStateOf(true) }

    // Listen to the scroll state to update the visibility of buttons
    LaunchedEffect(bottomBarOffsetHeightPx.value) {
        buttonsVisible.value = bottomBarOffsetHeightPx.value >= -5
    }

    Scaffold(
        modifier = Modifier
            .let {
                if (barAnimation) it.bottomBarAnimatedScroll(
                    height = bottomBarHeight,
                    offsetHeightPx = bottomBarOffsetHeightPx
                ) else it
            },
        bottomBar = {
            NaviBar(
                navController = navController,
                state = buttonsVisible,
                modifier = Modifier
                    .height(bottomBarHeight)
                    .offset {
                        IntOffset(
                            x = 0, y = -bottomBarOffsetHeightPx.value.roundToInt()
                        )
                    },
                bottomNavList = screens.keys.toList()
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            NaviScreen(navController = navController, screens = screens)
        }
    }
}