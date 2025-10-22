package com.example.waterwell.ui.habits

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import com.example.waterwell.R
import com.example.waterwell.data.models.Habit

object CreateHabitDialog {

    fun show(context: Context, onSave: (Habit) -> Unit) {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_create_habit, null)
        val title = view.findViewById<EditText>(R.id.etTitle)
        val amount = view.findViewById<EditText>(R.id.etAmount)
        val time = view.findViewById<EditText>(R.id.etTime)

        AlertDialog.Builder(context)
            .setTitle("Add New Habit")
            .setView(view)
            .setPositiveButton("Save") { _, _ ->
                val habit = Habit(
                    id = System.currentTimeMillis().toString(),
                    title = title.text.toString(),
                    amount = amount.text.toString(),
                    time = time.text.toString(),
                    isCompletedToday = false
                )
                onSave(habit)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}

