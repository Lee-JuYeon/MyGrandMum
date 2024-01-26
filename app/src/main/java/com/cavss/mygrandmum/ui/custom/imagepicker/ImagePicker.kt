package com.cavss.mygrandmum.ui.custom.imagepicker

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi

fun Modifier.multiplePhotoPickingFromGallery(
    photoURIs : (List<Uri>) -> Unit
): Modifier = composed {
    val context = LocalContext.current

    var uris by remember {
        mutableStateOf<List<Uri>>(emptyList())
    }

    /** 여러 개의 사진을 선택하는 활동을 시작하는 데 사용되는 코드 런처. 한마디로 앨범에서 사진 선택하는 코드. **/
    val launcherMultiplePhotosPicking = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = {
            try{
                uris = it
                photoURIs(uris)
            }catch (e:Exception){
                Log.e("mException", "ImagePicker, multiplePhotoPickingFromGallery, launcherMultiplePhotosPicking // Exception : ${e.localizedMessage}")
            }
        }
    )

    /** 요청할 권한 **/
    val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    /** 다중 권한을 요청하는 데 사용되는 런처 **/
    val launcherRequestMultiplePermissions = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap -> // 권한요청결과
        val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
        if (areGranted) {
            try{
                /** 권한 요청시 동의 했을 경우 **/
                launcherMultiplePhotosPicking.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                ).apply {
                    Toast.makeText(context, "권한이 승낙되어 앨범에 접근합니다.", Toast.LENGTH_SHORT)
                }
            }catch (e:Exception){
                Log.e("mException", "ImagePicker, multiplePhotoPickingFromGallery, launcherRequestMultiplePermissions, 권한 요청시 동의 // Exception : ${e.localizedMessage}")
            }
        }
        else {
            try{
                /** 권한 요청시 거부 했을 경우 **/
                Toast.makeText(context, "권한이 거부되어 앨범에 접근 불가합니다.", Toast.LENGTH_SHORT)
            }catch (e:Exception){
                Log.e("mException", "ImagePicker, multiplePhotoPickingFromGallery, launcherRequestMultiplePermissions, 권한 요청시 거부 // Exception : ${e.localizedMessage}")
            }
        }
    }

    this
        .clickable {
            if (permissions.all { /** 권한이 이미 있는 경우 **/
                    ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
                }) {
                try{
                    // 앨범에서 사진선택
                    launcherMultiplePhotosPicking.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    ).apply {
                        Toast.makeText(context, "권한이 승낙되어 앨범에 접근합니다", Toast.LENGTH_SHORT)
                    }
                }catch (e:Exception){
                    Log.e("mException", "ImagePicker, multiplePhotoPickingFromGallery, clickable, 앨범에서 사진선택 // Exception : ${e.localizedMessage}")
                }
            }else { /** 권한이 없는 경우 **/
                try{
                    // 권한 재요청
                    launcherRequestMultiplePermissions.launch(permissions).apply {
                        Toast.makeText(context, "권한이 없어, 권한을 재요청합니다.", Toast.LENGTH_SHORT)
                    }
                }catch (e:Exception){
                    Log.e("mException", "ImagePicker, multiplePhotoPickingFromGallery, clickable, 권한 재요청 // Exception : ${e.localizedMessage}")
                }
            }
        }
}

@OptIn(ExperimentalPermissionsApi::class)
fun Modifier.singlePhotoPickingFromGallery(
    photoURI : (Uri?) -> Unit
): Modifier = composed {

    val context = LocalContext.current

    var uri by remember {
        mutableStateOf<Uri?>(null)
    }

    /** 사진을 선택하는 활동을 시작하는 데 사용되는 코드 런처. 한마디로 앨범에서 사진 선택하는 코드. **/
    val singlePhotoPickingLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            try{
                uri = it
                photoURI(uri)
            }catch (e:Exception){
                Log.e("mException", "ImagePicker, singlePhotoPickingFromGallery, singlePhotoPickingLauncher // Exception : ${e.localizedMessage}")
            }
        }
    )

    /** 요청할 권한 **/
    val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    /** 다중 권한을 요청하는 데 사용되는 런처 **/
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap -> // 권한요청결과
        val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
        if (areGranted) {
            try{
                /** 권한 요청시 동의 했을 경우 **/
                singlePhotoPickingLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
                Toast.makeText(context, "권한이 승낙되었습니다.", Toast.LENGTH_SHORT)
            }catch (e:Exception){
                Log.e("mException", "ImagePicker, singlePhotoPickingFromGallery, requestPermissionLauncher, 권한 요청시 동의 // Exception : ${e.localizedMessage}")
            }
        }
        else {
            try{
                /** 권한 요청시 거부 했을 경우 **/
                Toast.makeText(context, "권한이 거부었습니다.", Toast.LENGTH_SHORT)
            }catch (e:Exception){
                Log.e("mException", "ImagePicker, singlePhotoPickingFromGallery, requestPermissionLauncher, 권한 요청시 거부 // Exception : ${e.localizedMessage}")
            }
        }
    }



    this
        .clickable {
            if (permissions.all { /** 권한이 이미 있는 경우 **/
                    ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
                }) {
                try{
                    // 앨범에서 사진선택
                    singlePhotoPickingLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    ).apply {
                        Toast.makeText(context, "권한이 승낙되어 앨범에 접근합니다", Toast.LENGTH_SHORT)
                    }
                }catch (e:Exception){
                    Log.e("mException", "ImagePicker, singlePhotoPickingFromGallery, clickable, 앨범에서 사진선택 // Exception : ${e.localizedMessage}")
                }
            }else { /** 권한이 없는 경우 **/
                try{
                    // 권한 재요청
                    requestPermissionLauncher.launch(permissions).apply {
                        Toast.makeText(context, "권한이 없어, 앨범에 접근이 불가합니다.", Toast.LENGTH_SHORT)
                    }
                }catch (e:Exception){
                    Log.e("mException", "ImagePicker, singlePhotoPickingFromGallery, clickable, 권한 재요청 // Exception : ${e.localizedMessage}")
                }
            }
        }
}
