package com.example.waterwell.ui.habits

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.waterwell.data.models.Habit
import com.example.waterwell.databinding.DialogCreateHabitBinding

object CreateHabitDialog {

    fun show(context: Context, onSave: (Habit) -> Unit) {
        val binding = DialogCreateHabitBinding.inflate(LayoutInflater.from(context))

        val dialog = AlertDialog.Builder(context)
            .setView(binding.root)
            .setCancelable(false)
            .create()

        // 🟩 Save new habit
        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString().trim()
            val amount = binding.etAmount.text.toString().trim()
            val time = binding.etTime.text.toString().trim()

            if (title.isEmpty()) {
                binding.etTitle.error = "Please enter a title"
                return@setOnClickListener
            }

            val newHabit = Habit(
                id = System.currentTimeMillis().toString(),
                title = title,
                description = "Custom added habit",
                amount = amount,
                time = time
            )

            onSave(newHabit)
            dialog.dismiss()
        }

        // 🟥 Cancel / Delete button
        binding.btnCancel.setOnClickListener {
            dialog.dismiss() // simply close without saving
        }

        dialog.show()
    }
}
