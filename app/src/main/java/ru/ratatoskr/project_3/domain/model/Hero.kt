package ru.ratatoskr.project_3.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Hero {
    @SerializedName("id")
    @Expose
    private var id: Int? = null

    @SerializedName("name")
    @Expose
    private var name: String? = null

    @SerializedName("localized_name")
    @Expose
    private var localizedName: String? = null

    @SerializedName("primary_attr")
    @Expose
    private var primaryAttr: String? = null

    @SerializedName("attack_type")
    @Expose
    private var attackType: String? = null

    @SerializedName("roles")
    @Expose
    private var roles: List<String>? = null

    @SerializedName("img")
    @Expose
    private var img: String? = null

    @SerializedName("icon")
    @Expose
    private var icon: String? = null

    @SerializedName("base_health")
    @Expose
    private var baseHealth: Int? = null

    @SerializedName("base_health_regen")
    @Expose
    private var baseHealthRegen: Double? = null

    @SerializedName("base_mana")
    @Expose
    private var baseMana: Int? = null

    @SerializedName("base_mana_regen")
    @Expose
    private var baseManaRegen: Double? = null

    @SerializedName("base_armor")
    @Expose
    private var baseArmor: Double? = null

    @SerializedName("base_mr")
    @Expose
    private var baseMr: Int? = null

    @SerializedName("base_attack_min")
    @Expose
    private var baseAttackMin: Int? = null

    @SerializedName("base_attack_max")
    @Expose
    private var baseAttackMax: Int? = null

    @SerializedName("base_str")
    @Expose
    private var baseStr: Int? = null

    @SerializedName("base_agi")
    @Expose
    private var baseAgi: Int? = null

    @SerializedName("base_int")
    @Expose
    private var baseInt: Int? = null

    @SerializedName("str_gain")
    @Expose
    private var strGain: Double? = null

    @SerializedName("agi_gain")
    @Expose
    private var agiGain: Double? = null

    @SerializedName("int_gain")
    @Expose
    private var intGain: Double? = null

    @SerializedName("attack_range")
    @Expose
    private var attackRange: Int? = null

    @SerializedName("projectile_speed")
    @Expose
    private var projectileSpeed: Int? = null

    @SerializedName("attack_rate")
    @Expose
    private var attackRate: Double? = null

    @SerializedName("move_speed")
    @Expose
    private var moveSpeed: Int? = null

    @SerializedName("turn_rate")
    @Expose
    private var turnRate: Double? = null

    @SerializedName("cm_enabled")
    @Expose
    private var cmEnabled: Boolean? = null

    @SerializedName("legs")
    @Expose
    private var legs: Int? = null

    @SerializedName("hero_id")
    @Expose
    private var heroId: Int? = null

    @SerializedName("turbo_picks")
    @Expose
    private var turboPicks: Int? = null

    @SerializedName("turbo_wins")
    @Expose
    private var turboWins: Int? = null

    @SerializedName("pro_ban")
    @Expose
    private var proBan: Int? = null

    @SerializedName("pro_win")
    @Expose
    private var proWin: Int? = null

    @SerializedName("pro_pick")
    @Expose
    private var proPick: Int? = null

    @SerializedName("1_pick")
    private var _1Pick: Int? = null

    @SerializedName("1_win")
    @Expose
    private var _1Win: Int? = null

    @SerializedName("2_pick")
    @Expose
    private var _2Pick: Int? = null

    @SerializedName("2_win")
    @Expose
    private var _2Win: Int? = null

    @SerializedName("3_pick")
    @Expose
    private var _3Pick: Int? = null

    @SerializedName("3_win")
    @Expose
    private var _3Win: Int? = null

    @SerializedName("4_pick")
    @Expose
    private var _4Pick: Int? = null

    @SerializedName("4_win")
    @Expose
    private var _4Win: Int? = null

    @SerializedName("5_pick")
    @Expose
    private var _5Pick: Int? = null

    @SerializedName("5_win")
    @Expose
    private var _5Win: Int? = null

    @SerializedName("6_pick")
    @Expose
    private var _6Pick: Int? = null

    @SerializedName("6_win")
    @Expose
    private var _6Win: Int? = null

    @SerializedName("7_pick")
    @Expose
    private var _7Pick: Int? = null

    @SerializedName("7_win")
    @Expose
    private var _7Win: Int? = null

    @SerializedName("8_pick")
    @Expose
    private var _8Pick: Int? = null

    @SerializedName("8_win")
    @Expose
    private var _8Win: Int? = null

    @SerializedName("null_pick")
    @Expose
    var nullPick: Int? = null

    @SerializedName("null_win")
    @Expose
    var nullWin: Int? = null

    fun getLocalizedName(): String? {
        return localizedName
    }

    fun getCmEnabled(): Boolean? {
        return cmEnabled
    }

    fun setTurnRate(turnRate: Double?) {
        this.turnRate = turnRate
    }

    fun get1Pick(): Int? {
        return _1Pick
    }

    fun set1Pick(_1Pick: Int?) {
        this._1Pick = _1Pick
    }

    fun get1Win(): Int? {
        return _1Win
    }

    fun set1Win(_1Win: Int?) {
        this._1Win = _1Win
    }

    fun get2Pick(): Int? {
        return _2Pick
    }

    fun set2Pick(_2Pick: Int?) {
        this._2Pick = _2Pick
    }

    fun get2Win(): Int? {
        return _2Win
    }

    fun set2Win(_2Win: Int?) {
        this._2Win = _2Win
    }

    fun get3Pick(): Int? {
        return _3Pick
    }

    fun set3Pick(_3Pick: Int?) {
        this._3Pick = _3Pick
    }

    fun get3Win(): Int? {
        return _3Win
    }

    fun set3Win(_3Win: Int?) {
        this._3Win = _3Win
    }

    fun get4Pick(): Int? {
        return _4Pick
    }

    fun set4Pick(_4Pick: Int?) {
        this._4Pick = _4Pick
    }

    fun get4Win(): Int? {
        return _4Win
    }

    fun set4Win(_4Win: Int?) {
        this._4Win = _4Win
    }

    fun get5Pick(): Int? {
        return _5Pick
    }

    fun set5Pick(_5Pick: Int?) {
        this._5Pick = _5Pick
    }

    fun get5Win(): Int? {
        return _5Win
    }

    fun set5Win(_5Win: Int?) {
        this._5Win = _5Win
    }

    fun get6Pick(): Int? {
        return _6Pick
    }

    fun set6Pick(_6Pick: Int?) {
        this._6Pick = _6Pick
    }

    fun get6Win(): Int? {
        return _6Win
    }

    fun set6Win(_6Win: Int?) {
        this._6Win = _6Win
    }

    fun get7Pick(): Int? {
        return _7Pick
    }

    fun set7Pick(_7Pick: Int?) {
        this._7Pick = _7Pick
    }

    fun get7Win(): Int? {
        return _7Win
    }

    fun set7Win(_7Win: Int?) {
        this._7Win = _7Win
    }

    fun get8Pick(): Int? {
        return _8Pick
    }

    fun set8Pick(_8Pick: Int?) {
        this._8Pick = _8Pick
    }

    fun get8Win(): Int? {
        return _8Win
    }

    fun set8Win(_8Win: Int?) {
        this._8Win = _8Win
    }


}