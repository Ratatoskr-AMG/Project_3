package ru.ratatoskr.project_3.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ratatoskr.project_3.data.impl.HeroesRepoImpl

class MainViewModelFactory(val repository: HeroesRepoImpl) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}