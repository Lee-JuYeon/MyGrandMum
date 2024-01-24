package com.cavss.mygrandmum.ui.custom.permission

import android.Manifest
import androidx.compose.runtime.Composable

enum class Permissions(val permission : String) {
    READ_PHOTO(Manifest.permission.READ_EXTERNAL_STORAGE),
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun requestPermission() {
    val cameraPermissionState = rememberPermissionState(한
        android.Manifest.permission.CAMERA
    )

    if (cameraPermissionState.status.isGranted) {
        Text("카메라 권한 획득")
    } else {
        Column {
            val textToShow = if (cameraPermissionState.status.shouldShowRationale) { //유저가 권한 요청을 거절 했을 때 호출
                "카메라 권한은 필수 입니다. 카메라 권한을 획득해 주세요"
            } else {
                "카메라 권한이 필요 합니다."
            }
            Text(textToShow)
            Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                Text("권한 요청")
            }
        }
    }
}

@Composable
fun PermissionManager() {
    val permission = rememberPermission(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    // 권한 요청 상태 처리
    when (permission.state) {
        PermissionState.Granted -> {
            // 권한이 승인된 경우
        }
        PermissionState.Denied -> {
            // 권한이 거부된 경우
        }
        PermissionState.Prompted -> {
            // 권한 요청 대화 상자가 표시된 경우
        }
    }

    // 권한 요청
    val permissionState = rememberPermissionState(
        permissions = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    )

    // 권한 요청 결과 처리
    permissionState.onPermissionResult {
        if (it.allGranted) {
            // 권한이 모두 승인된 경우
        } else {
            // 권한이 거부된 경우
        }
    }
}