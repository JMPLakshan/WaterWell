package com.example.waterwell.ui.mood

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.waterwell.R
import java.text.SimpleDateFormat
import java.util.*

enum class MoodType { SAD, NEUTRAL, HAPPY }

data class LocalMoodEntry(
    val timestampMillis: Long,
    val mood: MoodType,
    val note: String?
)

class MoodAdapter : ListAdapter<LocalMoodEntry, MoodAdapter.VH>(Diff()) {

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvEmoji: TextView = itemView.findViewById(R.id.tvEmoji)
        val tvDateTime: TextView = itemView.findViewById(R.id.tvDateTime)
        val tvNote: TextView = itemView.findViewById(R.id.tvNote)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_mood, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        holder.tvEmoji.text = when (item.mood) {
            MoodType.SAD -> "😞"
            MoodType.NEUTRAL -> "🙂"
            MoodType.HAPPY -> "😄"
        }
        val fmt = SimpleDateFormat("MMM d, h:mm a", Locale.getDefault())
        holder.tvDateTime.text = fmt.format(Date(item.timestampMillis))

        if (item.note.isNullOrBlank()) {
            holder.tvNote.visibility = View.GONE
        } else {
            holder.tvNote.visibility = View.VISIBLE
            holder.tvNote.text = item.note
        }
    }

    private class Diff : DiffUtil.ItemCallback<LocalMoodEntry>() {
        override fun areItemsTheSame(oldItem: LocalMoodEntry, newItem: LocalMoodEntry) =
            oldItem.timestampMillis == newItem.timestampMillis && oldItem.mood == newItem.mood

        override fun areContentsTheSame(oldItem: LocalMoodEntry, newItem: LocalMoodEntry) =
            oldItem == newItem
    }
}
