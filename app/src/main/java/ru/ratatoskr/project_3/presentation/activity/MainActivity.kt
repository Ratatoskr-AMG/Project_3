package ru.ratatoskr.project_3.presentation.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.presentation.theme.Project_3Theme
import ru.ratatoskr.project_3.presentation.MyComposable
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.ui.graphics.Color
import coil.compose.rememberImagePainter
import androidx.compose.foundation.*
import androidx.compose.ui.graphics.Brush
import ru.ratatoskr.project_3.presentation.viewmodel.MainViewModel


class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var myComposable = MyComposable()
        setContent {
            myComposable.Wrapper { myComposable.w8() }
        }
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.HeroesList.observe(this) {
            if (viewModel.HeroesList.value != null) {
                viewModel.updateAllHeroesTable(this, viewModel.HeroesList.value!!)
                setContent {
                    var heroesList: List<Hero> = remember { viewModel.HeroesList.value!! }
                    myComposable.Wrapper {myComposable.Heroes(heroesList)
                    }
                }
            }
        }
        GlobalScope.launch(Dispatchers.IO) {
            try {
                viewModel.getAllHeroesList()
            } catch (exception: Exception) {
                Log.d("TOHA", "exception:" + exception.toString())
            }
        }
    }
}