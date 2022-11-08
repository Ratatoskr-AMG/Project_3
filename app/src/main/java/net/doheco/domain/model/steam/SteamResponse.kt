package net.doheco.domain.model.steam

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class SteamResponse(

    @SerializedName("response")
    @Expose
    var response: SteamResponsePlayers,

    )



