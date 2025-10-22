package com.example.waterwell.ui.habits

import com.example.waterwell.data.models.Habit

/**
 * Provides pre-made default habits for quick setup.
 */
object DefaultHabits {

    fun getDefaultWaterHabits(): List<Habit> {
        return listOf(
            Habit(
                title = "Drink Water (Morning)",
                description = "Start your day with hydration",
                amount = "250 ml",
                time = "8:00 AM"
            ),
            Habit(
                title = "Drink Water (Afternoon)",
                description = "Stay hydrated after lunch",
                amount = "300 ml",
                time = "2:00 PM"
            ),
            Habit(
                title = "Drink Water (Evening)",
                description = "Keep up water intake before dinner",
                amount = "200 ml",
                time = "6:00 PM"
            )
        )
    }

    fun getDefaultWellnessHabits(): List<Habit> {
        return listOf(
            Habit(
                title = "Stretch for 5 minutes",
                description = "Loosen muscles after sitting",
                time = "9:00 AM"
            ),
            Habit(
                title = "Take a short walk",
                description = "Clear your mind and move around",
                time = "5:00 PM"
            ),
            Habit(
                title = "Sleep early",
                description = "Go to bed before 11:00 PM"
            )
        )
    }
}

