package ru.ratatoskr.project_3.presentation.screens

import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import ru.ratatoskr.project_3.R
import ru.ratatoskr.project_3.presentation.screens.profile.models.ProfileEvent
import ru.ratatoskr.project_3.presentation.screens.profile.models.ProfileState
import ru.ratatoskr.project_3.presentation.screens.profile.ProfileViewModel
import ru.ratatoskr.project_3.presentation.screens.profile.views.DefinedBySteamProfileView
import ru.ratatoskr.project_3.presentation.screens.profile.views.UndefinedProfileView
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
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


    val player_tier = viewModel.getPlayerTierFromSP()
    val player_steam_name = viewModel.getPlayerSteamNameFromSP()
    //val heroes_list_last_modified =
    //    Date(appSharedPreferences.getLong("heroes_list_last_modified", 0))
    //        .toString()
    val secondApiFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
    val calendarDate = Calendar.getInstance()
    calendarDate.timeInMillis = appSharedPreferences.getLong("heroes_list_last_modified", 0)
    val month = getAbbreviatedFromDateTime(calendarDate, "MM");
    val day = getAbbreviatedFromDateTime(calendarDate, "dd");
    val year = getAbbreviatedFromDateTime(calendarDate, "YYYY");
    val hours = getAbbreviatedFromDateTime(calendarDate, "HH");
    val minutes = getAbbreviatedFromDateTime(calendarDate, "mm");
    val seconds = getAbbreviatedFromDateTime(calendarDate, "ss");

    var date = Date(appSharedPreferences.getLong("heroes_list_last_modified", 0))

    var heroes_list_last_modified =
        day + "/" + month + "/" + year + " " + hours + ":" + minutes + ":" + seconds

    if (player_steam_name != "undefined") {
        viewModel.setSteamIsDefinedProfileState()
    } else {
        viewModel.setUndefinedProfileState()
    }

    val viewState = viewModel.profileState.observeAsState()

    when (val state = viewState.value) {

        is ProfileState.UndefinedState -> {

            if(state.heroes_list_last_modified=="0") {
                heroes_list_last_modified= stringResource(id = R.string.wait)
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

            if(state.heroes_list_last_modified=="0") {
                heroes_list_last_modified= stringResource(id = R.string.wait)
            }

            DefinedBySteamProfileView(
                state,
                viewModel,
                player_tier,
                heroes_list_last_modified,
                navController,
                {viewModel.obtainEvent(ProfileEvent.OnSteamExit) },
                {viewModel.obtainEvent(ProfileEvent.OnUpdate)}
            )

        }
        else -> {}
    }

    LaunchedEffect(key1 = Unit, block = {

    })
}