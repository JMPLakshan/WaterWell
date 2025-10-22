package com.example.waterwell.ui.mood

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.waterwell.R
import com.example.waterwell.data.repository.MoodRepository
import com.example.waterwell.data.repository.HabitRepository
import com.example.waterwell.databinding.FragmentMoodBinding
import com.example.waterwell.data.Prefs
import java.text.SimpleDateFormat
import java.util.*

class MoodFragment : Fragment() {

    private var _b: FragmentMoodBinding? = null
    private val b get() = _b!!
    private lateinit var repo: MoodRepository
    private lateinit var adapter: MoodDaySummaryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _b = FragmentMoodBinding.inflate(inflater, container, false)
        repo = MoodRepository(requireContext())
        adapter = MoodDaySummaryAdapter(buildSummaries())
        b.recycler.layoutManager = LinearLayoutManager(requireContext())
        b.recycler.adapter = adapter

        // Tabs: List and Calendar (calendar placeholder for now)
        b.tabLayout.addTab(b.tabLayout.newTab().setText(com.example.waterwell.R.string.mood_tab_list))
        b.tabLayout.addTab(b.tabLayout.newTab().setText(com.example.waterwell.R.string.mood_tab_calendar))
        b.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> { // List
                        b.recycler.visibility = View.VISIBLE
                        // calendar view not implemented; keep container simple
                    }
                    1 -> {
                        // Simple placeholder until a calendar view is added
                        b.recycler.visibility = View.VISIBLE
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        b.fabAddMood.setOnClickListener {
            findNavController().navigate(R.id.action_moodFragment_to_addMoodFragment)
        }
        return b.root
    }

    override fun onDestroyView() { super.onDestroyView(); _b = null }

    private fun buildSummaries(): List<MoodDaySummary> {
        val prefs = Prefs(requireContext())
        val habitRepo = HabitRepository(requireContext())
        val sdf = SimpleDateFormat("MMM dd", Locale.getDefault())
        val results = mutableListOf<MoodDaySummary>()

        // include history (most recent first)
        val history = prefs.loadHabitHistory().asReversed()
        history.forEach { day ->
            val time = Calendar.getInstance()
            // parse dayKey (yyyy-MM-dd) to label
            val parts = day.dayKey.split("-")
            if (parts.size == 3) {
                val cal = Calendar.getInstance()
                cal.set(parts[0].toInt(), parts[1].toInt() - 1, parts[2].toInt(), 0, 0, 0)
                results.add(MoodDaySummary(sdf.format(cal.time), day.completed, day.total))
            }
        }

        // add today on top
        val completedToday = habitRepo.all().count { it.isCompletedToday }
        val total = habitRepo.all().size
        results.add(0, MoodDaySummary("Today", completedToday, total))
        return results
    }
}
