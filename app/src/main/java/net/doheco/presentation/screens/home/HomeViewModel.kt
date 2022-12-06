package net.doheco.presentation.screens.home

import android.app.Application
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.*
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.ImageResult
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.doheco.domain.useCases.favorites.GetAllFavoriteHeroesUseCase
import net.doheco.domain.useCases.heroes.*
import net.doheco.domain.useCases.user.GetPlayerIdFromSP
import net.doheco.domain.useCases.user.SetUUIdToSPUseCase
import net.doheco.presentation.screens.home.models.HomeState
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private var mFirebaseAnalytics: FirebaseAnalytics,
    var imageLoader: ImageLoader,
    var appSharedPreferences: SharedPreferences,
    val getAllHeroesSortByNameUseCase: GetAllHeroesSortByNameUseCase,
    private val getAllHeroesFromOpendotaUseCase: GetAllHeroesFromOpendotaUseCase,
    val getAllHeroesByAttrUseCase: GetAllHeroesByAttrUseCase,
    private val addHeroesUserCase: AddHeroesUserCase,
    val getAllFavoriteHeroesUseCase: GetAllFavoriteHeroesUseCase,
    private val getPlayerIdFromSP: GetPlayerIdFromSP,
    val getAllHeroesByRoleUseCase: GetAllHeroesByRoleUseCase,
    val setUUIdToSPUseCase: SetUUIdToSPUseCase,
) : AndroidViewModel(Application()) {

    fun setPlayerUUIdToSPIfNeeded(){
        if(getPlayerIdFromSP.getUUIdFromSP(appSharedPreferences).isEmpty()){
            var UUId = UUID.randomUUID().toString()
            setUUIdToSPUseCase.SetUUIdToSP(appSharedPreferences,UUId)
        }
    }

    private val _heroesListState: MutableLiveData<HomeState> = MutableLiveData<HomeState>()
    val homeState: LiveData<HomeState> = _heroesListState


    init {
        setPlayerUUIdToSPIfNeeded()
        getAllHeroesSortByName()
    }

    fun getAllHeroesSortByName() {
        Log.e("WHAT", "get heroes")
        //_heroesListState.value = HomeState.LoadingHomeState()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val heroes = getAllHeroesSortByNameUseCase.getAllHeroesSortByName()
                if (heroes.isEmpty()) {
                    getAllHeroesFromApi(appSharedPreferences)
                } else {
                    getAllHeroesByStrSortByName("")
                }
            } catch (e: java.lang.Exception) {
                Log.e("TOHA", "e:$e")
                e.printStackTrace()
            }
        }
    }

    fun getAllHeroesByStrSortByName(str: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val heroes = getAllHeroesSortByNameUseCase.getAllHeroesByStrSortByName(str)
                val favoriteHeroes = getAllFavoriteHeroesUseCase.getAllFavoriteHeroesUseCase()
                if (heroes.isNotEmpty()) {
                    _heroesListState.postValue(
                        HomeState.LoadedHomeState(
                            heroes,
                            str,
                            false,
                            favoriteHeroes
                        )
                    )
                }
            } catch (e: java.lang.Exception) {
                Log.e("TOHA", "e:$e")
                e.printStackTrace()
            }
        }
    }



    private suspend fun getAllHeroesFromApi(appSharedPreferences: SharedPreferences) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.e("DOHECO", "getAllHeroesFromApi")
                val heroes = getAllHeroesFromOpendotaUseCase.getAllHeroesFromApi("init")
                val favoriteHeroes = getAllFavoriteHeroesUseCase.getAllFavoriteHeroesUseCase()

                if (heroes.isEmpty()) {
                    Log.e("DOHECO", "heroes isEmpty")
                    _heroesListState.postValue(HomeState.NoHomeState("Empty Heroes from API list"))
                } else {
                    Log.e("DOHECO", "heroes isNotEmpty")
                    addHeroesUserCase.addHeroes(heroes)
                    appSharedPreferences.edit()
                        .putLong("heroes_list_last_modified", Date(System.currentTimeMillis()).time)
                        .apply()
                    Log.e("DOHECO", "All heroes updated at:" + Date(System.currentTimeMillis()).time)
                    _heroesListState.postValue(
                        HomeState.LoadedHomeState(
                            heroes = heroes.sortedBy { it.localizedName },
                            "",
                            false,
                            favoriteHeroes
                        )
                    )
                }

            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                _heroesListState.postValue(HomeState.NoHomeState(e.toString()))
            }
        }
    }

}