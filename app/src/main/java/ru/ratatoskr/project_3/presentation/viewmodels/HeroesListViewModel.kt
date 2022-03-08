package ru.ratatoskr.project_3.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ratatoskr.project_3.domain.extensions.set
import ru.ratatoskr.project_3.domain.helpers.State
import ru.ratatoskr.project_3.domain.repository.heroes.HeroesSqliteRepoImpl
import ru.ratatoskr.project_3.domain.repository.heroes.HeroesOpendotaRepoImpl
import ru.ratatoskr.project_3.domain.useCases.sqlite.GetAllHeroesByAttrUseCase
import ru.ratatoskr.project_3.domain.useCases.sqlite.GetAllHeroesByNameUseCase
import ru.ratatoskr.project_3.domain.useCases.opendota.GetAllHeroesFromOpendotaUseCase
import ru.ratatoskr.project_3.domain.useCases.sqlite.GetHeroByIdUseCase
import javax.inject.Inject

@HiltViewModel
class HeroesListViewModel @Inject constructor(
    val getAllHeroesByNameUseCase: GetAllHeroesByNameUseCase,
    val getAllHeroesFromOpendotaUseCase: GetAllHeroesFromOpendotaUseCase,
    val getHeroByIdUseCase: GetHeroByIdUseCase,
    val getAllHeroesByAttrUseCase: GetAllHeroesByAttrUseCase,
) : ViewModel() {
    val state: MutableLiveData<State> = MutableLiveData<State>(State.LoadingState())

    fun getAllHeroesByName() {
        state.set(State.LoadingState())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val heroes = getAllHeroesByNameUseCase.getAllHeroesByName()
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
                //val heroes = GetAllHeroesFromOpendotaUseCase(opendotaRepoImpl).getAllHeroesFromApi()
                val heroes = getAllHeroesFromOpendotaUseCase.getAllHeroesFromApi()
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
    fun getHeroById(id: String) {
        state.set(newValue = State.LoadingState())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val hero = getHeroByIdUseCase.GetHeroById(id)
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

    fun getAllHeroesByAttr(attr: String) {
        state.set(newValue = State.LoadingState())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val heroes = getAllHeroesByAttrUseCase.getAllHeroesByAttr(attr)
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


}