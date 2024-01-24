package com.cavss.mygrandmum

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cavss.mygrandmum.ui.custom.navi.NaviView
import com.cavss.mygrandmum.ui.custom.navi.Screen
import com.cavss.mygrandmum.ui.screen.callbook.CallBookModel
import com.cavss.mygrandmum.ui.screen.callbook.CallBookView
import com.cavss.mygrandmum.ui.screen.cardgame.CardGameView
import com.cavss.mygrandmum.ui.screen.map.MapView
import com.cavss.mygrandmum.ui.theme.MygrandmumTheme

class MainActivity : ComponentActivity() {
    val callBookList = mutableListOf<CallBookModel>(
        CallBookModel(id = 0, name = "이주연", relation = "손자", imagePath = ""),
        CallBookModel(id = 1, name = "장태옥", relation = "딸", imagePath = ""),
        CallBookModel(id = 2, name = "장리디아", relation = "손녀", imagePath = ""),
        CallBookModel(id = 3, name = "장경수", relation = "손자", imagePath = ""),
        CallBookModel(id = 4, name = "이우연", relation = "손자", imagePath = ""),
        CallBookModel(id = 5, name = "이찬연", relation = "손자", imagePath = ""),
        CallBookModel(id = 6, name = "이용빈", relation = "사위", imagePath = ""),
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController: NavHostController = rememberNavController()
            val screens = hashMapOf<Screen, @Composable () -> Unit>(
                Screen.CallBook to { CallBookView(callBookList = callBookList) },
                Screen.Map to { MapView(navController) },
                Screen.CardGame to { CardGameView(navController) }
            )
            MygrandmumTheme {
                NaviView(
                    barAnimation = false,
                    navController = navController,
                    screens = screens
                )
            }
        }
    }
}









