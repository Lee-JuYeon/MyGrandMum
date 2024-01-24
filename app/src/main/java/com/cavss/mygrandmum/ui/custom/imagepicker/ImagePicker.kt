package com.cavss.mygrandmum.ui.custom.imagepicker

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter

fun Modifier.multiplePhotoPickingFromGallery(
    photoURIs : (List<Uri>) -> Unit
): Modifier = composed {

    var uris by remember {
        mutableStateOf<List<Uri>>(emptyList())
    }

    val multiplePhotosPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = {
            uris = it
            photoURIs(uris)
        }
    )

    this
        .clickable {
            multiplePhotosPicker.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }
}

fun Modifier.singlePhotoPickingFromGallery(
    photoURI : (Uri?) -> Unit
): Modifier = composed {

    var uri by remember {
        mutableStateOf<Uri?>(null)
    }

    val singlePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            uri = it
            photoURI(uri)
        }
    )

    this
        .clickable {
            singlePhotoPicker.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }
}

fun checkAndRequestPermissions(
    context: Context,
    permissions: Array<String>,
    launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>,
) {

    /** 권한이 이미 있는 경우 **/
    if (permissions.all {
            ContextCompat.checkSelfPermission(
                context,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }) {
        Log.d("test5", "권한이 이미 존재합니다.")
    }

    /** 권한이 없는 경우 **/
    else {
        launcher.launch(permissions)
        Log.d("test5", "권한을 요청하였습니다.")
    }
}

@Composable
fun permissions(){
    val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.LOCATION
    )
    if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.TIRAMISU) {
        checkPermission(Manifest.permission.READ_MEDIA_IMAGES)
    } else{
        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    val launcherMultiplePermissions = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
        /** 권한 요청시 동의 했을 경우 **/
        if (areGranted) {
            Log.d("test5", "권한이 동의되었습니다.")
        }
        /** 권한 요청시 거부 했을 경우 **/
        else {
            Log.d("test5", "권한이 거부되었습니다.")
        }
    }

    Button(onClick = {
        checkAndRequestPermissions(
            context,
            permissions,
            launcherMultiplePermissions
        )
    }) {
        Text(text = "권한 요청하기")
    }
}