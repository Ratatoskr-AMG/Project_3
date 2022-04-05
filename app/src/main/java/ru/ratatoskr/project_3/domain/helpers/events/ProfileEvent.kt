package ru.ratatoskr.project_3.domain.helpers.events

import android.content.SharedPreferences

sealed class ProfileEvent {
    data class OnSteamLogin(val steam_user_id: String) : ProfileEvent()
    data class OnSteamExit(val appSharedPreferences: SharedPreferences) : ProfileEvent()
}
