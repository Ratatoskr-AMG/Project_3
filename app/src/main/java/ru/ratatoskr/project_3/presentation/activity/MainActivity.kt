package ru.ratatoskr.project_3.presentation.activity

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.*
import androidx.compose.ui.graphics.*
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.internal.Contexts
import ru.ratatoskr.project_3.R
import ru.ratatoskr.project_3.presentation.screens.*


@AndroidEntryPoint
class MainActivity() : AppCompatActivity() {


    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            var appSharedPreferences = this.getSharedPreferences(
                "app_preferences", Context.MODE_PRIVATE
            )
            val systemUiController = rememberSystemUiController()
            systemUiController.setStatusBarColor(color = Color.Transparent)
            ProvideWindowInsets {
                WrapperScreen(appSharedPreferences)
            }
        }
    }

}