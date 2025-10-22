package com.example.waterwell.ui.habits

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.waterwell.data.models.Habit
import com.example.waterwell.data.repository.HabitRepository
import com.example.waterwell.databinding.FragmentEditHabitBinding
import com.google.android.material.snackbar.Snackbar

class EditHabitFragment : Fragment() {

    private var _binding: FragmentEditHabitBinding? = null
    private val binding get() = _binding!!

    private lateinit var repo: HabitRepository
    private var currentHabit: Habit? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditHabitBinding.inflate(inflater, container, false)
        repo = HabitRepository(requireContext())

        // Safe Parcelable retrieval
        currentHabit = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("habit", Habit::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getParcelable("habit")
        }

        setupUI()
        setupSaveButton()

        return binding.root
    }

    private fun setupUI() {
        currentHabit?.let { habit ->
            binding.editHabitName.setText(habit.name)
            binding.editHabitDescription.setText(habit.description)
        }
    }

    private fun setupSaveButton() {
        binding.btnSave.setOnClickListener {
            val name = binding.editHabitName.text.toString().trim()
            val description = binding.editHabitDescription.text.toString().trim()

            if (name.isEmpty()) {
                Snackbar.make(binding.rootLayout, "Please enter a habit name", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val updatedHabit = currentHabit?.copy(
                name = name,
                description = description
            )

            if (updatedHabit != null) {
                repo.update(updatedHabit)
                Snackbar.make(binding.rootLayout, "Habit updated successfully", Snackbar.LENGTH_SHORT).show()
                requireActivity().onBackPressedDispatcher.onBackPressed()
            } else {
                Snackbar.make(binding.rootLayout, "Error updating habit", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
