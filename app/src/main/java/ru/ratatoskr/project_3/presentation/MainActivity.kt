package ru.ratatoskr.project_3.presentation

import android.R.attr
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
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
import ru.ratatoskr.project_3.data.HeroesDBHelper
import ru.ratatoskr.project_3.domain.model.Hero
import ru.ratatoskr.project_3.presentation.theme.Project_3Theme
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.OpenParams
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import ru.ratatoskr.project_3.data.HeroesContract
import coil.transform.CircleCropTransformation

import android.R.attr.data
import androidx.compose.foundation.*
import androidx.compose.ui.graphics.Brush


class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Wrapper { w8() }
        }
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.HeroesList.observe(this) {
            if (viewModel.HeroesList.value != null) {
                viewModel.updateAllHeroesTable(this, viewModel.HeroesList.value!!)
                setContent {
                    var heroesList: List<Hero> = remember { viewModel.HeroesList.value!! }
                    Wrapper { Heroes(heroesList) }
                }
            }
        }
        GlobalScope.launch(Dispatchers.IO) {
            try {
                viewModel.getAllHeroesList()

            } catch (exception: Exception) {
                Log.d("TOHA", "exception:" + exception.toString())
                //Toast.makeText(applicationContext,"viewModel.getAllHeroesList() error", Toast.LENGTH_LONG)
            }

        }

    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Heroes(heroes: List<Hero>) {

    LazyVerticalGrid(

        modifier = Modifier
            .fillMaxSize()
            .padding(start = 0.dp, end = 0.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Blue,
                        Color.Cyan
                    )
                )
            ),
        cells = GridCells.Adaptive(128.dp),

        contentPadding = PaddingValues(
            start = 0.dp,
            top = 0.dp,
            end = 0.dp,
            bottom = 0.dp
        ),
        content = {
            items(heroes.size) { index ->
                Card(
                    backgroundColor = Color.White,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
                    elevation = 0.dp,
                ) {
                    Image(

                        painter = rememberImagePainter(
                            data = "https://cdn.dota2.com" + heroes[index].getImg(),
                            builder = {
                                crossfade(true)
                            }
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .size(64.dp)
                            .fillMaxWidth()
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        Color.Blue,
                                        Color.Cyan
                                    )
                                )
                            )
                    )
                }
            }
        }
    )
}

@Composable
fun Wrapper(inner: @Composable() () -> Unit) {
    Project_3Theme {
        Surface(
            color = MaterialTheme.colors.background
        ) {

            inner()

        }
    }
}

@Composable
fun w8() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 26.dp, end = 26.dp)
            .verticalScroll(state = ScrollState(0), enabled = false),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Text("w8");
    }
}