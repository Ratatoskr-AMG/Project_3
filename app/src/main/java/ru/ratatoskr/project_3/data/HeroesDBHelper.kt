package ru.ratatoskr.project_3.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.P)
class HeroesDBHelper(
    context: Context?,
    name: String?,
    version: Int,
    openParams: SQLiteDatabase.OpenParams
) : SQLiteOpenHelper(context, name, version, openParams) {

    override fun onCreate(p0: SQLiteDatabase?) {
        p0!!.execSQL(HeroesContract.CREATE_COMMAND)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL(HeroesContract.DROP_COMMAND)
        onCreate(p0)
    }
}