package com.example.waterwell.ui.habits

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.waterwell.data.models.Habit
import com.example.waterwell.data.repository.HabitRepository
import com.example.waterwell.databinding.DialogCreateHabitBinding
import com.google.android.material.snackbar.Snackbar

class CreateHabitDialog : DialogFragment() {

    private var _binding: DialogCreateHabitBinding? = null
    private val binding get() = _binding!!
    private lateinit var repo: HabitRepository

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogCreateHabitBinding.inflate(LayoutInflater.from(context))
        repo = HabitRepository(requireContext())

        val builder = AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setTitle("Create New Habit")
            .setPositiveButton("Save") { _, _ ->
                saveHabit()
            }
            .setNegativeButton("Cancel", null)

        return builder.create()
    }

    private fun saveHabit() {
        val name = binding.editHabitName.text.toString().trim()
        val description = binding.editHabitDescription.text.toString().trim()
        val amountText = binding.editHabitAmount.text.toString().trim()
        val time = binding.editHabitTime.text.toString().trim()

        if (name.isEmpty()) {
            Snackbar.make(binding.root, "Please enter a habit name", Snackbar.LENGTH_SHORT).show()
            return
        }

        val amount = amountText.toIntOrNull() ?: 0

        val newHabit = Habit(
            id = System.currentTimeMillis().toString(),
            name = name,
            description = description,
            amount = amount,
            time = time,
            isCompletedToday = false
        )

        repo.add(newHabit)
        Snackbar.make(binding.root, "Habit created successfully", Snackbar.LENGTH_SHORT).show()
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
