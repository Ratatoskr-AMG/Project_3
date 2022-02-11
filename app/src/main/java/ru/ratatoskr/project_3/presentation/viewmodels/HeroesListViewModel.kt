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

@HiltViewModel
class HeroesListViewModel @Inject constructor(val repository: HeroesRepoImpl, val roomAppDatabase: RoomAppDatabase) : ViewModel() {
    val state: MutableLiveData<State> = MutableLiveData<State>(State.LoadingState())

    //private var _heroesList = MutableLiveData<List<Hero>>()
    //val HeroesList = _heroesList as LiveData<List<Hero>>

    fun fetchCarries() {
        state.set(newValue = State.LoadingState())
        viewModelScope.launch {
            try {
                val heroes = repository.getAllHeroesListFromAPI()
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

    suspend fun getAllHeroesList(context: Context) {

        //var roomAppDatabase: RoomAppDatabase = RoomAppDatabase.buildDataSource(context = context)
        updateAllHeroesTable(repository.getAllHeroesListFromAPI())

        //_heroesList.postValue(getListHeroesFromDB(context))
    }

    fun getListHeroesFromDB(context: Context): List<Hero> {

       // var roomAppDatabase: RoomAppDatabase = RoomAppDatabase.buildDataSource(context = context)
        return repository.getAllHeroesListFromDB();
    }

    fun updateAllHeroesTable(
        //roomAppDatabase: RoomAppDatabase,
        Heroes: List<Hero>
    ) {
        repository.updateAllHeroesTable(Heroes);
    }

    fun getListHeroesFromAPI(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getAllHeroesList(context)
            } catch (exception: Exception) {
                Log.d("TOHA", "exception:" + exception.toString())
            }
        }
    }

}