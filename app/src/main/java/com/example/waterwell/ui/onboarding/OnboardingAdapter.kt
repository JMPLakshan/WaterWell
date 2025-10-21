package com.example.waterwell.ui.onboarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.waterwell.R

class OnboardingAdapter(private val items: List<OnboardingItem>) : 
    RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>() {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_onboarding_screen, parent, false)
        return OnboardingViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        holder.bind(items[position])
    }
    
    override fun getItemCount(): Int = items.size
    
    class OnboardingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val illustration: ImageView = itemView.findViewById(R.id.illustration)
        private val title: TextView = itemView.findViewById(R.id.title)
        private val description: TextView = itemView.findViewById(R.id.description)
        
        fun bind(item: OnboardingItem) {
            illustration.setImageResource(item.illustration)
            title.text = item.title
            description.text = item.description
        }
    }
}
