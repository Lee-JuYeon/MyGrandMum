package com.cavss.mygrandmum.ui.screen.callbook

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.cavss.mygrandmum.App
import com.cavss.mygrandmum.R
import com.cavss.mygrandmum.ui.custom.imagepicker.singlePhotoPickingFromGallery
import com.cavss.mygrandmum.util.typeConverter.pxToDp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CallBookItemAdd (){
    val context = LocalContext.current

    var photoUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    fun getnavigationBarHeight() : Int {
        val resourceId = context.resources.getIdentifier("navigation_bar_height", "dimen", "android")
        var navigationbarHeight = 0
        return if (resourceId > 0) {
            navigationbarHeight = context.resources.getDimensionPixelSize(resourceId)
            navigationbarHeight
        }else{
            0
        }
    }

    var nameState by remember { mutableStateOf("") }
    var digitState by remember { mutableStateOf("") }
    var relationState by remember { mutableStateOf("") }

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
            .clickable {
                showBottomSheet = !showBottomSheet
            }
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

        if (showBottomSheet){
            ModalBottomSheet(
                sheetState = sheetState,
                tonalElevation = 50.dp,
                windowInsets = WindowInsets(
                    left = 0.dp,
                    top = 0.dp,
                    right = 0.dp,
                    bottom = getnavigationBarHeight().pxToDp()
                ),
                onDismissRequest = {
                    showBottomSheet = !showBottomSheet
                    if (nameState != "" && relationState != "" && digitState != "" && photoUri.toString() != null){
                        val name = nameState
                        val relation = relationState
                        val digit = digitState
                        val callBookModel = CallBookModel(
                            name = name,
                            relation = relation,
                            imagePath = photoUri.toString(),
                            digit = digit
                        )
                        Log.e("mException", "모델 값 : ${callBookModel.toJSON()}")
                        App.SHARED_PREFERENCE.createAndUpdateData(name, callBookModel.toJSON())
                    }
                },
                modifier = Modifier
            ) {
                OutlinedTextField(
                    value = nameState,
                    onValueChange = { newText ->
                        nameState = newText },
                    singleLine = true,
                    label = {
                        Text("이름") },
                    visualTransformation = VisualTransformation.None,
                    textStyle = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .fillMaxWidth()
                )
                OutlinedTextField(
                    value = relationState,
                    onValueChange = { newText ->
                        relationState = newText },
                    singleLine = true,
                    label = {
                        Text("관계") },
                    visualTransformation = VisualTransformation.None,
                    textStyle = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .fillMaxWidth()
                    )
                OutlinedTextField(
                    value = digitState,
                    onValueChange = { newText ->
                        digitState = newText },
                    singleLine = true,
                    label = {
                        Text("전화번호") },
                    visualTransformation = VisualTransformation.None,
                    textStyle = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .fillMaxWidth()
                )
                if (photoUri == null){
                    Image(
                        painter = painterResource(id = R.drawable.image_gm_v),
                        contentDescription = "추가하기",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth()
                            .singlePhotoPickingFromGallery { selectedUri: Uri? ->
                                photoUri = selectedUri
                            },
                    )
                }else{
                    Image(
                        painter = rememberAsyncImagePainter(photoUri),
                        contentDescription = "이미지",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth()
                            .singlePhotoPickingFromGallery { selectedUri: Uri? ->
                                photoUri = selectedUri
                            },
                    )
                }
            }
        }
    }
}