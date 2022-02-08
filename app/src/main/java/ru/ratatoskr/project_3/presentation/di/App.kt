package ru.ratatoskr.project_3.presentation.di

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import ru.ratatoskr.project_3.data.impl.HeroesRepoImpl
import ru.ratatoskr.project_3.data.storage.RoomAppDatabase
import ru.ratatoskr.project_3.presentation.activity.MainActivity
import javax.inject.Inject
import javax.inject.Singleton

@HiltAndroidApp
class App: Application()