package ru.ratatoskr.project_3.presentation.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.ratatoskr.project_3.data.impl.HeroesRepoImpl
import ru.ratatoskr.project_3.data.storage.RoomAppDatabase
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.presentation.MyComposable
import ru.ratatoskr.project_3.presentation.viewmodel.MainViewModel


class MainActivity : ComponentActivity() {

    private val viewModel = MainViewModel(repository = HeroesRepoImpl())
    lateinit var roomAppDatabase: RoomAppDatabase
    //private val heroesConverter: HeroesConverter = HeroesConverterImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var myComposable = MyComposable()
        setContent {
            myComposable.Wrapper { myComposable.WaitScreen() }
        }
        viewModel.HeroesList.observe(this) {
            if (viewModel.HeroesList.value != null) {
               // roomAppDatabase = RoomAppDatabase.buildDataSource(context = applicationContext)
                //viewModel.updateAllHeroesTable(roomAppDatabase,this, viewModel.HeroesList.value!!)
                setContent {
                    var heroesList: List<Hero> = remember { viewModel.HeroesList.value!! }
                    myComposable.Wrapper {myComposable.Heroes(heroesList)
                    }
                }
            }
        }
        //viewModel.getListHeroesFromAPI()
        GlobalScope.launch(Dispatchers.IO) {
            try {
                viewModel.getAllHeroesList()
            } catch (exception: Exception) {
                Log.d("TOHA", "exception:" + exception.toString())
            }
        }
    }
}