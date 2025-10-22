package com.example.waterwell.ui.mood

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.waterwell.data.Prefs
import com.example.waterwell.databinding.ItemMoodDaySummaryBinding

data class MoodDaySummary(
    val dateLabel: String,
    val completed: Int,
    val total: Int
)

class MoodDaySummaryAdapter(private var items: List<MoodDaySummary>) :
    RecyclerView.Adapter<MoodDaySummaryAdapter.VH>() {

    class VH(val b: ItemMoodDaySummaryBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val b = ItemMoodDaySummaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(b)
    }

    override fun onBindViewHolder(h: VH, position: Int) {
        val item = items[position]
        val ratio = if (item.total == 0) 0f else item.completed.toFloat() / item.total
        val emoji = when {
            ratio >= 0.67f -> "😄"
            ratio >= 0.34f -> "🙂"
            else -> "😞"
        }
        h.b.tvEmoji.text = emoji
        h.b.tvDate.text = item.dateLabel
        h.b.tvSummary.text = "${item.completed}/${item.total} habits completed"
    }

    override fun getItemCount(): Int = items.size

    fun update(newItems: List<MoodDaySummary>) {
        items = newItems
        notifyDataSetChanged()
    }
}


