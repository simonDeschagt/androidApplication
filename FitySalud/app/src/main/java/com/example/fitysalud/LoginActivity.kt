package com.example.fitysalud

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText
    private lateinit var loginButton: Button
    private lateinit var signupButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inizializza Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Collega gli elementi della UI
        emailField = findViewById(R.id.etEmail)
        passwordField = findViewById(R.id.etPassword)
        loginButton = findViewById(R.id.btnLogin)
        signupButton = findViewById(R.id.btnSignup)

        // Listener per il login
        loginButton.setOnClickListener {
            val email = emailField.text.toString()
            val password = passwordField.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                Toast.makeText(this, "Compila tutti i campi", Toast.LENGTH_SHORT).show()
            }
        }

        // Listener per il signup
        signupButton.setOnClickListener {
            val email = emailField.text.toString()
            val password = passwordField.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                signupUser(email, password)
            } else {
                Toast.makeText(this, "Compila tutti i campi", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Login riuscito", Toast.LENGTH_SHORT).show()
                    // Naviga alla prossima activity
                } else {
                    Toast.makeText(this, "Autenticazione fallita", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun signupUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Registrazione riuscita", Toast.LENGTH_SHORT).show()
                    // Naviga alla prossima activity
                } else {
                    Toast.makeText(this, "Registrazione fallita", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
