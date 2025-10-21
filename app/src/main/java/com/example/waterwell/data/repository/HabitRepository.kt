package com.example.waterwell.data.repository

import android.content.Context
import com.example.waterwell.data.Prefs
import com.example.waterwell.data.models.Habit

class HabitRepository(ctx: Context) {
    private val prefs = Prefs(ctx)
    private val habits: MutableList<Habit> = prefs.loadHabits()

    fun all(): MutableList<Habit> = habits
    fun add(h: Habit) { habits.add(0, h); prefs.saveHabits(habits) }
    fun update(h: Habit) {
        val i = habits.indexOfFirst { it.id == h.id }
        if (i >= 0) habits[i] = h
        prefs.saveHabits(habits)
    }
    fun remove(id: String) { habits.removeAll { it.id == id }; prefs.saveHabits(habits) }
    fun toggle(id: String, done: Boolean) { habits.find { it.id == id }?.apply {
        isCompletedToday = done
    }; prefs.saveHabits(habits) }

    fun resetTodayFlags() { habits.forEach { it.isCompletedToday = false }; prefs.saveHabits(habits) }
}
