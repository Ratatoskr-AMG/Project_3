package ru.ratatoskr.project_3.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ratatoskr.project_3.domain.extensions.set
import ru.ratatoskr.project_3.domain.useCases.opendota.GetAllHeroesFromOpendotaUseCase
import ru.ratatoskr.project_3.domain.useCases.sqlite.favorites.GetAllFavoriteHeroesUseCase
import ru.ratatoskr.project_3.domain.useCases.sqlite.heroes.AddHeroesUserCase
import ru.ratatoskr.project_3.domain.useCases.sqlite.heroes.GetAllHeroesByAttrUseCase
import ru.ratatoskr.project_3.domain.useCases.sqlite.heroes.GetAllHeroesByNameUseCase
import javax.inject.Inject

sealed class HeroesListState {
    class LoadingHeroesListState : HeroesListState()
    class LoadedHeroesListState<T>(val heroes: List<T>) : HeroesListState()
    class HeroLoadedHeroesListState<Hero>(val heroes: Hero) : HeroesListState()
    class NoHeroesListState(val message: String) : HeroesListState()
    class ErrorHeroesListState(val message: String) : HeroesListState()
}

@HiltViewModel
class HeroesListViewModel @Inject constructor(
    val getAllHeroesByNameUseCase: GetAllHeroesByNameUseCase,
    val getAllHeroesFromOpendotaUseCase: GetAllHeroesFromOpendotaUseCase,
    val getAllHeroesByAttrUseCase: GetAllHeroesByAttrUseCase,
    val addHeroesUserCase: AddHeroesUserCase,
    val GetAllFavoriteHeroesUseCase: GetAllFavoriteHeroesUseCase,
) : ViewModel() {

    val _heroesList_state: MutableLiveData<HeroesListState> = MutableLiveData<HeroesListState>(HeroesListState.LoadingHeroesListState())
    val heroesListState: LiveData<HeroesListState> = _heroesList_state

    fun getAllHeroesByName(){
        Log.e("TOHA","getAllHeroesByName")
        _heroesList_state.set(HeroesListState.LoadingHeroesListState())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val heroes = getAllHeroesByNameUseCase.getAllHeroesByName()
                if (heroes.isEmpty()) {
                    Log.e("TOHA","isEmpty")
                    getAllHeroesFromApi()
                } else {
                    Log.e("TOHA","isNotEmpty")
                    _heroesList_state.postValue(HeroesListState.LoadedHeroesListState(heroes))
                }
            } catch (e: java.lang.Exception) {
                Log.e("TOHA","e:"+e.toString())
                e.printStackTrace()
            }
        }
    }

    suspend fun getAllHeroesFromApi() {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.e("TOHA","getAllHeroesFromApi")
                var heroes = getAllHeroesFromOpendotaUseCase.getAllHeroesFromApi()

                if (heroes.isEmpty()) {
                    _heroesList_state.postValue(HeroesListState.NoHeroesListState("Empty Heroes from API list"))
                } else {
                    addHeroesUserCase.addHeroes(heroes)
                    _heroesList_state.postValue(HeroesListState.LoadedHeroesListState(heroes = heroes.sortedBy { it.localizedName }))
                }

            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                _heroesList_state.postValue(HeroesListState.NoHeroesListState(e.toString()))
            }
        }
    }

    fun getAllHeroesByAttr(attr: String) {
        _heroesList_state.set(newValue = HeroesListState.LoadingHeroesListState())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val heroes = getAllHeroesByAttrUseCase.getAllHeroesByAttr(attr)
                if (heroes.isEmpty()) {
                    _heroesList_state.postValue(HeroesListState.NoHeroesListState("Empty Heroes by attr list"))
                } else {
                    _heroesList_state.postValue(HeroesListState.LoadedHeroesListState(heroes = heroes))
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getAllFavoriteHeroes() {
        _heroesList_state.set(newValue = HeroesListState.LoadingHeroesListState())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val heroes = GetAllFavoriteHeroesUseCase.getAllFavoriteHeroesUseCase()
                if (heroes.isEmpty()) {
                    _heroesList_state.postValue(HeroesListState.NoHeroesListState("Empty favorite heroes list"))
                } else {
                    _heroesList_state.postValue(HeroesListState.LoadedHeroesListState(heroes = heroes))
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }


}