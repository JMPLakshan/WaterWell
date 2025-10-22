package com.example.waterwell.ui.mood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.waterwell.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.CalendarDay

class MoodFragment : Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerHabitsForDay: RecyclerView
    private lateinit var calendarView: MaterialCalendarView
    private lateinit var fabAddMood: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mood, container, false)

        tabLayout = view.findViewById(R.id.tabLayoutMood)
        recyclerView = view.findViewById(R.id.recycler)
        recyclerHabitsForDay = view.findViewById(R.id.recyclerHabitsForDay)
        calendarView = view.findViewById(R.id.calendarView)
        fabAddMood = view.findViewById(R.id.fabAddMood)

        setupTabs()
        setupRecycler()
        setupCalendar()

        return view
    }

    private fun setupTabs() {
        tabLayout.addTab(tabLayout.newTab().setText("List"))
        tabLayout.addTab(tabLayout.newTab().setText("Calendar"))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.text == "List") {
                    recyclerView.visibility = View.VISIBLE
                    calendarView.visibility = View.GONE
                    recyclerHabitsForDay.visibility = View.GONE
                } else {
                    recyclerView.visibility = View.GONE
                    calendarView.visibility = View.VISIBLE
                    recyclerHabitsForDay.visibility = View.VISIBLE
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun setupRecycler() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerHabitsForDay.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupCalendar() {
        calendarView.setOnDateChangedListener { _, date, _ ->
            showHabitsForDate(date)
        }
    }

    private fun showHabitsForDate(date: CalendarDay) {
        // TODO: replace this with your real data logic
        // Example: load habits for the selected date from database
        println("Selected date: ${date.date}")
    }
}
