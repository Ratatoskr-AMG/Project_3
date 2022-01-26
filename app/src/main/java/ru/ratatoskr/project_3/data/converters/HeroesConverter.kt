package ru.ratatoskr.project_3.data.converters

interface HeroesConverter {
    fun fromRoles(roles: List<String>): String
    fun toRoles(roles:String): List<String>
}