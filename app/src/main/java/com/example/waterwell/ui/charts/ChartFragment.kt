package com.example.waterwell.ui.charts

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.waterwell.data.repository.HabitRepository
import com.example.waterwell.databinding.FragmentChartBinding
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

class ChartFragment : Fragment() {

    private var _binding: FragmentChartBinding? = null
    private val binding get() = _binding!!
    private lateinit var repo: HabitRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChartBinding.inflate(inflater, container, false)
        repo = HabitRepository(requireContext())

        setupChart()
        updateChart()
        return binding.root
    }

    private fun setupChart() {
        val chart = binding.barChart
        chart.setDrawGridBackground(false)
        chart.axisRight.isEnabled = false
        chart.xAxis.isEnabled = false
        chart.legend.isEnabled = false

        val desc = Description()
        desc.text = "Daily Habit Completion"
        desc.textColor = Color.DKGRAY
        chart.description = desc
    }

    private fun updateChart() {
        val allHabits = repo.all()
        if (allHabits.isEmpty()) {
            binding.barChart.clear()
            return
        }

        val completed = allHabits.count { it.isCompletedToday }
        val total = allHabits.size
        val progress = (completed.toFloat() / total * 100f)

        val entries = mutableListOf<BarEntry>()
        entries.add(BarEntry(1f, progress))

        val dataSet = BarDataSet(entries, "Completion %").apply {
            color = Color.parseColor("#4CAF50")
            valueTextColor = Color.BLACK
            valueTextSize = 14f
        }

        val barData = BarData(dataSet)
        barData.barWidth = 0.5f
        binding.barChart.data = barData
        binding.barChart.invalidate()
    }

    override fun onResume() {
        super.onResume()
        updateChart() // refresh chart every time you open the page
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
