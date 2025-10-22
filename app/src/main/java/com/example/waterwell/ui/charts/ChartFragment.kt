package com.example.waterwell.ui.charts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.waterwell.data.repository.HabitRepository
import com.example.waterwell.databinding.FragmentChartBinding

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
        return binding.root
    }

    private fun setupChart() {
        val habits = repo.getAll()

        // Example: Display names of completed habits
        val completed = habits.filter { it.isCompletedToday }
        val habitNames = completed.map { it.name }

        binding.chartText.text = "Completed habits: ${habitNames.joinToString(", ")}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
