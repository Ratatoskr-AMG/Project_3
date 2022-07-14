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
    val getAllHeroesByAttrUseCase: GetAllHeroesByAttrUseCase,
    val getAllFavoriteHeroesUseCase: GetAllFavoriteHeroesUseCase,
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