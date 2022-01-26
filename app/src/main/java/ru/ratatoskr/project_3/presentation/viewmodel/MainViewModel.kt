package ru.ratatoskr.project_3.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.ratatoskr.project_3.data.impl.HeroesRepoImpl
import ru.ratatoskr.project_3.data.storage.RoomAppDatabase
import ru.ratatoskr.project_3.domain.model.Hero

class MainViewModel(val repository: HeroesRepoImpl) : ViewModel() {


    private var _heroesList = MutableLiveData<List<Hero>>()
    val HeroesList = _heroesList as LiveData<List<Hero>>

    suspend fun getAllHeroesList(context:Context){
         val list = repository.getAllHeroesListFromAPI();
        _heroesList.postValue(list)
        var roomAppDatabase: RoomAppDatabase = RoomAppDatabase.buildDataSource(context = context)
        updateAllHeroesTable(roomAppDatabase,context, HeroesList.value!!)
    }

    fun updateAllHeroesTable(roomAppDatabase: RoomAppDatabase, context: Context, Heroes: List<Hero>){
        repository.updateAllHeroesTable(roomAppDatabase,context,Heroes);
    }

    fun getListHeroesFromAPI(context:Context){
        viewModelScope.launch {
            try {
                getAllHeroesList(context)
            } catch (exception: Exception) {
                Log.d("TOHA", "exception:" + exception.toString())
            }
        }
    }

}