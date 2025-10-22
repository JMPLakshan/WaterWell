package com.example.waterwell.ui.habits

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.waterwell.data.models.Habit
import com.example.waterwell.data.repository.HabitRepository

class DeleteHabitDialog(private val habit: Habit) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val repo = HabitRepository(requireContext())

        return AlertDialog.Builder(requireContext())
            .setTitle("Delete Habit")
            .setMessage("Are you sure you want to delete '${habit.name}'?")
            .setPositiveButton("Delete") { _, _ ->
                repo.delete(habit)
            }
            .setNegativeButton("Cancel", null)
            .create()
    }
}
