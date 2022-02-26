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

//Dagger - viewModelStore

@HiltViewModel
class HeroesListViewModel @Inject constructor(val repository: HeroesRepoImpl) : ViewModel() {
    val state: MutableLiveData<State> = MutableLiveData<State>(State.LoadingState())

    suspend fun getAllHeroes() {
        state.set(newValue = State.LoadingState())
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
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
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                Log.e("TOHA", e.message.toString())
            }
        }
    }

    suspend fun getHeroById(id:String){
        state.set(newValue = State.LoadingState())
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    val hero = repository.getHeroById(id)

                    if (hero.id.toInt()<1) {
                        withContext(Dispatchers.Main) {
                            state.set(newValue = State.NoItemsState())
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            state.set(newValue = State.HeroLoadedState(data = hero))
                        }
                    }
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                Log.e("TOHA", e.message.toString())
            }
        }
    }



}