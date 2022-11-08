package net.doheco.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = net.doheco.data.contracts.HeroesContract.HEROES_TABLE_NAME)
data class Hero(
    @PrimaryKey
    @SerializedName("id")
    @Expose
    var id: Int,

    @SerializedName("name")
    @Expose
    var name: String,

    @SerializedName("localized_name")
    @Expose
    var localizedName: String,

    @SerializedName("primary_attr")
    @Expose
    var primaryAttr: String,

    @SerializedName("attack_type")
    @Expose
    var attackType: String,

    @SerializedName("roles")
    @Expose
    var roles: List<String>,

    @SerializedName("img")
    @Expose
    var img: String,

    @SerializedName("icon")
    @Expose
    var icon: String,

    @SerializedName("base_health")
    @Expose
    var baseHealth: Int,

    @SerializedName("base_health_regen")
    @Expose
    var baseHealthRegen: Double,

    @SerializedName("base_mana")
    @Expose
    var baseMana: Int,

    @SerializedName("base_mana_regen")
    @Expose
    var baseManaRegen: Double,

    @SerializedName("base_armor")
    @Expose
    var baseArmor: Double,

    @SerializedName("base_mr")
    @Expose
    var baseMr: Int,

    @SerializedName("base_attack_min")
    @Expose
    var baseAttackMin: Int,

    @SerializedName("base_attack_max")
    @Expose
    var baseAttackMax: Int,

    @SerializedName("base_str")
    @Expose
    var baseStr: Int,

    @SerializedName("base_agi")
    @Expose
    var baseAgi: Int,

    @SerializedName("base_int")
    @Expose
    var baseInt: Int,

    @SerializedName("str_gain")
    @Expose
    var strGain: Double,

    @SerializedName("agi_gain")
    @Expose
    var agiGain: Double,

    @SerializedName("int_gain")
    @Expose
    var intGain: Double,

    @SerializedName("attack_range")
    @Expose
    var attackRange: Int,

    @SerializedName("projectile_speed")
    @Expose
    var projectileSpeed: Int,

    @SerializedName("attack_rate")
    @Expose
    var attackRate: Double,

    @SerializedName("move_speed")
    @Expose
    var moveSpeed: Int,

    @SerializedName("turn_rate")
    @Expose
    var turnRate: Double,

    @SerializedName("cm_enabled")
    @Expose
    var cmEnabled: String,

    @SerializedName("legs")
    @Expose
    var legs: Int,

    @SerializedName("hero_id")
    @Expose
    var heroId: Int,

    @SerializedName("turbo_picks")
    @Expose
    var turboPicks: Int,

    @SerializedName("turbo_wins")
    @Expose
    var turboWins: Int,

    @SerializedName("pro_ban")
    @Expose
    var proBan: Int,

    @SerializedName("pro_win")
    @Expose
    var proWin: Int,

    @SerializedName("pro_pick")
    @Expose
    var proPick: Int,

    @SerializedName("1_pick")
    var _1Pick: Int,

    @SerializedName("1_win")
    @Expose
    var _1Win: Int,

    @SerializedName("2_pick")
    @Expose
    var _2Pick: Int,

    @SerializedName("2_win")
    @Expose
    var _2Win: Int,

    @SerializedName("3_pick")
    @Expose
    var _3Pick: Int,

    @SerializedName("3_win")
    @Expose
    var _3Win: Int,

    @SerializedName("4_pick")
    @Expose
    var _4Pick: Int,

    @SerializedName("4_win")
    @Expose
    var _4Win: Int,

    @SerializedName("5_pick")
    @Expose
    var _5Pick: Int,

    @SerializedName("5_win")
    @Expose
    var _5Win: Int,

    @SerializedName("6_pick")
    @Expose
    var _6Pick: Int,

    @SerializedName("6_win")
    @Expose
    var _6Win: Int,

    @SerializedName("7_pick")
    @Expose
    var _7Pick: Int,

    @SerializedName("7_win")
    @Expose
    var _7Win: Int,

    @SerializedName("8_pick")
    @Expose
    var _8Pick: Int,

    @SerializedName("8_win")
    @Expose
    var _8Win: Int,

    @SerializedName("null_pick")
    @Expose
    var nullPick: Int,

    @SerializedName("null_win")
    @Expose
    var nullWin: Int
)