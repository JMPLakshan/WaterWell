package com.example.waterwell.data.models

data class MoodEntry(
    val id: String = java.util.UUID.randomUUID().toString(),
    val timestamp: Long = System.currentTimeMillis(),
    val emoji: String,
    val note: String? = null
)
