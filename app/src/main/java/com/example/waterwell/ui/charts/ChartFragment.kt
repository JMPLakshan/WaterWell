package com.example.waterwell.ui.charts

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.waterwell.databinding.FragmentChartBinding
import com.example.waterwell.ui.shared.HabitSharedViewModel
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

class ChartFragment : Fragment() {

    private var _binding: FragmentChartBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: HabitSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChartBinding.inflate(inflater, container, false)
        setupChart()
        observeHabits()
        return binding.root
    }

    private fun setupChart() {
        val chart = binding.barChart
        chart.axisRight.isEnabled = false
        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chart.xAxis.textColor = Color.WHITE
        chart.axisLeft.textColor = Color.WHITE
        chart.legend.isEnabled = false
        chart.setDrawGridBackground(false)
        chart.setNoDataText("Check your water habits to see your hydration chart!")
        chart.setNoDataTextColor(Color.LTGRAY)

        val desc = Description()
        desc.text = "Water Intake Tracker"
        desc.textColor = Color.LTGRAY
        chart.description = desc
    }

    private fun observeHabits() {
        sharedViewModel.habits.observe(viewLifecycleOwner) { habits ->
            val waterHabits = habits.filter {
                it.title.contains("water", ignoreCase = true) ||
                        it.title.contains("drink", ignoreCase = true)
            }

            val entries = mutableListOf<BarEntry>()
            val labels = mutableListOf<String>()
            var index = 0f
            var total = 0f

            for (habit in waterHabits) {
                if (habit.isCompletedToday) {
                    val amount = habit.amount?.filter { it.isDigit() }?.toFloatOrNull() ?: 0f
                    if (amount > 0f) {
                        entries.add(BarEntry(index, amount))
                        labels.add(habit.time ?: "")
                        total += amount
                        index++
                    }
                }
            }

            if (entries.isEmpty()) {
                binding.barChart.clear()
                binding.txtTotalWater.text = "No water data yet."
                return@observe
            }

            val dataSet = BarDataSet(entries, "Water (ml)").apply {
                color = Color.parseColor("#4FC3F7")
                valueTextColor = Color.WHITE
                valueTextSize = 14f
            }

            val barData = BarData(dataSet)
            barData.barWidth = 0.5f
            binding.barChart.data = barData
            binding.barChart.invalidate()

            binding.txtTotalWater.text = "💧 Total Water: ${total.toInt()} ml"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
