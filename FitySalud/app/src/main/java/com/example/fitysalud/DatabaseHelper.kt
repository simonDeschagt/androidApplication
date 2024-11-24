package com.example.fitysalud

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope

class DatabaseHelper(private val context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    companion object {
        private const val DATABASE_NAME = "fitysalud.db"
        private const val DATABASE_VERSION = 3
        private const val TABLE_NAME = "data"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_CONTRASENA = "contrasena"
        private const val COLUMN_CORREO = "correo"
        private const val COLUMN_NUMERO = "numero"
        private const val COLUMN_EJERCICIOS = "ejercicios"
    }

    private val preferences = Preferences(context)

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = ("CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NOMBRE TEXT, " +
                "$COLUMN_CONTRASENA TEXT, " +
                "$COLUMN_CORREO TEXT, " +
                "$COLUMN_NUMERO TEXT, " +
                "$COLUMN_EJERCICIOS TEXT)")
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertUser(nombre: String, contrasena: String, correo: String, numero: String): Long {
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, nombre)
            put(COLUMN_CONTRASENA, contrasena)
            put(COLUMN_CORREO, correo)
            put(COLUMN_NUMERO, numero)
            put(COLUMN_EJERCICIOS, "")
        }
        val db = writableDatabase
        return db.insert(TABLE_NAME, null, values)
    }

    fun readUser(correo: String, contrasena: String, callback: (Boolean) -> Unit)
    {
        CoroutineScope(Dispatchers.IO).launch {
            val db = readableDatabase
            val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_CORREO = ? AND $COLUMN_CONTRASENA = ?"
            val cursor = db.rawQuery(query, arrayOf(correo, contrasena))
            val userExists = if (cursor.moveToFirst()) {
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE))
                preferences.saveNombre(nombre)
                preferences.saveEmail(correo)
                true
            } else {
                cursor.close()
                false
            }
            withContext(Dispatchers.Main) {
                callback(userExists)
            }
        }

    }

    fun getUserData(): User {
        val nombre = preferences.getNombre() ?: ""
        val correo = preferences.getEmail() ?: ""
        return User(nombre, correo)
    }

    fun getUserId(correo: String, contrasena: String): Int {
        val db = readableDatabase
        val query = "SELECT $COLUMN_ID FROM $TABLE_NAME WHERE $COLUMN_CORREO = ? AND $COLUMN_CONTRASENA = ?"
        val cursor = db.rawQuery(query, arrayOf(correo, contrasena))
        return if (cursor.moveToFirst()) {
            val userId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            cursor.close()
            userId
        } else {
            cursor.close()
            -1
        }
    }

    fun saveUserEjercicios(userId: Int, ejercicios: List<Int>) {
        val db = writableDatabase
        val ejerciciosString = ejercicios.joinToString(",")
        val query = "UPDATE $TABLE_NAME SET $COLUMN_EJERCICIOS = '$ejerciciosString' WHERE $COLUMN_ID = $userId"
        db.execSQL(query)
    }

    fun getUserEjercicios(userId: Int): List<Int> {
        val db = readableDatabase
        val query = "SELECT $COLUMN_EJERCICIOS FROM $TABLE_NAME WHERE $COLUMN_ID = ?"
        val cursor = db.rawQuery(query, arrayOf(userId.toString()))
        return if (cursor.moveToFirst()) {
            val ejerciciosString = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EJERCICIOS))
            cursor.close()
            ejerciciosString.split(",").mapNotNull { it.toIntOrNull() }
        } else {
            cursor.close()
            emptyList()
        }
    }
}

data class User(val nombre: String, val correo: String)