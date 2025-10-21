package com.example.waterwell.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.waterwell.MainActivity
import com.example.waterwell.R
import com.example.waterwell.utils.PreferenceManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class OnboardingActivity : AppCompatActivity() {
    
    private lateinit var viewPager: ViewPager2
    private lateinit var btnNext: MaterialButton
    private lateinit var btnSkip: View
    private lateinit var indicators: LinearLayout
    private lateinit var adapter: OnboardingAdapter
    
    private val onboardingData = listOf(
        OnboardingItem(
            title = "Track Your Hydration",
            description = "Log your daily water intake and monitor your hydration progress with beautiful charts and insights.",
            illustration = R.drawable.ic_water
        ),
        OnboardingItem(
            title = "Smart Reminders",
            description = "Get personalized reminders to drink water throughout the day based on your schedule and preferences.",
            illustration = R.drawable.ic_checklist
        ),
        OnboardingItem(
            title = "Build Healthy Habits",
            description = "Create and maintain healthy hydration habits with our intuitive habit tracking system.",
            illustration = R.drawable.ic_chart
        )
    )
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        
        // Hide action bar
        supportActionBar?.hide()
        
        initViews()
        setupViewPager()
        setupIndicators()
        setupButtons()
    }
    
    private fun initViews() {
        viewPager = findViewById(R.id.viewPager)
        btnNext = findViewById(R.id.btnNext)
        btnSkip = findViewById(R.id.btnSkip)
        indicators = findViewById(R.id.indicators)
    }
    
    private fun setupViewPager() {
        adapter = OnboardingAdapter(onboardingData)
        viewPager.adapter = adapter
        
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateIndicators(position)
                updateButtons(position)
            }
        })
    }
    
    private fun setupIndicators() {
        for (i in onboardingData.indices) {
            val indicator = MaterialCardView(this).apply {
                layoutParams = LinearLayout.LayoutParams(16, 16).apply {
                    setMargins(8, 0, 8, 0)
                }
                radius = 8f
                setCardBackgroundColor(ContextCompat.getColor(this@OnboardingActivity, R.color.indicator_inactive))
            }
            indicators.addView(indicator)
        }
        updateIndicators(0)
    }
    
    private fun updateIndicators(position: Int) {
        for (i in 0 until indicators.childCount) {
            val indicator = indicators.getChildAt(i) as MaterialCardView
            val color = if (i == position) R.color.indicator_active else R.color.indicator_inactive
            indicator.setCardBackgroundColor(ContextCompat.getColor(this, color))
        }
    }
    
    private fun setupButtons() {
        btnNext.setOnClickListener {
            if (viewPager.currentItem < onboardingData.size - 1) {
                viewPager.currentItem++
            } else {
                completeOnboarding()
            }
        }
        
        btnSkip.setOnClickListener {
            completeOnboarding()
        }
        
        updateButtons(0)
    }
    
    private fun updateButtons(position: Int) {
        if (position == onboardingData.size - 1) {
            btnNext.text = "Get Started"
        } else {
            btnNext.text = "Next"
        }
    }
    
    private fun completeOnboarding() {
        val preferenceManager = PreferenceManager(this)
        preferenceManager.setFirstLaunchCompleted()
        preferenceManager.setOnboardingCompleted()
        
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
