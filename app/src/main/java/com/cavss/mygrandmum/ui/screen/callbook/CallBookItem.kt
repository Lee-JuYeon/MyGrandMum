package com.cavss.mygrandmum.ui.screen.callbook

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.WindowMetrics
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.cavss.mygrandmum.R
import com.cavss.mygrandmum.ui.custom.imagepicker.singlePhotoPickingFromGallery
import com.cavss.mygrandmum.ui.custom.outlinetext.OutlineText
import com.cavss.mygrandmum.ui.custom.permission.phoneCall
import com.cavss.mygrandmum.util.dialConverter.DialConverter
import com.cavss.mygrandmum.util.dialConverter.DialConverter.toPhoneNumber

@Composable
fun CallBookItem (model : CallBookModel){
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .requiredHeight(200.dp)
            .background(
                Color.Transparent,
                RoundedCornerShape(CornerSize(10.dp))
            )
            .phoneCall(model.digit.toPhoneNumber())
    ) {
        Image(
            painter = rememberAsyncImagePainter(model.imagePath),
            contentDescription = "이미지",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize()
        )


        OutlineText(
            text = model.relation,
            textFontSize = 50,
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .align(Alignment.TopEnd)
        )

        OutlineText(
            text = model.name,
            textFontSize = 100,
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .align(Alignment.BottomEnd)
        )
    }
}