package com.example.waterwell.ui.onboarding

data class OnboardingItem(
    val title: String,
    val description: String,
    val imageResId: Int   // ✅ renamed from "illustration"
)
