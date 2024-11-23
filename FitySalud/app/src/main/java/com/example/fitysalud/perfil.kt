package com.example.fitysalud

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fitysalud.databinding.FragmentPerfilBinding

class perfil : Fragment() {

    private lateinit var binding: FragmentPerfilBinding
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databaseHelper = DatabaseHelper(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPerfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Fetch data from the database and set it to the TextViews
        val userData = databaseHelper.getUserData()
        binding.nombreEntero.text = userData.nombre
        binding.perfilCorreo.text = userData.correo

        // Set button click listeners
        binding.backButton.setOnClickListener {
            // Redirect to respective page
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }
        binding.dataButton.setOnClickListener {
            // Redirect to respective page
        }
        binding.personalWorkoutButton.setOnClickListener {
            // Redirect to respective page
        }
        binding.educativaButton.setOnClickListener {
            // Redirect to respective page
        }
        binding.logoutButton.setOnClickListener {
            // Logout and redirect to login
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
}