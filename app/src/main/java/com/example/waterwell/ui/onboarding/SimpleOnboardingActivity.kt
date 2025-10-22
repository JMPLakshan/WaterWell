package com.example.waterwell.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import android.widget.LinearLayout
import android.widget.Button
import com.example.waterwell.R
import com.example.waterwell.ui.MainActivity

class SimpleOnboardingActivity : AppCompatActivity() {

    private lateinit var onboardingViewPager: ViewPager2
    private lateinit var adapter: OnboardingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_simple)

        onboardingViewPager = findViewById(R.id.onboardingViewPager)

        adapter = OnboardingAdapter(
            listOf(
                OnboardingItem("Track Hydration", "Monitor your water intake.", R.drawable.onboarding1),
                OnboardingItem("Stay Motivated", "Get reminders throughout the day.", R.drawable.onboarding2),
                OnboardingItem("Healthy Lifestyle", "Build great daily habits.", R.drawable.onboarding3)
            )
        )

        onboardingViewPager.adapter = adapter

        findViewById<Button>(R.id.buttonNext).setOnClickListener {
            if (onboardingViewPager.currentItem + 1 < adapter.itemCount) {
                onboardingViewPager.currentItem++
            } else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }
}
