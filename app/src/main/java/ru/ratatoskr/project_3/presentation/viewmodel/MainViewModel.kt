package ru.ratatoskr.project_3.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.ratatoskr.project_3.data.impl.HeroesRepoImpl
import ru.ratatoskr.project_3.domain.model.Hero

class MainViewModel : ViewModel() {

    private val repository = HeroesRepoImpl

    val HeroesList=MutableLiveData<List<Hero>>()

    suspend fun getAllHeroesList(){
          val list = repository.getAllHeroesListFromAPI();
          HeroesList.postValue(list)
    }

    fun updateAllHeroesTable(context: Context, Heroes: List<Hero>){
        repository.updateAllHeroesTable(context,Heroes);
    }

}