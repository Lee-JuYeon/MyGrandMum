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
import com.cavss.mygrandmum.util.typeConverter.TypeConverter
import com.cavss.mygrandmum.util.typeConverter.TypeConverter.toCallBookModel

class MainActivity : ComponentActivity() {
    val callBookList = mutableListOf<CallBookModel>(
        CallBookModel(name = "장태옥", relation = "딸", imagePath = "drawable/photo_taeok", digit = "01073723616"),
        CallBookModel(name = "장리디아", relation = "손녀", imagePath = "drawable/photo_lydia", digit = "01075203616"),
        CallBookModel(name = "이주연", relation = "손자", imagePath = "drawable/photo_juyeon", digit = "01073077896"),
        CallBookModel(name = "이우연", relation = "손자", imagePath = "drawable/photo_yooyeon", digit = "01055123616"),
        CallBookModel(name = "이찬연", relation = "손자", imagePath = "drawable/photo_chanyeon", digit = "01055323616"),
        CallBookModel(name = "장경수", relation = "손자", imagePath = "drawable/photo_kyoungsu", digit = "01085091418"),
        CallBookModel(name = "이용빈", relation = "사위", imagePath = "drawable/photo_youngbin", digit = "01047663616")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        App.INSTANCE

        super.onCreate(savedInstanceState)
        setContent {
            val navController: NavHostController = rememberNavController()
            val screens = hashMapOf<Screen, @Composable () -> Unit>(
                Screen.CallBook to { CallBookView(callBookList = getCallBooks()) },
                Screen.Map to { MapView(navController) },
                Screen.CardGame to { CardGameView() }
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

    private fun getCallBooks() : List<CallBookModel>{
        val mList : MutableList<CallBookModel> = mutableListOf()
        App.SHARED_PREFERENCE.getAllData().keys // name
        App.SHARED_PREFERENCE.getAllData().let {
            callBookList.forEach { model : CallBookModel ->
                mList.add(model)
            }
            it.values.map {  data : Any? -> //data는 callbook model의 json형태.

                val model = data.toString()
                Log.e("mException", "db 데이버 뽑기 : ${model}")

                val parsedModel = model.toCallBookModel()
                mList.add(parsedModel)
                Log.e("mException", "db 파싱데이터 : ${parsedModel}")
            }
        }
        return mList
    }
}









