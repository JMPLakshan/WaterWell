package com.example.waterwell.ui.mood

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.waterwell.data.models.MoodEntry
import com.example.waterwell.databinding.ItemMoodBinding
import com.example.waterwell.util.DateUtils

class MoodAdapter(private var items: MutableList<MoodEntry>) :
    RecyclerView.Adapter<MoodAdapter.VH>() {

    class VH(val b: ItemMoodBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val b = ItemMoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(b)
    }

    override fun onBindViewHolder(h: VH, position: Int) {
        val item = items[position]
        h.b.tvEmoji.text = item.emoji
        h.b.tvTime.text = DateUtils.display(item.timestamp)
        h.b.tvNote.text = item.note ?: ""
    }

    override fun getItemCount() = items.size
}
