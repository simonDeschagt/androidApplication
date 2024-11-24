package com.example.fitysalud

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.fitysalud.databinding.FragmentDatosPersonalBinding
import com.jjoe64.graphview.DefaultLabelFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

class DatosPersonal : Fragment() {

    private lateinit var binding: FragmentDatosPersonalBinding
    private lateinit var series: LineGraphSeries<DataPoint>
    private var day = 0
    private var firstWeight: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDatosPersonalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        series = LineGraphSeries()
        binding.graph.addSeries(series)

        // Enable scaling and scrolling
        binding.graph.viewport.isScalable = true
        binding.graph.viewport.isScrollable = true

        // Set the viewport to fit the data
        binding.graph.viewport.setScalableY(true)
        binding.graph.viewport.setScrollableY(true)
        binding.graph.viewport.setXAxisBoundsManual(true)
        binding.graph.viewport.setMinX(0.0)
        binding.graph.viewport.setMaxX(10.0) // Initial max value, will adjust dynamically

        // Configure the graph to display days and weight
        binding.graph.gridLabelRenderer.horizontalAxisTitle = "Dias"
        binding.graph.gridLabelRenderer.verticalAxisTitle = "Pesa"
        binding.graph.gridLabelRenderer.labelFormatter = object : DefaultLabelFormatter() {
            override fun formatLabel(value: Double, isValueX: Boolean): String {
                return if (isValueX) {
                    value.toInt().toString()
                } else {
                    super.formatLabel(value, isValueX)
                }
            }
        }

        binding.graph.gridLabelRenderer.numHorizontalLabels = 11 // Ensure labels are spaced correctly

        binding.pesoDeDia.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || event?.keyCode == KeyEvent.KEYCODE_ENTER) {
                val peso = v.text.toString().toDoubleOrNull()
                if (peso != null) {
                    if (firstWeight == null) {
                        firstWeight = peso
                        binding.graph.viewport.setMinY(firstWeight!!)
                    }
                    series.appendData(DataPoint(day.toDouble(), peso), true, 100)
                    day++
                    binding.pesoDeDia.text.clear()

                    // Adjust the viewport to fit the new data
                    binding.graph.viewport.setMaxX(day.toDouble() + 1)
                    binding.graph.viewport.setMinX(0.0) // Ensure the minimum x value stays at 0
                }
                return@OnEditorActionListener true
            }
            false
        })
    }
}