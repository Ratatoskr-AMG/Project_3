package net.doheco.presentation.screens.profile.views

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import net.doheco.R
import net.doheco.presentation.screens.profile.ProfileViewModel
import net.doheco.presentation.screens.profile.models.ProfileState

@ExperimentalFoundationApi
@Composable
fun UndefinedProfileView(
    state: ProfileState.UndefinedState,
    viewModel: ProfileViewModel,
    navController: NavController,
    player_tier: String,
    dialogState: MutableState<Boolean>,
    onReloadClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        ProfileHeaderView(
            navController,
            state,
            viewModel,
            player_tier,
            onReloadClick,
            dialogState,
            false,
        ) {}

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(start = 50.dp, end = 50.dp),
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.login_offer),
                color = Color.White, fontWeight = FontWeight.Medium, fontSize = 14.sp
            )
        }
    }
}
