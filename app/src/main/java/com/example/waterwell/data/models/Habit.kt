package com.example.waterwell.data.models

data class Habit(
    val id: String = java.util.UUID.randomUUID().toString(),
    var title: String,
    var description: String? = null,
    var amount: String? = null,         // New field
    var time: String? = null,           // New field
    var isCompletedToday: Boolean = false
)
