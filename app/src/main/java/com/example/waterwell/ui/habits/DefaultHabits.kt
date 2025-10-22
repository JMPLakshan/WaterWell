package com.example.waterwell.ui.habits

import com.example.waterwell.data.models.Habit

object DefaultHabits {
    fun getDefaults(): List<Habit> = listOf(
        Habit(
            id = System.currentTimeMillis().toString(),
            name = "Drink Water",
            description = "Drink at least 8 glasses of water daily",
            amount = 8,
            time = "Daily"
        ),
        Habit(
            id = System.currentTimeMillis().toString(),
            name = "Exercise",
            description = "30 minutes of exercise",
            amount = 30,
            time = "Morning"
        ),
        Habit(
            id = System.currentTimeMillis().toString(),
            name = "Read",
            description = "Read 10 pages of a book",
            amount = 10,
            time = "Evening"
        ),
        Habit(
            id = System.currentTimeMillis().toString(),
            name = "Meditate",
            description = "Meditate for 15 minutes",
            amount = 15,
            time = "Morning"
        ),
        Habit(
            id = System.currentTimeMillis().toString(),
            name = "Sleep Early",
            description = "Go to bed before 11 PM",
            amount = 1,
            time = "Night"
        )
    )
}
