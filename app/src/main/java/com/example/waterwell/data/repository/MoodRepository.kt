package com.example.waterwell.data.repository

import android.content.Context
import com.example.waterwell.data.Prefs
import com.example.waterwell.data.models.MoodEntry

class MoodRepository(ctx: Context) {
    private val prefs = Prefs(ctx)
    private val moods: MutableList<MoodEntry> = prefs.loadMoods()

    fun all(): MutableList<MoodEntry> = moods
    fun add(e: MoodEntry) { moods.add(0, e); prefs.saveMoods(moods) }
    fun remove(id: String) { moods.removeAll { it.id == id }; prefs.saveMoods(moods) }
}
