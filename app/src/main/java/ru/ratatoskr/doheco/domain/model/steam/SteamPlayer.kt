package ru.ratatoskr.doheco.domain.model.steam

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import ru.ratatoskr.doheco.data.contracts.HeroesContract
import ru.ratatoskr.doheco.data.contracts.SteamContract

/*
{
"steamid":"76561198165608798",
"communityvisibilitystate":3,
"profilestate":1,
"personaname":"AMG",
"profileurl":"https://steamcommunity.com/profiles/76561198165608798/",
"avatar":"https://steamcdn-a.akamaihd.net/steamcommunity/public/images/avatars/d9/d992f14848645364976ba464ad2f2442c138611f.jpg",
"avatarmedium":"https://steamcdn-a.akamaihd.net/steamcommunity/public/images/avatars/d9/d992f14848645364976ba464ad2f2442c138611f_medium.jpg",
"avatarfull":"https://steamcdn-a.akamaihd.net/steamcommunity/public/images/avatars/d9/d992f14848645364976ba464ad2f2442c138611f_full.jpg",
"avatarhash":"d992f14848645364976ba464ad2f2442c138611f",
"lastlogoff":1647426748,
"personastate":0,
"primaryclanid": "103582791429521408",
"timecreated":1417273892,
"personastateflags":0,
"loccountrycode":"RU",
"locstatecode":"48",
"loccityid":41460
}
 */
@Serializable
@Entity(tableName = ru.ratatoskr.doheco.data.contracts.SteamContract.USERS_STEAM_TABLE_NAME)
data class SteamPlayer(

    @PrimaryKey
    @SerializedName(ru.ratatoskr.doheco.data.contracts.SteamContract.COLUMN_STEAM_ID)
    @Expose
    var steamid: String,

    @SerializedName("communityvisibilitystate")
    var communityvisibilitystate: Int? = 0,

    @SerializedName("profilestate")
    var profilestate: Int? = 0,

    @SerializedName("personaname")
    @Expose
    var personaname: String? = "",

    @SerializedName("profileurl")
    @Expose
    var profileurl: String? = "",

    @SerializedName("avatar")
    @Expose
    var avatar: String? = "",

    @SerializedName("avatarmedium")
    @Expose
    var avatarmedium: String? = "",

    @SerializedName("avatarfull")
    @Expose
    var avatarfull: String? = "",

    @SerializedName("avatarhash")
    @Expose
    var avatarhash: String? = "",

    @SerializedName("lastlogoff")
    @Expose
    var lastlogoff: Int? = 0,

    @SerializedName("personastate")
    @Expose
    var personastate: Int? = 0,

    @SerializedName("primaryclanid")
    @Expose
    var primaryclanid: String? = "",

    @SerializedName("timecreated")
    @Expose
    var timecreated: Int? = 0,

    @SerializedName("personastateflags")
    @Expose
    var personastateflags: String? = "",

    @SerializedName("loccountrycode")
    @Expose
    var loccountrycode: String? = "",

    @SerializedName("locstatecode")
    @Expose
    var locstatecode: String? = "",

    @SerializedName("loccityid")
    @Expose
    var loccityid: Int? = 0

)