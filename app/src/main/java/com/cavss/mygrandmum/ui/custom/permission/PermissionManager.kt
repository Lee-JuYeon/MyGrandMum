package com.cavss.mygrandmum.ui.custom.permission

import android.content.Context
import androidx.core.app.ActivityCompat.requestPermissions
import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi


class PermissionManager {

    @Composable
    fun requestPermissions(context : Context, permissions : Array<String>, onGranted : () -> Unit) {
        /** 다중 권한을 요청하는 데 사용되는 런처 **/
        val launcherRequestMultiplePermissions = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissionsMap -> // 권한요청결과
            val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
            if (areGranted) {
                try {
                    /** 권한 요청시 동의 했을 경우 **/
                    onGranted()
                } catch (e: Exception) {
                    Log.e(
                        "mException",
                        "PermissionManager, multiplePhotoPickingFromGallery, launcherRequestMultiplePermissions, 권한 요청시 동의 // Exception : ${e.localizedMessage}"
                    )
                }
            } else {
                try {
                    /** 권한 요청시 거부 했을 경우 **/
                    Toast.makeText(context, "권한이 거부되어 앨범에 접근 불가합니다.", Toast.LENGTH_SHORT)
                } catch (e: Exception) {
                    Log.e(
                        "mException",
                        "PermissionManager, multiplePhotoPickingFromGallery, launcherRequestMultiplePermissions, 권한 요청시 거부 // Exception : ${e.localizedMessage}"
                    )
                }
            }
        }
        launcherRequestMultiplePermissions.launch(permissions)
    }
}