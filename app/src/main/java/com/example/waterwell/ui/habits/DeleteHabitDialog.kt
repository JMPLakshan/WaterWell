package com.example.waterwell.ui.habits

import android.app.AlertDialog
import android.content.Context
import com.example.waterwell.data.models.Habit

object DeleteHabitDialog {
    fun show(context: Context, habit: Habit, onConfirm: () -> Unit) {
        AlertDialog.Builder(context)
            .setTitle("Delete Habit")
            .setMessage("Are you sure you want to delete \"${habit.title}\"?")
            .setPositiveButton("Delete") { _, _ -> onConfirm() }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
