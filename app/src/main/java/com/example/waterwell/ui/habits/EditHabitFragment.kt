package com.example.waterwell.ui.habits

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.waterwell.data.models.Habit
import com.example.waterwell.data.repository.HabitRepository
import com.example.waterwell.databinding.DialogEditHabitBinding
import kotlinx.parcelize.Parcelize

class EditHabitFragment : Fragment() {
    companion object {
        private const val KEY_HABIT = "habit_arg"

        fun newDialog(ctx: Context, onSave: (title: String, desc: String?, amount: String?, time: String?) -> Unit): AlertDialog {
            val b = DialogEditHabitBinding.inflate(android.view.LayoutInflater.from(ctx))
            return AlertDialog.Builder(ctx)
                .setTitle("Add Habit")
                .setView(b.root)
                .setPositiveButton("Save") { _, _ ->
                    onSave(
                        b.etTitle.text.toString().trim(),
                        b.etDesc.text.toString().trim().ifEmpty { null },
                        b.etAmount.text.toString().trim().ifEmpty { null },
                        b.etTime.text.toString().trim().ifEmpty { null }
                    )
                }
                .setNegativeButton("Cancel", null)
                .create()
        }

        fun bundleFor(habit: Habit) = bundleOf(KEY_HABIT to HabitArg.from(habit))
    }

    @Parcelize
    data class HabitArg(
        val id: String,
        val title: String,
        val description: String?,
        val amount: String?,
        val time: String?,
        val done: Boolean
    ) : Parcelable {
        fun toHabit() = Habit(id = id, title = title, description = description, amount = amount, time = time, isCompletedToday = done)
        companion object { fun from(h: Habit) = HabitArg(h.id, h.title, h.description, h.amount, h.time, h.isCompletedToday) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val arg = requireArguments().getParcelable<HabitArg>(KEY_HABIT) ?: return
        val repo = HabitRepository(requireContext())
        val ctx = requireContext()
        val b = DialogEditHabitBinding.inflate(android.view.LayoutInflater.from(ctx))
        b.etTitle.setText(arg.title)
        b.etDesc.setText(arg.description ?: "")
        b.etAmount.setText(arg.amount ?: "")
        b.etTime.setText(arg.time ?: "")

        AlertDialog.Builder(ctx)
            .setTitle("Edit Habit")
            .setView(b.root)
            .setPositiveButton("Update") { _, _ ->
                val updated = arg.toHabit().copy(
                    title = b.etTitle.text.toString().trim(),
                    description = b.etDesc.text.toString().trim().ifEmpty { null },
                    amount = b.etAmount.text.toString().trim().ifEmpty { null },
                    time = b.etTime.text.toString().trim().ifEmpty { null }
                )
                repo.update(updated)
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
            .setNegativeButton("Cancel") { _, _ -> requireActivity().onBackPressedDispatcher.onBackPressed() }
            .setOnDismissListener { requireActivity().onBackPressedDispatcher.onBackPressed() }
            .show()
    }
}
