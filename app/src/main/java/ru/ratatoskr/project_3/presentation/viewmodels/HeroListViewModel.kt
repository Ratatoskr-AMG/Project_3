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
import ru.ratatoskr.project_3.domain.helpers.states.HeroListState
import ru.ratatoskr.project_3.domain.useCases.opendota.GetAllHeroesFromOpendotaUseCase
import ru.ratatoskr.project_3.domain.useCases.sqlite.favorites.GetAllFavoriteHeroesUseCase
import ru.ratatoskr.project_3.domain.useCases.sqlite.heroes.AddHeroesUserCase
import ru.ratatoskr.project_3.domain.useCases.sqlite.heroes.GetAllHeroesByAttrUseCase
import ru.ratatoskr.project_3.domain.useCases.sqlite.heroes.GetAllHeroesByNameUseCase
import javax.inject.Inject

@HiltViewModel
class HeroesListViewModel @Inject constructor(
    val getAllHeroesByNameUseCase: GetAllHeroesByNameUseCase,
    val getAllHeroesFromOpendotaUseCase: GetAllHeroesFromOpendotaUseCase,
    val getAllHeroesByAttrUseCase: GetAllHeroesByAttrUseCase,
    val addHeroesUserCase: AddHeroesUserCase,
    val GetAllFavoriteHeroesUseCase: GetAllFavoriteHeroesUseCase,
) : ViewModel() {

    val _heroList_state: MutableLiveData<HeroListState> = MutableLiveData<HeroListState>(HeroListState.LoadingHeroListState())
    val heroListState: LiveData<HeroListState> = _heroList_state

    fun getAllHeroesByName(){
        Log.e("TOHA","getAllHeroesByName")
        _heroList_state.set(HeroListState.LoadingHeroListState())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val heroes = getAllHeroesByNameUseCase.getAllHeroesByName()
                if (heroes.isEmpty()) {
                    Log.e("TOHA","isEmpty")
                    getAllHeroesFromApi()
                } else {
                    Log.e("TOHA","isNotEmpty")
                    _heroList_state.postValue(HeroListState.LoadedHeroListState(heroes))
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
                    _heroList_state.postValue(HeroListState.NoHeroListState("Empty Heroes from API list"))
                } else {
                    addHeroesUserCase.addHeroes(heroes)
                    _heroList_state.postValue(HeroListState.LoadedHeroListState(heroes = heroes.sortedBy { it.localizedName }))
                }

            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                _heroList_state.postValue(HeroListState.NoHeroListState(e.toString()))
            }
        }
    }

    fun getAllHeroesByAttr(attr: String) {
        _heroList_state.set(newValue = HeroListState.LoadingHeroListState())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val heroes = getAllHeroesByAttrUseCase.getAllHeroesByAttr(attr)
                if (heroes.isEmpty()) {
                    _heroList_state.postValue(HeroListState.NoHeroListState("Empty Heroes by attr list"))
                } else {
                    _heroList_state.postValue(HeroListState.LoadedHeroListState(heroes = heroes))
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getAllFavoriteHeroes() {
        _heroList_state.set(newValue = HeroListState.LoadingHeroListState())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val heroes = GetAllFavoriteHeroesUseCase.getAllFavoriteHeroesUseCase()
                if (heroes.isEmpty()) {
                    _heroList_state.postValue(HeroListState.NoHeroListState("Empty favorite heroes list"))
                } else {
                    _heroList_state.postValue(HeroListState.LoadedHeroListState(heroes = heroes))
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

}