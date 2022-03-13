package ru.ratatoskr.project_3.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ratatoskr.project_3.domain.base.EventHandler
import ru.ratatoskr.project_3.domain.extensions.set
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.domain.useCases.sqlite.DropHeroFromFavorites
import ru.ratatoskr.project_3.domain.useCases.sqlite.GetHeroByIdUseCase
import ru.ratatoskr.project_3.domain.useCases.sqlite.GetIfHeroIsFavoriteUseCase
import ru.ratatoskr.project_3.domain.useCases.sqlite.InsertHeroesUseCase
import javax.inject.Inject

sealed class HeroState {
    class NoHeroState : HeroState()
    class LoadingHeroState : HeroState()
    class ErrorHeroState : HeroState()
    data class HeroLoadedState(
        val hero: Hero,
        val isFavorite: Boolean,
    ) : HeroState()
}

sealed class HeroEvent {
    data class OnFavoriteCLick(val heroId: Int, val newValue: Boolean) : HeroEvent()
}

@HiltViewModel
class HeroViewModel @Inject constructor(
    private val getHeroByIdUseCase: GetHeroByIdUseCase,
    private val getIfHeroIsFavoriteUseCase: GetIfHeroIsFavoriteUseCase,
    private val dropHeroFromFavorites: DropHeroFromFavorites,
    private val insertHeroesUseCase: InsertHeroesUseCase
) : ViewModel(), EventHandler<HeroEvent> {

    var isHeroFavorite = false
    val _hero_state: MutableLiveData<HeroState> = MutableLiveData<HeroState>()
    val hero_state: LiveData<HeroState> = _hero_state

    override fun obtainEvent(event: HeroEvent) {

        when (val currentState = _hero_state.value) {
            is HeroState.HeroLoadedState -> reduce(event, currentState)
        }

    }

    private fun reduce(event: HeroEvent, currentState: HeroState.HeroLoadedState) {

        Log.e("TOHA", "reduce")

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
                    _hero_state.postValue(
                        HeroState.HeroLoadedState(
                            hero = hero,
                            isFavorite = false
                        )
                    )
                    isHeroFavorite=false

                } catch (e: Exception) {
                    _hero_state.postValue(HeroState.ErrorHeroState())
                }
            }else{
                try {
                    insertHeroesUseCase.insertHeroToFavorites(hero.id)
                    _hero_state.postValue(
                        HeroState.HeroLoadedState(
                            hero = hero,
                            isFavorite = true
                        )
                    )
                    isHeroFavorite=true
                } catch (e: Exception) {
                    _hero_state.postValue(HeroState.ErrorHeroState())
                }
            }
        }

    }


    fun getHeroById(id: String) {
        Log.e("TOHA","getHeroById");
        _hero_state.set(newValue = HeroState.LoadingHeroState())

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val hero = getHeroByIdUseCase.GetHeroById(id)
                var isFavorite = getIfHeroIsFavoriteUseCase.getIfHeroIsFavoriteById(hero.id)
                isHeroFavorite = isFavorite
                if (hero.id < 1) {
                    _hero_state.postValue(HeroState.NoHeroState())
                } else {
                    _hero_state.postValue(
                        HeroState.HeroLoadedState(
                            hero = hero,
                            isFavorite = isFavorite
                        )
                    )
                }
            } catch (e: java.lang.Exception) {
                Log.e("TOHA","e:"+e.toString());
                e.printStackTrace()
            }
        }
    }


}