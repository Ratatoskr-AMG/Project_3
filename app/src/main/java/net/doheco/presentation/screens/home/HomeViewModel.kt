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
    var appSharedPreferences: SharedPreferences,
    val getAllHeroesSortByNameUseCase: GetAllHeroesSortByNameUseCase,
    val getAllHeroesByAttrUseCase: GetAllHeroesByAttrUseCase,
    val getAllFavoriteHeroesUseCase: GetAllFavoriteHeroesUseCase,
    val UUIdSPUseCase: UUIdSPUseCase,
) : AndroidViewModel(Application()) {


    private val _heroesListState: MutableLiveData<HomeState> = MutableLiveData<HomeState>(HomeState.LoadingHomeState())
    val homeState: LiveData<HomeState> = _heroesListState


    init {
        getAllHeroesByStrSortByName("")
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
            } catch (e: Exception) {
                Log.e("TOHA", "e:$e")
                _heroesListState.postValue(
                    HomeState.ErrorHomeState(
                        message = e.toString()
                    )
                )
                e.printStackTrace()
            }
        }
    }
}