package com.example.fitysalud

import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.Button
import android.widget.LinearLayout
import com.example.fitysalud.databinding.FragmentPersonalWorkoutBinding



class PersonalWorkout : Fragment() {

    private lateinit var binding: FragmentPersonalWorkoutBinding
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
        binding = FragmentPersonalWorkoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        addEjercicioButtons(selectedEjercicios)
    }

    private fun addEjercicioButtons(ejercicios: List<Int>) {
        val marginBottom = 20.dpToPx()
        val sidePadding = 40.dpToPx()

        ejercicios.forEachIndexed() { index, ejercicio ->
            val button = Button(requireContext()).apply {
                id = View.generateViewId()
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0, 0, 0, marginBottom)
                }
                text = resources.getResourceEntryName(ejercicio)
                setBackgroundResource(R.drawable.button_background)
                setCompoundDrawablesRelativeWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.baseline_star_24,
                    0
                )
                gravity = Gravity.CENTER
                setPadding(sidePadding, 0, sidePadding, 0)
                setOnClickListener {
                    selectedEjercicios.remove(ejercicio)
                    databaseHelper.saveUserEjercicios(userId, selectedEjercicios)
                    binding.personalWorkoutLinearLayout.removeView(this)
                }
            }
            binding.personalWorkoutLinearLayout.addView(button)
        }
    }

    private fun Int.dpToPx(): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            resources.displayMetrics
        ).toInt()
    }
}