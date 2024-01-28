package com.cavss.mygrandmum.ui.screen.cardgame

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.cavss.mygrandmum.R
import com.cavss.mygrandmum.ui.custom.navi.NaviBar
import com.cavss.mygrandmum.ui.custom.navi.NaviScreen
import com.cavss.mygrandmum.ui.custom.navi.Screen
import com.cavss.mygrandmum.ui.custom.navi.bottomBarAnimatedScroll
import com.cavss.mygrandmum.ui.screen.callbook.CallBookItem
import com.cavss.mygrandmum.ui.screen.callbook.CallBookItemAdd
import kotlin.math.roundToInt

@Composable
fun CardGameView() {

    val cards : List<CardGameModel> = listOf(
        CardGameModel("banana",R.drawable.image_banana),
        CardGameModel("berry",R.drawable.image_berry),
        CardGameModel("bird",R.drawable.image_bird),
        CardGameModel("cat",R.drawable.image_cat),
        CardGameModel("dog",R.drawable.image_dog),
        CardGameModel("flower",R.drawable.image_flower),

        CardGameModel("banana",R.drawable.image_banana),
        CardGameModel("berry",R.drawable.image_berry),
        CardGameModel("bird",R.drawable.image_bird),
        CardGameModel("cat",R.drawable.image_cat),
        CardGameModel("dog",R.drawable.image_dog),
        CardGameModel("flower",R.drawable.image_flower),

        CardGameModel("banana",R.drawable.image_banana),
        CardGameModel("berry",R.drawable.image_berry),
        CardGameModel("bird",R.drawable.image_bird),
        CardGameModel("cat",R.drawable.image_cat),
        CardGameModel("dog",R.drawable.image_dog),
        CardGameModel("flower",R.drawable.image_flower),

        CardGameModel("banana",R.drawable.image_banana),
        CardGameModel("berry",R.drawable.image_berry),
        CardGameModel("bird",R.drawable.image_bird),
        CardGameModel("cat",R.drawable.image_cat),
        CardGameModel("dog",R.drawable.image_dog),
        CardGameModel("flower",R.drawable.image_flower)
    )
    val randomCards = cards.shuffled()

    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Transparent),
        content = {
            items(randomCards.size) { index ->
                CardGameItem(randomCards[index])
            }
        }
    )
}

data class CardGameModel(
    val id : String,
    val image : Int
)

@Composable
fun CardGameItem(model : CardGameModel){

    var isOpen by remember { mutableStateOf(false) }


    val customRotateY = animateFloatAsState(
        targetValue = if (isOpen) 180f else 360f,
        animationSpec = tween(
            durationMillis = 1000
        ),
        label = ""
    )

    Box(
        modifier = Modifier
            .height(100.dp)
            .padding(2.dp)
            .graphicsLayer {
                transformOrigin = TransformOrigin(0.5f, 0.0f)
                rotationY = customRotateY.value
            }
            .clickable {
                isOpen = !isOpen
            },
    ) {
        if (isOpen){
            Image(
                painter = painterResource(id = model.image),
                contentScale = ContentScale.FillBounds,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .border(2.dp, Color.DarkGray, RoundedCornerShape(10.dp))
                    .background(Color.White, RoundedCornerShape(10.dp))
            )
        }else{
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .border(2.dp, Color.DarkGray, RoundedCornerShape(10.dp))
                    .background(Color.Black, RoundedCornerShape(10.dp))
            ){

            }
        }
    }
}
