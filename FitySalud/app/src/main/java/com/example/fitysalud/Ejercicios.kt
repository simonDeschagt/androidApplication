package com.example.fitysalud

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.fitysalud.databinding.FragmentEjerciciosBinding


class Ejercicios : Fragment() {

    private lateinit var binding: FragmentEjerciciosBinding
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var preferences: Preferences
    private var selectedEjercicios = mutableListOf<Int>()
    private var userId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databaseHelper = DatabaseHelper(requireContext())
        preferences = Preferences(requireContext())
        userId = preferences.getUserId()
        if (userId > 0) {
            selectedEjercicios = databaseHelper.getUserEjercicios(userId).toMutableList()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEjerciciosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonIds = listOf(
            R.id.ejercicio1, R.id.ejercicio2, R.id.ejercicio3, R.id.ejercicio4,
            R.id.ejercicio5, R.id.ejercicio6, R.id.ejercicio7, R.id.ejercicio8,
            R.id.ejercicio9, R.id.ejercicio10, R.id.ejercicio11, R.id.ejercicio12,
            R.id.ejercicio13, R.id.ejercicio14, R.id.ejercicio15, R.id.ejercicio16,
            R.id.ejercicio17, R.id.ejercicio18, R.id.ejercicio19, R.id.ejercicio20
        )

        binding.backButton.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

        buttonIds.forEach { id ->
            val button = binding.root.findViewById<Button>(id)
            if (selectedEjercicios.contains(id)) {
                button.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.baseline_star_24, 0)
            }
            button.setOnClickListener {
                if (selectedEjercicios.contains(id)) {
                    button.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.baseline_star_border_24, 0)
                    selectedEjercicios.remove(id)

                } else {
                    button.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.baseline_star_24, 0)
                    selectedEjercicios.add(id)
                }
                databaseHelper.saveUserEjercicios(userId, selectedEjercicios)
            }
        }
    }
}