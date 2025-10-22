package com.example.waterwell.ui.habits

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.waterwell.data.models.Habit
import com.example.waterwell.databinding.ItemHabitBinding

class HabitAdapter(
    private val habits: MutableList<Habit>,
    private val onEditClick: (Habit) -> Unit,
    private val onDeleteClick: (Habit) -> Unit
) : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {

    inner class HabitViewHolder(val binding: ItemHabitBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val binding = ItemHabitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HabitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = habits[position]
        holder.binding.apply {
            habitName.text = habit.name
            habitDescription.text = habit.description
            habitAmount.text = "Amount: ${habit.amount}"
            habitTime.text = "Time: ${habit.time}"
            habitCompleted.isChecked = habit.isCompletedToday

            btnEdit.setOnClickListener { onEditClick(habit) }
            btnDelete.setOnClickListener { onDeleteClick(habit) }
        }
    }

    override fun getItemCount(): Int = habits.size

    fun updateData(newHabits: List<Habit>) {
        habits.clear()
        habits.addAll(newHabits)
        notifyDataSetChanged()
    }
}
