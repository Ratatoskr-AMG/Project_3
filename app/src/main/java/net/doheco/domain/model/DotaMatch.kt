package net.doheco.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "matches")
data class DotaMatch(
    @SerializedName("assists")
    val assists: Long?,
    @SerializedName("average_rank")
    val averageRank: Long?,
    @SerializedName("deaths")
    val deaths: Long?,
    @SerializedName("duration")
    val duration: Long?,
    @SerializedName("game_mode")
    val gameMode: Long?,
    @SerializedName("hero_id")
    val heroId: Long?,
    @SerializedName("kills")
    val kills: Long?,
    @SerializedName("leaver_status")
    val leaverStatus: Long?,
    @SerializedName("lobby_type")
    val lobbyType: Long?,
    @PrimaryKey
    @SerializedName("match_id")
    val matchId: Long?,
    @SerializedName("party_size")
    val partySize: Long?,
    @SerializedName("player_slot")
    val playerSlot: Long?,
    @SerializedName("radiant_win")
    val radiantWin: Boolean?,
    @SerializedName("skill")
    val skill: Long?,
    @SerializedName("start_time")
    val startTime: Long?,
    @SerializedName("version")
    val version: Long?,
    @SerializedName("heroIcon")
    val heroIcon: String,
    @SerializedName("start_time_formatted")
    val startTimeFormatted: String,
    @SerializedName("duration_formatted")
    val durationFormatted: String,
)
