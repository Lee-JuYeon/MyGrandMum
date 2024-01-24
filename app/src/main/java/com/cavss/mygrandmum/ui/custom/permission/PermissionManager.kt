package com.cavss.mygrandmum.ui.custom.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.shouldShowRationale

enum class Permissions(val permission : String) {
    READ_PHOTO(Manifest.permission.READ_EXTERNAL_STORAGE),
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionManager(permissions : List<String>) {
    val multiplePermissionsState = rememberMultiplePermissionsState(
        permissions = permissions
    )

    multiplePermissionsState.launchMultiplePermissionRequest()


    multiplePermissionsState.permissions.forEach {
        when(it.permission){
            Manifest.permission.CAMERA -> {
                if (it.status.isGranted){
                    // 권한승낙
                }else{
                    var text = if(it.status.shouldShowRationale){
                        // 권한 요청
                    }else{
                        // plz provide the 카메라 권한
                    }
                }
            }
        }
    }
}
//
//fun checkAndRequestPermissions(
//    context: Context,
//    permissions: Array<String>,
//    launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>,
//) {
//    /** 권한이 이미 있는 경우 **/
//    if (permissions.all {
//            ContextCompat.checkSelfPermission(
//                context,
//                it
//            ) == PackageManager.PERMISSION_GRANTED
//        }) {
//        Log.d("test5", "권한이 이미 존재합니다.")
//    }
//
//    /** 권한이 없는 경우 **/
//    else {
//        launcher.launch(permissions)
//        Log.d("test5", "권한을 요청하였습니다.")
//    }
//}
//
//@Composable
//fun test(){
//    /** 요청할 권한 **/
//    val permissions = arrayOf(
//        Manifest.permission.ACCESS_COARSE_LOCATION,
//        Manifest.permission.ACCESS_FINE_LOCATION,
//        Manifest.permission.READ_EXTERNAL_STORAGE,
//        Manifest.permission.WRITE_EXTERNAL_STORAGE
//    )
//
//    if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.TIRAMISU) {
//        checkPermission(Manifest.permission.READ_MEDIA_IMAGES)
//    } else{
//        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//    }
//
//
//    val launcherMultiplePermissions = rememberLauncherForActivityResult(
//        ActivityResultContracts.RequestMultiplePermissions()
//    ) { permissionsMap ->
//        val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
//        /** 권한 요청시 동의 했을 경우 **/
//        if (areGranted) {
//            Log.d("test5", "권한이 동의되었습니다.")
//        }
//        /** 권한 요청시 거부 했을 경우 **/
//        else {
//            Log.d("test5", "권한이 거부되었습니다.")
//        }
//    }
//
//
//
//    Button(onClick = {
//        checkAndRequestPermissions(
//            context,
//            permissions,
//            launcherMultiplePermissions
//        )
//    }) {
//        Text(text = "권한 요청하기")
//    }
//}
//
//@OptIn(ExperimentalPermissionsApi::class)
//@Composable
//private fun requestPermission() {
//
//    private val requestPermissionLauncher =
//        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
//            val allGranted = permissions.values.all { it }
//            if (allGranted) { // 모든 권한이 승인되었을 때
//                Timber.tag("MainActivity").d("권한 승인")
//            } else {
//                Timber.tag("MainActivity").d("일부 권한이 거부되었습니다.")
//                // 거부된 권한에 대한 처리
//            }
//        }
//
//
//
//    val cameraPermissionState = rememberPermissionState(
//        android.Manifest.permission.CAMERA
//    )
//
//    if (cameraPermissionState.status.isGranted) {
//        Text("카메라 권한 획득")
//    } else {
//        Column {
//            val textToShow = if (cameraPermissionState.status.shouldShowRationale) { //유저가 권한 요청을 거절 했을 때 호출
//                "카메라 권한은 필수 입니다. 카메라 권한을 획득해 주세요"
//            } else {
//                "카메라 권한이 필요 합니다."
//            }
//            Text(textToShow)
//            Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
//                Text("권한 요청")
//            }
//        }
//    }
//}
//
//@Composable
//fun PermissionManager() {
//    val permission = rememberPermission(
//        Manifest.permission.READ_EXTERNAL_STORAGE,
//        Manifest.permission.WRITE_EXTERNAL_STORAGE
//    )
//
//    // 권한 요청 상태 처리
//    when (permission.state) {
//        PermissionState.Granted -> {
//            // 권한이 승인된 경우
//        }
//        PermissionState.Denied -> {
//            // 권한이 거부된 경우
//        }
//        PermissionState.Prompted -> {
//            // 권한 요청 대화 상자가 표시된 경우
//        }
//    }
//
//    // 권한 요청
//    val permissionState = rememberPermissionState(
//        permissions = arrayOf(
//            Manifest.permission.READ_EXTERNAL_STORAGE,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE
//        )
//    )
//
//    // 권한 요청 결과 처리
//    permissionState.onPermissionResult {
//        if (it.allGranted) {
//            // 권한이 모두 승인된 경우
//        } else {
//            // 권한이 거부된 경우
//        }
//    }
//}