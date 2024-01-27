package com.cavss.mygrandmum.ui.custom.permission

import android.content.Context
import androidx.core.app.ActivityCompat.requestPermissions
import android.Manifest
import android.app.Activity
import android.content.Intent
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
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.cavss.mygrandmum.util.dialConverter.DialConverter.toPhoneNumber
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@OptIn(ExperimentalFoundationApi::class)
fun Modifier.phoneCall(
    digit : String
): Modifier = composed {

    val context = LocalContext.current

    try {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse(digit)

        // 전화를 걸기 위해 `intent` 객체를 시작합니다.
        ContextCompat.startActivity(context, intent, null)
    } catch (e: Exception) {
        Log.e(
            "mException",
            "PermissionManager, phoneCall // Exception : ${e.localizedMessage}"
        )
    }

    /** 요청할 권한 **/
    val permissions = arrayOf(
        Manifest.permission.CALL_PHONE
    )

    /** 다중 권한을 요청하는 데 사용되는 런처 **/
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap -> // 권한요청결과
        val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
        if (areGranted) {
            try{
                /** 권한 요청시 동의 했을 경우 **/
                val intent = Intent(Intent.ACTION_CALL)
                intent.data = Uri.parse(digit)

                // 전화를 걸기 위해 `intent` 객체를 시작합니다.
                ContextCompat.startActivity(context, intent, null)
            }catch (e:Exception){
                Log.e("mException", "PermissionManager, phoneCall, requestPermissionLauncher, 권한 요청시 동의 // Exception : ${e.localizedMessage}")
            }
        }
        else {
            try{
                /** 권한 요청시 거부 했을 경우 **/
                Toast.makeText(context, "권한이 거부었습니다.", Toast.LENGTH_SHORT)
            }catch (e:Exception){
                Log.e("mException", "PermissionManager, phoneCall, requestPermissionLauncher, 권한 요청시 거부 // Exception : ${e.localizedMessage}")
            }
        }
    }



    this
        .combinedClickable(
            onClick = {},
            onLongClick = {
                if (permissions.all { /** 권한이 이미 있는 경우 **/
                        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
                    }) {
                    try{
                        val intent = Intent(Intent.ACTION_CALL)
                        intent.data = Uri.parse(digit)

                        // 전화를 걸기 위해 `intent` 객체를 시작합니다.
                        ContextCompat.startActivity(context, intent, null)
                    }catch (e:Exception){
                        Log.e("mException", "PermissionManager, phoneCall, clickable, 앨범에서 사진선택 // Exception : ${e.localizedMessage}")
                    }
                }else { /** 권한이 없는 경우 **/
                    try{
                        // 권한 재요청
                        requestPermissionLauncher.launch(permissions).apply {
                            Toast.makeText(context, "권한이 없어, 전화가 불가합니다.", Toast.LENGTH_SHORT)
                        }
                    }catch (e:Exception){
                        Log.e("mException", "PermissionManager, phoneCall, clickable, 권한 재요청 // Exception : ${e.localizedMessage}")
                    }
                }
            }
        )
}
