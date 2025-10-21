package com.example.waterwell.util

object EmojiScore {
    fun score(e: String) = when (e) {
        "😞","☹️" -> 1
        "😕" -> 2
        "😐" -> 3
        "🙂" -> 4
        else -> 5 // 😊 😄 😁 etc
    }
}
