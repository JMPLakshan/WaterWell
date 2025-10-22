package com.example.waterwell.ui.habits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.waterwell.data.models.Habit
import com.example.waterwell.data.repository.HabitRepository
import com.example.waterwell.databinding.FragmentHabitsBinding

class HabitsFragment : Fragment() {

    private var _binding: FragmentHabitsBinding? = null
    private val binding get() = _binding!!
    private lateinit var repo: HabitRepository
    private lateinit var adapter: HabitAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHabitsBinding.inflate(inflater, container, false)
        repo = HabitRepository(requireContext())

        setupRecyclerView()
        loadHabits()

        binding.fabAddHabit.setOnClickListener {
            CreateHabitDialog().show(parentFragmentManager, "createHabit")
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = HabitAdapter(
            habits = mutableListOf(),
            onEditClick = { habit -> EditHabitDialog(habit).show(parentFragmentManager, "editHabit") },
            onDeleteClick = { habit ->
                DeleteHabitDialog(habit).show(parentFragmentManager, "deleteHabit")
            }
        )
        binding.recyclerViewHabits.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewHabits.adapter = adapter
    }

    private fun loadHabits() {
        val habits = repo.getAll()
        adapter.updateData(habits)
    }

    override fun onResume() {
        super.onResume()
        loadHabits()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
