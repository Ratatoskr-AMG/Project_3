package ru.ratatoskr.project_3.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ratatoskr.project_3.domain.extensions.set
import ru.ratatoskr.project_3.domain.helpers.HeroesListState
import ru.ratatoskr.project_3.domain.useCases.opendota.GetAllHeroesFromOpendotaUseCase
import ru.ratatoskr.project_3.domain.useCases.sqlite.*
import javax.inject.Inject


@HiltViewModel
class HeroesListViewModel @Inject constructor(
    val getAllHeroesByNameUseCase: GetAllHeroesByNameUseCase,
    val getAllHeroesFromOpendotaUseCase: GetAllHeroesFromOpendotaUseCase,
    val getHeroByIdUseCase: GetHeroByIdUseCase,
    val getAllHeroesByAttrUseCase: GetAllHeroesByAttrUseCase,
    val addHeroesUserCase: AddHeroesUserCase,
    val InsertHeroesUseCase: InsertHeroesUseCase,
) : ViewModel() {

    val _HeroesList_state: MutableLiveData<HeroesListState> = MutableLiveData<HeroesListState>(HeroesListState.LoadingHeroesListState())
    val heroesListState: LiveData<HeroesListState> = _HeroesList_state

    fun getAllHeroesByName(){
        _HeroesList_state.set(HeroesListState.LoadingHeroesListState())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val heroes = getAllHeroesByNameUseCase.getAllHeroesByName()
                if (heroes.isEmpty()) {
                    getAllHeroesFromApi()
                } else {
                    _HeroesList_state.postValue(HeroesListState.LoadedHeroesListState(data = heroes))
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun getAllHeroesFromApi() {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                var heroes = getAllHeroesFromOpendotaUseCase.getAllHeroesFromApi()

                if (heroes.isEmpty()) {
                    _HeroesList_state.postValue(HeroesListState.NoHeroesListState())
                } else {
                    addHeroesUserCase.addHeroes(heroes)
                    _HeroesList_state.postValue(HeroesListState.LoadedHeroesListState(data = heroes.sortedBy { it.localizedName }))
                }

            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getHeroById(id: String) {
        _HeroesList_state.set(newValue = HeroesListState.LoadingHeroesListState())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val hero = getHeroByIdUseCase.GetHeroById(id)
                if (hero.id < 1) {
                    _HeroesList_state.postValue(HeroesListState.NoHeroesListState())
                } else {
                    _HeroesList_state.postValue(HeroesListState.HeroLoadedHeroesListState(data = hero))
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getAllHeroesByAttr(attr: String) {
        _HeroesList_state.set(newValue = HeroesListState.LoadingHeroesListState())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val heroes = getAllHeroesByAttrUseCase.getAllHeroesByAttr(attr)
                if (heroes.isEmpty()) {
                    _HeroesList_state.postValue(HeroesListState.NoHeroesListState())
                } else {
                    _HeroesList_state.postValue(HeroesListState.LoadedHeroesListState(data = heroes))
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }



}