package com.example.waterwell.ui.shared

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.waterwell.data.models.Habit

class HabitSharedViewModel : ViewModel() {
	private val _habits = MutableLiveData<List<Habit>>(emptyList())
	val habits: LiveData<List<Habit>> = _habits

	fun updateHabits(newHabits: List<Habit>) {
		_habits.value = newHabits
	}
}
