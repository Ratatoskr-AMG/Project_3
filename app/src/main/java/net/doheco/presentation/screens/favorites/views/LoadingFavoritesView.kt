package net.doheco.presentation.screens.favorites.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import net.doheco.R
import net.doheco.presentation.theme.LoadingScreen

@ExperimentalFoundationApi
@Composable
fun LoadingFavoritesView(
    navController: NavController,
    title: String
) {
    LoadingScreen(stringResource(id = R.string.loading), navController, R.drawable.ic_back, title)
}