package net.doheco.data.converters

interface HeroesConverter {
    fun fromRoles(roles: List<String>): String
    fun toRoles(roles:String): List<String>
}