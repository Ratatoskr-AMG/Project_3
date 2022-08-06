package ru.ratatoskr.project_3.presentation.screens.home

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.ImageResult
import com.google.android.exoplayer2.SimpleExoPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ratatoskr.project_3.domain.extensions.set
import ru.ratatoskr.project_3.presentation.screens.home.models.HomeState
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.domain.useCases.favorites.GetAllFavoriteHeroesUseCase
import ru.ratatoskr.project_3.domain.useCases.heroes.*
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    imageLoader: ImageLoader,
    appSharedPreferences: SharedPreferences,
    val getAllHeroesSortByNameUseCase: GetAllHeroesSortByNameUseCase,
    val getAllHeroesFromOpendotaUseCase: GetAllHeroesFromOpendotaUseCase,
    val getAllHeroesByAttrUseCase: GetAllHeroesByAttrUseCase,
    val addHeroesUserCase: AddHeroesUserCase,
    val getAllFavoriteHeroesUseCase: GetAllFavoriteHeroesUseCase,
    val getAllHeroesByRoleUseCase: GetAllHeroesByRoleUseCase,
) : AndroidViewModel(Application()) {

    var appSharedPreferences = appSharedPreferences
    var imageLoader = imageLoader

    val _heroesList_state: MutableLiveData<HomeState> =
        MutableLiveData<HomeState>(HomeState.LoadingHomeState())
    val homeState: LiveData<HomeState> = _heroesList_state

    fun getAllHeroesSortByName() {
        _heroesList_state.set(HomeState.LoadingHomeState())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val heroes = getAllHeroesSortByNameUseCase.getAllHeroesSortByName()
                if (heroes!!.isEmpty()) {
                    getAllHeroesFromApi(appSharedPreferences)
                }else {
                    getAllHeroesByStrSortByName("")
                }
            } catch (e: java.lang.Exception) {
                Log.e("TOHA", "e:" + e.toString())
                e.printStackTrace()
            }
        }
    }

    fun getAllHeroesByStrSortByName(str: String)  {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val heroes = getAllHeroesSortByNameUseCase.getAllHeroesByStrSortByName(str)
                if (!heroes.isEmpty()) {
                    _heroesList_state.postValue(
                        HomeState.LoadedHomeState(
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

    fun saveHeroImage(request:ImageRequest){
        viewModelScope.launch(Dispatchers.IO) {
            imageLoader.execute(request)
        }
    }

    fun getHeroImage(request:ImageRequest) : ImageResult {
        lateinit var imageres : ImageResult
        viewModelScope.launch(Dispatchers.IO) {
            imageres = imageLoader.execute(request)
        }
        return imageres
    }

    suspend fun getAllHeroesFromApi(appSharedPreferences:SharedPreferences) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.e("TOHA", "getAllHeroesFromApi")
                var heroes = getAllHeroesFromOpendotaUseCase.getAllHeroesFromApi()

                if (heroes.isEmpty()) {
                    _heroesList_state.postValue(HomeState.NoHomeState("Empty Heroes from API list"))
                } else {
                    addHeroesUserCase.addHeroes(heroes)
                    appSharedPreferences.edit().putLong("heroes_list_last_modified", Date(System.currentTimeMillis()).time).apply();
                    Log.e("TOHA","All heroes updated at:"+Date(System.currentTimeMillis()).time)
                    _heroesList_state.postValue(
                        HomeState.LoadedHomeState(
                            heroes = heroes.sortedBy { it.localizedName },
                            "",
                            false
                        )
                    )
                }

            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                _heroesList_state.postValue(HomeState.NoHomeState(e.toString()))
            }
        }
    }

}