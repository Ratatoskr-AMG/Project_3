package ru.ratatoskr.project_3.presentation.screens.favorites.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import ru.ratatoskr.project_3.R
import ru.ratatoskr.project_3.presentation.theme.LoadingScreen

@ExperimentalFoundationApi
@Composable
fun LoadingFavoritesView(
    navController: NavController,
    title: String
) {
    LoadingScreen("$title is loading", navController, R.drawable.ic_back, title)
}