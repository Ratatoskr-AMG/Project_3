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
                fillHeroesToDb(this, viewModel.HeroesList.value!!)
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

fun getHeroesFromDb(context: Context): List<Hero> {
    var DB_NAME = "heroes"
    var DB_VERSION = 1
    val openParams = OpenParams.Builder()
        .addOpenFlags(SQLiteDatabase.CREATE_IF_NECESSARY) // Укажите открытые разрешения.
        .build()
    var dbhelper = HeroesDBHelper(context, DB_NAME, DB_VERSION, openParams)
    var db: SQLiteDatabase = dbhelper.writableDatabase
    val HeroesFormDb = mutableListOf<Hero>()
    var cursor: Cursor =
        db.query(HeroesContract.HEROES_TABLE_NAME, null, null, null, null, null, null)
    while (cursor.moveToNext()) {
        var Hero = Hero(
            cursor.getString(
                cursor.getColumnIndexOrThrow(HeroesContract.COLUMN_NAME)
            ),
            cursor.getString(
                cursor.getColumnIndexOrThrow(HeroesContract.COLUMN_LOCALIZED_NAME)
            ),
            cursor.getString(
                cursor.getColumnIndexOrThrow(HeroesContract.COLUMN_PRIMARY_ATTR)
            ),
            cursor.getString(
                cursor.getColumnIndexOrThrow(HeroesContract.COLUMN_ATTACK_TYPE)
            ),
            cursor.getString(
                cursor.getColumnIndexOrThrow(HeroesContract.COLUMN_ROLES)
            ),
            cursor.getString(
                cursor.getColumnIndexOrThrow(HeroesContract.COLUMN_IMG)
            ),
            cursor.getString(
                cursor.getColumnIndexOrThrow(HeroesContract.COLUMN_ICON)
            ),
            cursor.getInt(
                cursor.getColumnIndexOrThrow(HeroesContract.COLUMN_BASE_HEALTH)
            ),
            cursor.getDouble(
                cursor.getColumnIndexOrThrow(HeroesContract.COLUMN_BASE_HEALTH_REGEN)
            ),
            cursor.getInt(
                cursor.getColumnIndexOrThrow(HeroesContract.COLUMN_BASE_MANA)
            ),
            cursor.getDouble(
                cursor.getColumnIndexOrThrow(HeroesContract.COLUMN_BASE_MANA_REGEN)
            ),
            cursor.getDouble(
                cursor.getColumnIndexOrThrow(
                    HeroesContract.COLUMN_BASE_ARMOR
                )
            ),
            cursor.getInt(
                cursor.getColumnIndexOrThrow(
                    HeroesContract.COLUMN_BASE_MR
                )
            ),
            cursor.getInt(
                cursor.getColumnIndexOrThrow(
                    HeroesContract.COLUMN_BASE_ATTACK_MIN
                )
            ),
            cursor.getInt(
                cursor.getColumnIndexOrThrow(
                    HeroesContract.COLUMN_BASE_ATTACK_MAX
                )
            ),
            cursor.getInt(
                cursor.getColumnIndexOrThrow(
                    HeroesContract.COLUMN_BASE_STR
                )
            ),
            cursor.getInt(
                cursor.getColumnIndexOrThrow(
                    HeroesContract.COLUMN_BASE_AGI
                )
            ),
            cursor.getInt(
                cursor.getColumnIndexOrThrow(
                    HeroesContract.COLUMN_BASE_INT
                )
            ),
            cursor.getDouble(
                cursor.getColumnIndexOrThrow(
                    HeroesContract.COLUMN_STR_GAIN
                )
            ),
            cursor.getDouble(
                cursor.getColumnIndexOrThrow(
                    HeroesContract.COLUMN_AGI_GAIN
                )
            ),
            cursor.getDouble(
                cursor.getColumnIndexOrThrow(
                    HeroesContract.COLUMN_INT_GAIN
                )
            ),
            cursor.getInt(
                cursor.getColumnIndexOrThrow(
                    HeroesContract.COLUMN_ATTACK_RANGE
                )
            ),
            cursor.getInt(
                cursor.getColumnIndexOrThrow(
                    HeroesContract.COLUMN_PROJECTILE_SPEED
                )
            ),
            cursor.getDouble(
                cursor.getColumnIndexOrThrow(
                    HeroesContract.COLUMN_ATTACK_RATE
                )
            ),
            cursor.getInt(
                cursor.getColumnIndexOrThrow(
                    HeroesContract.COLUMN_MOVE_SPEED
                )
            ),
            cursor.getDouble(
                cursor.getColumnIndexOrThrow(
                    HeroesContract.COLUMN_TURN_RATE
                )
            ),
            cursor.getString(
                cursor.getColumnIndexOrThrow(
                    HeroesContract.COLUMN_CM_ENABLED
                )
            ),
            cursor.getInt(
                cursor.getColumnIndexOrThrow(
                    HeroesContract.COLUMN_LEGS
                )
            ),
            cursor.getInt(
                cursor.getColumnIndexOrThrow(
                    HeroesContract.COLUMN_HERO_ID
                )
            ),
            cursor.getInt(
                cursor.getColumnIndexOrThrow(
                    HeroesContract.COLUMN_TURBO_PICKS
                )
            ),
            cursor.getInt(
                cursor.getColumnIndexOrThrow(
                    HeroesContract.COLUMN_TURBO_WINS
                )
            ),
            cursor.getInt(
                cursor.getColumnIndexOrThrow(
                    HeroesContract.COLUMN_PRO_BAN
                )
            ),
            cursor.getInt(
                cursor.getColumnIndexOrThrow(
                    HeroesContract.COLUMN_PRO_WIN
                )
            ),
            cursor.getInt(
                cursor.getColumnIndexOrThrow(
                    HeroesContract.COLUMN_PRO_PICK
                )
            ),
            cursor.getInt(
                cursor.getColumnIndexOrThrow(
                    HeroesContract.COLUMN_1_PICK
                )
            ),
            cursor.getInt(
                cursor.getColumnIndexOrThrow(
                    HeroesContract.COLUMN_1_WIN
                )
            ),
            cursor.getInt(
                cursor.getColumnIndexOrThrow(
                    HeroesContract.COLUMN_2_PICK
                )
            ),
            cursor.getInt(
                cursor.getColumnIndexOrThrow(
                    HeroesContract.COLUMN_2_WIN
                )
            ),
            cursor.getInt(
                cursor.getColumnIndexOrThrow(
                    HeroesContract.COLUMN_3_PICK
                )
            ),
            cursor.getInt(
                cursor.getColumnIndexOrThrow(
                    HeroesContract.COLUMN_3_WIN
                )
            ),
            cursor.getInt(
                cursor.getColumnIndexOrThrow(
                    HeroesContract.COLUMN_4_PICK
                )
            ),
            cursor.getInt(
                cursor.getColumnIndexOrThrow(
                    HeroesContract.COLUMN_4_WIN
                )
            ),
            cursor.getInt(
                cursor.getColumnIndexOrThrow(
                    HeroesContract.COLUMN_5_PICK
                )
            ),
            cursor.getInt(
                cursor.getColumnIndexOrThrow(
                    HeroesContract.COLUMN_5_WIN
                )
            ),
            cursor.getInt(
                cursor.getColumnIndexOrThrow(
                    HeroesContract.COLUMN_6_PICK
                )
            ),
            cursor.getInt(
                cursor.getColumnIndexOrThrow(
                    HeroesContract.COLUMN_6_WIN
                )
            ),
            cursor.getInt(
                cursor.getColumnIndexOrThrow(
                    HeroesContract.COLUMN_7_PICK
                )
            ),
            cursor.getInt(
                cursor.getColumnIndexOrThrow(
                    HeroesContract.COLUMN_7_WIN
                )
            ),
            cursor.getInt(
                cursor.getColumnIndexOrThrow(
                    HeroesContract.COLUMN_8_PICK
                )
            ),
            cursor.getInt(
                cursor.getColumnIndexOrThrow(
                    HeroesContract.COLUMN_8_WIN
                )
            ),
            cursor.getInt(
                cursor.getColumnIndexOrThrow(
                    HeroesContract.COLUMN_NULL_PICK
                )
            ),
            cursor.getInt(
                cursor.getColumnIndexOrThrow(
                    HeroesContract.COLUMN_NULL_WIN
                )
            )
        )
        HeroesFormDb.add(Hero)

    }
/*
    Log.d("TOHA","db:START")

    for (Hero in HeroesFormDb) {
        Log.d("TOHA","db:"+Hero.getLocalizedName())
    }

    Log.d("TOHA","db:END")
*/
    return HeroesFormDb
}

fun fillHeroesToDb(context: Context, Heroes: List<Hero>) {

    var DB_NAME = "heroes"
    var DB_VERSION = 1

    val openParams = OpenParams.Builder()
        //.setCursorFactory(factory) // Укажите CursorFactory
        //.setErrorHandler(errorHandler) // Укажите DatabaseErrorHandler
        .addOpenFlags(SQLiteDatabase.CREATE_IF_NECESSARY) // Укажите открытые разрешения.
        .build()

    var dbhelper = HeroesDBHelper(context, DB_NAME, DB_VERSION, openParams)
    var db: SQLiteDatabase = dbhelper.writableDatabase

    for (Hero in Heroes) {

        var contentValues = ContentValues()
        var HeroRoles = Hero.getRoles()
        var Roles = "";

        for (HeroRole in HeroRoles!!) {
            if (Roles != "") Roles += ","
            Roles += HeroRole
        }

        contentValues.put(HeroesContract.COLUMN_NAME, Hero.getName())
        contentValues.put(
            HeroesContract.COLUMN_LOCALIZED_NAME,
            Hero.getLocalizedName()
        )
        contentValues.put(
            HeroesContract.COLUMN_PRIMARY_ATTR,
            Hero.getPrimaryAttr()
        )
        contentValues.put(
            HeroesContract.COLUMN_ATTACK_TYPE,
            Hero.getAttackType()
        )
        contentValues.put(
            HeroesContract.COLUMN_ROLES,
            Roles
        )
        contentValues.put(
            HeroesContract.COLUMN_IMG,
            Hero.getImg()
        )
        contentValues.put(
            HeroesContract.COLUMN_ICON,
            Hero.getIcon()
        )
        contentValues.put(
            HeroesContract.COLUMN_BASE_HEALTH,
            Hero.getBaseHealth()
        )
        contentValues.put(
            HeroesContract.COLUMN_BASE_HEALTH_REGEN,
            Hero.getBaseHealthRegen()
        )
        contentValues.put(
            HeroesContract.COLUMN_BASE_MANA,
            Hero.getBaseMana()
        )
        contentValues.put(
            HeroesContract.COLUMN_BASE_MANA_REGEN,
            Hero.getBaseManaRegen()
        )
        contentValues.put(
            HeroesContract.COLUMN_BASE_ARMOR,
            Hero.getBaseArmor()
        )
        contentValues.put(
            HeroesContract.COLUMN_BASE_MR,
            Hero.getBaseMr()
        )
        contentValues.put(
            HeroesContract.COLUMN_BASE_ATTACK_MIN,
            Hero.getBaseAttackMin()
        )
        contentValues.put(
            HeroesContract.COLUMN_BASE_ATTACK_MAX,
            Hero.getBaseAttackMax()
        )
        contentValues.put(
            HeroesContract.COLUMN_BASE_STR,
            Hero.getBaseStr()
        )
        contentValues.put(
            HeroesContract.COLUMN_BASE_AGI,
            Hero.getBaseAgi()
        )
        contentValues.put(
            HeroesContract.COLUMN_BASE_INT,
            Hero.getBaseInt()
        )
        contentValues.put(
            HeroesContract.COLUMN_STR_GAIN,
            Hero.getStrGain()
        )
        contentValues.put(
            HeroesContract.COLUMN_AGI_GAIN,
            Hero.getAgiGain()
        )
        contentValues.put(
            HeroesContract.COLUMN_INT_GAIN,
            Hero.getIntGain()
        )
        contentValues.put(
            HeroesContract.COLUMN_ATTACK_RANGE,
            Hero.getAttackRange()
        )
        contentValues.put(
            HeroesContract.COLUMN_PROJECTILE_SPEED,
            Hero.getProjectileSpeed()
        )
        contentValues.put(
            HeroesContract.COLUMN_ATTACK_RATE,
            Hero.getAttackRate()
        )
        contentValues.put(
            HeroesContract.COLUMN_MOVE_SPEED,
            Hero.getMoveSpeed()
        )
        contentValues.put(
            HeroesContract.COLUMN_TURN_RATE,
            Hero.getTurnRate()
        )
        contentValues.put(
            HeroesContract.COLUMN_CM_ENABLED,
            Hero.getCmEnabled()
        )
        contentValues.put(
            HeroesContract.COLUMN_LEGS,
            Hero.getLegs()
        )
        contentValues.put(
            HeroesContract.COLUMN_HERO_ID,
            Hero.getHeroId()
        )
        contentValues.put(
            HeroesContract.COLUMN_TURBO_PICKS,
            Hero.getTurboPicks()
        )
        contentValues.put(
            HeroesContract.COLUMN_TURBO_WINS,
            Hero.getTurboWins()
        )
        contentValues.put(
            HeroesContract.COLUMN_PRO_BAN,
            Hero.getProBan()
        )
        contentValues.put(
            HeroesContract.COLUMN_PRO_WIN,
            Hero.getProWin()
        )
        contentValues.put(
            HeroesContract.COLUMN_PRO_PICK,
            Hero.getProPick()
        )
        contentValues.put(
            HeroesContract.COLUMN_1_PICK,
            Hero.get1Pick()
        )
        contentValues.put(
            HeroesContract.COLUMN_1_WIN,
            Hero.get1Win()
        )
        contentValues.put(
            HeroesContract.COLUMN_2_PICK,
            Hero.get2Pick()
        )
        contentValues.put(
            HeroesContract.COLUMN_2_WIN,
            Hero.get2Win()
        )
        contentValues.put(
            HeroesContract.COLUMN_3_PICK,
            Hero.get3Pick()
        )
        contentValues.put(
            HeroesContract.COLUMN_3_WIN,
            Hero.get3Win()
        )
        contentValues.put(
            HeroesContract.COLUMN_4_PICK,
            Hero.get4Pick()
        )
        contentValues.put(
            HeroesContract.COLUMN_4_WIN,
            Hero.get4Win()
        )
        contentValues.put(
            HeroesContract.COLUMN_5_PICK,
            Hero.get5Pick()
        )
        contentValues.put(
            HeroesContract.COLUMN_5_WIN,
            Hero.get5Win()
        )
        contentValues.put(
            HeroesContract.COLUMN_6_PICK,
            Hero.get6Pick()
        )
        contentValues.put(
            HeroesContract.COLUMN_6_WIN,
            Hero.get6Win()
        )
        contentValues.put(
            HeroesContract.COLUMN_7_PICK,
            Hero.get7Pick()
        )
        contentValues.put(
            HeroesContract.COLUMN_7_WIN,
            Hero.get7Win()
        )
        contentValues.put(
            HeroesContract.COLUMN_8_PICK,
            Hero.get8Pick()
        )
        contentValues.put(
            HeroesContract.COLUMN_8_WIN,
            Hero.get8Win()
        )
        contentValues.put(
            HeroesContract.COLUMN_NULL_PICK,
            Hero.getNullPick()
        )
        contentValues.put(
            HeroesContract.COLUMN_NULL_WIN,
            Hero.getNUllWin()
        )

        db.insert(HeroesContract.HEROES_TABLE_NAME, null, contentValues)
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