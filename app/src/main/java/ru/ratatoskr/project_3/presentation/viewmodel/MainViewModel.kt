package ru.ratatoskr.project_3.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ratatoskr.project_3.data.impl.HeroesRepoImpl
import ru.ratatoskr.project_3.data.storage.RoomAppDatabase
import ru.ratatoskr.project_3.domain.model.Hero
import javax.inject.Inject


class MainViewModel (val repository: HeroesRepoImpl) : ViewModel() {

    @Inject
    lateinit var roomAppDatabase: RoomAppDatabase

    private var _heroesList = MutableLiveData<List<Hero>>()
    val HeroesList = _heroesList as LiveData<List<Hero>>

    suspend fun getAllHeroesList() {

       // var roomAppDatabase: RoomAppDatabase = RoomAppDatabase.buildDataSource(context = context)

        updateAllHeroesTable(repository.getAllHeroesListFromAPI())
        _heroesList.postValue(getListHeroesFromDB())
    }

    fun getListHeroesFromDB(): List<Hero> {

       // var roomAppDatabase: RoomAppDatabase = RoomAppDatabase.buildDataSource(context = context)
        return repository.getAllHeroesListFromDB();
    }

    fun updateAllHeroesTable(
        Heroes: List<Hero>
    ) {
        repository.updateAllHeroesTable(Heroes);
    }

    fun getListHeroesFromAPI() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getAllHeroesList()
            } catch (exception: Exception) {
                Log.d("TOHA", "exception:" + exception.toString())
            }
        }
    }

}