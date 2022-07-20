package ru.ratatoskr.project_3.presentation.screens.attribute

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ratatoskr.project_3.domain.extensions.set
import ru.ratatoskr.project_3.domain.useCases.heroes.*
import ru.ratatoskr.project_3.presentation.screens.attribute.models.AttributeState
import javax.inject.Inject

@HiltViewModel
class AttributeViewModel @Inject constructor(
    appSharedPreferences: SharedPreferences,
    val getAllHeroesByAttrUseCase: GetAllHeroesByAttrUseCase
) : AndroidViewModel(Application()) {

    var appSharedPreferences = appSharedPreferences

    val _heroesList_state: MutableLiveData<AttributeState> =
        MutableLiveData<AttributeState>(AttributeState.LoadingHeroesState())
    val heroesListState: LiveData<AttributeState> = _heroesList_state

    fun switchAttrSortDirection(attr: String, sortAsc: Boolean) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val heroes = getAllHeroesByAttrUseCase.getAllHeroesByAttr(attr, sortAsc)
                _heroesList_state.postValue(
                    AttributeState.LoadedHeroesState(
                        heroes,
                        "",
                        sortAsc
                    )
                )
            } catch (e: java.lang.Exception) {
                Log.e("TOHA", "e:" + e.toString())
                e.printStackTrace()
            }

        }
    }

    fun getAllHeroesByAttr(attr: String) {
        _heroesList_state.set(newValue = AttributeState.LoadingHeroesState())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val heroes = getAllHeroesByAttrUseCase.getAllHeroesByAttr(attr, false)
                if (heroes.isEmpty()) {
                    _heroesList_state.postValue(AttributeState.NoHeroesState("Empty Heroes by attr list"))
                } else {
                    _heroesList_state.postValue(
                        AttributeState.LoadedHeroesState(
                            heroes = heroes,
                            "",
                            false
                        )
                    )
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }
}