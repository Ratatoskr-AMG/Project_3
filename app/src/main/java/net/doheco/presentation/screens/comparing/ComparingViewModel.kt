package net.doheco.presentation.screens.comparing

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.doheco.domain.extensions.set
import net.doheco.domain.model.Hero
import net.doheco.domain.useCases.favorites.GetAllFavoriteHeroesUseCase
import net.doheco.domain.useCases.heroes.CurrentInfoBlockUseCase
import net.doheco.domain.useCases.heroes.GetAllHeroesSortByNameUseCase
import net.doheco.domain.useCases.heroes.GetHeroByIdUseCase
import net.doheco.domain.utils.EventHandler
import net.doheco.presentation.screens.comparing.models.ComparingEvent
import net.doheco.presentation.screens.comparing.models.ComparingState
import javax.inject.Inject

@HiltViewModel


class ComparingViewModel @Inject constructor(
    appSharedPreferences: SharedPreferences,
    val getAllHeroesSortByNameUseCase: GetAllHeroesSortByNameUseCase,
    val getHeroByIdUseCase: GetHeroByIdUseCase,
    val getAllFavoriteHeroesUseCase: GetAllFavoriteHeroesUseCase,
    val currentInfoBlockUseCase: CurrentInfoBlockUseCase,

    ): ViewModel(), EventHandler<ComparingEvent> {

    var appSharedPreferences = appSharedPreferences
    private val _comparingState: MutableLiveData<ComparingState> = MutableLiveData<ComparingState>()
    val comparingState: LiveData<ComparingState> = _comparingState

    override fun obtainEvent(event: ComparingEvent) {
        when (val currentState = _comparingState.value) {
            is ComparingState.HeroesState -> reduce(event, currentState)
        }
    }

    private fun reduce(event: ComparingEvent, currentState: ComparingState.HeroesState) {
        when (event) {
            is ComparingEvent.OnInfoBlockSelect -> selectInfoBlock(
                currentState.left,
                currentState.right,
                currentState.heroes,
                currentState.favoriteHeroes,
                event.infoBlockName,
            )

        }
    }

    fun setLeftSelectedComparingHeroToSP(id: String){
        Log.e("TOHA","setRight:"+id)
        appSharedPreferences.edit().putString("comparing_left", id).apply();
    }

    fun setRightSelectedComparingHeroToSP(id: String){
        Log.e("TOHA","setRight:"+id)
        appSharedPreferences.edit().putString("comparing_right", id).apply();
    }

    private fun selectInfoBlock(left: Hero, right: Hero, heroes: List<Hero>, favoriteHeroes: List<Hero>,newInfoBlock:String) {

        currentInfoBlockUseCase.setCurrentInfoBlockComparing(appSharedPreferences,newInfoBlock)

        viewModelScope.launch {
            _comparingState.postValue(
                ComparingState.HeroesState(
                    left = left,
                    right = right,
                    heroes = heroes,
                    favoriteHeroes = favoriteHeroes,
                    currentInfoBlock = newInfoBlock
                )
            )
        }
    }

    fun setHeroesState(left: Hero? = null, right: Hero? = null) {
        _comparingState.set(newValue = ComparingState.LoadingState())

        val currentInfoBlock = currentInfoBlockUseCase.getCurrentInfoBlockComparing(appSharedPreferences)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val heroes = getAllHeroesSortByNameUseCase.getAllHeroesSortByName()
                if (left != null && right != null) {
                    setStateHeroesState(left, right, heroes,currentInfoBlock)
                } else {
                    var hero1 = heroes.filter { it.id == 1 }[0]
                    var hero2 = heroes.filter { it.id == 2 }[0]
                    var curr_left = appSharedPreferences.getString("comparing_left", "undefined")
                    var curr_right = appSharedPreferences.getString("comparing_right", "undefined")
                    Log.e("TOHA","curr_left:"+curr_left)
                    Log.e("TOHA","curr_right:"+curr_right)
                    if(curr_left!="undefined"){
                        var heroesFilter=heroes.filter { it.id.toString() == curr_left}
                        hero1=heroesFilter[0]
                    }
                    if(curr_right!="undefined"){
                        var heroesFilter=heroes.filter { it.id.toString() == curr_right}
                        hero2=heroesFilter[0]
                    }
                    setStateHeroesState(hero1, hero2, heroes,currentInfoBlock)
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

    fun setStateHeroesState(hero1: Hero, hero2: Hero, heroes: List<Hero>,currentInfoBlock:String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val favoriteHeroes = getAllFavoriteHeroesUseCase.getAllFavoriteHeroesUseCase()
                _comparingState.postValue(
                    ComparingState.HeroesState(hero1, hero2, heroes,favoriteHeroes,currentInfoBlock)
                )
            } catch (e: java.lang.Exception) {
                Log.e("TOHA", "e:" + e.toString())
                e.printStackTrace()
            }
        }
    }

}

