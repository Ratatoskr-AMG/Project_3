package net.doheco.presentation.screens

import android.content.SharedPreferences
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import net.doheco.R
import net.doheco.presentation.screens.profile.models.ProfileEvent
import net.doheco.presentation.screens.profile.models.ProfileState
import net.doheco.presentation.screens.profile.ProfileViewModel
import net.doheco.presentation.screens.profile.views.DefinedBySteamProfileView
import net.doheco.presentation.screens.profile.views.ProfileView
import net.doheco.presentation.screens.profile.views.UndefinedProfileView
import java.text.SimpleDateFormat
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalFoundationApi
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel,
    appSharedPreferences: SharedPreferences
) {

    val openDialog = remember { mutableStateOf(false) }
    var titleText by remember { mutableStateOf("") }
    var messageText by remember { mutableStateOf("") }

    val test = remember {
        mutableStateOf("")
    }

    var openUpdateDialog = remember { mutableStateOf(false) }

    val context = LocalContext.current
    val playerTier = viewModel.getPlayerTierFromSP()
    val viewState = viewModel.profileState.observeAsState(ProfileState.Init())



    /*

    when (val state = viewState.value) {

        is ProfileState.UndefinedState -> {

            UndefinedProfileView(
                state,
                viewModel,
                navController,
                playerTier,
                dialogState = openDialog,
            ) {
                Toast.makeText(context, "Wait...", Toast.LENGTH_SHORT).show()
                var result = viewModel.obtainEvent(ProfileEvent.OnProfileUpdate)

                /*
                when  {
                    "Update data" in state.btnText -> {
                        Toast.makeText(context, state.btnText, Toast.LENGTH_SHORT).show()
                        viewModel.obtainEvent(ProfileEvent.OnUndefinedProfileUpdate)
                    }
                    "Обновление данных" in state.btnText -> {
                        Toast.makeText(context, state.btnText, Toast.LENGTH_SHORT).show()
                        viewModel.obtainEvent(ProfileEvent.OnUndefinedProfileUpdate)
                    }
                    else -> {
                        openUpdateDialog.value = true
                    }
                }
                */
            }

        }

        is ProfileState.SteamNameIsDefinedState -> {
            DefinedBySteamProfileView(
                state,
                viewModel,
                playerTier,
                navController,
                dialogState = openDialog,
                { viewModel.obtainEvent(ProfileEvent.OnSteamExit) },
                { viewModel.obtainEvent(ProfileEvent.OnUpdate) }
            )
        }
        else -> {}
    }

*/

    ProfileView(
        viewState.value,
        viewModel,
        navController,
        playerTier,
        dialogState = openDialog,
        onReloadClick = {
            viewModel.obtainEvent(ProfileEvent.OnUpdate)
            when (val state = viewState.value) {
                is ProfileState.SteamNameIsDefinedState -> {
                    Toast.makeText(context, state.btnText, Toast.LENGTH_SHORT).show()
                }
            }

        },
    )

    if (openDialog.value) {
        AlertDialog(
            modifier = Modifier.fillMaxWidth(),
            onDismissRequest = {
                openDialog.value = false
            },
            backgroundColor = Color(0xFF131313),
            shape = RoundedCornerShape(10.dp),
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(id = R.string.offer),
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp),
                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = titleText,
                        onValueChange = { titleText = it},
                        singleLine = true,
                        label = { Text(text = stringResource(id = R.string.send_name))},
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = Color.White,
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.White,
                            focusedLabelColor = Color.White,
                            unfocusedLabelColor = Color.White
                        )
                    )
                    OutlinedTextField(
                        value = messageText,
                        onValueChange = { messageText = it},
                        label = { Text(text = stringResource(id = R.string.message)) },
                        modifier = Modifier
                            .height(150.dp)
                            .fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = Color.White,
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.White,
                            focusedLabelColor = Color.White,
                            unfocusedLabelColor = Color.White
                        )
                    )
                }
            },
            buttons = {
                Column(
                    modifier = Modifier.padding(horizontal = 24.dp),
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            viewModel.obtainEvent(ProfileEvent.OnSendFeedback(titleText,messageText))
                            openDialog.value = false
                            Toast.makeText(context, "Спасибо за отзыв", Toast.LENGTH_SHORT).show()
                        },
                        enabled = titleText.isNotEmpty() && messageText.isNotEmpty(),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF00821d),
                            disabledBackgroundColor = Color(0xFF6a36a3),
                            contentColor = Color.White,
                            disabledContentColor = Color.White
                        )
                    ) {
                        Text(text = stringResource(id = R.string.send))
                    }
                    OutlinedButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 26.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.White,
                            backgroundColor = Color(0xFF131313)
                        ),
                        onClick = { openDialog.value = false }
                    ) {
                        Text(text = stringResource(id = R.string.close))
                    }

                }
            }
        )
    }

    if (openUpdateDialog.value) {
        AlertDialog(
            modifier = Modifier.fillMaxWidth(),
            onDismissRequest = {
                openUpdateDialog.value = false
            },
            backgroundColor = Color(0xFF131313),
            shape = RoundedCornerShape(10.dp),
            title = {
                Text(
                    text = "Ups",
                    color = Color.White,
                )
            },
            text = {
                Text(
                    text = "Text",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            buttons = {
                Column(
                    modifier = Modifier.padding(horizontal = 24.dp),
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            openUpdateDialog.value = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF00821d),
                            contentColor = Color.White,
                        )
                    ) {
                        Text(text = "Buy")
                    }
                    OutlinedButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 26.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.White,
                            backgroundColor = Color(0xFF131313)
                        ),
                        onClick = { openUpdateDialog.value = false }
                    ) {
                        Text(text = stringResource(id = R.string.close))
                    }

                }
            }
        )
    }

}
