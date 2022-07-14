package ru.ratatoskr.project_3.presentation.screens.favorites.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ru.ratatoskr.project_3.R
import ru.ratatoskr.project_3.presentation.theme.leftBtnHeaderBox

@ExperimentalFoundationApi
@Composable
fun EmptyFavoritesListView(
    navController: NavController,
    title: String
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column {
            leftBtnHeaderBox(navController, R.drawable.ic_back, "Favorites")

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x55202020))
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center),
                    textAlign = TextAlign.Center,
                    text = title,
                    color = Color.White, fontWeight = FontWeight.Medium, fontSize = 14.sp
                )
            }
        }


    }
}