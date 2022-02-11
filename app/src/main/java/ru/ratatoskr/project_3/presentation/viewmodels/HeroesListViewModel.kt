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
import ru.ratatoskr.project_3.domain.helpers.State
import ru.ratatoskr.project_3.domain.model.Hero
import javax.inject.Inject

//HILT: @HiltViewModel, @Inject constructor (говорим Хилту, что предоставляемый экземпляр зависит от HeroesRepoImpl)
@HiltViewModel
class HeroesListViewModel @Inject constructor(val repository: HeroesRepoImpl) : ViewModel() {

    //Замок (Алексей 1:2)
    private var _heroesList = MutableLiveData<List<Hero>>()
    val HeroesList = _heroesList as LiveData<List<Hero>>

    //(с)пиздил
    val state: MutableLiveData<State> = MutableLiveData<State>(State.LoadingState())

    suspend fun getAllHeroesList(context: Context) {

        var roomAppDatabase: RoomAppDatabase = RoomAppDatabase.buildDataSource(context = context)
        updateAllHeroesTable(roomAppDatabase, context, repository.getAllHeroesListFromAPI())

        _heroesList.postValue(getListHeroesFromDB(context))
    }

    fun getListHeroesFromDB(context: Context): List<Hero> {

        var roomAppDatabase: RoomAppDatabase = RoomAppDatabase.buildDataSource(context = context)
        return repository.getAllHeroesListFromDB(roomAppDatabase);
    }

    fun updateAllHeroesTable(
        roomAppDatabase: RoomAppDatabase,
        context: Context,
        Heroes: List<Hero>
    ) {
        repository.updateAllHeroesTable(roomAppDatabase, context, Heroes);
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