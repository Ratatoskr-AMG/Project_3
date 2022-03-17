package ru.ratatoskr.project_3.domain.helpers

import ru.ratatoskr.project_3.R

sealed class Screens(val route: String, val stringId: Int) {
    object Home : Screens("home", R.string.title_home)
    object Hero : Screens("hero", R.string.title_hero)
    object Attr : Screens("attr", R.string.title_hero)
    object Favorites : Screens("favorites", R.string.title_favorites)
    object Profile : Screens("profile", R.string.title_profile)
}