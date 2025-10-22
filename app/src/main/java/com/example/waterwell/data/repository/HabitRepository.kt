package com.example.waterwell.data.repository

import android.content.Context
import com.example.waterwell.data.models.Habit

class HabitRepository(context: Context) {

    // Use a mutable list
    private val habitList = mutableListOf<Habit>()

    fun getAll(): List<Habit> = habitList

    fun add(habit: Habit) {
        habitList.add(habit)
    }

    fun update(updatedHabit: Habit) {
        val index = habitList.indexOfFirst { it.id == updatedHabit.id }
        if (index != -1) {
            habitList[index] = updatedHabit
        }
    }

    fun delete(habit: Habit) {
        habitList.removeIf { it.id == habit.id }
    }

    fun clear() {
        habitList.clear()
    }
}
