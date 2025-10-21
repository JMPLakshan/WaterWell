package com.example.waterwell.ui.habits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.waterwell.data.Prefs
import com.example.waterwell.data.models.Habit
import com.example.waterwell.data.repository.HabitRepository
import com.example.waterwell.databinding.FragmentHabitsBinding
import com.example.waterwell.util.DateUtils
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class HabitsFragment : Fragment() {

    private var _binding: FragmentHabitsBinding? = null
    private val binding get() = _binding!!

    private lateinit var repo: HabitRepository
    private lateinit var adapter: HabitAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHabitsBinding.inflate(inflater, container, false)
        repo = HabitRepository(requireContext())

        val prefs = Prefs(requireContext())
        val today = DateUtils.todayKey()
        if (prefs.getLastResetDay() != today) {
            repo.resetTodayFlags()
            prefs.setLastResetDay(today)
        }

        setupRecycler()
        binding.fabAdd.setOnClickListener { showCreateHabitDialog() }

        if (repo.all().isEmpty()) showQuickSetupDialog()

        updateEmpty()
        return binding.root
    }

    private fun setupRecycler() {
        adapter = HabitAdapter(
            repo.all(),
            onToggle = { id, done ->
                repo.toggle(id, done)
                adapter.refresh(repo.all())
                updateEmpty()
            },
            onEdit = { habit ->
                EditHabitDialog.show(requireContext(), habit) { updated ->
                    repo.update(updated)
                    adapter.refresh(repo.all())
                    updateEmpty()
                }
            },
            onDelete = { habit ->
                repo.remove(habit.id)
                adapter.refresh(repo.all())
                updateEmpty()
                Snackbar.make(binding.root, "Habit deleted", Snackbar.LENGTH_LONG)
                    .setAction("Undo") {
                        repo.add(habit)
                        adapter.refresh(repo.all())
                        updateEmpty()
                    }.show()
            }
        )
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter
    }

    private fun showCreateHabitDialog() {
        CreateHabitDialog.show(requireContext()) { newHabit ->
            repo.add(newHabit)
            adapter.refresh(repo.all())
            updateEmpty()
            Snackbar.make(binding.root, "Habit added", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun showQuickSetupDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Quick Setup")
            .setMessage("Would you like to add some default wellness habits?")
            .setPositiveButton("Add Water Habits") { _, _ -> addDefaultWaterHabits() }
            .setNeutralButton("Add All Habits") { _, _ -> addAllDefaultHabits() }
            .setNegativeButton("Skip", null)
            .show()
    }

    private fun addDefaultWaterHabits() {
        DefaultHabits.getDefaultWaterHabits().forEach { repo.add(it) }
        adapter.refresh(repo.all())
        updateEmpty()
    }

    private fun addAllDefaultHabits() {
        val all = DefaultHabits.getDefaultWaterHabits() + DefaultHabits.getDefaultWellnessHabits()
        all.forEach { repo.add(it) }
        adapter.refresh(repo.all())
        updateEmpty()
    }

    private fun updateEmpty() {
        binding.empty.isVisible = adapter.current.isEmpty()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
