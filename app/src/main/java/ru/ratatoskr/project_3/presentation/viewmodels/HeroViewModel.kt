package ru.ratatoskr.project_3.presentation.viewmodels

import android.util.Log
import android.util.Log.ASSERT
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ratatoskr.project_3.domain.base.EventHandler
import ru.ratatoskr.project_3.domain.extensions.set
import ru.ratatoskr.project_3.domain.helpers.State
import ru.ratatoskr.project_3.domain.helpers.FavoriteState
import ru.ratatoskr.project_3.domain.useCases.sqlite.GetHeroByIdUseCase
import javax.inject.Inject

sealed class FavoriteEvent {
    object FavoriteClicked : FavoriteEvent()
    object ReloadScreen : FavoriteEvent()
    object PreviousDayClicked : FavoriteEvent()
    object NextDayClicked : FavoriteEvent()
    data class OnHabitClick(val habitId: Int, val newValue: Boolean) : FavoriteEvent()
}

@HiltViewModel
class HeroViewModel @Inject constructor(
    val getHeroByIdUseCase: GetHeroByIdUseCase
) : ViewModel(), EventHandler<FavoriteEvent> {

    val _state: MutableLiveData<State> = MutableLiveData<State>(State.LoadingState())
    val state: LiveData<State> = _state

    val _favorite_state: MutableLiveData<FavoriteState> =
        MutableLiveData<FavoriteState>(FavoriteState.No())
    val favorite_state: LiveData<FavoriteState> = _favorite_state

    override fun obtainEvent(event: FavoriteEvent) {
        Log.e("TOHA","obtainEvent");
        when (val currentState = _favorite_state.value) {
            is FavoriteState.Yes -> reduce(event, currentState)
            is FavoriteState.No -> reduce(event, currentState)
        }

    }

    private fun reduce(event: FavoriteEvent, currentState: FavoriteState) {
        Log.e("TOHA","reduce");
        when (event) {
            FavoriteEvent.FavoriteClicked ->  fetchFavorite(currentState)
        }
    }

    /*
    Ядро обработки клика (MutableLiveData)
     */
    private fun fetchFavorite(currentState: FavoriteState) {
        Log.e("TOHA","fetchFavorite");
        viewModelScope.launch {
            try {
                when (currentState) {
                    is FavoriteState.Yes -> _favorite_state.postValue(FavoriteState.No())
                    is FavoriteState.No -> _favorite_state.postValue(FavoriteState.Yes())
                }
            } catch (e: Exception) {
                _favorite_state.postValue(FavoriteState.Error())
            }
        }
    }

    fun getHeroById(id: String) {
        _state.set(newValue = State.LoadingState())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val hero = getHeroByIdUseCase.GetHeroById(id)
                if (hero.id < 1) {
                    _state.postValue(State.NoItemsState())
                } else {
                    _state.postValue(State.HeroLoadedState(data = hero,false))
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }
}