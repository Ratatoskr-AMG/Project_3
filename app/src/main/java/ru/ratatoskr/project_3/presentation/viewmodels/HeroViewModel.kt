package ru.ratatoskr.project_3.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ratatoskr.project_3.ReadMe
import ru.ratatoskr.project_3.domain.base.EventHandler
import ru.ratatoskr.project_3.domain.extensions.set
import ru.ratatoskr.project_3.domain.helpers.events.HeroEvent
import ru.ratatoskr.project_3.domain.helpers.states.HeroState
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.domain.useCases.sqlite.favorites.InsertHeroToFavoritesUseCase
import ru.ratatoskr.project_3.domain.useCases.sqlite.favorites.DropHeroFromFavorites
import ru.ratatoskr.project_3.domain.useCases.sqlite.heroes.GetHeroByIdUseCase
import ru.ratatoskr.project_3.domain.useCases.sqlite.favorites.GetIfHeroIsFavoriteUseCase
import javax.inject.Inject

@HiltViewModel
class HeroViewModel @Inject constructor(
    private val getHeroByIdUseCase: GetHeroByIdUseCase,
    private val getIfHeroIsFavoriteUseCase: GetIfHeroIsFavoriteUseCase,
    private val dropHeroFromFavorites: DropHeroFromFavorites,
    private val insertHeroToFavoritesUseCase: InsertHeroToFavoritesUseCase
) : ViewModel(), EventHandler<HeroEvent> {

    private val _heroState: MutableLiveData<HeroState> = MutableLiveData<HeroState>()
    val heroState: LiveData<HeroState> = _heroState

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
            }else{
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

    fun getHeroById(id: String) {

        _heroState.set(newValue = HeroState.LoadingHeroState())

        viewModelScope.launch(Dispatchers.IO) {

            try {
                val hero = getHeroByIdUseCase.GetHeroById(id)

                ReadMe.q1()
                if (hero.id < 1) {
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
                Log.e("TOHA","getHeroById exception:"+e.toString());
                e.printStackTrace()
            }
        }
    }
}