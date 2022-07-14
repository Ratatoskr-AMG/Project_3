package ru.ratatoskr.project_3.presentation.screens.tiers

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ratatoskr.project_3.domain.utils.EventHandler
import ru.ratatoskr.project_3.domain.useCases.user.GetDotaBuffUserUseCase
import ru.ratatoskr.project_3.domain.useCases.user.GetOpenDotaUserUseCase
import ru.ratatoskr.project_3.domain.useCases.user.GetPlayerTierFromSPUseCase
import ru.ratatoskr.project_3.domain.useCases.user.GetSteamUserUseCase
import ru.ratatoskr.project_3.presentation.screens.tiers.models.TiersEvent
import ru.ratatoskr.project_3.presentation.screens.tiers.models.TiersState
import javax.inject.Inject

@HiltViewModel
class TiersViewModel @Inject constructor(
    appSharedPreferences: SharedPreferences,
    getPlayerTierFromSPUseCase: GetPlayerTierFromSPUseCase
) : AndroidViewModel(Application()), EventHandler<TiersEvent> {

    var appSharedPreferences = appSharedPreferences

    private val player_tier_from_sp =
        getPlayerTierFromSPUseCase.getPlayerTierFromSP(appSharedPreferences)

    private val _tiers_state: MutableLiveData<TiersState> =
        MutableLiveData<TiersState>(
            TiersState.IndefinedState(
                player_tier_from_sp!!,
            )
        )
    val tiersState: LiveData<TiersState> = _tiers_state

    override fun obtainEvent(event: TiersEvent) {
        Log.e("TOHA","obtainEvent")
        when (tiersState.value) {
            is TiersState.IndefinedState -> reduce(event)
            is TiersState.DefinedState -> reduce(event)
        }
    }

    private fun reduce(event: TiersEvent) {
        Log.e("TOHA","reduce")
        when (event) {
            is TiersEvent.OnTierChange -> selectTier(event)
        }
    }

    private fun selectTier(event: TiersEvent.OnTierChange) {
        val selected_tier = event.selected_tier
        viewModelScope.launch(Dispatchers.IO) {

            var state = tiersState.value

            appSharedPreferences.edit().putString("player_tier", selected_tier).apply()
            var curr_tier = appSharedPreferences.getString("player_tier", "undefined").toString()
            Log.d("TOHA","curr_tier:"+curr_tier);

            when (state) {
                is TiersState.IndefinedState -> {
                    try {
                        if (selected_tier != "undefined") {
                            _tiers_state.postValue(
                                TiersState.IndefinedState(
                                    selected_tier
                                )
                            )
                            Log.e("TOHA"," _tiers_state:"+ _tiers_state.value.toString())
                        }
                    } catch (e: Exception) {
                        _tiers_state.postValue(TiersState.ErrorTiersState)
                    }
                }
                is TiersState.DefinedState -> {
                    try {
                        if (selected_tier != "undefined") {
                            _tiers_state.postValue(
                                TiersState.DefinedState(
                                    selected_tier,
                                )
                            )
                        }
                    } catch (e: Exception) {
                        _tiers_state.postValue(TiersState.ErrorTiersState)
                    }
                }
            }
        }
    }


}




