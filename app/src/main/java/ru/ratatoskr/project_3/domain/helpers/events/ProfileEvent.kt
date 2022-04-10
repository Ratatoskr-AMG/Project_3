package ru.ratatoskr.project_3.domain.helpers.events

import android.content.SharedPreferences

sealed class ProfileEvent {
    data class OnSteamLogin(val steam_user_id: String) : ProfileEvent()
    data class OnTierChange(val selected_tier: String) : ProfileEvent()
    object OnSteamExit : ProfileEvent()
}
