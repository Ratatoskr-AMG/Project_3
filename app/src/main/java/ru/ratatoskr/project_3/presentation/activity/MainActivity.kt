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
    var myComposable = MyComposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            myComposable.Wrapper { myComposable.WaitScreen() }
        }

        viewModel.HeroesList.observe(this) {
            if (viewModel.HeroesList.value != null) {
                setContent {
                    var heroesList: List<Hero> = remember { viewModel.HeroesList.value!! }
                    myComposable.Wrapper {myComposable.Heroes(heroesList)
                    }
                }
            }
        }
        viewModel.getListHeroesFromAPI(this)

    }
}