package net.doheco.presentation.screens

import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import net.doheco.R
import net.doheco.presentation.screens.profile.models.ProfileEvent
import net.doheco.presentation.screens.profile.models.ProfileState
import net.doheco.presentation.screens.profile.ProfileViewModel
import net.doheco.presentation.screens.profile.views.DefinedBySteamProfileView
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

    fun getAbbreviatedFromDateTime(dateTime: Calendar, field: String): String? {
        val output = SimpleDateFormat(field)
        try {
            return output.format(dateTime.time)    // format output
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    val calendarDate = Calendar.getInstance()
    val player_tier = viewModel.getPlayerTierFromSP()

    val viewState = viewModel.profileState.observeAsState()

    when (val state = viewState.value) {

        is ProfileState.UndefinedState -> {

            var heroes_list_last_modified = ""
            if (state.heroes_list_last_modified == "0"
            ) {
                heroes_list_last_modified = stringResource(id = R.string.wait)
            }
            if (state.heroes_list_last_modified == "1") {
                heroes_list_last_modified = stringResource(id = R.string.time_block)
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
                heroes_list_last_modified =
                    day + "/" + month + "/" + year + " " + hours + ":" + minutes + ":" + seconds

            }

            UndefinedProfileView(
                state,
                viewModel,
                navController,
                player_tier,
                heroes_list_last_modified
            ) {
                viewModel.obtainEvent(ProfileEvent.OnUpdate)
            }

        }
        is ProfileState.SteamNameIsDefinedState -> {
            var heroes_list_last_modified = ""

            if (state.heroes_list_last_modified == "0") {
                heroes_list_last_modified = stringResource(id = R.string.wait)
            }
            if (state.heroes_list_last_modified == "1") {
                heroes_list_last_modified = stringResource(id = R.string.time_block)
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
                heroes_list_last_modified =
                    day + "/" + month + "/" + year + " " + hours + ":" + minutes + ":" + seconds

                if(state.heroes_list_last_modified=="time"){
                    appSharedPreferences.edit().putLong("heroes_list_last_modified", 1).apply()
                    heroes_list_last_modified="time"
                }

            }

            DefinedBySteamProfileView(
                state,
                viewModel,
                player_tier,
                heroes_list_last_modified,
                navController,
                { viewModel.obtainEvent(ProfileEvent.OnSteamExit) },
                { viewModel.obtainEvent(ProfileEvent.OnUpdate) }
            )

        }
        else -> {}
    }

    LaunchedEffect(key1 = Unit, block = {

    })
}