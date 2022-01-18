package ru.ratatoskr.project_3.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.ratatoskr.project_3.presentation.theme.Project_3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val client = HttpClient(Android) {
            install(JsonFeature) {
                serializer = GsonSerializer()
            }
        }

        GlobalScope.launch(Dispatchers.IO) {

            val Heroes = client.get<List<Hero>>("https://api.opendota.com/api/heroStats/")
            var log = "";

            for (Hero in Heroes) {
                log += Hero.getLocalizedName() + ":" + Hero.get1Pick()

            }

            Log.i("TOHA", log)
        }

        setContent {
            Project_3Theme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Hello")
                }
            }
        }

    }
}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Project_3Theme {
        Greeting("Android")
    }
}

/*
fun simpleCase() {

    val BASE_URL = "https://httpbin.org"
    val GET_UUID = "$BASE_URL/uuid"
    val client = HttpClient()

    GlobalScope.launch(Dispatchers.IO) {
        //val data = client.get<String>(GET_UUID)
        //Log.i("TOHA", data)

    }
}

 */

/*
suspend fun getAndPrintWeather() {

    val client = HttpClient(Android) {
        install(JsonFeature) {
            serializer = GsonSerializer()
        }
    }

    val Heroes = client.get<List<Hero>>(DOTA_URL)
    for(Hero in Heroes){
        Log.i("TOHA", Hero.getHeroName().replace("npc_dota_hero_", ""))
    }
}

 */

/*
data class Weather(
    val consolidated_weather: List<ConsolidatedWeather>,
    val time: String,
    val sun_rise: String,
    val sun_set: String,
    val timezone_name: String,
    val parent: Parent,
    val sources: List<Source>,
    val title: String,
    val location_type: String,
    val woeid: Int,
    val latt_long: String,
    val timezone: String
)

data class Source(
    val title: String,
    val slug: String,
    val url: String,
    val crawl_rate: Int
)

data class ConsolidatedWeather(
    val id: Long,
    val weather_state_name: String,
    val weather_state_abbr: String,
    val wind_direction_compass: String,
    val created: String,
    val applicable_date: String,
    val min_temp: Double,
    val max_temp: Double,
    val the_temp: Double,
    val wind_speed: Double,
    val wind_direction: Double,
    val air_pressure: Double,
    val humidity: Int,
    val visibility: Double,
    val predictability: Int
)

data class Parent(
    val title: String,
    val location_type: String,
    val woeid: Int,
    val latt_long: String
)

private const val SF_WEATHER_URL = "https://www.metaweather.com/api/location/2487956/"
private const val DOTA_URL = "https://api.opendota.com/api/heroStats/"


 */
