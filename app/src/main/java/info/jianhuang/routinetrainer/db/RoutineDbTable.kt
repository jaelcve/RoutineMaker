package info.jianhuang.routinetrainer.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import info.jianhuang.routinetrainer.Routine
import java.io.ByteArrayOutputStream

class RoutineDbTable(context: Context) {

    private val TAG = RoutineDbTable::class.java.simpleName
    private val dbHelper = RoutineTrainerDb(context)

    fun store(routine: Routine): Long {
        val db = dbHelper.writableDatabase

        val values = ContentValues()
        with(values) {
            put(RoutineEntry.TITLE_COL, routine.title)
            put(RoutineEntry.DESC_COL, routine.description)
            put(RoutineEntry.IMAGE_COL, routine.image.toString())
        }

       val id = db.transaction {
            insert(RoutineEntry.TABLE_NAME, null, values)
        }

        Log.d(TAG, "Stored new habit to DB $routine")

        return id
    }

    fun readAllRoutines(): List<Routine> {
        val columns = arrayOf(RoutineEntry._ID, RoutineEntry.TITLE_COL, RoutineEntry.DESC_COL,
            RoutineEntry.IMAGE_COL)
        val order = "${RoutineEntry._ID} ASC"
        val db = dbHelper.readableDatabase
        val cursor = db.doQuery(RoutineEntry.TABLE_NAME, columns, orderBy = order)

        return parseRoutineFrom(cursor)
    }

    private fun parseRoutineFrom(cursor: Cursor): MutableList<Routine> {
        val routines = mutableListOf<Routine>()
        while (cursor.moveToNext()) {
            val title = cursor.getString(RoutineEntry.TITLE_COL)
            val desc = cursor.getString(RoutineEntry.DESC_COL)
            val bitmap = cursor.getUri(RoutineEntry.IMAGE_COL)
            routines.add(Routine(title, desc, bitmap))
        }
        cursor.close()
        return routines
    }

    private fun toByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream)
        return stream.toByteArray()
    }
}

private fun SQLiteDatabase.doQuery(table: String, columns: Array<String>, selection: String? = null,
                                   selectionArgs: Array<String>? = null, groupBy: String? = null, having: String? = null,
                                   orderBy: String? = null) : Cursor {
    return query(table, columns, selection, selectionArgs, groupBy, having, orderBy)
}

private fun Cursor.getBitMap(columnName: String) : Bitmap{
    val bytes = getBlob(getColumnIndex(columnName))
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
}

private fun Cursor.getString(columnName: String) : String = getString(getColumnIndex(columnName))

private fun Cursor.getUri(columnName: String) : Uri = Uri.parse(getString(getColumnIndex(columnName)))


private inline fun <T> SQLiteDatabase.transaction(function: SQLiteDatabase.() -> T): T{
    beginTransaction()
    val result = try{
        val returnValue = function()
        setTransactionSuccessful()

        returnValue
    }finally {
        endTransaction()
    }
    close()

    return result
}