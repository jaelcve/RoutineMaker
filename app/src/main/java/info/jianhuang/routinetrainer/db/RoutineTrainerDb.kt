package info.jianhuang.routinetrainer.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class RoutineTrainerDb(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    private val SQL_CREATE_ENTRIES = "CREATE TABLE ${RoutineEntry.TABLE_NAME} ("+
            "${RoutineEntry._ID} INTEGER PRIMARY KEY," +
            "${RoutineEntry.TITLE_COL} TEXT," +
            "${RoutineEntry.DESC_COL} TEXT," +
            "${RoutineEntry.IMAGE_COL} TEXT" +
            ")"

    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${RoutineEntry.TABLE_NAME}"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }
}