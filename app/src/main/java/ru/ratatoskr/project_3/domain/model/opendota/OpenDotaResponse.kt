package ru.ratatoskr.project_3.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class OpenDotaResponse(

    @SerializedName("tracked_until") @Expose var tracked_until: String,
    @SerializedName("rank_tier") @Expose var rank_tier: String,
    @SerializedName("leaderboard_rank") @Expose var leaderboard_rank: String,
    @SerializedName("mmr_estimate") @Expose var mmr_estimate: OpenDotaMmrEstimate,
    @SerializedName("profile") @Expose var profile: OpenDotaProfile,
    @SerializedName("solo_competitive_rank") @Expose var solo_competitive_rank: Int,
    @SerializedName("competitive_rank") @Expose var competitive_rank: Int,

    )



