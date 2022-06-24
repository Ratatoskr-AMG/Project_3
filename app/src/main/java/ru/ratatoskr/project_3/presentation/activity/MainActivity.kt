package ru.ratatoskr.project_3.presentation.activity

import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.*
import androidx.compose.ui.graphics.*
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import ru.ratatoskr.project_3.presentation.screens.*

@AndroidEntryPoint
class MainActivity() : AppCompatActivity() {

    val APP_SHARED_PREFERENCES_NAME = "app_preferences"

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            rememberSystemUiController().setStatusBarColor(color = Color.Transparent)
            ProvideWindowInsets {
                WrapperScreen(
                    this.getSharedPreferences(
                        APP_SHARED_PREFERENCES_NAME,
                        Context.MODE_PRIVATE
                    )
                )
            }
        }
    }

}