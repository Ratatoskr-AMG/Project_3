package ru.ratatoskr.project_3.domain.di

import android.app.Application
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.request.CachePolicy
import coil.util.CoilUtils
import com.google.android.exoplayer2.util.Log
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import ru.ratatoskr.project_3.R

@HiltAndroidApp
class App: Application()