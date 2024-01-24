package com.cavss.mygrandmum.ui.screen.callbook

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.cavss.mygrandmum.R
import com.cavss.mygrandmum.ui.custom.imagepicker.singlePhotoPickingFromGallery

@Composable
fun CallBookItemAdd (){
    var photoUri by remember {
        mutableStateOf<Uri?>(null)
    }

    Box(
        modifier = Modifier
            .padding(10.dp)
            .border(
                width = 5.dp,
                color = Color.Yellow,
                shape = RoundedCornerShape(
                    CornerSize(10.dp)
                )
            )
            .fillMaxWidth()
            .requiredHeight(200.dp)
            .background(
                Color.White,
                RoundedCornerShape(CornerSize(10.dp))
            )
            .singlePhotoPickingFromGallery { selectedUri : Uri? ->
                photoUri = selectedUri
            },
    ) {
        Image(
            painter = painterResource(id = R.drawable.image_gm_v),
            contentDescription = "추가하기",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        )

        Text(
            text = "추가",
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            style = TextStyle(
                color = Color.Black
            ),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 50.sp,
        )

//        if (photoUri == null){
//            Image(
//                painter = painterResource(id = R.drawable.image_gm_v),
//                contentDescription = "추가하기",
//                contentScale = ContentScale.Fit,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .align(Alignment.Center)
//            )
//
//            Text(
//                text = "추가",
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .align(Alignment.BottomCenter),
//                style = TextStyle(
//                    color = Color.Black
//                ),
//                textAlign = TextAlign.Center,
//                fontWeight = FontWeight.Bold,
//                fontSize = 50.sp,
//            )
//        }else{
//            Image(
//                painter = rememberAsyncImagePainter(photoUri),
//                contentDescription = "이미지",
//                contentScale = ContentScale.Fit,
//                modifier = Modifier
//                    .fillMaxWidth()
//            )
//        }
    }
}