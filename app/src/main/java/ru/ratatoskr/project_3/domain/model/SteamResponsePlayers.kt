package ru.ratatoskr.project_3.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class SteamResponsePlayers(

    @SerializedName("players")
    @Expose
    var players: List<SteamPlayer>,

    )