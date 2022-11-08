package net.doheco.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class OpenDotaMmrEstimate(

    @SerializedName("estimate") @Expose var estimate: Int,
    @SerializedName("stdDev") @Expose var stdDev: Int,
    @SerializedName("n") @Expose var n: Int,

)
