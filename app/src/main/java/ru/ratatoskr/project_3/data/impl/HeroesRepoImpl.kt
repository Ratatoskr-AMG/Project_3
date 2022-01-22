package ru.ratatoskr.project_3.data.impl

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import ru.ratatoskr.project_3.data.HeroesContract
import ru.ratatoskr.project_3.data.HeroesDBHelper
import ru.ratatoskr.project_3.data.HeroesRepo
import ru.ratatoskr.project_3.domain.model.Hero

object HeroesRepoImpl : HeroesRepo {

    override suspend fun getAllHeroesListFromAPI(): List<Hero> {

        val URL = "https://api.opendota.com/api/heroStats/";

        val client = HttpClient(Android) {
            install(JsonFeature) {
                serializer = GsonSerializer()
            }
        }

        var result = client.get<List<Hero>>(URL);

        return result

    }

    override suspend fun getAllHeroesListFromDB(context: Context): List<Hero> {

        var DB_NAME = "heroes"
        var DB_VERSION = 1
        val openParams = SQLiteDatabase.OpenParams.Builder()
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


}

