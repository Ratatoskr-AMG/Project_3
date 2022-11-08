package net.doheco.presentation.screens.role

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.doheco.domain.extensions.set
import net.doheco.domain.useCases.favorites.GetAllFavoriteHeroesUseCase
import net.doheco.domain.useCases.heroes.*
import net.doheco.domain.useCases.user.GetPlayerTierFromSPUseCase
import net.doheco.presentation.screens.role.models.RoleState
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
    val getPlayerTierFromSPUseCase: GetPlayerTierFromSPUseCase,
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
                    val player_tier_from_sp =
                        getPlayerTierFromSPUseCase.getPlayerTierFromSP(appSharedPreferences)
                    val favoriteHeroes = getAllFavoriteHeroesUseCase.getAllFavoriteHeroesUseCase()

                    _role_state.postValue(
                        RoleState.LoadedHeroesListState(
                            player_tier = player_tier_from_sp,
                            heroes = heroes,
                            favoriteHeroes = favoriteHeroes,
                        )
                    )
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }
}