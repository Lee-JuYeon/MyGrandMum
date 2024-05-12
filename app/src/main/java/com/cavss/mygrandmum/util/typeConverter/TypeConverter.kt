package com.cavss.mygrandmum.util.typeConverter

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import com.cavss.mygrandmum.ui.screen.callbook.CallBookModel
import com.google.gson.Gson

@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }
object TypeConverter {
    fun String.toCallBookModel(): CallBookModel {
        return Gson().fromJson(this, CallBookModel::class.java)
    }
}
