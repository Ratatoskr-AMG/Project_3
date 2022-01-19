package ru.ratatoskr.project_3.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
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

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.HeroesList.observe(this) {

            if (viewModel.HeroesList.value != null) {
                setContent {
                    var heroesList: List<Hero> = remember { viewModel.HeroesList.value!! }
                    Wrapper { Heroes(heroesList) }
                }
            }

        }

        GlobalScope.launch(Dispatchers.IO) {
            viewModel.getAllHeroesList()
        }

        setContent {
            Wrapper { w8() }
        }

    }

}

@Composable
fun Wrapper(inner: @Composable() () -> Unit) {
    Project_3Theme {
        Surface(color = MaterialTheme.colors.background) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 26.dp, end = 26.dp)
                    .verticalScroll(state = ScrollState(0), enabled = true),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                inner()

            }
        }
    }
}

@Composable
fun Heroes(heroesList: List<Hero>) {
    for (Hero in heroesList) {
        Text(Hero.localizedName!!);
    }
}

@Composable
fun w8() {
    Text("w8");
}