package ru.ratatoskr.project_3.domain.helpers

import android.content.res.Resources
import android.graphics.drawable.Drawable
import ru.ratatoskr.project_3.R

sealed class Screens(val route: String, val description: Int = 0, val icon_tr: Int = 0, val icon_wh: Int = 0) {
    object Home : Screens("home", R.string.title_home,R.drawable.ic_house_tr,R.drawable.ic_house_wh)
    object Hero : Screens("hero")
    object Attr : Screens("attr")
    object Favorites : Screens("favorites", R.string.title_favorites,R.drawable.ic_hearth_tr,R.drawable.ic_hearth_wh)
    object Profile : Screens("profile", R.string.title_profile,R.drawable.ic_human_tr,R.drawable.ic_human_wh)
    object Video : Screens("video", R.string.video_string,R.drawable.ic_video_tr,R.drawable.ic_video_wh)
    object Role : Screens("role")
    object Steam : Screens("steam")
    object Tier : Screens("tier")
}