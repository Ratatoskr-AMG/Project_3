package net.doheco.presentation.activity

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.doheco.domain.useCases.heroes.AddHeroesUserCase
import net.doheco.domain.useCases.heroes.GetAllHeroesFromOpendotaUseCase
import net.doheco.domain.useCases.heroes.GetAllHeroesSortByNameUseCase
import net.doheco.domain.useCases.system.ServerApiCallUseCase
import net.doheco.domain.useCases.user.UUIdSPUseCase
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val getAllHeroesSortByNameUseCase: GetAllHeroesSortByNameUseCase,
    private var appSharedPreferences: SharedPreferences,
    private val UUIdSPUseCase: UUIdSPUseCase,
    private val serverApiCallUseCase: ServerApiCallUseCase,
    private val getAllHeroesFromOpendotaUseCase: GetAllHeroesFromOpendotaUseCase,
    private val addHeroesUserCase: AddHeroesUserCase,
) : AndroidViewModel(Application()) {

    init {
        setAppUUIdToSPIfNeeded()
        getAllHeroesSortByName()
    }

    private val _loadState: MutableLiveData<Boolean> = MutableLiveData(true)
    val loadState: LiveData<Boolean> = _loadState

    private fun setAppUUIdToSPIfNeeded(){
        if(UUIdSPUseCase.GetAppUUIdFromSP(appSharedPreferences).isEmpty()){
            val uuID = UUID.randomUUID().toString()
            UUIdSPUseCase.SetAppUUIdToSP(appSharedPreferences,uuID)
        }
    }

    private fun getAllHeroesSortByName() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val heroes = getAllHeroesSortByNameUseCase.getAllHeroesSortByName()
                if (heroes.isEmpty()) {
                    getAllHeroesFromApi(appSharedPreferences)
                } else {
                    _loadState.postValue(false)
                }
            } catch (e: Exception) {
                Log.e("SplashScreenViewModel", "Error: $e")
                e.printStackTrace()
            }
        }
    }

    private suspend fun getAllHeroesFromApi(appSharedPreferences: SharedPreferences) {
        viewModelScope.launch(Dispatchers.IO) {
            val uuId = UUIdSPUseCase.GetAppUUIdFromSP(appSharedPreferences)
            val apiAnswer = serverApiCallUseCase.getHeroesAndMatches(uuId)
            val heroes = getAllHeroesFromOpendotaUseCase.calculate(apiAnswer.heroes!!)
            if (heroes.isNotEmpty()) {
                _loadState.postValue(false)
            }
            addHeroesUserCase.addHeroes(heroes)
            appSharedPreferences.edit()
                .putLong("heroes_list_last_modified", Date(System.currentTimeMillis()).time)
                .apply()
        }
    }
}