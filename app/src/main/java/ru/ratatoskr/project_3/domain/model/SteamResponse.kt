package ru.ratatoskr.project_3.domain.model

import androidx.room.Entity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import ru.ratatoskr.project_3.data.contracts.SteamContract

@Serializable
data class SteamResponse(

    @SerializedName("response")
    @Expose
    var response: SteamResponsePlayers,

)