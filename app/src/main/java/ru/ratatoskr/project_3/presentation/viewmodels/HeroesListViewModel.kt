package ru.ratatoskr.project_3.presentation.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.ratatoskr.project_3.data.impl.HeroesRepoImpl
import ru.ratatoskr.project_3.data.storage.RoomAppDatabase
import ru.ratatoskr.project_3.domain.extensions.set
import ru.ratatoskr.project_3.domain.helpers.State
import ru.ratatoskr.project_3.domain.model.Hero
import javax.inject.Inject


//HILT: @HiltViewModel, @Inject constructor (говорим Хилту, что предоставляемый экземпляр зависит от HeroesRepoImpl)
@HiltViewModel
class HeroesListViewModel @Inject constructor(val repository: HeroesRepoImpl) : ViewModel() {

    //Замок (Алексей 1:2)
   // private var _heroesList = MutableLiveData<List<Hero>>()
   // val HeroesList = _heroesList as LiveData<List<Hero>>

    //(с)пиздил
    val state: MutableLiveData<State> = MutableLiveData<State>(State.LoadingState())

    fun getHeroesList() {
        state.set(newValue = State.LoadingState())
        viewModelScope.launch {
            try {
                val heroes = repository.getAllHeroesListFromDB()
                if (heroes.isEmpty()) {
                    withContext(Dispatchers.Main) {
                        state.set(newValue = State.NoItemsState())
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        state.set(newValue = State.LoadedState(data = heroes))
                    }
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }
}

