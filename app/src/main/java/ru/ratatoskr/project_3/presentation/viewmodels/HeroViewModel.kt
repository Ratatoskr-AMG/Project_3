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
import ru.ratatoskr.project_3.domain.helpers.HeroState
import ru.ratatoskr.project_3.domain.helpers.HeroesListState
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.domain.useCases.sqlite.GetHeroByIdUseCase
import javax.inject.Inject



sealed class HeroEvent {
    data class OnFavoriteCLick(val heroId: Int, val newValue: Boolean) : HeroEvent()
}

@HiltViewModel
class HeroViewModel @Inject constructor(
    val getHeroByIdUseCase: GetHeroByIdUseCase
) : ViewModel(), EventHandler<HeroEvent> {

    var isFavorite = false
    val _hero_state: MutableLiveData<HeroState> = MutableLiveData<HeroState>()
    val hero_state: LiveData<HeroState> = _hero_state

    override fun obtainEvent(event: HeroEvent) {

        when (val currentState = _hero_state.value) {
            is HeroState.Display -> reduce(event, currentState)
        }

    }

    private fun reduce(event: HeroEvent, currentState: HeroState.Display) {

        Log.e("TOHA","reduce")

        when (event) {
            HeroEvent.OnFavoriteCLick() -> isFavoriteSwitch(currentState.isFavorite)
        }
    }

    private fun isFavoriteSwitch(isFavorite: Boolean = false) {

        viewModelScope.launch {
            try {

                if (habbits.isEmpty()) {
                    _dailyViewState.postValue(DailyViewState.NoItems)
                } else {
                    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                    val diaryResponse = dailyRepository.fetchDiary()
                    Log.e("TAG", "response -> $diaryResponse")
                    val dailyActivities = diaryResponse
                        .filter { it.date == dateFormat.format(currentDate) }.firstOrNull()

                    val cardItems: List<HabitCardItemModel> = habbits.map { habbitEntity ->
                        HabitCardItemModel(
                            habitId = habbitEntity.itemId,
                            title = habbitEntity.title,
                            isChecked = if (dailyActivities != null) {
                                val dailyItem = dailyActivities.habits.firstOrNull { it.habbitId == habbitEntity.itemId }
                                dailyItem?.value ?: false
                            } else {
                                false
                            }
                        )
                    }



                    _hero_state.postValue(
                        HeroState.Display(
                            hero = hero,
                            hasNextDay = setHasNextDay,
                            title = getTitleForADay()
                        )
                    )
                }
            } catch (e: Exception) {
                _dailyViewState.postValue(DailyViewState.Error)
            }
        }
    }


    fun getHeroById(id: String) {
        _hero_state.set(newValue = HeroState.LoadingHeroState())

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val hero = getHeroByIdUseCase.GetHeroById(id)
                if (hero.id < 1) {
                    _hero_state.postValue(HeroState.NoHeroState())
                } else {
                    _hero_state.postValue(HeroState.HeroLoadedState(hero = hero))
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }


}