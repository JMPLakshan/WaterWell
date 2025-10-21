package com.example.waterwell.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    private val dayKeyFmt = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
    private val displayFmt = SimpleDateFormat("EEE, MMM d • h:mm a", Locale.getDefault())
    fun dayKey(ts: Long) = dayKeyFmt.format(Date(ts))
    fun todayKey() = dayKey(System.currentTimeMillis())
    fun display(ts: Long) = displayFmt.format(Date(ts))
    fun isSameDay(a: Long, b: Long) = dayKey(a) == dayKey(b)
    fun daysBack(n: Int): Long = System.currentTimeMillis() - n * 24L * 60 * 60 * 1000
}
