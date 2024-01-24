package com.cavss.mygrandmum.ui.custom.outlinetext

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.Stroke.Companion.DefaultCap
import androidx.compose.ui.graphics.drawscope.Stroke.Companion.DefaultJoin
import androidx.compose.ui.graphics.drawscope.Stroke.Companion.DefaultMiter
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun OutlineText(
    text : String,
    textFontSize : Int,
    textAlign : TextAlign,
    modifier : Modifier
) {

    val textPaintStroke = Paint().asFrameworkPaint().apply {
        isAntiAlias = true
        style = android.graphics.Paint.Style.STROKE
        textSize = textFontSize.toFloat()
        color = android.graphics.Color.WHITE
        strokeWidth = 5f
        strokeMiter = 0f
        strokeJoin = android.graphics.Paint.Join.ROUND
    }

    val textPaint = Paint().asFrameworkPaint().apply {
        isAntiAlias = true
        style = android.graphics.Paint.Style.FILL
        textSize = textFontSize.toFloat()
        color = android.graphics.Color.BLACK
    }

    Canvas(
        modifier = modifier,
        onDraw = {
            drawIntoCanvas {
                it.nativeCanvas.drawText(
                    text,
                    0f,
                    0f,
                    textPaintStroke
                )

                it.nativeCanvas.drawText(
                    text,
                    0f,
                    0f,
                    textPaint
                )
            }
        }
    )
}