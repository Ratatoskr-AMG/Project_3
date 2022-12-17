package net.doheco.domain.model


import com.google.gson.annotations.SerializedName
import net.doheco.domain.model.opendota.OpenDotaMatch

data class HeroesAndMatchesApiCallResult(
    @SerializedName("heroes")
    var heroes: List<Hero>? = null,
    @SerializedName("last_up_date")
    var lastUpDate: String? = null,
    @SerializedName("result")
    var result: Boolean? = null,
    @SerializedName("matches")
    var matches: List<OpenDotaMatch>? = null,
    @SerializedName("next_up_date")
    var nextUpDate: String? = null,
    @SerializedName("remain")
    var remain: String? = null
)