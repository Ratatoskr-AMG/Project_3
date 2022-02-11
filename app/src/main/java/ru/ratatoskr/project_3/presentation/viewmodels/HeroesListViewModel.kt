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
import ru.ratatoskr.project_3.data.impl.HeroesRepoImpl
import ru.ratatoskr.project_3.data.storage.RoomAppDatabase
import ru.ratatoskr.project_3.domain.model.Hero
import javax.inject.Inject

@HiltViewModel
class HeroesListViewModel @Inject constructor(val repository: HeroesRepoImpl, val roomAppDatabase: RoomAppDatabase) : ViewModel() {

    private var _heroesList = MutableLiveData<List<Hero>>()
    val HeroesList = _heroesList as LiveData<List<Hero>>

    suspend fun getAllHeroesList(context: Context) {

        //var roomAppDatabase: RoomAppDatabase = RoomAppDatabase.buildDataSource(context = context)
        updateAllHeroesTable(repository.getAllHeroesListFromAPI())

        _heroesList.postValue(getListHeroesFromDB(context))
    }

    fun getListHeroesFromDB(context: Context): List<Hero> {

       // var roomAppDatabase: RoomAppDatabase = RoomAppDatabase.buildDataSource(context = context)
        return repository.getAllHeroesListFromDB(roomAppDatabase);
    }

    fun updateAllHeroesTable(
        //roomAppDatabase: RoomAppDatabase,
        Heroes: List<Hero>
    ) {
        repository.updateAllHeroesTable(roomAppDatabase, Heroes);
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