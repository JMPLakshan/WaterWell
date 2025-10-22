package com.example.waterwell.ui.habits

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.waterwell.data.models.Habit
import com.example.waterwell.databinding.DialogEditHabitBinding

/**
 * Dialog for editing an existing habit.
 */
object EditHabitDialog {

    fun show(context: Context, habit: Habit, onUpdate: (Habit) -> Unit) {
        val b = DialogEditHabitBinding.inflate(LayoutInflater.from(context))

        // Pre-fill existing data
        b.etTitle.setText(habit.title)
        b.etDesc.setText(habit.description ?: "")
        b.etAmount.setText(habit.amount ?: "")
        b.etTime.setText(habit.time ?: "")

        AlertDialog.Builder(context)
            .setTitle("Edit Habit")
            .setView(b.root)
            .setPositiveButton("Update") { _, _ ->
                val updated = habit.copy(
                    title = b.etTitle.text.toString().trim(),
                    description = b.etDesc.text.toString().trim().ifEmpty { null },
                    amount = b.etAmount.text.toString().trim().ifEmpty { null },
                    time = b.etTime.text.toString().trim().ifEmpty { null }
                )
                onUpdate(updated)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}

