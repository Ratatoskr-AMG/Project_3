package net.doheco.domain.model.opendota


import com.google.gson.annotations.SerializedName

data class OpenDotaMatch(
    @SerializedName("assists")
    var assists: Long? = null,
    @SerializedName("average_rank")
    var averageRank: Long? = null,
    @SerializedName("deaths")
    var deaths: Long? = null,
    @SerializedName("duration")
    var duration: Long? = null,
    @SerializedName("game_mode")
    var gameMode: Long? = null,
    @SerializedName("hero_id")
    var heroId: Long? = null,
    @SerializedName("kills")
    var kills: Long? = null,
    @SerializedName("leaver_status")
    var leaverStatus: Long? = null,
    @SerializedName("lobby_type")
    var lobbyType: Long? = null,
    @SerializedName("match_id")
    var matchId: Long? = null,
    @SerializedName("party_size")
    var partySize: Long? = null,
    @SerializedName("player_slot")
    var playerSlot: Long? = null,
    @SerializedName("radiant_win")
    var radiantWin: Boolean? = null,
    @SerializedName("skill")
    var skill: Long? = null,
    @SerializedName("start_time")
    var startTime: Long? = null,
    @SerializedName("version")
    var version: Long? = null,
)