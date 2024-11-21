package com.example.fitysalud

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(private val context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    companion object {
        private const val DATABASE_NAME = "fitysalud.db"
        private const val DATABASE_VERSION = 2
        private const val TABLE_NAME = "data"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_CONTRASENA = "contrasena"
        private const val COLUMN_CORREO = "correo"
        private const val COLUMN_NUMERO = "numero"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = ("CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NOMBRE TEXT, " +
                "$COLUMN_CONTRASENA TEXT, " +
                "$COLUMN_CORREO TEXT, " +
                "$COLUMN_NUMERO TEXT)")
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
        }
        val db = writableDatabase
        return db.insert(TABLE_NAME, null, values)
    }

    fun readUser(correo: String, contrasena: String): Boolean {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_CORREO = '$correo' AND $COLUMN_CONTRASENA = '$contrasena'"
        val cursor = db.rawQuery(query, null)
        return cursor.count > 0
    }
}