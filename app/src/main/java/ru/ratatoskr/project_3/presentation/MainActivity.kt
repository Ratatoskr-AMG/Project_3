package ru.ratatoskr.project_3.presentation

import android.os.Bundle
import android.util.Log
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
import ru.ratatoskr.project_3.data.HeroesDBHelper
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.presentation.theme.Project_3Theme
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.OpenParams
import io.ktor.client.request.*


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
            try {
                viewModel.getAllHeroesList(MainActivity())
            } catch (exception: Exception) {
                Log.d("TOHA","exception:"+exception.toString())
                //Toast.makeText(applicationContext,"viewModel.getAllHeroesList() error", Toast.LENGTH_LONG)
            }

        }

        setContent {
            Wrapper { w8() }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
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
        Log.d("TOHA",Hero.toString())
        Text(Hero.getLocalizedName()!!+":"+Hero.getCmEnabled()!!);
    }
}

@Composable
fun w8() {
    Text("w8");
}