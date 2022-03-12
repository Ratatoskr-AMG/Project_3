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
import ru.ratatoskr.project_3.domain.helpers.State
import ru.ratatoskr.project_3.domain.useCases.sqlite.GetHeroByIdUseCase
import javax.inject.Inject


@HiltViewModel
class HeroViewModel @Inject constructor(
    val getHeroByIdUseCase: GetHeroByIdUseCase
) : ViewModel() , EventHandler<FavoriteEvent> {

    val _state: MutableLiveData<State> = MutableLiveData<State>(State.LoadingState())
    val state: LiveData<State> = _state

    override fun obtainEvent(event: FavoriteEvent) {

        Log.e("TOHA","testeg")
    }

    fun getHeroById(id: String) {
        _state.set(newValue = State.LoadingState())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val hero = getHeroByIdUseCase.GetHeroById(id)
                if (hero.id < 1) {
                    _state.postValue(State.NoItemsState())
                } else {
                    _state.postValue(State.HeroLoadedState(data = hero))
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }


}