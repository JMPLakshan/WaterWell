package com.example.waterwell.data

import android.content.Context
import com.example.waterwell.data.models.Habit
import com.example.waterwell.data.models.MoodEntry
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Prefs(ctx: Context) {
    private val sp = ctx.getSharedPreferences("waterwell_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    private val KEY_HABITS = "habits_json"
    private val KEY_MOODS = "moods_json"
    private val KEY_REMINDER_MIN = "reminder_min"
    private val KEY_LAST_RESET_DAY = "last_reset_day"

    fun loadHabits(): MutableList<Habit> {
        val json = sp.getString(KEY_HABITS, "[]")
        val type = object : TypeToken<List<Habit>>() {}.type
        return gson.fromJson<List<Habit>>(json, type).toMutableList()
    }
    fun saveHabits(list: List<Habit>) {
        sp.edit().putString(KEY_HABITS, gson.toJson(list)).apply()
    }

    fun loadMoods(): MutableList<MoodEntry> {
        val json = sp.getString(KEY_MOODS, "[]")
        val type = object : TypeToken<List<MoodEntry>>() {}.type
        return gson.fromJson<List<MoodEntry>>(json, type).toMutableList()
    }
    fun saveMoods(list: List<MoodEntry>) {
        sp.edit().putString(KEY_MOODS, gson.toJson(list)).apply()
    }

    fun setReminderMinutes(min: Int) = sp.edit().putInt(KEY_REMINDER_MIN, min).apply()
    fun getReminderMinutes(): Int = sp.getInt(KEY_REMINDER_MIN, 120)

    fun setLastResetDay(dayKey: String) = sp.edit().putString(KEY_LAST_RESET_DAY, dayKey).apply()
    fun getLastResetDay(): String? = sp.getString(KEY_LAST_RESET_DAY, null)
}
