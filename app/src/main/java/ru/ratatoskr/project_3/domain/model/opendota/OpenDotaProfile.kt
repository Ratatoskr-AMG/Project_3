package ru.ratatoskr.project_3.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable



@Serializable
data class OpenDotaProfile(

    @SerializedName("account_id") @Expose var account_id: Int,
    @SerializedName("personaname") @Expose var personaname: String,
    @SerializedName("name") @Expose var name: String,
    @SerializedName("plus") @Expose var plus: Boolean,
    @SerializedName("cheese") @Expose var cheese: Int,
    @SerializedName("steamid") @Expose var steamid: String,
    @SerializedName("avatar") @Expose var avatar: String,
    @SerializedName("avatarmedium") @Expose var avatarmedium: String,
    @SerializedName("avatarfull") @Expose var avatarfull: String,
    @SerializedName("profileurl") @Expose var profileurl: String,
    @SerializedName("last_login") @Expose var last_login: String,
    @SerializedName("loccountrycode") @Expose var loccountrycode: String,
    @SerializedName("is_contributor") @Expose var is_contributor: Boolean,

    )

