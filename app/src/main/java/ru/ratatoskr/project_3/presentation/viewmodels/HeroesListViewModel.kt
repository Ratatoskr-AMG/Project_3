package ru.ratatoskr.project_3.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ratatoskr.project_3.domain.repository.HeroesRepoImpl
import ru.ratatoskr.project_3.domain.extensions.set
import ru.ratatoskr.project_3.domain.helpers.State
import ru.ratatoskr.project_3.domain.useCases.GetAllHeroesByNameUseCase
import ru.ratatoskr.project_3.domain.useCases.GetAllHeroesFromApiUseCase
import ru.ratatoskr.project_3.domain.useCases.GetHeroByIdUseCase
import javax.inject.Inject

@HiltViewModel
class HeroesListViewModel @Inject constructor(val repository: HeroesRepoImpl) : ViewModel() {
    val state: MutableLiveData<State> = MutableLiveData<State>(State.LoadingState())

    fun getAllHeroesByName() {
        state.set(State.LoadingState())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                //val heroesAPI = GetAllHeroesFromApiUseCase(repository).getAllHeroesFromApi()
                val heroes = GetAllHeroesByNameUseCase(repository).getAllHeroesByName()
                if (heroes.isEmpty()) {
                    state.postValue(State.NoItemsState())
                } else {
                    state.postValue(State.LoadedState(data = heroes))
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun getAllHeroesFromApi() {
        state.set(State.LoadingState())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val heroes = GetAllHeroesFromApiUseCase(repository).getAllHeroesFromApi()
                if (heroes.isEmpty()) {
                    state.postValue(State.NoItemsState())
                } else {
                    state.postValue(State.LoadedState(data = heroes))
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }
    suspend fun getHeroById(id: String) {
        state.set(newValue = State.LoadingState())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val hero = GetHeroByIdUseCase(repository).GetHeroById(id)
                if (hero.id < 1) {
                    state.postValue(State.NoItemsState())
                } else {
                    state.postValue(State.HeroLoadedState(data = hero))
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }


}