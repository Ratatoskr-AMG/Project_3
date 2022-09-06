package ru.ratatoskr.doheco.presentation.screens.comparing

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ratatoskr.doheco.domain.extensions.set
import ru.ratatoskr.doheco.domain.model.Hero
import ru.ratatoskr.doheco.domain.useCases.heroes.GetAllHeroesByRoleUseCase
import ru.ratatoskr.doheco.domain.useCases.heroes.GetAllHeroesSortByNameUseCase
import ru.ratatoskr.doheco.domain.useCases.heroes.GetHeroByIdUseCase
import ru.ratatoskr.doheco.presentation.screens.comparing.models.ComparingState
import ru.ratatoskr.doheco.presentation.screens.hero.models.HeroState
import ru.ratatoskr.doheco.presentation.screens.role.models.RoleState
import javax.inject.Inject

@HiltViewModel
class ComparingViewModel @Inject constructor(
    val getAllHeroesSortByNameUseCase: GetAllHeroesSortByNameUseCase,
    val getHeroByIdUseCase: GetHeroByIdUseCase,
) : AndroidViewModel(Application()) {

    private val _comparingState: MutableLiveData<ComparingState> = MutableLiveData<ComparingState>()
    val comparingState: LiveData<ComparingState> = _comparingState

    fun getHeroById(id: String) {

        //comparingState.set(newValue = ComparingState.LoadingHeroState())

        viewModelScope.launch(Dispatchers.IO) {
/*
            try {
                val hero = getHeroByIdUseCase.GetHeroById(id)


                if (hero.id < 1) {
                    переносим в ЮзКейс трайкетч
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
 */

        }
    }

    fun setHeroesState(left: Hero? = null, right: Hero? = null) {
        _comparingState.set(newValue = ComparingState.LoadingState())

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val heroes = getAllHeroesSortByNameUseCase.getAllHeroesSortByName()
                if (left != null && right != null) {
                    setStateHeroesState(left, right,heroes)
                } else {
                    val hero1 = heroes.filter { it.id == 1 }[0]
                    val hero2 = heroes.filter { it.id == 2 }[0]
                    setStateHeroesState(hero1, hero2,heroes)
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    fun setSelectHeroesState(left: Hero, right: Hero, isLeft: Boolean) {
        _comparingState.set(newValue = ComparingState.LoadingState())
        viewModelScope.launch(Dispatchers.IO) {

            try {
                val heroes = getAllHeroesSortByNameUseCase.getAllHeroesSortByName()
                _comparingState.postValue(
                    ComparingState.SelectHeroesState(left, right, isLeft, heroes)
                )
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    fun setStateHeroesState(hero1: Hero, hero2: Hero,heroes:List<Hero>) {
        _comparingState.postValue(
            ComparingState.HeroesState(hero1, hero2,heroes)
        )
    }

}

