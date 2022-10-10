package ru.ratatoskr.doheco.presentation.base

import ru.ratatoskr.doheco.R

sealed class Screens(
    val route: String,
    val description: Int = 0,
    val icon_tr: Int = 0,
    val icon_wh: Int = 0
) {
    object Home :
        Screens("home", R.string.title_home, R.drawable.ic_house_tr, R.drawable.ic_house_wh)

    object Hero : Screens("hero")
    object Attr : Screens("attr")
    object Favorites : Screens(
        "favorites",
        R.string.title_favorites,
        R.drawable.ic_star_tr,
        R.drawable.ic_star_wh
    )

    object Profile :
        Screens("profile", R.string.title_profile, R.drawable.ic_human_tr, R.drawable.ic_human_wh)

    object Video :
        Screens("video", R.string.video_string, R.drawable.ic_video_tr, R.drawable.ic_video_wh)

    object Role : Screens("role")
    object Steam : Screens("steam")
    object Tier : Screens("tier")
    object Comparing : Screens(
        "comparing",
        R.string.comparing,
        R.drawable.ic_comparing_tr,
        R.drawable.ic_comparing_wh
    )

    object Recommendations : Screens(
        "recommendations",
        R.string.recommendations,
        R.drawable.ic_rate_tr,
        R.drawable.ic_rate_wh
    )
}