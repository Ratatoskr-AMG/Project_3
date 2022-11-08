package net.doheco.presentation.screens.favorites

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.doheco.domain.extensions.set
import net.doheco.domain.model.Hero
import net.doheco.domain.useCases.favorites.DropHeroFromFavoritesUseCase
import net.doheco.domain.useCases.favorites.GetAllFavoriteHeroesUseCase
import net.doheco.domain.useCases.heroes.*
import net.doheco.presentation.screens.favorites.models.FavoritesState

import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    appSharedPreferences: SharedPreferences,
    val getAllHeroesByAttrUseCase: GetAllHeroesByAttrUseCase,
    val getAllFavoriteHeroesUseCase: GetAllFavoriteHeroesUseCase,
    private val dropHeroFromFavorites: DropHeroFromFavoritesUseCase,
) : AndroidViewModel(Application()) {

    var appSharedPreferences = appSharedPreferences

    val _favoritesList_state: MutableLiveData<FavoritesState> =
        MutableLiveData<FavoritesState>(FavoritesState.LoadingHeroesState())
    val favoritesState: LiveData<FavoritesState> = _favoritesList_state

    fun getAllFavoriteHeroes() {
        _favoritesList_state.set(newValue = FavoritesState.LoadingHeroesState())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val heroes = getAllFavoriteHeroesUseCase.getAllFavoriteHeroesUseCase()
                if (heroes.isEmpty()) {
                    _favoritesList_state.postValue(FavoritesState.NoHeroesState("Empty favorite heroes list"))
                } else {
                    _favoritesList_state.postValue(
                        FavoritesState.LoadedHeroesState<Hero>(
                            heroes = heroes
                        )
                    )
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }


    fun removeFromFavorites(hero: Hero, heroes:List<Hero>) {
        viewModelScope.launch {
            try {
                dropHeroFromFavorites.dropHeroFromFavorites(hero.id)
                if(heroes.isNotEmpty()) {
                    _favoritesList_state.postValue(
                        FavoritesState.LoadedHeroesState<Hero>(
                            heroes = heroes,
                        )
                    )
                }else{
                    _favoritesList_state.postValue(
                        FavoritesState.NoHeroesState("No heroes found")
                    )
                }

            } catch (e: Exception) {
                _favoritesList_state.postValue(FavoritesState.ErrorHeroesState(e.message.toString()))
            }

        }

    }
}