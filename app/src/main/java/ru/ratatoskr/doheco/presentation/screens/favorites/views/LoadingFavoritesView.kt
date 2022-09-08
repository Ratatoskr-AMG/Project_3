package ru.ratatoskr.doheco.presentation.screens.favorites.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import ru.ratatoskr.doheco.R
import ru.ratatoskr.doheco.presentation.theme.LoadingScreen

@ExperimentalFoundationApi
@Composable
fun LoadingFavoritesView(
    navController: NavController,
    title: String
) {
    LoadingScreen(stringResource(id = R.string.loading), navController, R.drawable.ic_back, title)
}