package com.example.waterwell.ui.habits

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.waterwell.data.models.Habit
import com.example.waterwell.databinding.ItemHabitBinding

class HabitAdapter(
    private var items: MutableList<Habit>,
    val onToggle: (id: String, done: Boolean) -> Unit,
    val onEdit: (habit: Habit) -> Unit
) : RecyclerView.Adapter<HabitAdapter.VH>() {

    val current get() = items

    class VH(val b: ItemHabitBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val b = ItemHabitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(b)
    }

    override fun onBindViewHolder(h: VH, position: Int) {
        val item = items[position]
        h.b.cbDone.setOnCheckedChangeListener(null)
        h.b.cbDone.isChecked = item.isCompletedToday
        h.b.tvTitle.text = item.title
        h.b.tvDesc.text = item.description ?: ""
        h.b.cbDone.setOnCheckedChangeListener { _, checked -> onToggle(item.id, checked) }
        h.b.root.setOnClickListener { onEdit(item) }
    }

    override fun getItemCount(): Int = items.size
    fun refresh(newItems: MutableList<Habit>) { items = newItems; notifyDataSetChanged() }
}
