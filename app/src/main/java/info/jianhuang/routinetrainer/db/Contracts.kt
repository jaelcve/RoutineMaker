package info.jianhuang.routinetrainer.db

import android.provider.BaseColumns

val DATABASE_NAME = "RoutineTrainer.db"
val DATABASE_VERSION = 10 //1.0

//Create objects needed for db
object RoutineEntry: BaseColumns {
    val _ID = "id"
    val TABLE_NAME = "routine"
    val TITLE_COL = "title"
    val DESC_COL = "description"
    val IMAGE_COL = "image"
}

