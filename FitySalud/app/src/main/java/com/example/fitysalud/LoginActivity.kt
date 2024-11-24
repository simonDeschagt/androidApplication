package com.example.fitysalud

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fitysalud.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)
        preferences = Preferences(this)

        binding.loginButton.setOnClickListener {
            val loginCorreo = binding.loginCorreo.text.toString()
            val loginContrasena = binding.loginContrasena.text.toString()
            loginDatabase(loginCorreo, loginContrasena)
        }
        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun loginDatabase(correo: String, contrasena: String) {
        databaseHelper.readUser(correo, contrasena) { userExists ->
            if (userExists) {
                val userId = databaseHelper.getUserId(correo, contrasena)
                preferences.saveUserId(userId)
                Toast.makeText(this, "Login exitoso", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
