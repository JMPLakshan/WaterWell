package com.example.waterwell.ui.habits

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.waterwell.data.models.Habit
import com.example.waterwell.data.repository.HabitRepository
import com.example.waterwell.databinding.DialogEditHabitBinding
import com.google.android.material.snackbar.Snackbar

class EditHabitDialog(private val habit: Habit) : DialogFragment() {

    private var _binding: DialogEditHabitBinding? = null
    private val binding get() = _binding!!
    private lateinit var repo: HabitRepository

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogEditHabitBinding.inflate(LayoutInflater.from(context))
        repo = HabitRepository(requireContext())

        // Populate fields with the existing habit data
        binding.editHabitName.setText(habit.name)
        binding.editHabitDescription.setText(habit.description)
        binding.editHabitAmount.setText(habit.amount.toString())
        binding.editHabitTime.setText(habit.time)

        val builder = AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setTitle("Edit Habit")
            .setPositiveButton("Save") { _, _ ->
                updateHabit()
            }
            .setNegativeButton("Cancel", null)

        return builder.create()
    }

    private fun updateHabit() {
        val name = binding.editHabitName.text.toString().trim()
        val description = binding.editHabitDescription.text.toString().trim()
        val amountText = binding.editHabitAmount.text.toString().trim()
        val time = binding.editHabitTime.text.toString().trim()

        if (name.isEmpty()) {
            Snackbar.make(binding.root, "Please enter a name", Snackbar.LENGTH_SHORT).show()
            return
        }

        val amount = amountText.toIntOrNull() ?: 0

        val updated = habit.copy(
            name = name,
            description = description,
            amount = amount,
            time = time
        )

        repo.update(updated)
        Snackbar.make(binding.root, "Habit updated successfully", Snackbar.LENGTH_SHORT).show()
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
