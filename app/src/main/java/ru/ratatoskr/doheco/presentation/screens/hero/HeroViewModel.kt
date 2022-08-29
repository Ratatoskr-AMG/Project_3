package ru.ratatoskr.doheco.presentation.screens.hero

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ratatoskr.doheco.domain.utils.EventHandler
import ru.ratatoskr.doheco.domain.extensions.set
import ru.ratatoskr.doheco.presentation.screens.hero.models.HeroEvent
import ru.ratatoskr.doheco.presentation.screens.hero.models.HeroState
import ru.ratatoskr.doheco.domain.model.Hero
import ru.ratatoskr.doheco.domain.useCases.favorites.InsertHeroToFavoritesUseCase
import ru.ratatoskr.doheco.domain.useCases.favorites.DropHeroFromFavoritesUseCase
import ru.ratatoskr.doheco.domain.useCases.heroes.GetHeroByIdUseCase
import ru.ratatoskr.doheco.domain.useCases.favorites.GetIfHeroIsFavoriteUseCase
import javax.inject.Inject

@HiltViewModel
class HeroViewModel @Inject constructor(
    private val getHeroByIdUseCase: GetHeroByIdUseCase,
    private val getIfHeroIsFavoriteUseCase: GetIfHeroIsFavoriteUseCase,
    private val dropHeroFromFavorites: DropHeroFromFavoritesUseCase,
    private val insertHeroToFavoritesUseCase: InsertHeroToFavoritesUseCase
) : ViewModel(), EventHandler<HeroEvent> {

    private val _heroState: MutableLiveData<HeroState> = MutableLiveData<HeroState>()
    val heroState: LiveData<HeroState> = _heroState

    fun getHeroById(id: String) {

        _heroState.set(newValue = HeroState.LoadingHeroState())

        viewModelScope.launch(Dispatchers.IO) {

            try {
                val hero = getHeroByIdUseCase.GetHeroById(id)


                if (hero.id < 1) {
                    //переносим в ЮзКейс трайкетч
                    _heroState.postValue(HeroState.NoHeroState())
                } else {
                    _heroState.postValue(
                        HeroState.HeroLoadedState(
                            hero = hero,
                            isFavorite = getIfHeroIsFavoriteUseCase.getIfHeroIsFavoriteById(hero.id)
                        )
                    )
                }
            } catch (e: java.lang.Exception) {
                /*
                Здесь следует измененять состояние, а не просто печатать в лог
                 */
                Log.e("TOHA", "getHeroById exception:" + e.toString());
                e.printStackTrace()
            }
        }
    }

    override fun obtainEvent(event: HeroEvent) {
        when (val currentState = _heroState.value) {
            is HeroState.HeroLoadedState -> reduce(event, currentState)
        }
    }

    private fun reduce(event: HeroEvent, currentState: HeroState.HeroLoadedState) {
        when (event) {
            is HeroEvent.OnFavoriteCLick -> isFavoriteSwitch(
                currentState.hero,
                currentState.isFavorite
            )
        }
    }

    private fun isFavoriteSwitch(hero: Hero, isFavorite: Boolean = false) {
        viewModelScope.launch {
            if (isFavorite) {
                try {
                    dropHeroFromFavorites.dropHeroFromFavorites(hero.id)
                    _heroState.postValue(
                        HeroState.HeroLoadedState(
                            hero = hero,
                            isFavorite = false
                        )
                    )
                } catch (e: Exception) {
                    _heroState.postValue(HeroState.ErrorHeroState())
                }
            } else {
                try {
                    insertHeroToFavoritesUseCase.insertHeroToFavorites(hero.id)
                    _heroState.postValue(
                        HeroState.HeroLoadedState(
                            hero = hero,
                            isFavorite = true
                        )
                    )
                } catch (e: Exception) {
                    _heroState.postValue(HeroState.ErrorHeroState())
                }
            }
        }

    }

}