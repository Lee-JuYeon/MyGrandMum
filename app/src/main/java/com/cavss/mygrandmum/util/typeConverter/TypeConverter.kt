package com.cavss.mygrandmum.util.typeConverter

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import com.cavss.mygrandmum.ui.screen.callbook.CallBookModel

@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }
object TypeConverter {
    fun String.toCallBookModel(): CallBookModel {
        val parts = this
            .replace("{", "")
            .replace("}", "")
            .split(",")
            .map { it.split(":") }
            .map { it[0].trim() to it[1].trim() }

        val name = parts.find { it.first == "'name'" }?.second.orEmpty()
        val relation = parts.find { it.first == "'relation'" }?.second.orEmpty()
        val imagePath = parts.find { it.first == "'imagePath'" }?.second.orEmpty()
        val digit = parts.find { it.first == "'digit'" }?.second.orEmpty()

        return CallBookModel(name, relation, imagePath, digit)
    }
}