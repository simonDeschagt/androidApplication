package com.example.fitysalud

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fitysalud.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)

        binding.registerButton.setOnClickListener {
            val registerNombre = binding.registerNombre.text.toString()
            val registerContrasena = binding.registerContrasena.text.toString()
            val registerCorreo = binding.registerCorreo.text.toString()
            val registerNumero = binding.registerNumero.text.toString()
            registerDatabase(registerNombre, registerContrasena, registerCorreo, registerNumero)
        }

        binding.loginRedirectLink.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun registerDatabase(nombre: String, contrasena: String, correo: String, numero: String) {
        val id = databaseHelper.insertUser(nombre, contrasena, correo, numero)
        if (id > 0) {
            Toast.makeText(this, "registrado exitosamente ", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
//            Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show()
              Toast.makeText(this, id.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}