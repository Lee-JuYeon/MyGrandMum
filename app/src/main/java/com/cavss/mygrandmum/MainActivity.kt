package com.cavss.mygrandmum

import android.os.Bundle
import android.util.Log
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
import com.cavss.mygrandmum.util.secure.AESHelper

class MainActivity : ComponentActivity() {
    val callBookList = mutableListOf<CallBookModel>(
        CallBookModel(id = 1, name = "장태옥", relation = "딸", imagePath = "drawable/photo_taeok", digit = "01073723616"),
        CallBookModel(id = 3, name = "장리디아", relation = "손녀", imagePath = "drawable/photo_lydia", digit = "01075203616"),
        CallBookModel(id = 4, name = "이주연", relation = "손자", imagePath = "drawable/photo_juyeon", digit = "01073077896"),
        CallBookModel(id = 5, name = "이우연", relation = "손자", imagePath = "drawable/photo_yooyeon", digit = "01055123616"),
        CallBookModel(id = 6, name = "이찬연", relation = "손자", imagePath = "drawable/photo_chanyeon", digit = "01055323616"),
        CallBookModel(id = 2, name = "장경수", relation = "손자", imagePath = "drawable/photo_kyoungsu", digit = "01085091418"),
        CallBookModel(id = 7, name = "이용빈", relation = "사위", imagePath = "drawable/photo_youngbin", digit = "01047663616")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        App.INSTANCE
        val encryptText = AESHelper.encrypt("암호화 할 텍스트".toByteArray(Charsets.UTF_8))
        val decryptText = String(AESHelper.decrypt(encryptText)!!, Charsets.UTF_8)
        Log.e("mDebug", "암호화 된 텍스트 : ${encryptText}")
        Log.e("mDebug", "복호화 된 텍스트 : ${decryptText}")

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









