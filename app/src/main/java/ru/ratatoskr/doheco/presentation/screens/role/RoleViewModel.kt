package ru.ratatoskr.doheco.presentation.screens.role

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ratatoskr.doheco.domain.extensions.set
import ru.ratatoskr.doheco.domain.useCases.favorites.GetAllFavoriteHeroesUseCase
import ru.ratatoskr.doheco.domain.useCases.heroes.*
import ru.ratatoskr.doheco.presentation.screens.role.models.RoleState
import javax.inject.Inject

@HiltViewModel
class RoleViewModel @Inject constructor(
    appSharedPreferences: SharedPreferences,
    val getAllHeroesSortByNameUseCase: GetAllHeroesSortByNameUseCase,
    val getAllHeroesFromOpendotaUseCase: GetAllHeroesFromOpendotaUseCase,
    val getAllHeroesByAttrUseCase: GetAllHeroesByAttrUseCase,
    val addHeroesUserCase: AddHeroesUserCase,
    val getAllFavoriteHeroesUseCase: GetAllFavoriteHeroesUseCase,
    val getAllHeroesByRoleUseCase: GetAllHeroesByRoleUseCase,
) : AndroidViewModel(Application()) {

    var appSharedPreferences = appSharedPreferences

    val _role_state: MutableLiveData<RoleState> =
        MutableLiveData<RoleState>(RoleState.LoadingHeroesListState())
    val roleState: LiveData<RoleState> = _role_state

    fun getAllHeroesByRole(role: String) {
        _role_state.set(newValue = RoleState.LoadingHeroesListState())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val heroes = getAllHeroesByRoleUseCase.getAllHeroesByRole(role)

                if (heroes.isEmpty()) {
                    _role_state.postValue(RoleState.NoHeroesListState("Empty Heroes by attr list"))
                } else {

                    _role_state.postValue(
                        RoleState.LoadedHeroesListState(
                            heroes = heroes
                        )
                    )
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }
}