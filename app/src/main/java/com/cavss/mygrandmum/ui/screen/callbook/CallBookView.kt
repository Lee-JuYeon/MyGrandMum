package com.cavss.mygrandmum.ui.screen.callbook

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun CallBookView(callBookList : List<CallBookModel>) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),

        // content padding
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        ),
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.DarkGray),
        content = {
            item {  // 헤더 아이템 추가
                CallBookItemAdd()  // 헤더 컴포저블 호출
            }
            items(callBookList.size) { index ->
                CallBookItem(callBookList[index])
            }
        }
    )
}

