package com.example.waterwell.ui.habits

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.waterwell.data.models.Habit
import com.example.waterwell.databinding.ItemHabitBinding

class HabitAdapter(
    private var items: MutableList<Habit>,
    private val onToggle: (id: String, done: Boolean) -> Unit,
    private val onEdit: (habit: Habit) -> Unit,
    private val onDelete: (habit: Habit) -> Unit
) : RecyclerView.Adapter<HabitAdapter.VH>() {

    val current get() = items

    class VH(val b: ItemHabitBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemHabitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]

        // --- BASIC INFO ---
        holder.b.cbDone.setOnCheckedChangeListener(null)
        holder.b.cbDone.isChecked = item.isCompletedToday
        holder.b.tvTitle.text = item.title
        holder.b.tvDesc.text = item.description ?: ""

        // Checkbox toggle logic
        holder.b.cbDone.setOnCheckedChangeListener { _, checked ->
            onToggle(item.id, checked)
        }

        // --- ICON LOGIC ---
        val icon = when {
            item.title.contains("water", ignoreCase = true) -> "💧"
            item.title.contains("drink", ignoreCase = true) -> "💧"
            item.title.contains("exercise", ignoreCase = true) -> "🏃"
            item.title.contains("sleep", ignoreCase = true) -> "😴"
            item.title.contains("mindful", ignoreCase = true) -> "🧘"
            item.title.contains("eat", ignoreCase = true) -> "🥗"
            else -> "📝"
        }
        holder.b.tvType.text = icon

        // --- WATER-SPECIFIC INFO ---
        if (!item.amount.isNullOrEmpty() || !item.time.isNullOrEmpty()) {
            holder.b.waterInfo.visibility = View.VISIBLE

            // Show amount
            holder.b.tvTarget.text = if (!item.amount.isNullOrEmpty())
                "💧 ${item.amount}"
            else
                "💧 No amount set"

            // Show reminder time
            if (!item.time.isNullOrEmpty()) {
                holder.b.tvReminder.text = "⏰ ${item.time}"
                holder.b.tvReminder.visibility = View.VISIBLE
            } else {
                holder.b.tvReminder.visibility = View.GONE
            }

            // Show progress bar (simple binary)
            holder.b.progressBar.visibility = View.VISIBLE
            holder.b.progressBar.progress = if (item.isCompletedToday) 100 else 0
        } else {
            holder.b.waterInfo.visibility = View.GONE
            holder.b.progressBar.visibility = View.GONE
        }

        // --- CLICK ACTIONS ---
        holder.b.root.setOnClickListener {
            onEdit(item)
        }

        // ✅ Delete button
        holder.b.btnDelete.setOnClickListener {
            onDelete(item)
        }
    }

    override fun getItemCount(): Int = items.size

    fun refresh(newItems: MutableList<Habit>) {
        items = newItems
        notifyDataSetChanged()
    }
}
