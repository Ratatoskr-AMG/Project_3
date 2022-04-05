package ru.ratatoskr.project_3.presentation.viewmodels

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ratatoskr.project_3.domain.extensions.set
import ru.ratatoskr.project_3.domain.helpers.states.HeroesListState
import ru.ratatoskr.project_3.domain.useCases.favorites.GetAllFavoriteHeroesUseCase
import ru.ratatoskr.project_3.domain.useCases.heroes.*
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HeroesListViewModel @Inject constructor(
    val getAllHeroesSortByNameUseCase: GetAllHeroesSortByNameUseCase,
    val getAllHeroesFromOpendotaUseCase: GetAllHeroesFromOpendotaUseCase,
    val getAllHeroesByAttrUseCase: GetAllHeroesByAttrUseCase,
    val addHeroesUserCase: AddHeroesUserCase,
    val getAllFavoriteHeroesUseCase: GetAllFavoriteHeroesUseCase,
    val getAllHeroesByRoleUseCase: GetAllHeroesByRoleUseCase,
) : AndroidViewModel(Application()) {

    val _heroesList_state: MutableLiveData<HeroesListState> =
        MutableLiveData<HeroesListState>(HeroesListState.LoadingHeroesListState())
    val heroesListState: LiveData<HeroesListState> = _heroesList_state

    fun switchAttrSortDirection(attr: String, sortAsc: Boolean) {
        Log.e("TOHA_test", "switchAttrSortDirection")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val heroes = getAllHeroesByAttrUseCase.getAllHeroesByAttr(attr, sortAsc)
                _heroesList_state.postValue(
                    HeroesListState.LoadedHeroesListState(
                        heroes,
                        "",
                        sortAsc
                    )
                )
            } catch (e: java.lang.Exception) {
                Log.e("TOHA", "e:" + e.toString())
                e.printStackTrace()
            }

        }
    }

    fun getAllHeroesSortByName(appSharedPreferences:SharedPreferences) {
        _heroesList_state.set(HeroesListState.LoadingHeroesListState())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val heroes = getAllHeroesSortByNameUseCase.getAllHeroesSortByName()
                if (heroes.isEmpty()) {
                    getAllHeroesFromApi(appSharedPreferences)
                } else {
                    _heroesList_state.postValue(
                        HeroesListState.LoadedHeroesListState(
                            heroes,
                            "",
                            false
                        )
                    )
                }
            } catch (e: java.lang.Exception) {
                Log.e("TOHA", "e:" + e.toString())
                e.printStackTrace()
            }
        }
    }

    fun getAllHeroesByStrSortByName(str: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val heroes = getAllHeroesSortByNameUseCase.getAllHeroesByStrSortByName(str)
                if (!heroes.isEmpty()) {
                    _heroesList_state.postValue(
                        HeroesListState.LoadedHeroesListState(
                            heroes,
                            str,
                            false
                        )
                    )
                }
            } catch (e: java.lang.Exception) {
                Log.e("TOHA", "e:" + e.toString())
                e.printStackTrace()
            }
        }
    }

    suspend fun getAllHeroesFromApi(opendotaUpdatePreferences: SharedPreferences) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.e("TOHA", "getAllHeroesFromApi")
                var heroes = getAllHeroesFromOpendotaUseCase.getAllHeroesFromApi()

                if (heroes.isEmpty()) {
                    _heroesList_state.postValue(HeroesListState.NoHeroesListState("Empty Heroes from API list"))
                } else {
                    addHeroesUserCase.addHeroes(heroes)

                    var date = Date(System.currentTimeMillis())
                    opendotaUpdatePreferences.edit().putLong("time", date.time).apply();
                    Log.e("TOHA","update time:"+date.time)

                    _heroesList_state.postValue(
                        HeroesListState.LoadedHeroesListState(
                            heroes = heroes.sortedBy { it.localizedName },
                            "",
                            false
                        )
                    )
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
                val heroes = getAllHeroesByAttrUseCase.getAllHeroesByAttr(attr, false)
                if (heroes.isEmpty()) {
                    _heroesList_state.postValue(HeroesListState.NoHeroesListState("Empty Heroes by attr list"))
                } else {
                    _heroesList_state.postValue(
                        HeroesListState.LoadedHeroesListState(
                            heroes = heroes,
                            "",
                            false
                        )
                    )
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
                val heroes = getAllFavoriteHeroesUseCase.getAllFavoriteHeroesUseCase()
                if (heroes.isEmpty()) {
                    _heroesList_state.postValue(HeroesListState.NoHeroesListState("Empty favorite heroes list"))
                } else {
                    _heroesList_state.postValue(
                        HeroesListState.LoadedHeroesListState(
                            heroes = heroes,
                            "",
                            false
                        )
                    )
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getAllHeroesByRole(role: String) {
        _heroesList_state.set(newValue = HeroesListState.LoadingHeroesListState())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val heroes = getAllHeroesByRoleUseCase.getAllHeroesByRole(role)

                if (heroes.isEmpty()) {
                    _heroesList_state.postValue(HeroesListState.NoHeroesListState("Empty Heroes by attr list"))
                } else {

                    _heroesList_state.postValue(
                        HeroesListState.LoadedHeroesListState(
                            heroes = heroes,
                            "",
                            false
                        )
                    )
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }
}