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
import ru.ratatoskr.project_3.domain.helpers.HeroesListState
import ru.ratatoskr.project_3.domain.useCases.sqlite.GetHeroByIdUseCase
import javax.inject.Inject


@HiltViewModel
class HeroViewModel @Inject constructor(
    val getHeroByIdUseCase: GetHeroByIdUseCase
) : ViewModel(), EventHandler<FavoriteEvent> {

    val _hero_state: MutableLiveData<HeroesListState> = MutableLiveData<HeroesListState>(HeroesListState.LoadingHeroesListState())
    val heroesListState: LiveData<HeroesListState> = _hero_state

    override fun obtainEvent(event: FavoriteEvent) {

        Log.e("TOHA","testeg")
    }

    fun getHeroById(id: String) {
        _hero_state.set(newValue = HeroesListState.LoadingHeroesListState())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val hero = getHeroByIdUseCase.GetHeroById(id)
                if (hero.id < 1) {
                    _hero_state.postValue(HeroesListState.NoHeroesListState())
                } else {
                    _hero_state.postValue(HeroesListState.HeroLoadedHeroesListState(data = hero))
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }


}