package com.cavss.mygrandmum.ui.screen.watcher

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.cavss.mygrandmum.R
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.util.concurrent.Executors


@androidx.annotation.OptIn(ExperimentalGetImage::class)
@Composable
fun WatcherView() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // 런처 초기화
    val isGranted = remember { mutableStateOf(false) }
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { permissionGranted ->
        // 카메라 권한 결과 처리
        isGranted.value = permissionGranted
    }

    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
        // 이미 권한이 있어 카메라 오픈
        isGranted.value = true
    } else {
        // 카메라 권한 요청
        LaunchedEffect(requestPermissionLauncher) {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    val previewView: PreviewView = remember { PreviewView(context) }
    val cameraController = remember { LifecycleCameraController(context) }
    cameraController.bindToLifecycle(lifecycleOwner)
    cameraController.cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    previewView.controller = cameraController

    val executor = remember { Executors.newSingleThreadExecutor() }
    val textRecognizer =
        remember { TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS) }

    var text by rememberSaveable { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AndroidView(
            factory = { previewView },
            modifier = Modifier.fillMaxSize()
        )

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.Center)
            )
        } else {
            IconButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                onClick = {
                    isLoading = true
                    cameraController.setImageAnalysisAnalyzer(executor) { imageProxy ->
                        imageProxy.image?.let { image ->
                            val img = InputImage.fromMediaImage(
                                image,
                                imageProxy.imageInfo.rotationDegrees
                            )

                            textRecognizer.process(img).addOnCompleteListener { task ->
                                isLoading = false
                                text =
                                    if (!task.isSuccessful) task.exception!!.localizedMessage.toString()
                                    else task.result.text

                                cameraController.clearImageAnalysisAnalyzer()
                                imageProxy.close()
                            }
                        }
                    }
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.image_gm_v),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(54.dp)
                )
            }
        }
    }

    if (text.isNotEmpty()) {
        Dialog(onDismissRequest = { text = "" }) {
            Card(modifier = Modifier.fillMaxWidth(0.8f)) {
                Text(
                    text = text,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp),
                    style = MaterialTheme.typography.bodySmall
                )
                Button(
                    onClick = { text = "" },
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                ) {
                    Text(text = "Done")
                }
            }
        }
    }
}