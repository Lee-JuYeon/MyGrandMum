package com.cavss.mygrandmum

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.SurfaceTexture
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraControl
import androidx.camera.core.CameraSelector
import androidx.camera.core.CameraX
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cavss.mygrandmum.ui.custom.navi.NaviView
import com.cavss.mygrandmum.ui.custom.navi.Screen
import com.cavss.mygrandmum.ui.screen.callbook.CallBookModel
import com.cavss.mygrandmum.ui.screen.callbook.CallBookView
import com.cavss.mygrandmum.ui.screen.cardgame.CardGameView
import com.cavss.mygrandmum.ui.screen.watcher.WatcherView
import com.cavss.mygrandmum.ui.theme.MygrandmumTheme
import com.cavss.mygrandmum.util.typeConverter.TypeConverter.toCallBookModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        App.INSTANCE

        super.onCreate(savedInstanceState)
        setContent {
            val navController: NavHostController = rememberNavController()
            val screens = hashMapOf<Screen, @Composable () -> Unit>(
                Screen.CallBook to { CallBookView(callBookList = getCallBooks()) },
                Screen.Watcher to { WatcherView() },
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

    private val callBookList = mutableListOf<CallBookModel>(
        CallBookModel(name = "장태옥", relation = "딸", imagePath = "drawable/photo_taeok", digit = "01073723616"),
        CallBookModel(name = "장리디아", relation = "손녀", imagePath = "drawable/photo_lydia", digit = "01075203616"),
        CallBookModel(name = "이주연", relation = "손자", imagePath = "drawable/photo_juyeon", digit = "01073077896"),
        CallBookModel(name = "이우연", relation = "손자", imagePath = "drawable/photo_yooyeon", digit = "01055123616"),
        CallBookModel(name = "이찬연", relation = "손자", imagePath = "drawable/photo_chanyeon", digit = "01055323616"),
        CallBookModel(name = "장경수", relation = "손자", imagePath = "drawable/photo_kyoungsu", digit = "01085091418"),
        CallBookModel(name = "이용빈", relation = "사위", imagePath = "drawable/photo_youngbin", digit = "01047663616")
    )
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