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
    private var cmEnabled: String? = null

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
    private var nullPick: Int? = null

    @SerializedName("null_win")
    @Expose
    var nullWin: Int? = null

    constructor(
        name: String?,
        localizedName: String?,
        primaryAttr: String?,
        attackType: String?,
        roles: String?,
        img: String?,
        icon: String?,
        baseHealth: Int?,
        baseHealthRegen: Double?,
        baseMana: Int?,
        baseManaRegen: Double?,
        baseArmor: Double?,
        baseMr: Int?,
        baseAttackMin: Int?,
        baseAttackMax: Int?,
        baseStr: Int?,
        baseAgi: Int?,
        baseInt: Int?,
        strGain: Double?,
        agiGain: Double?,
        intGain: Double?,
        attackRange: Int?,
        projectileSpeed: Int?,
        attackRate: Double?,
        moveSpeed: Int?,
        turnRate: Double?,
        cmEnabled: String?,
        legs: Int?,
        heroId: Int?,
        turboPicks: Int?,
        turboWins: Int?,
        proBan: Int?,
        proWin: Int?,
        proPick: Int?,
        _1Pick: Int?,
        _1Win: Int?,
        _2Pick: Int?,
        _2Win: Int?,
        _3Pick: Int?,
        _3Win: Int?,
        _4Pick: Int?,
        _4Win: Int?,
        _5Pick: Int?,
        _5Win: Int?,
        _6Pick: Int?,
        _6Win: Int?,
        _7Pick: Int?,
        _7Win: Int?,
        _8Pick: Int?,
        _8Win: Int?,
        nullPick: Int?,
        nullWin: Int?
    ) {
        val heroRoles: List<String> = roles!!.split(",").map { it.trim() }
        this.name = name
        this.localizedName = localizedName
        this.primaryAttr = primaryAttr
        this.attackType = attackType
        this.roles = heroRoles
        this.img = img
        this.icon = icon
        this.baseHealth = baseHealth
        this.baseHealthRegen = baseHealthRegen
        this.baseMana = baseMana
        this.baseManaRegen = baseManaRegen
        this.baseArmor = baseArmor
        this.baseMr = baseMr
        this.baseAttackMin = baseAttackMin
        this.baseAttackMax = baseAttackMax
        this.baseStr = baseStr
        this.baseAgi = baseAgi
        this.baseInt = baseInt
        this.strGain = strGain
        this.agiGain = agiGain
        this.intGain = intGain
        this.attackRange = attackRange
        this.projectileSpeed = projectileSpeed
        this.attackRate = attackRate
        this.moveSpeed = moveSpeed
        this.turnRate = turnRate
        this.cmEnabled = cmEnabled
        this.legs = legs
        this.heroId = heroId
        this.turboPicks = turboPicks
        this.turboWins = turboWins
        this.proBan = proBan
        this.proWin = proWin
        this.proPick = proPick
        this._1Pick = _1Pick
        this._1Win = _1Win
        this._2Pick = _2Pick
        this._2Win = _2Win
        this._3Pick = _3Pick
        this._3Win = _3Win
        this._4Pick = _4Pick
        this._4Win = _4Win
        this._5Pick = _5Pick
        this._5Win = _5Win
        this._6Pick = _6Pick
        this._6Win = _6Win
        this._7Pick = _7Pick
        this._7Win = _7Win
        this._8Pick = _8Pick
        this._8Win = _8Win
        this.nullPick = nullPick
        this.nullWin = nullWin
    }

    fun getName(): String? {
        return name
    }

    fun getLocalizedName(): String? {
        return localizedName
    }

    fun getPrimaryAttr(): String? {
        return primaryAttr
    }

    fun getAttackType(): String? {
        return attackType
    }

    fun getRoles(): List<String>? {
        return roles
    }

    fun getImg(): String? {
        return img
    }

    fun getIcon(): String? {
        return icon
    }

    fun getBaseHealth(): Int? {
        return baseHealth
    }

    fun getBaseHealthRegen(): Double? {
        return baseHealthRegen
    }

    fun getBaseMana(): Int? {
        return baseMana
    }

    fun getBaseManaRegen(): Double? {
        return baseManaRegen
    }

    fun getBaseArmor(): Double? {
        return baseArmor
    }

    fun getBaseMr(): Int? {
        return baseMr
    }

    fun getBaseAttackMin(): Int? {
        return baseAttackMin
    }

    fun getBaseAttackMax(): Int? {
        return baseAttackMax
    }

    fun getBaseStr(): Int? {
        return baseStr
    }

    fun getBaseAgi(): Int? {
        return baseAgi
    }

    fun getBaseInt(): Int? {
        return baseInt
    }

    fun getStrGain(): Double? {
        return strGain
    }

    fun getAgiGain(): Double? {
        return agiGain
    }

    fun getIntGain(): Double? {
        return intGain
    }

    fun getAttackRange(): Int? {
        return attackRange
    }

    fun getProjectileSpeed(): Int? {
        return projectileSpeed
    }

    fun getAttackRate(): Double? {
        return attackRate
    }

    fun getMoveSpeed(): Int? {
        return moveSpeed
    }

    fun getTurnRate(): Double? {
        return turnRate
    }

    fun getLegs(): Int? {
        return legs
    }

    fun getHeroId(): Int? {
        return heroId
    }

    fun getTurboPicks(): Int? {
        return turboPicks
    }

    fun getCmEnabled(): String? {
        return cmEnabled
    }

    fun getTurboWins(): Int? {
        return turboWins
    }

    fun getProBan(): Int? {
        return proBan
    }

    fun getProWin(): Int? {
        return proWin
    }

    fun getProPick(): Int? {
        return proPick
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

    fun getNullPick(): Int? {
        return nullPick
    }

    fun getNUllWin(): Int? {
        return nullWin
    }


}