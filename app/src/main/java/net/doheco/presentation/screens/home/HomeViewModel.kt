package net.doheco.presentation.screens.home

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*
import coil.ImageLoader
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.doheco.domain.useCases.favorites.GetAllFavoriteHeroesUseCase
import net.doheco.domain.useCases.heroes.*
import net.doheco.domain.useCases.system.ServerApiCallUseCase
import net.doheco.domain.useCases.user.GetPlayerIdFromSP
import net.doheco.domain.useCases.user.UUIdSPUseCase
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
    val UUIdSPUseCase: UUIdSPUseCase,
    val serverApiCallUseCase: ServerApiCallUseCase,
) : AndroidViewModel(Application()) {

    fun setAppUUIdToSPIfNeeded(){
        if(UUIdSPUseCase.GetAppUUIdFromSP(appSharedPreferences).isEmpty()){
            var UUId = UUID.randomUUID().toString()
            UUIdSPUseCase.SetAppUUIdToSP(appSharedPreferences,UUId)
        }
    }

    private val _heroesListState: MutableLiveData<HomeState> = MutableLiveData<HomeState>()
    val homeState: LiveData<HomeState> = _heroesListState


    init {
        setAppUUIdToSPIfNeeded()
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
                    when (val state = _heroesListState.value) {
                        is HomeState.LoadedHomeState<*> -> {
                            Log.e("TOHA_FIX",state.searchStr)
                            getAllHeroesByStrSortByName(state.searchStr)
                        }
                        else->{
                            getAllHeroesByStrSortByName("")
                        }
                    }

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

        var UUId = UUIdSPUseCase.GetAppUUIdFromSP(appSharedPreferences)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.e("DOHECO", "getAllHeroesFromApi")

                var apiAnswer = serverApiCallUseCase.getHeroesAndMatches(UUId)
                Log.e("APICALL","base:"+apiAnswer.toString())

                //HEROES API CALL
                val heroes = getAllHeroesFromOpendotaUseCase.calculate(apiAnswer.heroes!!)
                val favoriteHeroes = getAllFavoriteHeroesUseCase.getAllFavoriteHeroesUseCase()

                if (heroes!!.isEmpty()) {
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