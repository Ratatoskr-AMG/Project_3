package ru.ratatoskr.project_3.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import ru.ratatoskr.project_3.data.contracts.HeroesContract
import ru.ratatoskr.project_3.data.contracts.SteamContract

@Serializable
data class SteamResponsePlayers(

    @SerializedName("players")
    @Expose
    var players: List<UserSteam>,

)