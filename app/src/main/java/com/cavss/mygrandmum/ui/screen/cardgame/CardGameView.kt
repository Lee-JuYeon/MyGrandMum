package com.cavss.mygrandmum.ui.screen.cardgame

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.cavss.mygrandmum.R
import androidx.compose.runtime.*
import androidx.compose.ui.draw.alpha
import kotlinx.coroutines.*

@Composable
fun CardGameView() {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.image_cardgamebackground),
            contentDescription = "카드 보드 배경",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        CardBoard()
    }
}


@Composable
fun CardBoard(
) {
    val clickList by remember { mutableStateOf( mutableListOf<CardModel>() ) }
    val cardList = remember { mutableStateOf(shuffledCards()) }
    val coroutine = rememberCoroutineScope()

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxSize()
            .padding(3.dp),
        content = {
            items(cardList.value) { model ->
                val rotated = remember { mutableStateOf(false) }
                val rotation by animateFloatAsState(
                    targetValue = if (rotated.value) 180f else 0f,
                    animationSpec = tween(500),
                    label = ""
                )

                val frontImage = model.image
                val backImage = R.drawable.image_grandmother_basic
                val removedImage = R.drawable.ic_launcher_background
                val currentImage : Int = when (model.cardState.value) {
                    CardState.BACK -> backImage
                    CardState.FRONT -> frontImage
                    CardState.REMOVED -> removedImage
                }

                Image(
                    painter = painterResource(id = currentImage),
                    contentDescription = "추가하기",
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .alpha(when (model.cardState.value) {
                            CardState.REMOVED -> 0f // 투명하게
                            else -> 1f // 불투명하게
                        })
                        .requiredHeight(150.dp)
                        .clickable {
                            rotated.value = !rotated.value // 클릭할 때마다 rotated 값을 반전시킴
                            when (model.cardState.value) {
                                CardState.BACK -> {
                                    model.cardState.value = CardState.FRONT
                                }

                                CardState.FRONT -> {
                                    model.cardState.value = CardState.BACK
                                }

                                CardState.REMOVED -> {
                                    model.cardState.value = CardState.REMOVED
                                }
                            }

                            when (clickList.size) {
                                0 -> { // 첫클릭
                                    clickList.add(model)
                                }
                                1 -> { // 2번째 클릭
                                    try {
                                        clickList.add(model)
                                        if (clickList[0].image == clickList[1].image && clickList[0].uid != clickList[1].uid) {
                                            // 서로 동일한 카드를 클릭했을 경우
                                            cardList.value.forEach { it: CardModel ->
                                                if (it.image == clickList[0].image) {
                                                    it.cardState.value = CardState.REMOVED
                                                }
                                            }
                                        } else {
                                            // 서로 다른 카드를 클릭했을 경우
                                            coroutine.launch {
                                                delay(500) // 0.5초
                                                cardList.value.forEach {
                                                    if (it.cardState.value == CardState.FRONT){
                                                        it.cardState.value = CardState.BACK
                                                    }
                                                }
                                            }
                                        }
                                    } catch (e: Exception) {
                                        Log.e(
                                            "mException",
                                            "CardGame, CardBoard, click event // Exception : ${e.localizedMessage}"
                                        )
                                    } finally {
                                        clickList.clear()
                                    }
                                }

                                else -> { // 3번 이상 클릭
                                    clickList.clear() // claer하여 size를 0으로 만들어 다시 재귀함수처럼 바꿔놓음.
                                }
                            }
                        }
                        .graphicsLayer {
                            rotationY = rotation
                            cameraDistance = 8 * density
                        }
                        .padding(1.dp)
                        .border(
                            2.dp,
                            Color.Yellow,
                            RoundedCornerShape(10.dp)
                        )
                        .background(
                            Color.White,
                            RoundedCornerShape(10.dp)
                        )
                )
            }
        }
    )
}

enum class CardState {
    BACK,
    FRONT,
    REMOVED
}
data class CardModel (
    val image: Int,
    var cardState: MutableState<CardState>, // 변경된 부분
    val uid: String
)


fun shuffledCards(): List<CardModel> {
    val cardModels = listOf<CardModel>(
        CardModel(image = R.drawable.image_card_banana, cardState = mutableStateOf(CardState.BACK), uid = "1"),
        CardModel(image = R.drawable.image_card_berry, cardState = mutableStateOf(CardState.BACK), uid = "2"),
        CardModel(image = R.drawable.image_card_bird, cardState = mutableStateOf(CardState.BACK), uid = "3"),
        CardModel(image = R.drawable.image_card_cat, cardState = mutableStateOf(CardState.BACK), uid = "4"),
        CardModel(image = R.drawable.image_card_dog, cardState = mutableStateOf(CardState.BACK), uid = "5"),
        CardModel(image = R.drawable.image_card_flower, cardState = mutableStateOf(CardState.BACK), uid = "6"),
        CardModel(image = R.drawable.image_card_banana, cardState = mutableStateOf(CardState.BACK), uid = "7"),
        CardModel(image = R.drawable.image_card_berry, cardState = mutableStateOf(CardState.BACK), uid = "8"),
        CardModel(image = R.drawable.image_card_bird, cardState = mutableStateOf(CardState.BACK), uid = "9"),
        CardModel(image = R.drawable.image_card_cat, cardState = mutableStateOf(CardState.BACK), uid = "10"),
        CardModel(image = R.drawable.image_card_dog, cardState = mutableStateOf(CardState.BACK), uid = "11"),
        CardModel(image = R.drawable.image_card_flower, cardState = mutableStateOf(CardState.BACK), uid = "12"),
    )
    return cardModels.shuffled().shuffled()
}