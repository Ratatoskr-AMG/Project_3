package ru.ratatoskr.project_3.presentation.screens

import android.content.SharedPreferences
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.navigationBarsPadding
import ru.ratatoskr.project_3.domain.helpers.Screens
import ru.ratatoskr.project_3.presentation.viewmodels.HeroViewModel
import ru.ratatoskr.project_3.presentation.viewmodels.HeroesListViewModel
import ru.ratatoskr.project_3.presentation.screens.account.profile.ProfileViewModel
import ru.ratatoskr.project_3.presentation.viewmodels.VideoViewModel

@ExperimentalFoundationApi
@Composable
fun WrapperScreen(
    appSharedPreferences:SharedPreferences
) {



}

//val videoViewState = videoViewModel.videoState.observeAsState()