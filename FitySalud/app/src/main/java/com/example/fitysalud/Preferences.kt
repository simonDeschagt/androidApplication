package com.example.fitysalud

import android.content.Context

class Preferences (val context: Context) {

    private val storage = context.getSharedPreferences("mypref", Context.MODE_PRIVATE)

    fun saveNombre(nombre: String) {
        storage.edit().putString("USUARIO", nombre).apply()
    }

    fun getNombre(): String? {
        return storage.getString("USUARIO", "")
    }

    fun saveEmail(email: String) {
        storage.edit().putString("EMAIL", email).apply()
    }

    fun getEmail(): String? {
        return storage.getString("EMAIL", "")
    }

    fun saveEjercicios(ejercicios: String) {
        storage.edit().putString("EJERCICIOS", ejercicios).apply()
    }

    fun getEjercicios(): String? {
        return storage.getString("EJERCICIOS", "")
    }

    fun saveUserId(userId: Int) {
        storage.edit().putInt("USER ID", userId).apply()
    }

    fun getUserId(): Int {
        return storage.getInt("USER ID", 0)
    }
}