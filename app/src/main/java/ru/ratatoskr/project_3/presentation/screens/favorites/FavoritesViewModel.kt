package ru.ratatoskr.project_3.presentation.screens.favorites

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ratatoskr.project_3.domain.extensions.set
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.domain.useCases.favorites.GetAllFavoriteHeroesUseCase
import ru.ratatoskr.project_3.domain.useCases.heroes.*
import ru.ratatoskr.project_3.presentation.screens.favorites.models.FavoritesState
import java.util.*
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    appSharedPreferences: SharedPreferences,
    val getAllHeroesSortByNameUseCase: GetAllHeroesSortByNameUseCase,
    val getAllHeroesFromOpendotaUseCase: GetAllHeroesFromOpendotaUseCase,
    val getAllHeroesByAttrUseCase: GetAllHeroesByAttrUseCase,
    val addHeroesUserCase: AddHeroesUserCase,
    val getAllFavoriteHeroesUseCase: GetAllFavoriteHeroesUseCase,
    val getAllHeroesByRoleUseCase: GetAllHeroesByRoleUseCase,
) : AndroidViewModel(Application()) {

    var appSharedPreferences = appSharedPreferences

    val _favoritesList_state: MutableLiveData<FavoritesState> =
        MutableLiveData<FavoritesState>(FavoritesState.LoadingHeroesState())
    val favoritesState: LiveData<FavoritesState> = _favoritesList_state

    fun switchAttrSortDirection(attr: String, sortAsc: Boolean) {
        Log.e("TOHA_test", "switchAttrSortDirection")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val heroes = getAllHeroesByAttrUseCase.getAllHeroesByAttr(attr, sortAsc)
                _favoritesList_state.postValue(
                    FavoritesState.LoadedHeroesState(
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

    fun getAllHeroesSortByName() {
        _favoritesList_state.set(FavoritesState.LoadingHeroesState())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val heroes = getAllHeroesByStrSortByName("")
                if (heroes!!.isEmpty()) {
                    getAllHeroesFromApi(appSharedPreferences)
                }
            } catch (e: java.lang.Exception) {
                Log.e("TOHA", "e:" + e.toString())
                e.printStackTrace()
            }
        }
    }

    fun getAllHeroesByStrSortByName(str: String): List<Hero>?  {
        lateinit var heroes : List<Hero>
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val heroes = getAllHeroesSortByNameUseCase.getAllHeroesByStrSortByName(str)
                if (!heroes.isEmpty()) {
                    _favoritesList_state.postValue(
                        FavoritesState.LoadedHeroesState(
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
        return heroes
    }

    suspend fun getAllHeroesFromApi(appSharedPreferences:SharedPreferences) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.e("TOHA", "getAllHeroesFromApi")
                var heroes = getAllHeroesFromOpendotaUseCase.getAllHeroesFromApi()

                if (heroes.isEmpty()) {
                    _favoritesList_state.postValue(FavoritesState.NoHeroesState("Empty Heroes from API list"))
                } else {
                    addHeroesUserCase.addHeroes(heroes)
                    appSharedPreferences.edit().putLong("heroes_list_last_modified", Date(System.currentTimeMillis()).time).apply();
                    Log.e("TOHA","All heroes updated at:"+Date(System.currentTimeMillis()).time)
                    _favoritesList_state.postValue(
                        FavoritesState.LoadedHeroesState(
                            heroes = heroes.sortedBy { it.localizedName },
                            "",
                            false
                        )
                    )
                }

            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                _favoritesList_state.postValue(FavoritesState.NoHeroesState(e.toString()))
            }
        }
    }



    fun getAllFavoriteHeroes() {
        _favoritesList_state.set(newValue = FavoritesState.LoadingHeroesState())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val heroes = getAllFavoriteHeroesUseCase.getAllFavoriteHeroesUseCase()
                if (heroes.isEmpty()) {
                    _favoritesList_state.postValue(FavoritesState.NoHeroesState("Empty favorite heroes list"))
                } else {
                    _favoritesList_state.postValue(
                        FavoritesState.LoadedHeroesState(
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