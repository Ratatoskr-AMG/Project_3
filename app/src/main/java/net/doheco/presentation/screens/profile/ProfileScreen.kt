package net.doheco.presentation.screens

import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import net.doheco.R
import net.doheco.presentation.screens.profile.models.ProfileEvent
import net.doheco.presentation.screens.profile.models.ProfileState
import net.doheco.presentation.screens.profile.ProfileViewModel
import net.doheco.presentation.screens.profile.views.DefinedBySteamProfileView
import net.doheco.presentation.screens.profile.views.UndefinedProfileView
import java.text.SimpleDateFormat
import java.util.*

    fun getAbbreviatedFromDateTime(dateTime: Calendar, field: String): String? {
        val output = SimpleDateFormat(field)
        try {
            return output.format(dateTime.time)    // format output
        } catch (e: Exception) {
            e.printStackTrace()
        }

    return null
}

@Composable
fun getUpdatingButtonText(calendarDate:Calendar, heroes_list_last_modified:String) : String{
    var result = ""

    if (heroes_list_last_modified == "0"
    ) {
        result = stringResource(id = R.string.wait)
    }
    if (heroes_list_last_modified == "1" || heroes_list_last_modified=="time") {
        result = stringResource(id = R.string.time_block)
    }
    if (heroes_list_last_modified != "1" && heroes_list_last_modified != "0" && heroes_list_last_modified != "time") {
        calendarDate.timeInMillis = heroes_list_last_modified.toLong()
        //calendarDate.timeInMillis = appSharedPreferences.getLong("heroes_list_last_modified", 0)
        val month = getAbbreviatedFromDateTime(calendarDate, "MM");
        val day = getAbbreviatedFromDateTime(calendarDate, "dd");
        val year = getAbbreviatedFromDateTime(calendarDate, "YYYY");
        val hours = getAbbreviatedFromDateTime(calendarDate, "HH");
        val minutes = getAbbreviatedFromDateTime(calendarDate, "mm");
        val seconds = getAbbreviatedFromDateTime(calendarDate, "ss");
        var date = day + "/" + month + "/" + year + " " + hours + ":" + minutes + ":" + seconds
        if(date=="01/01/1970 03:00:00"){
            result = stringResource(id = R.string.wait)
        }else{
            result = stringResource(id = R.string.heroes_list_last_modified) + " (" + date + ")"
        }
    }
    return result
}

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

    val calendarDate = Calendar.getInstance()
    val playerTier = viewModel.getPlayerTierFromSP()

    val viewState = viewModel.profileState.observeAsState()

    when (val state = viewState.value) {

        is ProfileState.UndefinedState -> {

            var updatingDataButtonText=getUpdatingButtonText(calendarDate,state.heroes_list_last_modified)

            /*
            if (state.heroes_list_last_modified == "0"
            ) {
                heroesListLastModified = stringResource(id = R.string.wait)
            }
            if (state.heroes_list_last_modified == "1") {
                heroesListLastModified = stringResource(id = R.string.time_block)
            }
            if (state.heroes_list_last_modified != "1" && state.heroes_list_last_modified != "0") {
                calendarDate.timeInMillis = state.heroes_list_last_modified.toLong()
                //calendarDate.timeInMillis = appSharedPreferences.getLong("heroes_list_last_modified", 0)
                val month = getAbbreviatedFromDateTime(calendarDate, "MM");
                val day = getAbbreviatedFromDateTime(calendarDate, "dd");
                val year = getAbbreviatedFromDateTime(calendarDate, "YYYY");
                val hours = getAbbreviatedFromDateTime(calendarDate, "HH");
                val minutes = getAbbreviatedFromDateTime(calendarDate, "mm");
                val seconds = getAbbreviatedFromDateTime(calendarDate, "ss");
                heroesListLastModified =
                    "$day/$month/$year $hours:$minutes:$seconds"

            }
            */

            UndefinedProfileView(
                state,
                viewModel,
                navController,
                playerTier,
                dialogState = openDialog,
                updatingDataButtonText
            ) {
                viewModel.obtainEvent(ProfileEvent.OnUpdate)
            }

        }
        is ProfileState.SteamNameIsDefinedState -> {

            var updatingDataButtonText=getUpdatingButtonText(calendarDate,state.heroes_list_last_modified)

            /*var heroes_list_last_modified = ""

            if (state.heroes_list_last_modified == "0") {
                heroesListLastModified = stringResource(id = R.string.wait)
            }
            if (state.heroes_list_last_modified == "1") {
                heroesListLastModified = stringResource(id = R.string.time_block)
            }

            if ((state.heroes_list_last_modified != "1" && state.heroes_list_last_modified != "0") ) {

                if(state.heroes_list_last_modified!="time") {
                    calendarDate.timeInMillis = state.heroes_list_last_modified.toLong()
                }
                else{
                    calendarDate.timeInMillis = 1.toLong()
                }
                //calendarDate.timeInMillis = appSharedPreferences.getLong("heroes_list_last_modified", 0)
                val month = getAbbreviatedFromDateTime(calendarDate, "MM");
                val day = getAbbreviatedFromDateTime(calendarDate, "dd");
                val year = getAbbreviatedFromDateTime(calendarDate, "YYYY");
                val hours = getAbbreviatedFromDateTime(calendarDate, "HH");
                val minutes = getAbbreviatedFromDateTime(calendarDate, "mm");
                val seconds = getAbbreviatedFromDateTime(calendarDate, "ss");
                heroesListLastModified =
                    "$day/$month/$year $hours:$minutes:$seconds"

                if(state.heroes_list_last_modified=="time"){
                    appSharedPreferences.edit().putLong("heroes_list_last_modified", 1).apply()
                    heroesListLastModified="time"
                }

            }

             */

            DefinedBySteamProfileView(
                state,
                viewModel,
                playerTier,
                updatingDataButtonText,
                navController,
                dialogState = openDialog,
                { viewModel.obtainEvent(ProfileEvent.OnSteamExit) },
                { viewModel.obtainEvent(ProfileEvent.OnUpdate) }
            )

        }
        else -> {}
    }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            backgroundColor = Color(0xFF131313),
            shape = RoundedCornerShape(10.dp),
            text = {
                Column() {
                    Text(
                        text = stringResource(id = R.string.offer),
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )
                    OutlinedTextField(
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
                        modifier = Modifier.height(150.dp),
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
                        onClick = { openDialog.value = false },
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
}


@Composable
@Preview(showBackground = true)
fun Prew() {
    val openDialog = remember { mutableStateOf(false) }
    var titleText by remember { mutableStateOf("") }
    var messageText by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = {
            openDialog.value = false
        },
        backgroundColor = Color(0xFF131313),
        shape = RoundedCornerShape(10.dp),
        text = {
            Column() {
                Text(
                    text = stringResource(id = R.string.offer),
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                OutlinedTextField(
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
                    modifier = Modifier.height(150.dp),
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
                    onClick = { openDialog.value = false },
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
