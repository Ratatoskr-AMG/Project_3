package ru.ratatoskr.project_3.presentation.viewmodels

import android.util.Log
import androidx.compose.compiler.plugins.kotlin.lower.forEachWith
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ratatoskr.project_3.domain.base.EventHandler
import ru.ratatoskr.project_3.domain.extensions.set
import ru.ratatoskr.project_3.domain.helpers.State
import ru.ratatoskr.project_3.domain.useCases.sqlite.GetAllHeroesByAttrUseCase
import ru.ratatoskr.project_3.domain.useCases.sqlite.GetAllHeroesByNameUseCase
import ru.ratatoskr.project_3.domain.useCases.opendota.GetAllHeroesFromOpendotaUseCase
import ru.ratatoskr.project_3.domain.useCases.sqlite.AddHeroesUserCase
import ru.ratatoskr.project_3.domain.useCases.sqlite.GetHeroByIdUseCase
import javax.inject.Inject



sealed class FavoriteViewState {
    object Loading : FavoriteViewState()
    object Error : FavoriteViewState()
    data class Display(val isAdded: Boolean) : FavoriteViewState()
    object NoItems: FavoriteViewState()
}

@HiltViewModel
class HeroesListViewModel @Inject constructor(
    val getAllHeroesByNameUseCase: GetAllHeroesByNameUseCase,
    val getAllHeroesFromOpendotaUseCase: GetAllHeroesFromOpendotaUseCase,
    val addHeroesUserCase: AddHeroesUserCase,
    val getHeroByIdUseCase: GetHeroByIdUseCase,
    val getAllHeroesByAttrUseCase: GetAllHeroesByAttrUseCase,
) : ViewModel(){

    val _state: MutableLiveData<State> = MutableLiveData<State>(State.LoadingState())
    val state: LiveData<State> = _state


    fun getAllHeroesByName() {
        _state.set(State.LoadingState())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val heroes = getAllHeroesByNameUseCase.getAllHeroesByName()
                if (heroes.isEmpty()) {
                    _state.postValue(State.NoItemsState())
                } else {
                    _state.postValue(State.LoadedState(data = heroes))
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun getAllHeroesFromApi() {
        _state.set(State.LoadingState())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val heroes = getAllHeroesFromOpendotaUseCase.getAllHeroesFromApi()
                addHeroesUserCase.addHeroes(heroes)
                if (heroes.isEmpty()) {
                    _state.postValue(State.NoItemsState())
                } else {
                    _state.postValue(State.LoadedState(data = heroes))

                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getHeroById(id: String) {
        _state.set(newValue = State.LoadingState())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val hero = getHeroByIdUseCase.GetHeroById(id)
                if (hero.id < 1) {
                    _state.postValue(State.NoItemsState())
                } else {
                    _state.postValue(State.HeroLoadedState(data = hero,false))
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getAllHeroesByAttr(attr: String) {
        _state.set(newValue = State.LoadingState())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val heroes = getAllHeroesByAttrUseCase.getAllHeroesByAttr(attr)
                if (heroes.isEmpty()) {
                    _state.postValue(State.NoItemsState())
                } else {
                    _state.postValue(State.LoadedState(data = heroes))
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }



}